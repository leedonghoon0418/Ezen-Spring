<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Member Detail</title>
</head>
<body>
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/nav.jsp" />



<table class="table table-hover">
	<tr>
		<th>E-Mail</th>
		<td>${mvo.email }</td>
	</tr>
	<tr>
		<th>Password</th>
		<td>${mvo.pwd }</td>
	</tr>
	<tr>
		<th>Nick-Name</th>
		<td>${mvo.nickName }</td>
	</tr>
	<tr>
		<th>Reg-Date</th>
		<td>${mvo.regAt }</td>
	</tr>
	<tr>
		<th>Last-Login</th>
		<td>${mvo.lastLogin }</td>
	</tr>
	<tr>
		<th>ROLE</th>
		<td>${mvo.authList.auth}</td>
	</tr>

</table>
<div class="btn-group" role="group" aria-label="Basic outlined example">
  <a href="/member/modify?email=${mvo.email }"><button type="button" class="btn btn-outline-primary"> MODIFY </button></a>
  <a href="/member/remove?email=${mvo.email }"><button type="button" class="btn btn-outline-primary"> REMOVE </button></a>
</div>

<jsp:include page="../common/footer.jsp" />
</body>
</html>