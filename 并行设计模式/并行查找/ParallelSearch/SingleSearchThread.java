package com.justin.parallelsearch;

import java.util.Random;
import java.util.Scanner;

public class SingleSearchThread {

	public static void main(String[] args) {
		int searchNum;
		int[] arr = new int[100];
		Random rd = new Random();
		Scanner in = new Scanner(System.in);
		
		System.out.println("数组元素:");
		for(int i=1; i<=100; i++) {
			arr[i-1] = rd.nextInt(1000);
			System.out.print(arr[i-1] + " ");
			if((i%10) == 0) {
				System.out.print("\r\n");
			}
		}
		System.out.print("\r\n请输入要查找的值:");
		searchNum = in.nextInt();
		
		//开始查找
		for(int i=0; i<arr.length; i++) {
			if(arr[i] == searchNum) {
				System.out.println("查找成功!下标为:" + i);
				break;
			}
		}
		in.close();
	}

}
