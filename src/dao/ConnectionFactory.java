package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

	private Connection con;
	
	public Connection getConnection() {

		try {

			Class.forName("org.mariadb.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mariadb://localhost:3307/db_investimentos", "root", "Crocodilo@");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return con;

	}

}
