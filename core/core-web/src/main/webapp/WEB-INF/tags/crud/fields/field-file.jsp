<c:set var="result" value="${requestScope['org.springframework.validation.BindingResult.entity']}"/>
<c:set var="hasFieldError"><spring:eval expression="result.hasFieldErrors(field.id)"/></c:set>
<c:if test="${hasFieldError}">
    <c:set var="fieldClass">error</c:set>
    <c:set var="fieldErrorMessage"><spring:eval expression="result.getFieldError(field.id).defaultMessage"/></c:set>
</c:if>
<div class="control-group ${fieldClass}">
    <label class="control-label" for="${field.id}">${field.label}</label>

    <div class="controls">
        <div>
            <input type="file" name="${field.id}" id="${field.id}">
            <c:if test="${hasFieldError}">
                <p class="help-inline">
                <span>
                        ${fieldErrorMessage}
                </span>
                </p>
            </c:if>
            <c:if test="${entity[field.uploadedProperty]}">
                <a class="btn" href="${context}/obj/${cs.slug}/${entity.id}/delete/${field.id}">Delete</a>
                <ul class="thumbnails">
                    <li class="span3" style="margin-bottom: 0">
                        <a class="thumbnail" href="${storageBaseUrl}${field.id}/${entity.id}">
                            <img src="${storageBaseUrl}${field.id}/${entity.id}">
                        </a>
                    </li>
                </ul>
            </c:if>
        </div>
    </div>
</div>
