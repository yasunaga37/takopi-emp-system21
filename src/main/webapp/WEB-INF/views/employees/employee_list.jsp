<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app.jsp">
	<c:param name="content">
		<c:if test="${flush != null}">
			<div id="flush_success"><c:out value="${flush}"/></div>
		</c:if>

		<table id="employee_list_title">
			<tr>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td><h2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;従業員　一覧&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</h2></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>	<a class="font_big"  href="<c:url value='/employees?action=new' />">新規従業員登録</a></td>
			</tr>
		</table>

		<table id="employee_list">
			<thead>
				<tr>
                    <th class="short">コード</th>
                    <th class="long">氏名</th>
                    <th class="long">カナ</th>
                    <th class="short">部署</th>
                    <th class="short">性別</th>
                    <th class="long">生年月日</th>
                    <th class="short"></th>
                </tr>
			</thead>
			<tbody>
				<c:forEach var="employee" items="${emp_list}" varStatus="status">
					<c:if test="${employee.deleteFlag == 0}">
						<tr class="row${status.count % 2}">
							<td class="short"><c:out value="${employee.code}"/></td>
							<td class="long"><c:out value="${employee.lastName}"/>&nbsp;
							        <c:out value="${employee.firstName}"/></td>
							<td class="long"><c:out value="${employee.lastKanaName}"/>&nbsp;
													   <c:out value="${employee.firstKanaName}"/></td>
							<td class="short"><c:out value="${employee.section.name}"/></td>
							<td class="short"><c:out value="${employee.gender}"/></td>
							<td class="long"><fmt:formatDate value="${employee.birthDay}" pattern="yyyy年MM月dd日" /></td>
							<td class="short"><a href="<c:url value='/employees' />?action=show&emp_code=<c:out value="${employee.code}"/>">詳細</a></td>
						</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table>

	</c:param>
</c:import>