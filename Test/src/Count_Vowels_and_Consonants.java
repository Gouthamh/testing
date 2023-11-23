
public class Count_Vowels_and_Consonants {
	
	static boolean isVolew(char ch) {
		if(ch =='a'||ch=='e'||ch=='i'||ch=='o'||ch=='u') 
			return true;
		return false;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String name = "Goutham";
		int len=name.length();
		char ch;
		int count = 0;
		for(int i=0;i<len;i++) {
			ch=name.toLowerCase().charAt(i);
			
		}
		System.out.println(count);

	}

}
