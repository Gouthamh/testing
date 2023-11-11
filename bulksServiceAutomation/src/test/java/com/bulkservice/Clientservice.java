package com.bulkservice;

import org.testng.annotations.Test;

import com.github.scribejava.core.model.Response;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.ResponseAwareMatcher.*;
import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matcher.*;

public class Clientservice {

//	@Test
//	public void getMethod() {
//		given().
//		baseUri("http://dap.pe-lab3.bdc-rancher.tecnotree.com").
//		when().
//		get("/dapBulkProcess/configure/fetchAllMasterConfiguratoins").
//		then().body("[0].serviceName", equalTo("ussd-put-bulk-service")).statusCode(200);
//	}
//	
//	@Test
//	public void getMethod1() {
//		String response =  given().
//		baseUri("http://dap.pe-lab3.bdc-rancher.tecnotree.com").
//		when().
//		get("/dapBulkProcess/configure/fetchAllMasterConfiguratoins").
//		then().
//		assertThat().
//		statusCode(200).
//		extract().
//		response().path("[0].serviceName", "ussd-put-bulk-service");
//		
//		
//	}
	
	public void request() {
		given().
		when().
		then();
	}
	
	
	
}
