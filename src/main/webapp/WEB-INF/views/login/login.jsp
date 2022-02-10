<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
	<c:param name="content">
		<%------ ログイン認証結果をflushで表示 ------%>
		<c:if test="${hasError}">
			<div id="flush_error">社員番号かパスワードが間違っています。</div>
		</c:if>
		<c:if test="${flush != null}">
			<div id="flush_success"><c:out value="${flush}"/></div>
		</c:if>

		<%------ ログイン認証用入力部 ------%>
		<div id="center">
		    <h2>ログイン</h2>
		    <c:out value="${login_user}"/>
	        <form method="POST" action="<c:url value='/login' />">
	            <label for="id">社員番号：</label>
	            <input type="text" name="id" value="${id}" />
	            <br /><br />

	            <label for="password">パスワード：</label>
	            <input type="password" name="password" />
	            <br /><br />

	            <input type="hidden" name="_token" value="${_token}" />
	            <button type="submit">ログイン</button>
	        </form>
        </div>
	</c:param>
</c:import>