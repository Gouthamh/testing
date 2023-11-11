
public class test08 {
	
	public static int strstr(String haystack,String needle) {
		if(haystack.contains(needle)) {
			return 1;
		}
		else {
		return -1;
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int value = strstr("sadbutsad","sad");
		System.out.println(value);
		

	}

}
