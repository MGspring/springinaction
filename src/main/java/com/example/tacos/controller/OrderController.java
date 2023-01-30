package com.example.tacos.controller;


import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.tacos.Order1;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {

	@GetMapping("/current")
	public String orderForm(Model model) {
		model.addAttribute("order1", new Order1());
		return "orderForm";
	}

	@PostMapping
	public String processOrder(@Valid Order1 order, Errors errors) {
		if(errors.hasErrors()) {
			return "orderForm";
		}
		log.info("Order1 submitted: " + order);
		return "redirect:/";
	}
}
