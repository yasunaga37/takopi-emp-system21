<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
	<c:param name="content">
 		<%------ ログイン認証結果をflushで表示 ------%>
		<c:if test="${flush != null}">
			<div id="flush_success"><c:out value="${flush}"/></div>
		</c:if>
		<%------ 初回ログイン時はログイン認証画面へ誘導 ------%>
		<c:if test="${login_user == null}">
			<div id="center"><h3><a href="login">ログイン認証へ</a></h3></div>
		</c:if>
	</c:param>
</c:import>