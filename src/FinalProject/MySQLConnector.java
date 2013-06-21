package FinalProject;

import java.sql.*;
import java.util.Properties;

public class MySQLConnector {
	// The JDBC Connector Class.
	private static final String dbClassName = "com.mysql.jdbc.Driver";

	// Connection string.javapong is the database the program
	// is connecting to. You can include user and password after this
	// by adding (say) ?user=paulr&password=paulr. Not recommended!

	private static final String CONNECTION = "jdbc:mysql://127.0.0.1/javapong";

	public static void addNewHighScore(String player, int score)
			throws ClassNotFoundException, SQLException {
		System.out.println(dbClassName);
		// Class.forName(xxx) loads the jdbc classes and
		// creates a drivermanager class factory
		Class.forName(dbClassName);

		// Properties for user and password. Here the user is 'ted' and password
		// is ''
		Properties p = new Properties();
		p.put("user", "root");
		p.put("password", "root");

		// Now try to connect
		Connection conn = DriverManager.getConnection(CONNECTION, p);

		PreparedStatement st = conn.prepareStatement("INSERT INTO highscores"
				+ " (player, score) values (?, ?)");
		st.setString(1, player.toString());
		st.setInt(2, score);
		st.executeUpdate();
		System.out.println("1 row affected");

		System.out.println("It works !");
		conn.close();
	}

}