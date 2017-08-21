package com.daidone.minesweeper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CheckingTable {
	
	//Method to check if player keeps going (0), wins (1), or loses (2)
	public static int result(HttpServletRequest request, HttpSession session) {
		
		int choosen = ((int[][]) session.getAttribute("mineSweeper"))[Integer.parseInt(
				request.getParameter("index1"))][Integer.parseInt(request.getParameter("index2"))];
		
		if (choosen == 10) {
			return 2; //For a hit on mine and a lose
		}
		
		if (win(request, session)) {
			return 1; //For a win
		}
		
		return 0; //For keep playing
	}
	
	//Method to check if playing won the round
	private static boolean win (HttpServletRequest request, HttpSession session) {
		
		int[][] mineSweeper = (int[][]) session.getAttribute("mineSweeper");
		boolean[][] showingMS = (boolean[][]) session.getAttribute("showingMS");
		showingMS [Integer.parseInt(request.getParameter("index1"))][Integer.parseInt(
				request.getParameter("index2"))] = true;
		
		for(int i = 0; i < mineSweeper.length; i++) {
			for(int j = 0; j <mineSweeper[i].length; j++) {
				if (showingMS[i][j] == false) {
					if (mineSweeper[i][j] < 10) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	//checks for zero in MineSweeper
	public static boolean zero (HttpServletRequest request, HttpSession session) {
		
		if (((int[][]) session.getAttribute("mineSweeper"))[Integer.parseInt(
				request.getParameter("index1"))][Integer.parseInt(request.getParameter(
				"index2"))] == 0) {
			return true;
		}
		return false;
	}
	
	public static boolean notZero (HttpServletRequest request, HttpSession session) {
		
		if (((int[][]) session.getAttribute("mineSweeper"))[Integer.parseInt(
				request.getParameter("index1"))][Integer.parseInt(request.getParameter(
				"index2"))] == 0) {
			return false;
		}
		return true;
	}
	
	public static boolean arrayMinusiMinus (int index1, int i) {
		
		if (index1 - i - 1 >= 0) {
			return true;
		}
		return false;
	}
	
	public static boolean arrayMinusiPlus (int[][] mineSweeper, int index1, int i) {
		
		if (index1 - i + 1 < mineSweeper.length) {
			return true;
		}
		return false;
	}
	
	public static boolean arrayPlusiPlus (int[][] mineSweeper, int index1, int i) {
		
		if (index1 + i + 1 < mineSweeper.length) {
			return true;
		}
		return false;
	}
	
	public static boolean arrayPlusiMinus (int index1, int i) {
		
		if (index1 + i - 1 >= 0) {
			return true;
		}
		return false;
	}
	
	public static boolean arrayMinusjMinus (int index2, int j) {
		
		if (index2 - j - 1 >= 0) {
			return true;
		}
		return false;
	}
	
	public static boolean arrayMinusjPlus (int[][] mineSweeper, int index2, int j, int k) {
		
		if (index2 - j + 1 < mineSweeper[k].length) {
			return true;
		}
		return false;
	}
	
	public static boolean arrayPlusjPlus (int[][] mineSweeper, int index2, int j, int k) {
		
		if (index2 + j + 1 < mineSweeper[k].length) {
			return true;
		}
		return false;
	}
	
	public static boolean arrayPlusjMinus (int index2, int j) {
		
		if (index2 + j - 1 >= 0) {
			return true;
		}
		return false;
	}

}
