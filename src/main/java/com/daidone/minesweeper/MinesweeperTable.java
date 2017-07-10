package com.daidone.minesweeper;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

public class MinesweeperTable {
	
	//Method to generate minesweeper table in 2d array
	public static void generatingMSTable(Model model, HttpSession session) {
		addingModelSession(model, session, addsNumbers(generateMines()), generatingShowingMS());
	}
	
	//Method get random mines for 2d array, higher level get more mines
	//Uses 10 for the mine
	private static int[][] generateMines() {
		
		//Add bigger array for higher level after base game is finished
		int rows = 10;
		int columns = 10;
		int[][] mineSweeper = new int[rows][columns];
		
		for (int i = 0; i < mineSweeper.length; i++) {
			for (int j = 0; j < mineSweeper[i].length; j++) {
				mineSweeper[i][j] = randomNumbers();
			}
		}
		return mineSweeper;
	}
	
	//Method for random outputs
	private static int randomNumbers() {
		
		//Might need to changes odds to change the number of mines
		Random random = new Random();
		int randomNumber = random.nextInt(15);
		
		if (randomNumber == 0) {
			return 10;
		} else {
			return 0;
		}
	}
	
	//Method for putting numbers in places with no mines based on number of mines around place
	private static int[][] addsNumbers(int[][] mineSweeper) {
		
		//Break up for loops into different methods maybe
		for (int i = 0; i < mineSweeper.length; i++) {
			for (int j = 0; j < mineSweeper[i].length; j++) {
				if (mineSweeper[i][j] == 10) {
					if (i - 1 >= 0) {
						mineSweeper[i-1][j]++;
						if (j - 1 >= 0) {
							mineSweeper[i-1][j-1]++;
						}
						if (j + 1 < mineSweeper[i].length) {
							mineSweeper[i-1][j+1]++;
						}
					}
					if (i + 1 < mineSweeper.length) {
						mineSweeper[i+1][j]++;
						if (j - 1 >= 0) {
							mineSweeper[i+1][j-1]++;
						}
						if (j + 1 < mineSweeper[i].length) {
							mineSweeper[i+1][j+1]++;
						}
					}
					if (j - 1 >= 0) {
						mineSweeper[i][j-1]++;
					}
					if (j + 1 < mineSweeper[i].length) {
						mineSweeper[i][j+1]++;
					}
				}
			}
		}
		
		return mineSweeper;
	}
	
	//Method for generating 2d array with all boolean values false
	private static boolean[][] generatingShowingMS() {
		
		//Add bigger array for higher level after base game is finished
		int rows = 10;
		int columns = 10;
		boolean[][] showingMS = new boolean[rows][columns];
		
		for (int i = 0; i < showingMS.length; i++) {
			for (int j = 0; j < showingMS[i].length; j++) {
				showingMS[i][j] = false;
			}
		}
		return showingMS;
	}
	
	//Public method for adding both 2d arrays to model and session
	public static void addingModelSession(Model model, HttpSession session, int[][] mineSweeper, 
			boolean[][] showingMS) {
		
		model.addAttribute("mineSweeper", mineSweeper);
		model.addAttribute("showingMS", showingMS);
		session.setAttribute("mineSweeper", mineSweeper);
		session.setAttribute("showingMS", showingMS);
		
	}

}
