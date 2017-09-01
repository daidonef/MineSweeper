package com.daidone.minesweeper;

import java.util.HashMap;
import java.util.Map;

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
			model.addAttribute("result2", 0);
		}
		//If player wins
		if (results == 1) {
			showingAll(session);
			model.addAttribute("result", "You win!");
			model.addAttribute("result2", 1);
		}
		//If player keeps going
		if (results == 0) {
			if (CheckingTable.notZero(request, session)) {
				changingShowingMS(request, session);
			}
			if (CheckingTable.zero(request, session)) {
				changingForZero(request, session);
			}
			if (CheckingTable.win(request, session)) {
				showingAll(session);
				model.addAttribute("result", "You win!");
				model.addAttribute("result2", 1);
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
	private static void changingForZero(HttpServletRequest request, HttpSession session) {
		
		int[][] mineSweeper = (int[][]) session.getAttribute("mineSweeper");
		boolean[][] showingMS = (boolean[][]) session.getAttribute("showingMS");
		int index1 = Integer.parseInt(request.getParameter("index1"));
		int index2 = Integer.parseInt(request.getParameter("index2"));
		
		boolean keepGoing = true;
		int i = 0;
		int j = 0;
		int count = 0;
		//HashMaps are for storing all the different i and j numbers so it can always go back
		Map<String, Integer> lasti = new HashMap<String, Integer>(100);
		Map<String, Integer> lastj = new HashMap<String, Integer>(100);
		
		while (keepGoing) {
			if (index1 + i < mineSweeper.length && index1 + i >= 0 && 
					index2 + j < mineSweeper[index1 + i].length && index2 + j >= 0) {
				showingMS[index1 + i][index2 + j] = true;
				if (CheckingTable.arrayPlusiPlus(mineSweeper, index1, i)) {
					if (mineSweeper[index1 + i + 1][index2 + j] > 0) {
						showingMS[index1 + i + 1][index2 + j] = true;
					} else if (mineSweeper[index1 + i + 1][index2 + j] == 0 &&
							showingMS[index1 + i + 1][index2 + j] == false) {
						count++;
						lasti.put("lasti" + count, i);
						lastj.put("lastj" + count, j);
						i++;
						continue;
					}
				}
				if (CheckingTable.arrayPlusiMinus(index1, i)) {
					if (mineSweeper[index1 + i - 1][index2 + j] > 0) {
						showingMS[index1 + i - 1][index2 + j] = true;
					} else if (mineSweeper[index1 + i - 1][index2 + j] == 0 &&
							showingMS[index1 + i - 1][index2 + j] == false) {
						count++;
						lasti.put("lasti" + count, i);
						lastj.put("lastj" + count, j);
						i--;
						continue;
					}
				}
				if (CheckingTable.arrayPlusiPlus(mineSweeper, index1, i) && 
						CheckingTable.arrayPlusjPlus(mineSweeper, index2, j, index1 + i + 1)) {
					if (mineSweeper[index1 + i + 1][index2 + j + 1] > 0) {
						showingMS[index1 + i + 1][index2 + j + 1] = true;
					} else if (mineSweeper[index1 + i + 1][index2 + j + 1] == 0 &&
							showingMS[index1 + i + 1][index2 + j + 1] == false) {
						count++;
						lasti.put("lasti" + count, i);
						lastj.put("lastj" + count, j);
						i++;
						j++;
						continue;
					}
				}
				if (CheckingTable.arrayPlusiPlus(mineSweeper, index1, i) && 
						CheckingTable.arrayPlusjMinus(index2, j)) {
					if (mineSweeper[index1 + i + 1][index2 + j - 1] > 0) {
						showingMS[index1 + i + 1][index2 + j - 1] = true;
					} else if (mineSweeper[index1 + i + 1][index2 + j - 1] == 0 &&
							showingMS[index1 + i + 1][index2 + j - 1] == false) {
						count++;
						lasti.put("lasti" + count, i);
						lastj.put("lastj" + count, j);
						i++;
						j--;
						continue;
					}
				}
				if (CheckingTable.arrayPlusiMinus(index1, i) &&
						CheckingTable.arrayPlusjPlus(mineSweeper, index2, j, index1 + i - 1)) {
					if (mineSweeper[index1 + i - 1][index2 + j + 1] > 0) {
						showingMS[index1 + i - 1][index2 + j + 1] = true;
					} else if (mineSweeper[index1 + i - 1][index2 + j + 1] == 0 &&
							showingMS[index1 + i - 1][index2 + j + 1] == false) {
						count++;
						lasti.put("lasti" + count, i);
						lastj.put("lastj" + count, j);
						i--;
						j++;
						continue;
					}
				}
				if (CheckingTable.arrayPlusiMinus(index1, i) &&
						CheckingTable.arrayPlusjMinus(index2, j)) {
					if (mineSweeper[index1 + i - 1][index2 + j - 1] > 0) {
						showingMS[index1 + i - 1][index2 + j - 1] = true;
					} else if (mineSweeper[index1 + i - 1][index2 + j - 1] == 0 &&
							showingMS[index1 + i - 1][index2 + j - 1] == false) {
						count++;
						lasti.put("lasti" + count, i);
						lastj.put("lastj" + count, j);
						i--;
						j--;
						continue;
					}
				}
				if (CheckingTable.arrayPlusjPlus(mineSweeper, index2, j, index1 + i)) {
					if (mineSweeper[index1 + i][index2 + j + 1] > 0) {
						showingMS[index1 + i][index2 + j + 1] = true;
					} else if (mineSweeper[index1 + i][index2 + j + 1] == 0 &&
							showingMS[index1 + i][index2 + j + 1] == false) {
						count++;
						lasti.put("lasti" + count, i);
						lastj.put("lastj" + count, j);
						j++;
						continue;
					}
				}
				if (CheckingTable.arrayPlusjMinus(index2, j)) {
					if (mineSweeper[index1 + i][index2 + j - 1] > 0) {
						showingMS[index1 + i][index2 + j - 1] = true;
					} else if (mineSweeper[index1 + i][index2 + j - 1] == 0 &&
							showingMS[index1 + i][index2 + j - 1] == false) {
						count++;
						lasti.put("lasti" + count, i);
						lastj.put("lastj" + count, j);
						j--;
						continue;
					}
				}
				if (lasti.get("lasti" + count) == null) {
					break;
				}
				if (lastj.get("lastj" + count) == null) {
					break;
				}
				i = lasti.get("lasti" + count);
				j = lastj.get("lastj" + count);
				
				if (CheckingTable.arrayPlusiPlus(mineSweeper, index1, i)) {
					if (showingMS[index1 + i + 1][index2 + j] == false) {
						lasti.remove("lasti" + count);
						lastj.remove("lastj" + count);
						count--;
						continue;
					}
				}
				if (CheckingTable.arrayPlusiMinus(index1, i)) {
					if (showingMS[index1 + i - 1][index2 + j] == false) {
						lasti.remove("lasti" + count);
						lastj.remove("lastj" + count);
						count--;
						continue;
					}
				}
				if (CheckingTable.arrayPlusiPlus(mineSweeper, index1, i) && 
						CheckingTable.arrayPlusjPlus(mineSweeper, index2, j, index1 + i + 1)) {
					if (showingMS[index1 + i + 1][index2 + j + 1] == false) {
						lasti.remove("lasti" + count);
						lastj.remove("lastj" + count);
						count--;
						continue;
					}
				}
				if (CheckingTable.arrayPlusiPlus(mineSweeper, index1, i) && 
						CheckingTable.arrayPlusjMinus(index2, j)) {
					if (showingMS[index1 + i + 1][index2 + j - 1] == false) {
						lasti.remove("lasti" + count);
						lastj.remove("lastj" + count);
						count--;
						continue;
					}
				}
				if (CheckingTable.arrayPlusiMinus(index1, i) &&
						CheckingTable.arrayPlusjPlus(mineSweeper, index2, j, index1 + i - 1)) {
					if (showingMS[index1 + i - 1][index2 + j + 1] == false) {
						lasti.remove("lasti" + count);
						lastj.remove("lastj" + count);
						count--;
						continue;
					}
				}
				if (CheckingTable.arrayPlusiMinus(index1, i) &&
						CheckingTable.arrayPlusjMinus(index2, j)) {
					if (showingMS[index1 + i - 1][index2 + j - 1] == false) {
						lasti.remove("lasti" + count);
						lastj.remove("lastj" + count);
						count--;
						continue;
					}
				}
				if (CheckingTable.arrayPlusjPlus(mineSweeper, index2, j, index1 + i)) {
					if (showingMS[index1 + i][index2 + j + 1] == false) {
						lasti.remove("lasti" + count);
						lastj.remove("lastj" + count);
						count--;
						continue;
					}
				}
				if (CheckingTable.arrayPlusjMinus(index2, j)) {
					if (showingMS[index1 + i][index2 + j - 1] == false) {
						lasti.remove("lasti" + count);
						lastj.remove("lastj" + count);
						count--;
						continue;
					}
				}
				if (count > 1) {
					lasti.remove("lasti" + count);
					lastj.remove("lastj" + count);
					count--;
					continue;
				}
				keepGoing = false;
			} else if (index1 + i >= mineSweeper.length) {
				i--;
			} else if (index1 + i < 0) {
				i++;
			} else if (index1 + i < mineSweeper.length && index1 + i >= 0 &&
					index2 + j >= mineSweeper[index1 + i].length) {
				j--;
			} else if (index2 + j < 0) {
				j++;
			} else {
				keepGoing = false;
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
	
}
