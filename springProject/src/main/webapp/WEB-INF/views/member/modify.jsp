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


<form action="/member/modify" method="post">
	<table class="table table-hover">
		<tr>
			<th>E-Mail</th>
			<td><input type="text" name="email" value="${mvo.email }" readonly="readonly"></td>
		</tr>
		<tr>
			<th>Password</th>
			<td><input type="text" name="pwd" placeholder="변경할 비밀번호를 입력해주세요"></td>
		</tr>
		<tr>
			<th>Nick-Name</th>
			<td><input type="text" name="nickName" value="${mvo.nickName }"></td>
		</tr>
		<tr>
			<th>Reg-Date</th>
			<td>${mvo.regAt }</td>
		</tr>
		<tr>
			<th>Last-Login</th>
			<td>${mvo.lastLogin }</td>
		</tr>	
	</table>
	<button type="submit">수정하기</button>
</form>
<jsp:include page="../common/footer.jsp" />

</body>
<script type="text/javascript">
 let rePwd =`<c:out value="${rePwd}" />`;
 if(rePwd == "1"){
	 alert("비밀번호를 입력해주세요");
 }
</script>
</html>