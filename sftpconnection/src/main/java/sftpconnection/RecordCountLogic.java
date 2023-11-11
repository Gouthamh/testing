package sftpconnection;

public class RecordCountLogic {

	public static void main(String[] args) {
		int records = 10;
		int limit = 3;
		int fileCount = 0;
		int recordCount = 0;
		
		if (limit > records || limit == 0) {
			int fileCount1 = 1;
			int recordCount1 = records;
			
			System.out.println("filecount : " + fileCount1 + ", record_count : " + recordCount1);

		} else {
			fileCount = records / limit;
			recordCount = records % limit;

			if (recordCount != 0) {
				int fileCount1 = fileCount + 1;
				System.out.println("filecount : " + fileCount1);
				System.out.println("number of line in file : " + limit);
				System.out.println("remaining file count : " + recordCount);
			} else {
				System.out.println("filecount : " + fileCount);
				System.out.println("number of line in files : " + limit);
			}
		}
	}

}
