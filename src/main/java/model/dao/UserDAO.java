package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

	public boolean loginCheck(String id, String password) {
		boolean loginFlg = false;
		String sql = "SELECT * FROM m_user WHERE user_id=? AND password=?";
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			ResultSet res = pstmt.executeQuery();
			while (res.next()) {
				loginFlg = true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return loginFlg;
	}

}
