package com.mahouttest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mahouttest.dao.StockInfoDAO;
import com.mahouttest.dao.UserInfoDAO;
import com.mahouttest.domain.model.StockInfo;

@Controller
public class HelloController {
	
	
	@RequestMapping(value="stocks",method=RequestMethod.GET)
	public String stocks(Model model) {
		model.addAttribute("name", "±Ë¡æ¿Œ");
		
		return "stocks";
	}
	
	@RequestMapping(value="users",method=RequestMethod.GET)
	public String users(Model model) {
		model.addAttribute("name", "±Ë¡æ¿Œ");
		
		return "users";
	}

	

}
