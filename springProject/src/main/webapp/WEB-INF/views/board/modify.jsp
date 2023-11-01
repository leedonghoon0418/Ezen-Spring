<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modify</title>
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

<form action="/board/modify" method="post" enctype="multipart/form-data">
	<div class="position-absolute top-50 start-50 translate-middle">
	<input type="hidden" value="${bvo.bno}" name="bno">
		<div class="mb-3">
			 <label for="exampleFormControlInput1" class="form-label">Title</label>
			 <input type="text" class="form-control" id="exampleFormControlInput1" name="title" value="${bvo.title }"/>
		</div>
			
		<div class="mb-3">
			 <label for="exampleFormControlInput1" class="form-label">Writer</label>
			 <input type="text" class="form-control" id="exampleFormControlInput1" name="writer" value="${bvo.writer }"/>
		</div>
			
		<div class="mb-3">
			 <label for="exampleFormControlTextarea1" class="form-label">Content</label>
			 <textarea class="form-control" id="exampleFormControlTextarea1" name="content" rows="3">${bvo.content }</textarea>
		</div>
		
		<div class="mb-3">
			<c:forEach items="${flist }" var="fvo">
		<ul>
			<c:choose>
				<c:when test="${fvo.fileType > 0}">
						<li data-uuid="${fvo.uuid }">
							<img alt="그림없음" src="/upload/${fn: replace(fvo.saveDir,'\\','/')}/${fvo.uuid }_th_${fvo.fileName}">
							<span class="badge text-bg-primary">${fvo.fileSize } Byte</span>
							<button type="button" id="fileRemoveBtn" class="btn btn-outline-danger">X</button>
						</li>
								
				</c:when>
			</c:choose>
		</ul>
	</c:forEach>
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
		
		
		<button type="submit" class="btn btn-secondary btn-lg" id="regBtn">Modify</button>
	</div>	
</form>

<script type="text/javascript" src="/resources/js/boardModify.js"></script>
<script type="text/javascript" src="/resources/js/boardRegister.js"></script>
<jsp:include page="../common/footer.jsp" />

</body>
</html>