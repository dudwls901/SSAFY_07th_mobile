<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%-- User를 사용하기 위해 dto 패키지를 import 한다. --%>
<%@ page import="com.ssafy.ws.dto.*"%>
<%-- jstl의 core 라이브러리를 사용하기 위해 taglib를 이용한다. --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%--절대경로를 쉽게 사용하기 위해 session에 root를 key로 컨텍스트 루트 경로를 저장해둔다. --%>
<c:set value="${pageContext.request.contextPath }" scope="session" var="root"></c:set>

<%-- bootstrap 관련 코드를 등록한다. --%>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>

<%-- 프로젝트에서 정의한 CSS를 참조할 때 resources 경로를 참조한다. --%>
<link rel="stylesheet" href="${root }/resources/css/common.css">
<style>
</style>
<div class="container">
	<img src="${root }/resources/img/ssafy_logo.png" id="ssafy_logo">
	<h1 class="display-4 text-center">도서 관리</h1>
	<br>
	<div class="text-right">
		<%-- session에서 loginUser를 가져와서 존재 여부에 따라 로그인 폼 또는 사용자 정보를 출력한다. --%>
		<%--로그인 정보가 없을 경우는 로그인을 위한 폼을 제공한다. --%>
		<c:if test="${empty loginUser }">
			<form method="post" action="${root }/login">
				<div class="form-group">
					<input type="text" name="id" placeholder="아이디">
					<input type="password" name="pass" placeholder="비밀번호">
					<input type="submit" class="btn btn-primary" value="로그인">
				</div>
			</form>
		</c:if>
		<%--로그인 정보가 있는 경우는 사용자 이름과 로그아웃을 위한 링크를 생성한다.--%>
		<c:if test="${!empty loginUser }">
			<div>
				${loginUser.name }님 반갑습니다.
				<a href="${root }/logout">로그아웃</a>
			</div>
		</c:if>
	</div>
</div>
<hr>
<script>
	// request 영역에 msg라는 이름의 attribute가 있다면 화면에 alert으로 출력한다.
	let msg = "${msg}";
	if (msg) {
		alert(msg)
	}
</script>