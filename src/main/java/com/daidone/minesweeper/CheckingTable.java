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
		
		for(int i = 0; i < mineSweeper.length; i++) {
			for(int j = 0; j <mineSweeper[i].length; j++) {
				if (showingMS[i][j] == false && mineSweeper[i][j] <= 10) {
					return false;
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

}
