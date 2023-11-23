
public class count {

	private static double num1;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		double num=1234;
		
		double res=0;
		while(num>0) {
			res=num%10;
			num1=(num1*10)+res;
			num=num/10;
			
		}
		System.out.println(num1);

	}

}
