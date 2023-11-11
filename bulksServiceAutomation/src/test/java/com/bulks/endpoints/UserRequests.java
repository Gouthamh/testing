package com.bulks.endpoints;

import org.testng.annotations.Test;

import com.playload.Fileupload;
import com.playload.bulkrequestplayload;
import com.playload.bulkurls;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.ResponseAwareMatcher.*;
import static org.hamcrest.Matcher.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.management.InvalidApplicationException;

public class UserRequests {

	 bulkurls bulkendpoints = new bulkurls();

	public Response createuser(bulkrequestplayload payload) {

		
		String mysql = bulkendpoints.triggerBulkService("mysql-post-bulk-service");

		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload).when()
				.post(mysql);

		return response;

	}

	public Response Post_Bulk_Service(String offset,String bulkservicename) throws IOException {
		Fileupload fileupload = new Fileupload();
		String mysql = bulkendpoints.triggerBulkService(bulkservicename);
		String body = fileupload.files(offset);
		Response response = given().
				contentType(ContentType.JSON).
				accept(ContentType.JSON).
				body(body).
				when().
				post(mysql);
		return response;

	}

	public Response formdata(String filepath, int Bulkid,String bulkservicename) {
		String Notification = bulkendpoints.fileUploadToProcessBulkData(bulkservicename);
		File file1 = new File(filepath);
		Response response = given().
				multiPart("file", file1).
				formParam("BulkID", Bulkid).
				when().
				post(Notification);
		return response;
	}
	
	public Response UpdateBulkServicesApi(String Services) throws IOException {
		Fileupload fileupload = new Fileupload();
		String body = fileupload.files(Services);
		Response response = given().
				contentType(ContentType.JSON).
				accept(ContentType.JSON).
				body(body).
				when().
				put(bulkurls.UpdateBulkMasterConfig());
		return response;
		
	}
	

}
