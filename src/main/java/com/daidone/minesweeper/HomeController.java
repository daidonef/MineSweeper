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
		
		//Need to figure out why it is not getting past level 1
		if (request.getParameter("index1") == null) {
			//To increase level of difficult for user
			if (request.getParameter("nextLevel") == null) {
				session.setAttribute("nextLevel", 1);
			} else {
				session.setAttribute("nextLevel", (Integer) session.getAttribute("nextLevel")
						+ 1);
			}
		}
		
		//Random minesweeper table generated on first load of page
		if (request.getParameter("index1") == null || Boolean.getBoolean((String) request
				.getParameter("startOver"))) {
			MinesweeperTable.generatingMSTable(model, session);
		} else {
			//After first load will use that table and user input to see if player loses or not
			ShowingValues.displayingTable(model, request, session);
		}

		return "home";
	}
	
}
