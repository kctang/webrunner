
<c:choose>
    <c:when test="${fieldMap[name].type eq 'JSP'}">
        <%--custom jsp page--%>
        <c:set var="name" value="${name}" scope="request"/>
        <c:import url="${fieldMap[name].id}"/>
    </c:when>
    <c:when test="${fieldMap[name].type eq 'TEXT'}">
        <%@include file="fields/field-text.jsp" %>
    </c:when>
    <c:when test="${fieldMap[name].type eq 'TEXTAREA'}">
        <%@include file="fields/field-textarea.jsp" %>
    </c:when>
    <c:when test="${fieldMap[name].type eq 'CHECKBOX'}">
        <%@include file="fields/field-checkbox.jsp" %>
    </c:when>
    <c:when test="${fieldMap[name].type eq 'CHECKBOXES'}">
        <%@include file="fields/field-checkboxes.jsp" %>
    </c:when>
    <c:when test="${fieldMap[name].type eq 'RADIO'}">
        <%@include file="fields/field-radio.jsp" %>
    </c:when>
    <c:when test="${fieldMap[name].type eq 'RADIOS'}">
        <%@include file="fields/field-radios.jsp" %>
    </c:when>
    <c:when test="${fieldMap[name].type eq 'PASSWORD'}">
        <%@include file="fields/field-password.jsp" %>
    </c:when>
    <c:when test="${fieldMap[name].type eq 'SELECT'}">
        <%@include file="fields/field-select.jsp" %>
    </c:when>
    <c:when test="${fieldMap[name].type eq 'HIDDEN'}">
        <form:hidden path="${name}" id="${name}"/>
    </c:when>
    <c:when test="${fieldMap[name].type eq 'FILE'}">
        <%@include file="fields/field-file.jsp" %>
    </c:when>
    <c:otherwise>
        <%@include file="fields/field-unknown.jsp" %>
    </c:otherwise>
</c:choose>