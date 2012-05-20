<%--@elvariable id="field" type="net.big2.webrunner.core.jpa.crud.Field"--%>
<c:set var="field" value="${fieldMap[name]}" />
<c:set var="hasFieldError"><form:errors path="${name}"/></c:set>
<c:set var="fieldClass"><c:if test="${not empty hasFieldError}">error</c:if></c:set>
<div class="control-group ${fieldClass}">
    <label class="control-label" for="${name}">${field.label}</label>
    <div class="controls">
        <form:input path="${name}" id="${name}" size="${field.size}"/>
        <p class="help-inline">
            <form:errors path="${name}"/>
        </p>
    </div>
</div>