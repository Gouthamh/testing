import java.util.Stack;

public class test04 {
	public static void solutions(String s) {
		//System.out.println(s);
		Stack<Character> left = new Stack<>();
		for(char c:s.toCharArray()) {
			if(c=='{' || c=='[' || c=='(') {
				left.push(c);
				
			}
			else if(c==')'&& !left.isEmpty() && left.peek()=='(') {
				left.pop();
			}
			else if(c==']'&& !left.isEmpty() && left.peek()=='[') {
				left.pop();
			}
			else if(c=='}'&& !left.isEmpty() && left.peek()=='{') {
				left.pop();
			}
			else {
				System.out.println(false);
			}
		}
		System.out.println(left.isEmpty());
	}
	
	public static void main(String[] args) {
		
		String s= "()[]{}";
		solutions(s);
	
	}

}
