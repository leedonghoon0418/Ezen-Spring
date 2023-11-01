<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board List</title>
</head>
<body>
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/nav.jsp" />


<!-- 검색 라인 -->
<div class="container-fluid">
	<form class="d-flex" role="search" method="get" action="/board/list">
		<div>
		<c:set value="${ph.pgvo.type }" var="typed"></c:set>
		<select class="form-select" aria-label="Default select example" name="type">
		  <option ${typed eq null? 'selected' : ''}>Choose...</option>
		  <option value="t" ${typed eq "t"? 'selected' : ''}>Title</option>
		  <option value="w" ${typed eq "w"? 'selected' : ''}>Writer</option>
		  <option value="c" ${typed eq "c"? 'selected' : ''}>Content</option>
		  <option value="tw" ${typed eq "tw"? 'selected' : ''}>Title or Writer</option>
		  <option value="tc" ${typed eq "tc"? 'selected' : ''}>Title or Content</option>
		  <option value="cw" ${typed eq "cw"? 'selected' : ''}>Writer or Content</option>
		  <option value="twc" ${typed eq "twc"? 'selected' : ''}>Title or Writer or Content</option>
		</select>
	
	    <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search" name="keyword" value="${ph.pgvo.keyword }">
		<input type="hidden" name="pageNo" value="1">
		<input type="hidden" name="qty" value="${ph.pgvo.qty }">
		
	    <button class="btn btn-primary position-relative" type="submit">Search
		    <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
	    		${ph.totalCount }
	    		<span class="visually-hidden">unread messages</span>
	  		</span>
  		</button>
	    </div>
	</form>
</div>

<table class="table table-hover">
<thead>
	<tr>
		<th>#</th>
		<th>Title</th>
		<th>Writer</th>
		<th>Reg-Date</th>
		<th>Read-Count</th>
		<th>Comment-Count</th>
		<th>File-Count</th>
	</tr>
</thead>
<tbody>
<c:forEach items="${list }" var="bvo">
	<tr>
		<td><a href="/board/detail?bno=${bvo.bno }">${bvo.bno }</a></td>
		<td>${bvo.title }</td>
		<td>${bvo.writer }</td>
		<td>${bvo.regAt }</td>
		<td>${bvo.readCount }</td>
		<td>${bvo.cmtQty }</td>
		<td>${bvo.hasFile }</td>
	</tr>
</c:forEach>
</tbody>
</table>

<!-- 페이징 라인 -->

<nav aria-label="Page navigation example">
  <ul class="pagination">
  
	  <!-- 이전  -->
	<li class="page-item ${(ph.prev eq false) ? 'disabled' : '' }">
		<a class="page-link" href="/board/list?pageNo=${ph.startPage-1 }&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}" aria-label="Previous">
		    <span aria-hidden="true">&laquo;</span>
		</a>
	</li>
    
	<c:forEach begin="${ph.startPage }" end="${ph.endPage }" var="i">	
    	<li class="page-item"><a class="page-link" href="/board/list?pageNo=${i }&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}">${i }</a></li>  
	</c:forEach>

	    <!-- 다음  -->
	    <li class="page-item${(ph.next eq false) ? 'disabled' : '' }">
	      <a class="page-link" href="/board/list?pageNo=${ph.endPage+1 }&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}" aria-label="Next">
	        <span aria-hidden="true">&raquo;</span>
	      </a>
	    </li>
  </ul>
</nav>






<jsp:include page="../common/footer.jsp" />
</body>
</html>