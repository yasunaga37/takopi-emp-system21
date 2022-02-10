package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.License;

public class LicenseDAO {

	public List<License> selectAll() {
		List<License> list = new ArrayList<License>();
		String sql = "SELECT * FROM m_license";
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			ResultSet res = pstmt.executeQuery();
			while (res.next()) {
				License license = new License();
				license.setCode(res.getString("license_code"));
				license.setName(res.getString("license_name"));
				list.add(license);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
//		System.out.println("list.size():" + list.size());
		return list;
	}

}
