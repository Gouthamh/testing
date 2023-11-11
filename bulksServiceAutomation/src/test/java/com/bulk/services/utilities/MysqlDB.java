package com.bulk.services.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bulks.endpoints.Routers;

public class MysqlDB {

	public Connection getConnection(String jdbcUrl, String username, String password)
			throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(jdbcUrl, username, password);

	}

	public int getCountFromTable(Connection connection, String tableName) throws SQLException {
		String query = "SELECT COUNT(*) AS recordCount FROM " + tableName;

		try (PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			
			//System.out.println(resultSet.absolute(1));
			if (resultSet.next()) {
				//System.out.println(resultSet.first());
				return resultSet.getInt("recordCount");
			
			}
		}

		return 0; // Default value if no records found
	}
	
	
	
	
	public static void main(String args[]) throws ClassNotFoundException, SQLException {
		
		MysqlDB mysqlDB = new MysqlDB();
		Connection connectios = mysqlDB.getConnection(Routers.jdbcUrl, Routers.username, Routers.password);
		long count = mysqlDB.getCountFromTable(connectios, Routers.UploadTest);
		System.out.println(count);
		
	}

}
