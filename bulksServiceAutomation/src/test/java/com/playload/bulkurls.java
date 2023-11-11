package com.playload;

import com.bulks.endpoints.Routers;

public class bulkurls {

	public String triggerBulkService(String servicename) {

		return Routers.base_url + "" + servicename + "" + Routers.triggerBulkService;

	}

	public String fileUploadToProcessBulkData(String servicename) {
		return Routers.base_url + "" + servicename + "" + Routers.fileUploadToProcessBulkData;
	}
	
	public static String UpdateBulkMasterConfig() {
		return Routers.base_url + "configure/UpdateBulkMasterConfig";
		
	}
	
	public static void main(String [] args) {
		System.out.println(UpdateBulkMasterConfig());
	}
	

}
