<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 入力ミスがあった場合のエラーメッセージ --%>
<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>
    </div>
</c:if>



<table id="input_form" align="center">
	<tr>
		<th>社員コード</th>
		<td>
			<c:out value="${employee.code}" />
			<input type="hidden" name="code" value="${employee.code}">
		</td>
	</tr>
	<%--------------- 入力部 -----------------%>
	<tr>
		<th>氏名</th>
		<td><input type="text" name="lastName" value="${employee.lastName}" required="required" />
		        <input type="text" name="firstName" value="${employee.firstName}" required="required" /></td>
	</tr>
	<tr>
		<th>カナ</th>
		<td><input type="text" name="lastKanaName" value="${employee.lastKanaName}" />
		        <input type="text" name="firstKanaName" value="${employee.firstKanaName}" /></td>
	</tr>
	<tr>
		<th>性別</th>
		<td>
			<input type="radio" name="gender" value="1"<c:if test="${employee.gender == '男'}"> checked</c:if> required="required">男&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="radio" name="gender" value="2"<c:if test="${employee.gender =='女'}"> checked</c:if>>女&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="radio" name="gender" value="0"<c:if test="${employee.gender =='不明'}"> checked</c:if>>不明
		</td>
	</tr>
	<tr>
		<th>生年月日</th>
		<td><input type="date" name="birthDay" value="${employee.birthDay}" /></td>
	</tr>
	<tr>
		<th>部署</th>
		<td>
		<select name="section">
			<c:forEach var="section" items="${sectionList}">
				<option value='<c:out value="${section.code}" />'
					<c:if test="${section.code == employee.section.code}"> selected</c:if>><c:out value="${section.name}" />
				</option>
			</c:forEach>
		</select>
		</td>
	</tr>
	<tr>
		<th><label for="hireDate">入社日</label></th>
		<td><input type="date" name="hireDate" value="${employee.hireDate}" /></td>
	</tr>
</table>
<br />
<input type="hidden" name="_token" value="${_token}" />
