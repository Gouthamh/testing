
public class test02 {

	public static void ispalindrome(int num) {
		if (num >= 0) {
			int num1 = num;
			
			int rem = 0;
			int temp = 0;
			while (num1 != 0) {
				rem = num1 % 10;
				
				temp = temp * 10 + rem;
				
				num1 = num1 / 10;
				

			}

			if (num == temp) {
				System.out.println(true);
			} else {
				System.out.println(false);
			}
		}

		else {
			System.out.println(false);
		}

	}

	public static void main(String[] args) {
		int number = -121;
		ispalindrome(number);

	}

}
