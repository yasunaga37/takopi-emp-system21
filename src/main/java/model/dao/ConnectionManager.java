/*
 * WebApp_05_sp01_MVC
 * model.dao.ConnectionManager.java
 */
package model.dao;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * コネクションマネージャ
 * @author emBex Education
 */
public class ConnectionManager {

//	/**
//	 * データベースURL
//	 */
//	private final static String URL = "jdbc:mysql://localhost:3306/em21_emp_sys_db?useSSL=false";
//
//	/**
//	 * ユーザ
//	 */
//	private final static String USER = "root";
//
//	/**
//	 * パスワード
//	 */
//	private final static String PASSWORD = "root";

	/**
	 * データベースへの接続を取得して返します。
	 *
	 * @return コネクション
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws URISyntaxException 
	 */
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		
        // heroku configに設定されている値を取得。
    URI dbUri = null;
	try {
		dbUri = new URI(System.getenv("CLEARDB_DATABASE_URL"));
	} catch (URISyntaxException e) {
		e.printStackTrace();
	}
    // :をデリミタとして必要な情報を抜き取る。
    String username = dbUri.getUserInfo().split(":")[0];
    String password = dbUri.getUserInfo().split(":")[1];
    // JDBC用のURLを生成。
    String dbUrl = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();

		// JDBCドライバの読み込み
//		Class.forName("com.mysql.jdbc.Driver");
		Class.forName ("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(dbUrl, username, password);
	}
}