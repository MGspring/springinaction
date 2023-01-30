package com.example.tacos.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.tacos.Ingredient;
import com.example.tacos.Ingredient.Type;
import com.example.tacos.Order1;
import com.example.tacos.Taco;
import com.example.tacos.data.IngredientRepository;
import com.example.tacos.data.TacoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order1")
public class DesignTacoController {

	private final IngredientRepository ingredientRepo;

	private TacoRepository tacoRepo;

	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository tacoRepo)
	{
		this.ingredientRepo = ingredientRepo;
		this.tacoRepo = tacoRepo;
	}

	@GetMapping
	public String showDesignForm(Model model) {
		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepo.findAll().forEach(i -> ingredients.add(i));

		Type[] types = Ingredient.Type.values();
		for(Type type : types) {
			model.addAttribute(type.toString().toLowerCase(),
			filterByType(ingredients, type));
		}

		model.addAttribute("taco", new Taco());

		return "design";
	}

	@PostMapping
	public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order1 order1) {
		if(errors.hasErrors()){
			return "desing";
		}
		Taco saved = tacoRepo.save(design);
		order1.addDesign(saved);

		return "redirect:/orders/current";
	}

	private List<Ingredient> filterByType(
		List<Ingredient> ingredients, Type type) {
		return ingredients
			.stream()
			.filter(x -> x.getType().equals(type))
			.collect(Collectors.toList());
	}

	@ModelAttribute(name = "order")
	public Order1 order1() {
		return new Order1();
	}
	@ModelAttribute(name = "taco")
	public Taco taco() {
		return new Taco();
	}
}
