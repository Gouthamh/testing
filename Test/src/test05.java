import java.util.Arrays;
import java.util.Vector;

public class test05 {
	
	public static int remove(int nums[]) {
		int j=1;
		Arrays.sort(nums);
		for(int i=1;i<nums.length;i++) {
			if(nums[i]!=nums[i-1]) {
				j++;
				
			}
		}
		return j;
		
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int nums [] = {0,0,1,1,1,2,2,3,3,4};
		remove(nums);
		
		

	}

}
