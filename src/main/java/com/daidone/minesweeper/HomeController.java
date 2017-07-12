package com.daidone.minesweeper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession(true);
		
		//Random minesweep table generated on first load of page
			//Uses method in the MinesweeperTable Class
			//Use sessions to make sure this is only generated once.
		MinesweeperTable.generatingMSTable(model, session);
		
		//For testing
		model.addAttribute("testing1", request.getParameter("index1"));
		model.addAttribute("testing2", request.getParameter("index2"));
		
		//After first load will use that table and user input to see if player loses or not
			//Uses method in the ShowingValues Class
		
		return "home";
	}
	
}
