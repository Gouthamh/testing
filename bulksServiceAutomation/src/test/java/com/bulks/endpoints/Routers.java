package com.bulks.endpoints;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.ResponseAwareMatcher.*;
import static org.hamcrest.Matcher.*;

@Test

public class Routers {

	public static String base_url = "https://dap.pe-lab3.bdc-rancher.tecnotree.com/dapBulkProcess/";

	// endpoints
	public static String triggerBulkService = "/triggerBulkService";
	public static String fileUploadToProcessBulkData = "/fileUploadToProcessBulkData";

	// mongoDB
	public static String mongoURL = "mongodb://DAP_IAT:DAP_IAT@172.20.21.212:27017,172.20.21.216:27017/DAP_IAT?replicaSet=repl";
	public static String databasename = "DAP_IAT";

	// mysql

	public static String jdbcUrl = "jdbc:mysql://172.20.21.213:3306/smdb";
	public static String username = "smdb";
	public static String password = "Smdb$123";

	// table name
	public static String UploadTest = "UploadTest";

}
