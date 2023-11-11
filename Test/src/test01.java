
public class test01 {
	public static int[] twoSum(int[] nums, int target) {

		int i = 0;
		int j = 0;

		for (i = 0; i < nums.length; i++) {
			// System.out.println(nums[i]);
			for (j = i + 1; j < nums.length; j++) {
				if (nums[i] + nums[j] == target) {
					return new int[] { i, j };
				}

			}
		}
		return nums;

	}

	public static void main(String[] args) {
		int array[] = { 2, 7, 11, 15 };
		int target = 9;
		twoSum(array, target);

	}

}
