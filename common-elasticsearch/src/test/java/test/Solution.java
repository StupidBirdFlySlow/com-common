package test;

import java.util.Arrays;

public class Solution {

	/**
	 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
	 * 
	 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
	 * 
	 * 示列： 给定 nums = [2, 7, 11, 15], target = 9
	 * 
	 * 因为 nums[0] + nums[1] = 2 + 7 = 9 所以返回 [0, 1]
	 * 
	 * 
	 * @param nums
	 * @param target
	 * @return
	 */
	public int[] twoSum(int[] nums, int target) {
		int[] result = new int[2];
		for (int i = 0; i < nums.length - 1; i++) {
			for (int j = i + 1; j < nums.length; j++) {
				if (nums[i] + nums[j] == target) {
					result[0] = i;
					result[1] = j;
					break;
				}
			}
		}
		return result;
	}

	public static void main(String[] args) {
		Solution s = new Solution();
		int[] twoSum = s.twoSum(new int[] { 2, 7, 11, 13, 15 }, 15);
		System.out.println(Arrays.toString(twoSum));
	}
	
	
	/****
	 * 写一个塔数不超过3个塔的桥跨自动策略
	 * 
	 * 输入 走向，及塔数
	 * exp:走向：南北，东西;塔数
	 */
	
	
	/***
	 * 写一个根据索股数量自动创建上下游索股构件的方法
	 * 问题：创建时，如果确定是基准索，相对基准索，待调一般索
	 */
	
	/***
	 * 根据猫道
	 */
}
