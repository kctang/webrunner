<%--@elvariable id="field" type="net.big2.webrunner.core.jpa.crud.Field"--%>
<c:set var="field" value="${fieldMap[name]}" />
<div class="control-group">
    <label class="control-label" for="${name}">${field.label}</label>
    <div class="controls">
        <form:textarea path="${name}" id="${name}" cols="${field.cols}" rows="${field.rows}"/>
        <p class="help-inline">
            <form:errors path="${name}"/>
        </p>
    </div>
</div>