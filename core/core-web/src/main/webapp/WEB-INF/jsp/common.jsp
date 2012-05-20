<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<%@ taglib tagdir="/WEB-INF/tags/wr" prefix="wr" %>

<%@ taglib tagdir="/WEB-INF/tags/bootstrap" prefix="bootstrap" %>
<%@ taglib tagdir="/WEB-INF/tags/crud" prefix="crud" %>

<c:if test="${pageContext.servletContext.servletContextName eq '/'}">
    <c:set var="context" value=""/>
</c:if>
<c:if test="${pageContext.servletContext.servletContextName ne '/'}">
    <c:set var="context" value="${pageContext.servletContext.servletContextName}"/>
</c:if>