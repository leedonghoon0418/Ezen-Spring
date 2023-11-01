<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register Page</title>
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
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/nav.jsp" />

<sec:authentication property="principal.mvo.email" var="authEmail"/>

  <form action="/board/register" method="post" enctype="multipart/form-data">
  	<div class="position-absolute top-50 start-50 translate-middle">
		<div class="mb-3">
		   <label for="t" class="form-label">Title</label>
		   <input type="text" class="form-control" id="t" name="title" placeholder="title">
		</div>
		
		<div class="mb-3">
		   <label for="w" class="form-label">Writer</label>
		   <input type="text" class="form-control" id="w" name="writer" value="${authEmail }" readonly="readonly">
		</div>
		
		<div class="mb-3">
		   <label for="c" class="form-label">Content</label>
		   <textarea class="form-control" id="c" name="content" rows="3"></textarea>
		</div>
		
		<!-- file upload -->
		<div class="mb-3">
			
		   <input type="file" class="form-control" name="files" id="files" style="display:none;" multiple="multiple">
		   <!-- input button 트리거용도의 버튼  -->
		   <button type="button" id="trigger" class="btn btn-outline-success">File Upload</button>
		</div>
		
		<div class="mb-3" id="fileZone">
			<!-- 첨부파일 표시  -->
		</div>
		
		<button type="submit" class="btn btn-secondary btn-lg" id="regBtn">Register</button>
	</div>	

  </form>
  <script type="text/javascript" src="/resources/js/boardRegister.js"></script>
  
  <jsp:include page="../common/footer.jsp" />  
</body>
</html>