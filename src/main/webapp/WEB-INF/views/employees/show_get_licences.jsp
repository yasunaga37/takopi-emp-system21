<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="right_side">
<%-- 	<c:out value="${get_license.size()}" /> --%>
	<c:choose>
		<c:when test="${get_license.size() != 0}">
			<table id="license_show">
				<thead>
					<tr>
						<th>資格コード</th><th>資格名</th><th>取得年月日</th><th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="glicense" items="${get_license}">
						<tr>
							<td class="short"><c:out value="${glicense.licenseCode}" /></td>
							<td class="short"><c:out value="${glicense.licenseName}" /></td>
							<td class="long"><fmt:formatDate value="${glicense.getLicenseDate}" pattern="yyyy年MM月dd日" /></td>
							<td class="shortshort">
								<a href="<c:url value='/employees' />?action=del_glicense&emp_code=${employee.code}&glicense_code=${glicense.licenseCode}"
								     onclick="return confirm('メッセージ内容')">削除</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

		</c:when>
		<c:otherwise>
			<h2>現在、保有資格はありません。</h2>
		</c:otherwise>
	</c:choose>
	<br />

	<form action="<c:url value='/employees' />" method="POST">
		<select name="get_new_license">
			<c:forEach var="license" items="${licenses}">
				<option value="<c:out value='${license.code}' />"><c:out value="${license.name}" /></option>
			</c:forEach>
		</select>&nbsp;
		<input type="date" name="date" required="required">&nbsp;
		<input type="hidden" name="emp_code" value="<c:out value="${employee.code}" />">
		<button class="add_license_button" name="action" value="add_license">保有資格の追加</button>
	</form>
</div>