<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 관리</title>
<style>
label {
	display: inline-block;
	width: 80px;
}

textarea {
	width: 100%;
}
</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/header.jsp"%>
	<div class="container">
		<h1>게시물 수정</h1>
		<form method="post" action="${ root }/modify">
			<!-- front-controller pattern에서 요청을 구분하기 위한 parameter -->
			<div class="form-group">
				<label for="title">글 제목</label>
				<input type="text" id="title" name="title" class="form-control-sm" value="${ book.title }">
			</div>
			<div class="form-group">
				<label for="content">글 내용</label>
			</div>
			<div class="form-group">
				<textarea id="content" name="content" class="form-control" rows="3">${ book.content }</textarea>
			</div>
			<div class="form-group">
				<label for="writer">글 작성자</label>
				<input type="text" id="writer" name="writer" class="form-control-sm" value="${ book.writer }">
			</div>
			<input type="hidden" name="no" value="${ book.isbn }">
			<div class="form-group">
				<input type="submit" value="수정">
				<input type="reset" value="취소">
			</div>
		</form>
		<br> <a href="${ root }/list">글 목록</a>
	</div>
</body>
</html>