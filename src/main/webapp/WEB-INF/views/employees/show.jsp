<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
	<c:param name="content">

		<%------ 編集結果をflushで表示 ------%>
		<c:if test="${flush != null}">
			<div id="flush_success"><c:out value="${flush}"/></div>
		</c:if>
		<c:if test="${hasError}">
			<div id="flush_error"><c:out value="${hasError}"/></div>
		</c:if>

		<div id="center">
			<%------ 従業員詳細ページのタイトル ------%>
			<c:if test="${employee != null}">
				<h2>コード：${employee.code} の従業員情報　詳細ページ</h2>
			</c:if>

			<%------ 従業員詳細ページの内容 ------%>
			<div class="flex_test-box">
				<div class="flex_test-item"><c:import url="emp_details.jsp" /></div>
				<div class="flex_test-item"><c:import url="show_get_licences.jsp" /></div>
			</div>
			<a class="font_big" href="<c:url value='/employees' />">一覧に戻る</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
	</c:param>
</c:import>