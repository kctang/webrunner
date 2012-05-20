<%--@elvariable id="field" type="net.big2.webrunner.core.jpa.crud.Field"--%>
<c:set var="field" value="${fieldMap[name]}" />
<div class="control-group">
    <label class="control-label" for="${name}">${field.label}</label>
    <div class="controls">
        <form:checkbox path="${name}" id="${name}" value="true"/>
        <%--// TODO: checkbox value in field--%>
        <p class="help-inline">
            <form:errors path="${name}"/>
        </p>
    </div>
</div>