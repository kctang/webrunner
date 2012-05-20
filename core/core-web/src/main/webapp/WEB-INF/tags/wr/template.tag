<%@ include file="/WEB-INF/jsp/common.jsp" %>
<%@tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="title" %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="debug" %>
<!DOCTYPE html>
<html>
<head>
    <title><c:out value="${title}" default="[Template]"/></title>
    <jsp:invoke fragment="head"/>
</head>
<body>

<jsp:doBody/>

<c:if test="${debug}">
    <jsp:include page="/WEB-INF/jsp/debug-simple.jsp"/>
</c:if>
</body>
</html>