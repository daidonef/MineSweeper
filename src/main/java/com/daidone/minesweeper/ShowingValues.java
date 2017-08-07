package com.daidone.minesweeper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

public class ShowingValues {
	
	//Method to know what is displayed for user on table
	public static void displayingTable (Model model, HttpServletRequest request, 
			HttpSession session) {
		
		int results = CheckingTable.result(request, session);
		//If player loses
		if (results == 2) {
			showingAll(session);
			model.addAttribute("result", "You lose!");
		}
		//If player wins
		if (results == 1) {
			showingAll(session);
			model.addAttribute("result", "You win!");
		}
		//If player keeps going
		if (results == 0) {
			changingShowingMS(request, session);
			if (CheckingTable.zero(request, session)) {
				changingForZero(request, session);
			}
		}
		MinesweeperTable.addingModelSession(model, session, (int[][]) session.getAttribute(
				"mineSweeper"), (boolean[][]) session.getAttribute("showingMS"));
	}
	
	//Method to change all boolean values to true if player loses or wins
	private static void showingAll (HttpSession session) {
		
		boolean[][] showingMS = (boolean[][]) session.getAttribute("showingMS");
		
		for(int i = 0; i < showingMS.length; i++) {
			for(int j = 0; j <showingMS[i].length; j++) {
				showingMS[i][j] = true;
			}
		}
		session.setAttribute("showingMS", showingMS);
	}
	
	//Method to change boolean values for the blank squares that are next to each other
	//Need to refine code so it only gets the zeros it should every time.
	private static void changingForZero(HttpServletRequest request, HttpSession session) {
		
		int[][] mineSweeper = (int[][]) session.getAttribute("mineSweeper");
		boolean[][] showingMS = (boolean[][]) session.getAttribute("showingMS");
		int index1 = Integer.parseInt(request.getParameter("index1"));
		int index2 = Integer.parseInt(request.getParameter("index2"));
		
		//Checks zeros until it gets to a higher number following the column for each row.
		for (int i = 0; i < showingMS.length; i++) {
			for (int j = 0; j < showingMS[i].length; j++) {
				if (index1 - i >= 0 && index2 - j >= 0) {
					showingMS = changingZerosMinusMinus(index1, index2, i, j, mineSweeper, showingMS);
					if (true) {
						if (mineSweeper[index1 - i][index2 - j] > 0) {
							break;
						}
					}
				}
			}
			for (int j = 0; j < showingMS[i].length; j++) {
				if (index1 - i >= 0 && index2 + j < mineSweeper[i].length) {
					showingMS = changingZerosMinusPlus(index1, index2, i, j, mineSweeper, showingMS);
					if (mineSweeper[index1 - i][index2 + j] > 0) {
						break;
					}
				}
			}
			for (int j = 0; j < showingMS[i].length; j++) {
				if (index1 + i < showingMS.length && index2 - j >= 0) {
					showingMS = changingZerosPlusMinus(index1, index2, i, j, mineSweeper, showingMS);
					if (mineSweeper[index1 + i][index2 - j] > 0) {
						break;
					}
				}
			}
			for (int j = 0; j < showingMS[i].length; j++) {
				if (index1 + i < mineSweeper.length && index2 + j < mineSweeper[i].length) {
					showingMS = changingZerosPlusPlus(index1, index2, i, j, mineSweeper, showingMS);
					if (mineSweeper[index1 + i][index2 + j] > 0) {
						break;
					}
				}
			}
		}
		//Checks zeros until it gets to a higher number following the row for each column
		for (int j = 0; j < showingMS.length; j++) {
			for (int i = 0; i < showingMS[j].length; i++) {
				if (index1 - i >= 0 && index2 - j >= 0) {
					showingMS = changingZerosMinusMinus(index1, index2, i, j, mineSweeper, showingMS);
					if (mineSweeper[index1 - i][index2 - j] > 0) {
						break;
					}
				}
			}
			for (int i = 0; i < showingMS[j].length; i++) {
				if (index1 - i >= 0 && index2 + j < mineSweeper[i].length) {
					showingMS = changingZerosMinusPlus(index1, index2, i, j, mineSweeper, showingMS);
					if (mineSweeper[index1 - i][index2 + j] > 0) {
						break;
					}
				}
			}
			for (int i = 0; i < showingMS[j].length; i++) {
				if (index1 + i < showingMS.length && index2 - j >= 0) {
					showingMS = changingZerosPlusMinus(index1, index2, i, j, mineSweeper, showingMS);
					if (mineSweeper[index1 + i][index2 - j] > 0) {
						break;
					}
				}
			}
			for (int i = 0; i < showingMS[j].length; i++) {
				if (index1 + i < mineSweeper.length && index2 + j < mineSweeper[i].length) {
					showingMS = changingZerosPlusPlus(index1, index2, i, j, mineSweeper, showingMS);
					if (mineSweeper[index1 + i][index2 + j] > 0) {
						break;
					}
				}
			}
		}
		
		session.setAttribute("showingMS", showingMS);
	}
	
	//Method to change one boolean value for the boolean 2d array
	private static void changingShowingMS(HttpServletRequest request, HttpSession session) {
		
		boolean[][] showingMS = (boolean[][]) session.getAttribute("showingMS");
		
		showingMS[Integer.parseInt(request.getParameter("index1"))][Integer.parseInt(
				request.getParameter("index2"))] = true;
		
		session.setAttribute("showingMS", showingMS);
	}
	
	private static boolean[][] changingZerosPlusPlus(int index1, int index2, int i, int j,
			int[][] mineSweeper, boolean[][] showingMS) {
		
		if (mineSweeper[index1 + i][index2 + j] == 0) {
			showingMS[index1 + i][index2 + j] = true;
			if (CheckingTable.arrayPlusiPlus(mineSweeper, index1, i)) {
				showingMS[index1 + i + 1][index2 + j] = true;
			}
			if (CheckingTable.arrayPlusiPlus(mineSweeper, index1, i) && 
					CheckingTable.arrayPlusjPlus(mineSweeper, index2, j, index1 + i + 1)) {
				showingMS[index1 + i + 1][index2 + j + 1] = true;
			}
			if (CheckingTable.arrayPlusiPlus(mineSweeper, index1, i) && 
					CheckingTable.arrayPlusjMinus(index2, j)) {
				showingMS[index1 + i + 1][index2 + j - 1] = true;
			}
			if (CheckingTable.arrayPlusiMinus(index1, i)) {
				showingMS[index1 + i - 1][index2 + j] = true;
			}
			if (CheckingTable.arrayPlusiMinus(index1, i) && 
					CheckingTable.arrayPlusjPlus(mineSweeper, index2, j, index1 + i - 1)) {
				showingMS[index1 + i - 1][index2 + j + 1] = true;
			}
			if (CheckingTable.arrayPlusiMinus(index1, i) && 
					CheckingTable.arrayPlusjMinus(index2, j)) {
				showingMS[index1 + i - 1][index2 + j - 1] = true;
			}
			if (CheckingTable.arrayPlusjPlus(mineSweeper, index2, j, index1 + i)) {
				showingMS[index1 + i][index2 + j + 1] = true;
			}
			if (CheckingTable.arrayPlusjMinus(index2, j)) {
				showingMS[index1 + i][index2 + j - 1] = true;
			}
		}
		return showingMS;
	}
	
	private static boolean[][] changingZerosPlusMinus(int index1, int index2, int i, int j,
			int[][] mineSweeper, boolean[][] showingMS) {
		
		if (mineSweeper[index1 + i][index2 - j] == 0) {
			showingMS[index1 + i][index2 - j] = true;
			if (CheckingTable.arrayPlusiPlus(mineSweeper, index1, i)) {
				showingMS[index1 + i + 1][index2 - j] = true;
			}
			if (CheckingTable.arrayPlusiPlus(mineSweeper, index1, i) && 
					CheckingTable.arrayMinusjPlus(mineSweeper, index2, j, index1 + i + 1)) {
				showingMS[index1 + i + 1][index2 - j + 1] = true;
			}
			if (CheckingTable.arrayPlusiPlus(mineSweeper, index1, i) && 
					CheckingTable.arrayMinusjMinus(index2, j)) {
				showingMS[index1 + i + 1][index2 - j - 1] = true;
			}
			if (CheckingTable.arrayPlusiMinus(index1, i)) {
				showingMS[index1 + i - 1][index2 - j] = true;
			}
			if (CheckingTable.arrayPlusiMinus(index1, i) && 
					CheckingTable.arrayMinusjPlus(mineSweeper, index2, j, index1 + i - 1)) {
				showingMS[index1 + i - 1][index2 - j + 1] = true;
			}
			if (CheckingTable.arrayPlusiMinus(index1, i) && 
					CheckingTable.arrayMinusjMinus(index2, j)) {
				showingMS[index1 + i - 1][index2 - j - 1] = true;
			}
			if (CheckingTable.arrayMinusjPlus(mineSweeper, index2, j, index1 + i)) {
				showingMS[index1 + i][index2 - j + 1] = true;
			}
			if (CheckingTable.arrayMinusjMinus(index2, j)) {
				showingMS[index1 + i][index2 - j - 1] = true;
			}
		}
		return showingMS;
	}
	
	private static boolean[][] changingZerosMinusPlus(int index1, int index2, int i, int j,
			int[][] mineSweeper, boolean[][] showingMS) {
		
		if (mineSweeper[index1 - i][index2 + j] == 0) {
			showingMS[index1 - i][index2 + j] = true;
			if (CheckingTable.arrayMinusiPlus(mineSweeper, index1, i)) {
				showingMS[index1 - i + 1][index2 + j] = true;
			}
			if (CheckingTable.arrayMinusiPlus(mineSweeper, index1, i) && 
					CheckingTable.arrayPlusjPlus(mineSweeper, index2, j, index1 - i + 1)) {
				showingMS[index1 - i + 1][index2 + j + 1] = true;
			}
			if (CheckingTable.arrayMinusiPlus(mineSweeper, index1, i) && 
					CheckingTable.arrayPlusjMinus(index2, j)) {
				showingMS[index1 - i + 1][index2 + j - 1] = true;
			}
			if (CheckingTable.arrayMinusiMinus(index1, i)) {
				showingMS[index1 - i - 1][index2 + j] = true;
			}
			if (CheckingTable.arrayMinusiMinus(index1, i) && 
					CheckingTable.arrayPlusjPlus(mineSweeper, index2, j, index1 - i - 1)) {
				showingMS[index1 - i - 1][index2 + j + 1] = true;
			}
			if (CheckingTable.arrayMinusiMinus(index1, i) && 
					CheckingTable.arrayPlusjMinus(index2, j)) {
				showingMS[index1 - i - 1][index2 + j - 1] = true;
			}
			if (CheckingTable.arrayPlusjPlus(mineSweeper, index2, j, index1 - i)) {
				showingMS[index1 - i][index2 + j + 1] = true;
			}
			if (CheckingTable.arrayPlusjMinus(index2, j)) {
				showingMS[index1 - i][index2 + j - 1] = true;
			}
		}
		return showingMS;
	}
	
	private static boolean[][] changingZerosMinusMinus(int index1, int index2, int i, int j,
			int[][] mineSweeper, boolean[][] showingMS) {
		
		if (mineSweeper[index1 - i][index2 - j] == 0) {
			showingMS[index1 - i][index2 - j] = true;
			if (CheckingTable.arrayMinusiPlus(mineSweeper, index1, i)) {
				showingMS[index1 - i + 1][index2 - j] = true;
			}
			if (CheckingTable.arrayMinusiPlus(mineSweeper, index1, i) && 
					CheckingTable.arrayMinusjPlus(mineSweeper, index2, j, index1 - i + 1)) {
				showingMS[index1 - i + 1][index2 - j + 1] = true;
			}
			if (CheckingTable.arrayMinusiPlus(mineSweeper, index1, i) && 
					CheckingTable.arrayMinusjMinus(index2, j)) {
				showingMS[index1 - i + 1][index2 - j - 1] = true;
			}
			if (CheckingTable.arrayMinusiMinus(index1, i)) {
				showingMS[index1 - i - 1][index2 - j] = true;
			}
			if (CheckingTable.arrayMinusiMinus(index1, i) && 
					CheckingTable.arrayMinusjPlus(mineSweeper, index2, j, index1 - i - 1)) {
				showingMS[index1 - i - 1][index2 - j + 1] = true;
			}
			if (CheckingTable.arrayMinusiMinus(index1, i) && 
					CheckingTable.arrayMinusjMinus(index2, j)) {
				showingMS[index1 - i - 1][index2 - j - 1] = true;
			}
			if (CheckingTable.arrayMinusjPlus(mineSweeper, index2, j, index1 - i)) {
				showingMS[index1 - i][index2 - j + 1] = true;
			}
			if (CheckingTable.arrayMinusjMinus(index2, j)) {
				showingMS[index1 - i][index2 - j - 1] = true;
			}
		}
		return showingMS;
	}
	
}
