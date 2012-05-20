<%--@elvariable id="field" type="net.big2.webrunner.core.jpa.crud.Field"--%>
<script type="text/javascript">
    $(function () {
        $("#${name}").chosen();
    });
</script>
<div class="control-group">
    <label class="control-label" for="${name}">${field.label}</label>
    <div class="controls">
        <form:select path="${name}" id="${name}" items="${requestScope.itemMap[name]}" multiple="${field.multiple}"/>
        <p class="help-inline">
            <form:errors path="${name}"/>
        </p>
    </div>
</div>
