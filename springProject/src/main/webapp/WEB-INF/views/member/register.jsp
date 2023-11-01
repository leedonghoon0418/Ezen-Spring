<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Member Rigister</title>
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
<!-- email pwd nickName -->

<form action="/member/register" method="post">
  	<div class="position-absolute top-50 start-50 translate-middle">
  	<h1 style="text-align: center" class="mainText">SIGN UP PAGE</h1>
		<div class="mb-3">
		   <label for="e" class="form-label mainText">E-Mail</label>
		   <input type="text" class="form-control" id="e" name="email" placeholder="E-Mail">
		</div>
		
		<div class="mb-3">
		   <label for="p" class="form-label mainText">Password</label>
		   <input type="text" class="form-control" id="p" name="pwd" placeholder="PassWord">
		</div>
		
		<div class="mb-3">
		   <label for="n" class="form-label mainText">Nick-Name</label>
		   <input class="form-control" id="n" name="nickName" placeholder="Nick-Name" />
		</div>

		<button type="submit" class="btn btn-secondary btn-lg" id="regBtn">Sign Up</button>
	</div>	
</form>


<jsp:include page="../common/footer.jsp"></jsp:include>

</body>
</html>