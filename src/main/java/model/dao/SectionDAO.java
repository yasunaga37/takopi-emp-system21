package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.Section;

public class SectionDAO {

	public List<Section> selectAll() {
		List<Section> list = new ArrayList<Section>();
		String sql = "SELECT * FROM m_section";
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			ResultSet res = pstmt.executeQuery();
			while (res.next()) {
				Section s = new Section();
				s.setCode(res.getString("section_code"));
				s.setName(res.getString("section_name"));
				s.setUpdate(res.getTimestamp("update_datetime"));
				list.add(s);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
//		System.out.println("list.size():" + list.size());
		return list;
	}

	public Section searchByCode(String code) {
		String sql = "SELECT * FROM m_section WHERE section_code=?";
		Section section = new Section();
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setString(1, code);
			ResultSet res = pstmt.executeQuery();
			while (res.next()) {
				section.setCode(res.getString("section_code"));
				section.setName(res.getString("section_name"));
				section.setUpdate(res.getTimestamp("update_datetime"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return section;
	}
}
