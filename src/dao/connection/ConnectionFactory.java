package dao.connection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionFactory {

	private Connection con;

	public Connection getConnection() {

		try {

			Class.forName("org.mariadb.jdbc.Driver");

			Properties props = new Properties();

			InputStream input = ConnectionFactory.class.getResourceAsStream("/config/db.properties");

			if (input == null) {
				throw new RuntimeException("Arquivo db.properties não encontrado");
			}

			props.load(input);

			String url = props.getProperty("db.url");
			String user = props.getProperty("db.user");
			String password = props.getProperty("db.password");

			con = DriverManager.getConnection(url, user, password);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}
}