package com.java.first;

import java.util.Scanner;

public class CheckPoint {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int height = sc.nextInt();
		int weight = sc.nextInt();
		int result = weight+100-height;
		System.out.printf("비만수치는 %d입니다.\n",result);
		if(result>0) {
			System.out.println("당신은 비만이군요.");
		}
		else {
			System.out.println("당신은 정상이군요.");
		}
	}
}
