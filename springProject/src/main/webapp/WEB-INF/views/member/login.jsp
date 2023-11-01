<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
.translate-middle{
	width: 500px;
	height: 500px;
}
.btn-lg{
	width: 100%;
}

</style>
<body>
	
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/nav.jsp"></jsp:include>

<!-- 로그아웃,로그인 무조건 method = post  -->

<form action="/member/login" method="post">

  	<div class="position-absolute top-50 start-50 translate-middle">
  	
  	<h1 style="text-align: center" class="mainText">로그인</h1>
		<div class="mb-3">
		   <label for="e" class="form-label mainText">ID</label>
		   <input type="text" class="form-control" id="e" name="email" placeholder="E-Mail">
		</div>
		
		<div class="mb-3">
		   <label for="p" class="form-label mainText">Password</label>
		   <input type="text" class="form-control" id="p" name="pwd" placeholder="PassWord">
		</div>
		
		<c:if test="${not empty param.errMsg }">
			<div class="text-danger mb-3">
			   <c:choose>
			   	<c:when test="${param.errMsg eq 'Bad credentials'}">
			   		<c:set var="errText" value="이메일 & 비밀번호가 일치하지 않습니다." />
			   	</c:when>	
			   	   	
			   	<c:otherwise>
			   		<c:set var="errText" value="관리자에게 문의해주세요." />
			   	</c:otherwise>
			   	
			   </c:choose>
			   ${errText }
			</div>
		</c:if>
		
		<button type="submit" class="btn btn-secondary btn-lg" id="regBtn">LOGIN</button>
	</div>
</form>


<jsp:include page="../common/footer.jsp"></jsp:include>
	
</body>
</html>