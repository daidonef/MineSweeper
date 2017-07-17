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
	
	//Method to change boolean values for the blank squares that are next to each other up
		//until a number is shown
		//Parameter is session
		//For loop to check the 2d array with numbers to change using for loop the 2d array
			//with boolean values
			//(Might use the method below the one to change the boolean values)
		//Change the session of that 2d array and return that 2d array
	private static void changingForZero(HttpServletRequest request, HttpSession session) {
		
		int[][] mineSweeper = (int[][]) session.getAttribute("mineSweeper");
		boolean[][] showingMS = (boolean[][]) session.getAttribute("showingMS");
		int index1 = Integer.parseInt(request.getParameter("index1"));
		int index2 = Integer.parseInt(request.getParameter("index2"));
		
		//Need to work on changing the boolean values in showingMS to true up until
		//there is no more zeros.
		//Need to use two for loops and have the inner for loop break if value is
		//greater than 0
		//The outer for loop would start the inner again at i++.
		//Need to make sure array does not go out of bounds with if statements
		
		session.setAttribute("showingMS", showingMS);
	}
	
	//Method to change one boolean value for the boolean 2d array
	private static void changingShowingMS(HttpServletRequest request, HttpSession session) {
		
		boolean[][] showingMS = (boolean[][]) session.getAttribute("showingMS");
		
		showingMS[Integer.parseInt(request.getParameter("index1"))][Integer.parseInt(
				request.getParameter("index2"))] = true;
		
		session.setAttribute("showingMS", showingMS);
	}

}
