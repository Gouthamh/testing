public class test06 {

	public static void main(String[] args) {
		isPrime(18);
	}

	public static void isPrime(int n) {
		if (n == 0 || n == 1) {
			System.out.println(n);
		}
		if (n == 2) {
			System.out.println(n);
		}
		for (int i = 2; i <= n / 2; i++) {
			
				System.out.println(i);
			
		}

		
	}

}