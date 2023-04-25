package org.burgers.web;


import lombok.extern.slf4j.Slf4j;//
import org.burgers.Burger;
import org.burgers.BurgerOrder;
import org.burgers.Ingredient;
import org.burgers.Ingredient.Type;
import org.springframework.stereotype.Controller; //
import org.springframework.ui.Model; //
import org.springframework.web.bind.annotation.*;

import java.util.Arrays; //
import java.util.List; //
import java.util.stream.Collectors; //



@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("burgerOrder")
public class DesignBurgerController {

    @ModelAttribute
    public void addIngredientsToModel(Model model){
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                new Ingredient("GRBF", "Groung Beef", Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("TMTO", "Decide Tomatoes", Type.VEGGIES),
                new Ingredient ("LETC","Lettuce", Type.VEGGIES),
                new Ingredient ("CHED","Cheddar", Type.CHEESE),
                new Ingredient ("JACK","Monterrey Jack", Type.CHEESE),
                new Ingredient ("SLSA","Salsa", Type.SAUCE),
                new Ingredient ("SRCR","Sour Cream", Type.SAUCE)
        );

        Type[] types = Ingredient.Type.values();
        for(Type type: types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }

    @ModelAttribute(name = "burgerOrder")
    public BurgerOrder order(){
        return new BurgerOrder();
    }

    @ModelAttribute(name = "burger")
    public Burger burger(){
        return new Burger();
    }

    @GetMapping
    public String showDesignForm(){
        return "design";
    }

    private Iterable <Ingredient> filterByType(
            List<Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }

    @PostMapping
    public String processBurger(Burger burger, @ModelAttribute BurgerOrder burgerOrder){
        burgerOrder.addBurger(burger);
        log.info("Processing burger: {}", burger);
        return "redirect:/orders/current";
    }
}
