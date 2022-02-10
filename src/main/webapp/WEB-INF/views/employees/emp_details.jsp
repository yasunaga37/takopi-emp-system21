<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="left_side">
	<c:choose>
		<c:when test="${employee != null}">
			<table id="employee_show">
				<tbody>
					<tr>
						<th>従業員コード</th>
						<td><c:out value="${employee.code}" /></td>
					</tr>
					<tr>
						<th>氏名</th>
						<td><c:out value="${employee.lastName}${employee.firstName}" /></td>
					</tr>
					<tr>
						<th>カナ</th>
						<td><c:out value="${employee.lastKanaName}${employee.firstKanaName}" /></td>
					</tr>
					<tr>
						<th>性別</th>
						<td><c:out value="${employee.gender}" /></td>
					</tr>
					<tr>
						<th>生年月日</th>
						<td><fmt:formatDate value="${employee.birthDay}" pattern="yyyy年MM月dd日" /></td>
					</tr>
					<tr>
						<th>部署</th>
						<td><c:out value="${employee.section.name}" /></td>
					</tr>
					<tr>
						<th>入社日</th>
						<td><fmt:formatDate value="${employee.hireDate}" pattern="yyyy年MM月dd日" /></td>
					</tr>
					<tr>
						<th>更新日</th>
<%-- 							<td><c:out value="${employee.update}" /></td> --%>
						<td><fmt:formatDate value="${employee.update}" pattern="yyyy年MM月dd日 hh時mm分ss秒" /></td>
					</tr>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<h2>お探しのデータは見つかりませんでした。</h2>
		</c:otherwise>
	</c:choose>
	<p>
		<a class="font_big" href="<c:url value='/employees' />?action=edit&emp_code=<c:out value="${employee.code}" />">編集</a>
	</p>
</div>