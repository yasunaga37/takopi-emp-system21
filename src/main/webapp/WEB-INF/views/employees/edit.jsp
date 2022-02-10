<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">

	<c:param name="content">
		<div id="center">
			<c:choose>
				<c:when test="${employee != null}">
					<h2>社員コード：<c:out value="${employee.code}" />の従業員情報　編集ページ</h2>
					<form method="POST" action="<c:url value='/employees' />">
						<br>
	                    <c:import url="_form.jsp" />
	                    <button name="action" value="update" onclick="return confirm('登録してもよろしいですか？')">編集</button>
	                </form>
				</c:when>
				<c:otherwise>
					<h2>お探しのデータは見つかりませんでした。</h2>
				</c:otherwise>
			</c:choose>
			<br />
			<p>
				<a class="font_big" href="<c:url value='/employees' />">一覧に戻る</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a class="font_big" href="<c:url value='/employees' />?action=show&emp_code=${employee.code}" >従業員詳細に戻る</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a class="font_big" href="<c:url value='/employees' />?action=delete&code=<c:out value="${employee.code}" /> " onclick="return confirm('本当に削除してもよろしいですか？')">この従業員情報を削除する</a>
			</p>
		</div>
	</c:param>

</c:import>