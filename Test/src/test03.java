import java.util.Arrays;

public class test03 {

	public static String longestCommonPrefix(String[] strs) {
		
		StringBuilder longest = new StringBuilder();
		if(strs==null||strs.length==0) {
			return "";
			
		}
		int minlength = strs[0].length();
		//System.out.println("--"+minlength);
		for(int i=0;i<strs.length;i++) {
			//System.out.println(strs[i].length());
			
			minlength = Math.min(minlength, strs[i].length());
			//System.out.println("---++"+minlength);
		}
		//System.out.println("---++"+minlength);
		for(int j=0;j<minlength;j++) {
			char current = strs[0].charAt(j);
			//System.out.println("current"+current);
			for(String str:strs) {
				System.out.println("---"+str);
				if(str.charAt(j)!=current) {
					return longest.toString();
				}
			}
			longest.append(current);
		}
		return longest.toString();
	}

	public static void main(String[] args) {

		String[] strs = { "flower", "flow", "flight" };
		String n = longestCommonPrefix(strs);
		System.out.println(n);

	}

}
