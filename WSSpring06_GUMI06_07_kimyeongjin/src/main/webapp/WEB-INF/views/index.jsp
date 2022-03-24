<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SSAFY 도서 관리</title>
<style>
</style>
</head>
<body>
	<%-- header.jsp를 include해서 재사용하기 --%>
	<%@ include file="/WEB-INF/views/include/header.jsp"%>
	<div class="container">
		<ul>
			<li>
				<a href="${root }/regist">도서 등록</a>
			<li>
				<a href="${root }/list">도서 목록</a>
		</ul>
	</div>
</body>
</html>