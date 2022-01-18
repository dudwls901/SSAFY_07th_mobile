package com.ssafy.ws01.step3;

import java.util.Scanner;
import java.util.HashMap;

public class GameTest {
	static HashMap<String,Integer> map = new HashMap<String,Integer>();
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		boolean canStart = false;
		int gameMode =0; 
		map.put("가위", 1);
		map.put("바위", 2);
		map.put("보", 3);
		
		while(gameMode <=0 || gameMode >3) {
			System.out.println("1. 5판 3승");
			System.out.println("2. 3판 2승");
			System.out.println("3. 1판 1승");
			System.out.println("번호를 입력하세요. ");
			gameMode = sc.nextInt();
			if(gameMode>=1 && gameMode<=3) {
				start(4-gameMode,sc);
			}
		}

		
	}
	public static void start(int limit, Scanner sc) {
		//1 가위
		//2 주먹
		//3 보
		String input ="";
		int userWinCnt=0;
		int comWinCnt=0;
		
		while(!(input=="가위" || input=="바위" || input=="보")) {
			System.out.println("가위 바위 보 중 하나 입력: ");
			
			input = sc.next();
			int comState = (int)(Math.random()*3) +1;
			int userState = map.getOrDefault(input, 0); 
			
			if(userState!=0) {
				if(userState==comState) {
					System.out.println("비겼습니다");
					userWinCnt++;
					comWinCnt++;
				}
				//컴터 보
				else if(comState==3) {
					if(userState==1) {
						System.out.println("이겼습니다!!");
						userWinCnt++;
					}
					else {
						System.out.println("졌습니다!!");
						comWinCnt++;
					}
				}
				//컴터 바위
				else if(comState==2) {
					if(userState==1) {
						System.out.println("졌습니다!!");
						comWinCnt++;
					}
					else {
						System.out.println("이겼습니다!!");
						userWinCnt++;
					}
				}
				//컴터 가위
				else if(comState==1) {
					if(userState==2) {
						System.out.println("이겼습니다!!");
						userWinCnt++;
					}
					else {
						System.out.println("졌습니다!!");
						comWinCnt++;
					}
				}
			}
			if(userWinCnt==limit) {
				System.out.println("### 당신의 승리입니다");
				break;
			}
			else if(comWinCnt==limit) {
				System.out.println("### 컴퓨터 승!");
				break;
			}
		}
		
	}
}
