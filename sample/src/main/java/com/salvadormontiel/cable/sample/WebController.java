package com.salvadormontiel.cable.sample;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Salvador Montiel
 */
@Controller
public class WebController {

	@GetMapping("/")
	public String index(Model model) {
		return "index";
	}
}
