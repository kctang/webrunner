<%--@elvariable id="field" type="net.big2.webrunner.core.jpa.crud.Field"--%>
<c:set var="field" value="${fieldMap[name]}" />
<div class="control-group">
    <label class="control-label" for="${name}">${field.label}</label>
    <div class="controls" style="padding-top: 5px">
        ${entity[name]}
    </div>
</div>