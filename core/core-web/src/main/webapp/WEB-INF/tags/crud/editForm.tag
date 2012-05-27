<%--@elvariable id="cs" type="net.big2.webrunner.core.web.crud.CrudSupport"--%>
<%@ include file="/WEB-INF/jsp/common.jsp" %>
<%@tag trimDirectiveWhitespaces="true" %>

<div class="page-header">
    <h1>Edit - ${cs.name}</h1>
    <span class="label pull-right">Modified: ${entity.modifiedDate}</span>
</div>

<form:form enctype="multipart/form-data" method="post" modelAttribute="entity" cssClass="form-horizontal"
           id="${cs.slug}">
    <fieldset>
            <%--display global errors--%>
        <spring:hasBindErrors name="entity">
            <div class="control-group error">
                <div class="controls">
                    <p class="help-block">
                        <form:errors/>
                    </p>
                </div>
            </div>
        </spring:hasBindErrors>

            <%--display form fields--%>
        <c:forEach var="name" items="${cs.editFieldList}">
            <%--extract field name from field list (remove "parameters")--%>
            <spring:eval var="name" expression="T(net.big2.webrunner.core.jpa.crud.Field).extractFieldName(name)"/>
            <c:choose>
                <c:when test="${cs.editFieldMap[name].viewOnly}">
                    <%--view mode--%>
                    <%@include file="fields/view.jsp" %>
                </c:when>
                <c:when test="${!cs.editFieldMap[name].viewOnly}">
                    <%--edit mode--%>
                    <c:set var="fieldMap" value="${cs.editFieldMap}"/>
                    <c:set var="field" value="${fieldMap[name]}"/>
                    <%@include file="fieldChooser.jsp" %>
                </c:when>
            </c:choose>
        </c:forEach>
    </fieldset>

    <div class="form-actions">
        <button id="save" class="btn btn-primary" type="submit">Save</button>
        <button id="cancel" class="btn">Cancel</button>
        <button id="delete" class="btn">Delete</button>
    </div>
</form:form>

<form:form method="delete" id="${cs.slug}Delete">

</form:form>

<script src="${context}/core/webrunner.js" type="text/javascript"></script>
<script type="text/javascript">
    var editForm;
    $(function () {
        var baseUrl = '${context}/obj/${cs.slug}';
        editForm = new CrudSupportForm('${cs.slug}', baseUrl);
    });
</script>