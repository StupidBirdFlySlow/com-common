package com.learn;

import java.util.Arrays;

public class SortAlgorithm {

	public static void main(String[] args) {

		int[] arr = { 10, 7, 6, 9, 3, 8, 2, 1, 5, 4 };
		sortMaoPao(arr);
		sortInsertSimple(arr);
	}

	/**
	 * 冒泡
	 */
	public static void sortMaoPao(int arr[]) {

		int length = arr.length;
		for (int i = 0; i < length - 1; i++) {
			for (int j = i + 1; j < length; j++) {
				if (arr[i] > arr[j]) {
					int temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
				}
			}
		}
		System.out.println(Arrays.toString(arr));
	}

	/**
	 * 简单插入排序
	 * 
	 * 数组中存在n个元素，开始时，假定第一个元素已经有序了，则[1,n-1]为剩余的待排序列。
	 * 从[1,n-1]中遍历取数，依次从大到小，和之前有序的序列中的值做对比，满足条件，插入，不满足条件，继续比较
	 */
	public static void sortInsertSimple(int arr[]) {
		int length = arr.length;
		for (int i = 1; i < length; i++) {
			int targetIndex = i - 1;// 被对比数的下标
			int targetValue = arr[targetIndex];// 被对比数的值
			int sourceValue = arr[i];// 对比数的值
			// 当存在被对比值，且对比值比被对比值小时，
			if (targetIndex >= 0 && sourceValue < targetValue) {
				// 使用被对比值,覆盖,被对比值后一位的值(即当前值)，然后继续向左对比
				targetValue = arr[i];
				targetIndex -= 1;
			}
			// 如果没有对比值了，或者对比值大于被对比值，则在第0位或者被对比值的后一位，插入对比值
			arr[targetIndex + 1] = sourceValue;
		}
		System.out.println(Arrays.toString(arr));
	}

	/**
	 * 希尔排序
	 * 
	 * 
	 * 
	 * @param arr
	 */
	public static void sortInsertShell(int arr[]) {

	}
}
