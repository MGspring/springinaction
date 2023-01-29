package com.example.tacos.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.tacos.Ingredient;
import com.example.tacos.Ingredient.Type;
import com.example.tacos.Taco;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {

	@GetMapping
	public String showDesignForm(Model model) {
		List<Ingredient> ingredients = Arrays.asList(
			new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
			new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
			new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
			new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
			new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
			new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
			new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
			new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
			new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
			new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE)
		);

		Type[] types = Ingredient.Type.values();
		for(Type type : types) {
			model.addAttribute(type.toString().toLowerCase(),
			filterByType(ingredients, type));
		}

		model.addAttribute("taco", new Taco());

		return "design";
	}

	@PostMapping
	public String processDesign(@Valid Taco design, Errors errors) {
		if(errors.hasErrors()){
			return "desing";
		}
		log.info("processing design: " + design);

		return "redirect:/orders/current";
	}

	private List<Ingredient> filterByType(
		List<Ingredient> ingredients, Type type) {
		return ingredients
			.stream()
			.filter(x -> x.getType().equals(type))
			.collect(Collectors.toList());
	}
}