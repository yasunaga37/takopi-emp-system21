package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.GetLicense;

public class GetLicenseDAO {

	/**
	 * 従業員コードによる保有資格時検索
	 * @param code 従業員コード
	 * @return 保有資格リスト(取得日順にソート)
	 */
	public List<GetLicense> searchByEmployeeCode(String code) {
		List<GetLicense> list = new ArrayList<GetLicense>();
		String sql = "SELECT " +
				"  e.last_name,  " +
				"  e.first_name,  " +
				"  l.license_code,  " +
				"  l.license_name,  " +
				"  t.get_license_date  " +
				"FROM " +
				"  m_employee e  " +
				"  INNER JOIN t_get_license t  " +
				"    ON e.employee_code = t.employee_code  " +
				"  INNER JOIN m_license l  " +
				"    ON t.license_code = l.license_code  " +
				"WHERE " +
				"  e.employee_code=? " +
				"ORDER BY " +
				"  t.get_license_date";;
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setString(1, code);
			ResultSet res = pstmt.executeQuery();
			while (res.next()) {
				GetLicense getLicense = new GetLicense();
				getLicense.setLicenseCode(res.getString("l.license_code"));
				getLicense.setLicenseName(res.getString("l.license_name"));
				getLicense.setEmployeeLname(res.getString("e.last_name"));
				getLicense.setEmployeeFname(res.getString("e.first_name"));
				getLicense.setGetLicenseDate(res.getDate("t.get_license_date"));
				list.add(getLicense);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
//		System.out.println("list.size():" + list.size() + " ");
//		for (GetLicense g : list) {
//			System.out.print(g.getEmployeeLname() + g.getEmployeeFname() + " " + g.getLicenseName());
//			System.out.println(" " + g.getGetLicenseDate());
//		}
		return list;
	}

	/**
	 * 保有資格の追加
	 * @param licenseCode 資格コード
	 * @param employeeCode 従業員コード
	 * @param date 資格取得日
	 * @return 追加件数
	 */
	public int insert(String licenseCode, String employeeCode, java.sql.Date date) {
		int count = 0;
		String sql = "INSERT INTO t_get_license VALUES(?,?,?)";
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setString(1, licenseCode);
			pstmt.setString(2, employeeCode);
			pstmt.setDate(3, date);
			count = pstmt.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
//		System.out.println("count:" + count);
		return count;
	}

	/**
	 * 保有資格情報の削除
	 * @param licenseCode 資格コード
	 * @param employeeCode 従業員コード
	 * @return 削除件数
	 */
	public int delete(String licenseCode, String employeeCode) {
		int count = 0;
		String sql = "DELETE FROM t_get_license WHERE license_code=? AND employee_code=?";
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setString(1, licenseCode);
			pstmt.setString(2, employeeCode);
			count = pstmt.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
}
