<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!-- DTO를 참조하기 위해서 import 처리가 필요하다. -->
<%@ page import="com.ssafy.ws.dto.*"%>
<%-- jstl의 core 라이브러리를 사용하기 위해 taglib를 이용한다. --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>도서 등록 결과</title>
<style>
td:nth-child(3) {
	width: 150px;
}

#cover {
	width: 150px;
}
</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/header.jsp"%>
	<div class="container">
		<h1>도서 등록 결과</h1>
		<%-- c:if 태그를 이용해 request 영역에 book이 있다면 내용을 출력한다. --%>
		<c:if test="${!empty book }">
			<table class="table">
				<thead>
					<tr>
						<th>항목</th>
						<th colspan="2">내용</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>도서번호</td>
						<td>${book.isbn }</td>
						<td rowspan="5"><img id="cover"></td>
					</tr>
					<tr>
						<td>도서명</td>
						<td>${book.title }</td>
					</tr>
					<tr>
						<td>저자</td>
						<td>${book.author }</td>
					</tr>
					<tr>
						<td>가격</td>
						<td>${book.price }</td>
					</tr>
					<tr>
						<td>이미지</td>
						<td>${book.img }</td>
					</tr>
					<tr>
						<td>설명</td>
						<td colspan="2">${book.content }</td>
					</tr>
				</tbody>
			</table>
		</c:if>
		<%-- c:if 태그를 이용해 request 영역에 book이 없다면 정보가 없음을 출력한다. --%>
		<c:if test="${empty book }">
			<p>등록된 도서가 없습니다.</p>
		</c:if>
		<!-- 다시 도서를 등록할 수 있는 링크를 제공한다. -->
		<a href="${root }/regist" class="stretched-link">[추가등록]</a>
		<a href="${root }/list" class="stretched-link">[목록보기]</a>
	</div>
</body>
</html>