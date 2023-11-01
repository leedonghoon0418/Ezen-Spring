<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/nav.jsp" />


<table class="table table-hover">
<thead>
	<tr>
		<th>E-Mail</th>
		<th>Password</th>
		<th>Nick-Name</th>
		<th>Reg-Date</th>
		<th>Last-Login</th>
		<th>ROLE</th>
	</tr>
</thead>
<tbody>
<c:forEach items="${mvo }" var="mvo">
	<tr>
		<td>${mvo.email }</td>
		<td>${mvo.pwd }</td>
		<td>${mvo.nickName }</td>
		<td>${mvo.regAt }</td>
		<td>${mvo.lastLogin }</td>
		<td>
			<c:forEach items="${mvo.authList }" var="list">
					 ${list.auth }
			</c:forEach>
		</td>
	</tr>
</c:forEach>
</tbody>
</table>



<jsp:include page="../common/footer.jsp" />
</body>
</html>