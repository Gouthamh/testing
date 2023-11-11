package Mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; // Correct import

public class Delete {

	static final String DB_URL = "jdbc:mysql://172.20.21.213:3306/smdb";
	static final String USER = "smdb";
	static final String PASS = "Smdb$123";
	static final String QUERY = "delete from smdb.automation_tbl";

	static final String selectQuery = "SELECT COUNT(*) FROM smdb.automation_tbl";

	public static void main(String[] args) throws ClassNotFoundException {
		
		try {
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement(); // Create a Statement
			stmt.executeUpdate(QUERY); // Execute the query

			ResultSet resultSet = stmt.executeQuery(selectQuery);

			if (resultSet.next()) {
				int rowCount = resultSet.getInt(1);
				if (rowCount == 0) {
					System.out.println("Deleted");
				} else {
					System.out.println("Not Deleted");
				}
			}

			conn.close(); // Close the connection when you're done

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
