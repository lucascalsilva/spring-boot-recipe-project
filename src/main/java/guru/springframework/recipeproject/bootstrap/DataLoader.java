package guru.springframework.recipeproject.bootstrap;

import guru.springframework.recipeproject.model.*;
import guru.springframework.recipeproject.repositories.CategoryRepository;
import guru.springframework.recipeproject.repositories.RecipeRepository;
import guru.springframework.recipeproject.repositories.UnitOfMeasureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.debug("Starting dataloader component...");
        log.debug("Creating recipe 1");
        Recipe recipe1 = new Recipe();
        recipe1.setDescription("Perfect Guacamole Recipe");
        recipe1.setDifficulty(Difficulty.MODERATE);
        String[] recipe1Ingredients = {"2 unit ripe avocados",
                "0.25 teaspoon of salt, more to taste",
                "1 tablespoon fresh lime juice or lemon juice",
                "2 tablespoon to 1/4 cup of minced red onion or thinly sliced green onion",
                "2 unit serrano chiles, stems and seeds removed, minced",
                "2 tablespoon cilantro (leaves and tender stems), finely chopped",
                "1 dash of freshly grated black pepper",
                "0.5 unit ripe tomato, seeds and pulp removed, chopped",
                "1 handful red radishes or jicama, to garnish",
                "1 handful tortilla chips, to serve"};

        recipe1.setDirections("1 Cut the avocado, remove flesh: Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl."
                + "\n2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The recipe1 should be a little chunky.)"
                + "\n3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown"
                + " Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the recipe1 to your desired degree of hotness."
                + " Remember that much of this is done to taste because of the variability in the fresh ingredient. Start with this recipe and adjust to your taste."
                + " Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your recipe1, add it just before serving."
                + "\n4 Serve: Serve immediately, or if making a few hours ahead, place plastic wrap on the surface of the recipe1 and press down to cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the recipe1 brown.) Refrigerate until ready to serve.");

        Notes notes1 = new Notes();
        notes1.setRecipeNotes("Guacamole! Did you know that over 2 billion pounds of avocados are consumed each year in the U.S.? (Google it.) That’s over 7 pounds per person. I’m guessing that most of those avocados go into what has become America’s favorite dip, guacamole." +
                "\nWHERE DOES GUACAMOLE COME FROM?" +
                "\nThe word “guacamole”, and the dip, are both originally from Mexico, where avocados have been cultivated for thousands of years. The name is derived from two Aztec Nahuatl words—ahuacatl (avocado) and molli (sauce)." +
                "\nINGREDIENTS FOR EASY GUACAMOLE" +
                "\nAll you really need to make guacamole is ripe avocados and salt. After that, a little lime or lemon juice—a splash of acidity—will help to balance the richness of the avocado. Then if you want, add chopped cilantro, chiles, onion, and/or tomato." +
                "\nGUACAMOLE TIP: USE RIPE AVOCADOS" +
                "\nThe trick to making perfect guacamole is using ripe avocados that are just the right quantity of ripeness. Not ripe enough and the avocado will be hard and tasteless. Too ripe and the taste will be off." +
                "\nCheck for ripeness by gently pressing the outside of the avocado. If there is no give, the avocado is not ripe yet and will not taste good. If there is a little give, the avocado is ripe. If there is a lot of give, the avocado may be past ripe and not good. In this case, taste test first before using." +
                "\nRemove the pit from the avocado with a chef knife" +
                "\nTHE BEST WAY TO CUT AN AVOCADO" +
                "\nTo slice open an avocado, cut it in half lengthwise with a sharp chef’s knife and twist apart the sides. One side will have the pit. To remove it, you can do one of two things:" +
                "\nMethod #1: Gently tap the pit with your chef’s knife so the knife gets wedged into the pit. Twist your knife slightly to dislodge the pit and lift to remove. If you use this method, first protect your hand with a thick kitchen towel before proceeding." +
                "\nMethod #2: Cut the side with the pit in half again, exposing more of the pit. Use your fingers or a spoon to remove the pit" +
                "\nOnce the pit is removed, just cut the avocado into chunks right inside the peel and use a spoon to scoop them out." +
                "\nStill curious? Read more about How to Cut and Peel an Avocado" +
                "\nHomemade guacamole on a chip" +
                "\nGUACAMOLE VARIATIONS" +
                "\nOnce you have basic guacamole down, feel free to experiment with variations including strawberries, peaches, pineapple, mangoes, even watermelon. One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). You can get creative with homemade guacamole!" +
                "\nSimple Guacamole: The simplest version of guacamole is just mashed avocados with salt. Don’t let the lack of availability of other ingredient stop you from making guacamole." +
                "\nQuick guacamole: For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados." +
                "\nDon’t have enough avocados? To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great." +
                "\nHere are a few other guacamole recipes to try:" +
                "\nSpicy Three-Chile Guacamole" +
                "\nStrawberry Guacamole" +
                "\nGuacamole with Charred Sweet Corn, Bacon, and Tomato" +
                "\nCopycat Chipotle Guacamole" +
                "\nBacon and Blue Cheese Guacamole" +
                "\nAuthentic guacamole in a bowl with chips" +
                "\nOTHER WAYS TO USE GUACAMOLE" +
                "\nGuacamole has a role in the kitchen beyond a party dip, of course. It’s great scooped on top of nachos and also makes an excellent topping or side for enchiladas, tacos, grilled salmon, or oven-baked chicken." +
                "\nGuacamole is great in foods, as well. Try mixing some guacamole into a tuna sandwich or your next batch of deviled eggs." +
                "\nHOW TO STORE GUACAMOLE" +
                "\nGuacamole is best eaten right after it’s made. Like apples, avocados start to oxidize and turn brown once they’ve been cut. That said, the acid in the lime juice you add to guacamole can help slow down that process, and if you store the guacamole properly, you can easily make it a few hours ahead if you are preparing for a party." +
                "\nThe trick to keeping guacamole green is to make sure air doesn’t touch it! Transfer it to a container, cover with plastic wrap, and press down on the plastic wrap to squeeze out any air pockets. Make sure any exposed surface of the guacamole is touching the plastic wrap, not air. This will keep the quantity of browning to a minimum." +
                "\nYou can store the guacamole in the fridge this way for up to three days." +
                "\nIf you leave the guacamole exposed to air, it will start to brown and discolor. That browning isn’t very appetizing, but the guacamole is still good. You can either scrape off the brown parts and discard, or stir them into the rest of the guacamole.");

        recipe1.setNotes(notes1);

        Arrays.stream(recipe1Ingredients).forEach(ingredient_ -> {
            recipe1.addIngredient(transformIngredient(ingredient_));
        });

        recipe1.setServings(4);
        recipe1.setPrepTime(4);
        recipe1.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        Category category1 = categoryRepository.findByNameIgnoreCase("Mexican")
                .orElseThrow(() -> new RuntimeException("Category not found..."));
        recipe1.addCategory(category1);

        log.debug("Creating recipe 2");
        Recipe recipe2 = new Recipe();
        recipe2.setDifficulty(Difficulty.MODERATE);
        recipe2.setDescription("Spicy Grilled Chicken Tacos");

        String[] recipe2Ingredients = {"2 tablespoon ancho chili powder",
                "1 teaspoon dried oregano",
                "1 teaspoon dried cumin",
                "1 teaspoon sugar",
                "0.5 teaspoon salt",
                "1 unit clove garlic, finely chopped",
                "1 tablespoon finely grated orange zest",
                "3 tablespoon fresh-squeezed orange juice",
                "2 tablespoon olive oil",
                "6 unit skinless, boneless chicken thighs (1 1/4 pounds)",
                "8 unit small corn tortillas",
                "3 cup packed baby arugula (3 ounces)",
                "2 unit medium ripe avocados, sliced",
                "4 unit radishes, thinly sliced",
                "0.5 pint cherry tomatoes, halved",
                "0.25 unit red onion, thinly sliced",
                "1 unit Roughly chopped cilantro (I hate it)",
                "0.5 cup sour cream thinned with 1/4 cup milk",
                "1 unit lime, cut into wedges"};

        recipe2.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat."
                + "\n2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over."
                + "Set aside to marinate while the grill heats and you prepare the rest of the toppings."
                + "\n3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes."
                + "\n4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side."
                + "Wrap warmed tortillas in a tea towel to keep them warm until serving."
                + "\n5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.");

        Notes notes2 = new Notes();
        notes2.setRecipeNotes("We have a family motto and it is this: Everything goes better in a tortilla." +
                "\nAny and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house." +
                "\nToday’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!" +
                "\nFirst, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings." +
                "\nGrill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!" +
                "\nSpicy Grilled Chicken TacosThe ancho chiles I use in the marinade are named for their wide shape. They are large, have a deep reddish brown color when dried, and are mild in flavor with just a hint of heat. You can find ancho chile powder at any markets that sell Mexican ingredient, or online." +
                "\nI like to put all the toppings in little bowls on a big platter at the center of the table: avocados, radishes, tomatoes, red onions, wedges of lime, and a sour cream sauce. I add arugula, as well – this green isn’t traditional for tacos, but we always seem to have some in the fridge and I think it adds a nice green crunch to the tacos." +
                "\nEveryone can grab a warm tortilla from the pile and make their own tacos just they way they like them." +
                "\nYou could also easily double or even triple this recipe for a larger party. A taco and a cold beer on a warm day? Now that’s living!");
        recipe2.setNotes(notes2);

        Category category2 = categoryRepository.findByNameIgnoreCase("American")
                .orElseThrow(() -> new RuntimeException("Category not found..."));
        recipe2.addCategory(category1);
        recipe2.addCategory(category2);

        Arrays.stream(recipe2Ingredients).forEach(ingredient_ -> {
            recipe2.addIngredient(transformIngredient(ingredient_));
        });

        recipe2.setServings(6);
        recipe2.setPrepTime(35);
        recipe2.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");

        log.debug("Persisting recipes...");

        recipeRepository.save(recipe1);
        recipeRepository.save(recipe2);
    }

    private Ingredient transformIngredient(String ingredientText) {
        ArrayList<String> splitIngredient = new ArrayList<String>(Arrays.asList(ingredientText.split(" ")));
        Ingredient ingredient = new Ingredient();
        ingredient.setQuantity(new BigDecimal(splitIngredient.get(0)));
        ingredient.setUnitOfMeasure(unitOfMeasureRepository.findByDescriptionIgnoreCase(splitIngredient.get(1)).get());
        ingredient.setDescription(String.join(" ", splitIngredient.subList(2, splitIngredient.size())));

        return ingredient;
    }
}
