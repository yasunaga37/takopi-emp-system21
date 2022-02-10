<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
	<c:param name="content">
		<div id="center">
			<h2>従業員　新規登録ページ</h2>

			<form method="post" action='<c:url value='/employees'/>'>
				<c:import url="_form.jsp"/>
				<button name="action" value="create" onclick="return confirm('登録してもよろしいですか？')">投稿</button>
			</form>

			<p><a class="font_big" href="<c:url value='/employees' />">一覧に戻る</a></p>
		</div>
	</c:param>
</c:import>