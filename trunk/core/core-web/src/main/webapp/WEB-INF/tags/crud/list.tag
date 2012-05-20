<%--@elvariable id="cs" type="net.big2.webrunner.core.web.crud.CrudSupport"--%>
<%--@elvariable id="page" type="org.springframework.data.domain.Page"--%>
<%@ include file="/WEB-INF/jsp/common.jsp" %>
<%@tag trimDirectiveWhitespaces="true" %>

<c:set var="baseUrl" value="/${context}obj/${cs.slug}"/>
<c:if test="${!empty flashMessage}">
    <div class="alert alert-success">
        <a class="close" data-dismiss="alert" href="#">x</a>
            ${flashMessage}
    </div>
</c:if>

<div class="page-header">
    <h1>${cs.name}</h1>
</div>

<form class="form-inline" id="${cs.slug}">
    <div style="margin-bottom: 1em">
        <input id="q" type="text" value="${param.q}">
        <a href="#" onclick="return list.doFind()" class="btn">Find</a>
        <a href="#" onclick="return list.doNew()" class="btn">New</a>
        <%--<a href="#" onclick="return list.doDelete()" class="btn">Delete</a>--%>
    </div>

    <c:if test="${page.totalPages eq 0}">
        <div class="well well-small">No data.</div>
    </c:if>
    <c:if test="${page.totalPages ne 0}">
        <%@include file="listPaging.jsp" %>

        <%--list data--%>
        <table class="table table-striped">
            <thead>
            <tr>
                    <%--<th style="width: 1px">#</th>--%>
                    <%--<th style="width: 1px">&nbsp;</th>--%>
                <c:forEach var="field" items="${cs.listFieldList}" varStatus="status">
                    <c:set var="sortLink" value="${baseUrl}?"/>
                    <c:if test="${!empty param.q}">
                        <c:set var="sortLink" value="${sortLink}q=${param.q}&"/>
                    </c:if>
                    <c:if test="${!empty param['page.size']}">
                        <c:set var="sortLink" value="${sortLink}page.size=${param['page.size']}&"/>
                    </c:if>
                    <th>
                        <spring:eval expression="cs.formatListHeader(field)"/>
                        <a href="${sortLink}page.sort=${field}&page.sort.dir=asc"><i class="icon-chevron-up"></i></a>
                        <a href="${sortLink}page.sort=${field}&page.sort.dir=desc"><i class="icon-chevron-down"></i></a>
                    </th>
                </c:forEach>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="entity" varStatus="status" items="${page.content}">
                <tr>
                        <%--<td>${page.size * (page.number) + (status.index+1)}</td>--%>
                        <%--<td><input type="checkbox" name="cb" value="${entity.id}"/></td>--%>
                    <c:forEach var="field" items="${cs.listFieldList}" varStatus="status">
                        <spring:eval var="value" expression="cs.formatListValue(field, entity[field], entity)"/>
                        <c:if test="${empty value}">
                            <c:set var="value">(empty)</c:set>
                        </c:if>
                        <c:if test="${cs.listFieldLinkIndex eq status.index}">
                            <td>
                                <a href="${baseUrl}/${entity.id}">${value}</a>
                            </td>
                        </c:if>
                        <c:if test="${cs.listFieldLinkIndex ne status.index}">
                            <td>${value}</td>
                        </c:if>
                    </c:forEach>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <%@include file="listPaging.jsp" %>
    </c:if>
</form>

<script src="${context}/core/webrunner.js" type="text/javascript"></script>
<script type="text/javascript">
    var list;
    $(function () {
        list = new CrudSupportList('${cs.slug}', '${baseUrl}');
    });
</script>
