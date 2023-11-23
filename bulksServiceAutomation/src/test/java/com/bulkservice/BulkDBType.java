package com.bulkservice;

import static org.hamcrest.CoreMatchers.equalTo;

import java.io.IOException;
import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.bulk.services.utilities.Mongotest;
import com.bulk.services.utilities.MysqlDB;
import com.bulks.endpoints.Routers;
import com.bulks.endpoints.UserRequests;
import com.playload.bulkrequestplayload;

import io.restassured.response.Response;
import io.restassured.response.Response;


public class BulkDBType {
	long val = 1;
	long val1=1;

	private bulkrequestplayload offset = new bulkrequestplayload();
	private UserRequests user = new UserRequests();

	@BeforeClass
	public long initialMongoCount() throws InterruptedException {
	

		Mongotest count = new Mongotest();
		System.out.println("MONGODB_FORM_DATA");
		val = count.mongotestcount("MONGODB_FORM_DATA");

		return val;
	}

	public long intialmysqlcount() throws ClassNotFoundException, SQLException {
		MysqlDB mysqlDB = new MysqlDB();
		java.sql.Connection connectios = (java.sql.Connection) mysqlDB.getConnection(Routers.jdbcUrl,
				Routers.username, Routers.password);
		return mysqlDB.getCountFromTable(connectios, Routers.UploadTest);
	}

	@BeforeMethod
	public long mongoDbBeforeCount() throws InterruptedException {
		Mongotest count = new Mongotest();
		System.out.println("NOTIFICATION_EMAIL_HISTORY");
		val1=count.mongotestcount("NOTIFICATION_EMAIL_HISTORY");
		return count.mongotestcount("NOTIFICATION_EMAIL_HISTORY");

	}

	@Test(priority = 1)
	public void mongoDbBulkService() throws IOException {
		String offsetFile = "Offset.json";
		Response response = user.Post_Bulk_Service(offsetFile, "mongodb-bulk-service");
		response.then().statusCode(200).body("response", equalTo("mongodb-bulk-service triggered Successfully"));
		}

	@AfterMethod
	public void mongoAfterCount() throws InterruptedException {
		Mongotest count = new Mongotest();
		BulkDBType bulkcount = new BulkDBType();
		Assert.assertEquals(count.mongotestcount("NOTIFICATION_EMAIL_HISTORY"), val+val1);

	}
	@Test(priority = 2)
	public void mongoDbBulkService1() throws IOException {
		String offsetFile = "Offset.json";
		Response response = user.Post_Bulk_Service(offsetFile, "mongodb-bulk-service");
		response.then().statusCode(200).body("response", equalTo("mongodb-bulk-service triggered Successfully"));
		}
//	@Test(priority = 2)
//	
//	public void mysqlbulkservice() throws IOException {
//        String offsetFile = "Offset.json";
//        Response response = user.createuser1(offsetFile,"mysql-post-bulk-service");
//		response.then().statusCode(200).body("response", equalTo("mysql-post-bulk-service triggered Successfully"));
//		System.out.println("mysql-post-bulk-service");
//	}
//
//	@AfterMethod
//	public void mysqlAfterCount() throws InterruptedException, ClassNotFoundException, SQLException {
//		Mongotest count = new Mongotest();
//		System.out.println("-----"+count.mongotestcount("NOTIFICATION_EMAIL_HISTORY"));
//		long count1 = mongoDbBeforeCount() + intialmysqlcount();
//		System.out.println("====="+count1);
//		System.out.println("@AfterMethod mysqlAfterCount"+count.mongotestcount("MONGODB_FORM_DATA"));
//		
//	}
	
	public void test() {
		String offset1 = "Offset.json";
		try {
			Response response = user.Post_Bulk_Service(offset1, "notification-bulk-service");
			response.
			then()
			.statusCode(200)
			.log().all()
			.rootPath("response",is("notification-bulk-service triggered Successfully"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}
