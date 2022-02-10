package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.Employee;
import model.entity.Section;

public class EmployeeDAO {

	/**
	 * 従業員一覧の取得
	 * @return List<Employee> 全従業員リスト(従業員コード順)
	 */
	public List<Employee> selectAll() {
		List<Employee> list = new ArrayList<>();
		String sql = "SELECT" +
				"  e.employee_code," +
				"  e.last_name," +
				"  e.first_name," +
				"  e.last_kana_name," +
				"  e.first_kana_name," +
				"  e.gender," +
				"  e.birth_day," +
				"  s.section_name," +
				"  e.hire_day," +
				"  e.update_datetime, " +
				"  e.delete_flag " +
				"FROM" +
				"  m_employee e " +
				"INNER JOIN m_section s " +
				"ON e.section_code = s.section_code "  +
				"ORDER BY e.employee_code";
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			ResultSet res = pstmt.executeQuery();
			while (res.next()) {
				Employee e = setEmployeeData(res);
				Section section = new Section();
				section.setName(res.getString("section_name"));
				e.setSection(section);
				list.add(e);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 新規従業員レコードの追加
	 * @param employee
	 * @return int 追加件数
	 */
	public int insertNewEmployee(Employee employee) {
		int count = 0;
		String sql = "INSERT INTO m_employee " +
						  "(employee_code,last_name,first_name,last_kana_name,first_kana_name,gender,birth_day,section_code,hire_day) " +
				          "VALUES(?,?,?,?,?,?,?,?,?)";
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			setNewdata(pstmt, employee);
			count = pstmt.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 従業員コード検索
	 * @param code 従業員コード
	 * @return employee
	 */
	public Employee searchByCode(String code) {
		Employee employee = null;
		String sql = "SELECT * FROM m_employee WHERE employee_code=?";
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setString(1, code);
			ResultSet res = pstmt.executeQuery();
			while (res.next()) {
				employee = setEmployeeData(res);
				SectionDAO sDao = new SectionDAO();
				Section section = sDao.searchByCode(res.getString("section_code"));
				employee.setSection(section);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return employee;
	}

	/**
	 * 従業員レコードの編集
	 * @param employee
	 * @return int 編集件数
	 */
	public int update(Employee employee) {
		int count = 0;
		String sql = "UPDATE m_employee SET employee_code=?, last_name=?, first_name=?, " +
						   "last_kana_name=?, first_kana_name=?, gender=?, birth_day=?, section_code=?, hire_day=? " +
				           "WHERE employee_code=?";
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			setNewdata(pstmt, employee);
			pstmt.setString(10, employee.getCode());
			count = pstmt.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 従業員情報を論理削除する
	 * @param employeeCode
	 * @return int 削除件数
	 */
	public int delete(String employeeCode) {
		int count = 0;
		String sql = "UPDATE m_employee SET delete_flag=1 WHERE employee_code=?";
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setString(1, employeeCode);
			count = pstmt.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return count;
	}


	/**
	 * 部署以外の従業員情報を設定する
	 * 部署に関する設定は別途必要
	 * @param res ResultSet
	 * @return Employee
	 * @throws SQLException
	 */
	private Employee setEmployeeData(ResultSet res) throws SQLException {
		Employee employee = new Employee();
		employee.setCode(res.getString("employee_code"));
		employee.setLastName(res.getString("last_name"));
		employee.setFirstName(res.getString("first_name"));
		employee.setLastKanaName(res.getString("last_kana_name"));
		employee.setFirstKanaName(res.getString("first_kana_name"));
		employee.setGender(res.getInt("gender"));
		employee.setBirthDay(res.getDate("birth_day"));
		employee.setHireDate(res.getDate("hire_day"));
		employee.setUpdate(res.getTimestamp("update_datetime"));
		employee.setDeleteFlag(res.getBoolean("delete_flag"));
//		System.out.println(employee.getCode() + " " + employee.getDeleteFlag());
		return employee;
	}

	/**
	 * PreparedStatementに対して従業員情報を引き渡す
	 * 呼出側はこのメソッド実行直後にクエリ実行する必要がある
	 * @param pstmt PreparedStatement
	 * @param employee
	 * @throws SQLException
	 */
	private void setNewdata(PreparedStatement pstmt, Employee employee) throws SQLException {
//		System.out.print(employee.getCode() + " " + employee.getLastName() + " " + employee.getFirstName() + " " + employee.getLastKanaName() + " " + employee.getFirstKanaName());
//		System.out.println(" " + employee.getGenderNumber() + " " + employee.getBirthDay() + " " + employee.getSection().getCode() + " " + employee.getHireDate());
		pstmt.setString(1, employee.getCode());
		pstmt.setString(2, employee.getLastName());
		pstmt.setString(3, employee.getFirstName());
		pstmt.setString(4, employee.getLastKanaName());
		pstmt.setString(5, employee.getFirstKanaName());
		pstmt.setInt(6, employee.getGenderNumber());
		pstmt.setDate(7, employee.getBirthDay());
		pstmt.setString(8, employee.getSection().getCode());
		pstmt.setDate(9, employee.getHireDate());
	}

	/**
	 * 従業員コードの最大値をint型の数値として取得する
	 * @return int 従業員コードの最大値
	 */
	public int getMaxCode() {
		int maxCode = 0;
		String sql = "SELECT max(employee_code) FROM m_employee";
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			ResultSet res = pstmt.executeQuery();
			if (res.next()) {
				maxCode = Integer.parseInt(res.getString("max(employee_code)").substring(1));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return maxCode;
	}

}
