
public class GP {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int i=1;
		Number number = 3;
		double gp=1;
		double num1=1;
		double num2=2;
		for(i=1;i<=number.doubleValue();i++) {
			gp=num1*num2;
			num1=num2;
			num2=gp;
			System.out.println(gp);
			
		}
		

	}

}
