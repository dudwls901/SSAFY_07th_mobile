<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SSAFY 도서 수정</title>
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
		<h1>도서 수정</h1>
		<form method="post" action="${ root }/modify">
			<!-- front-controller pattern에서 요청을 구분하기 위한 parameter -->
			<div class="form-group">
				<label for="isbn">도서번호</label>
				<input type="text" id="isbn" name="isbn" class="form-control-sm" value="${ book.isbn }" disabled>
			</div>
			<div class="form-group">
				<label for="title">도서명</label>
				<input type="text" id="title" name="title" class="form-control-sm" value="${ book.title }">
			</div>
			<div class="form-group">
				<label for="author">저자</label>
				<input type="text" id="author" name="author" class="form-control-sm" value="${ book.author }">
			</div>
			<div class="form-group">
				<label for="price">가격</label>
				<input type="number" id="price" name="price" class="form-control-sm" value="${ book.price }">
			</div>
			<div class="form-group">
				<label for="img">이미지</label>
				<input type="file" id="img" name="img" class="form-control-file-sm" value="${ book.img }" >
			</div>
			<div class="form-group">
				<label for="content">설명</label>
			</div>
			<div class="form-group">
				<textarea id="content" name="content" class="form-control" rows="3">${ book.content }</textarea>
			</div>
			<input type="hidden" name="isbn" value="${ book.isbn }">
			<div class="form-group">
				<input type="submit" value="수정">
				<input type="reset" value="취소">
			</div>
		</form>
		<br> <a href="${root }/list">도서 목록</a>
	</div>
</body>
</html>