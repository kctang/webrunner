<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib tagdir="/WEB-INF/tags/wr" prefix="wr" %>

<c:if test="${pageContext.servletContext.servletContextName eq '/'}">
    <c:set var="context" value=""/>
</c:if>
<c:if test="${pageContext.servletContext.servletContextName ne '/'}">
    <c:set var="context" value="${pageContext.servletContext.servletContextName}"/>
</c:if>