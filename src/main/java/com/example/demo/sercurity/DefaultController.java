package com.example.demo.sercurity;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {
	@RequestMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("2")) {
            return "redirect:/admin/";
        }
        else if (request.isUserInRole("3")) {
            return "redirect:/storehouse/";
        }
		/*
		 * else if (request.isUserInRole("Nhân viên kho")) { return "redirect:/admin/";
		 * }
		 */ else {
        	return "redirect:/";
        }
    }
}
