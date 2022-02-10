<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>従業員管理システム</title>
    <link rel="stylesheet" href="<c:url value='/css/reset.css' />">
    <link rel="stylesheet" href="<c:url value='/css/style.css' />">
    <link rel="stylesheet" href="<c:url value='/css/show.css' />">
        <link rel="stylesheet" href="<c:url value='/css/button.css' />">
</head>
<body>
	<div id="wrapper">
		<div id="header">
		   <table  id="table_header">
		   	<tr>
		   		<td><h1>従業員管理システム</h1></td>
		   		<c:if test="${sessionScope.login_user != null}">
		   			<td><br /><c:out value="${sessionScope.login_user}" />&nbsp;さん</td>
		   			<td><br /><a href="<c:url value='/employees' />">従業員管理</a></td>
		   			<td><br /><a href="<c:url value='/login' />?action=logout">ログアウト</a></td>
		   		</c:if>
		   	</tr>
		   </table>
	   </div>

	   <div id="content">${param.content}</div>
	   <br><hr>
	    <div id="footer">by Tako PikaPika.
	    	<marquee><img src="<c:url value='/img/tako1.png' />"></marquee>
	    </div>
	</div>
</body>
</html>