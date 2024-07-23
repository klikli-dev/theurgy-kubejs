package com.klikli_dev.theurgykubejs;

import com.klikli_dev.theurgy.content.recipe.CalcinationRecipe;
import com.klikli_dev.theurgy.content.recipe.result.RecipeResult;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.util.TickDuration;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

public interface TheurgyRecipeSchema {


    RecipeKey<SizedIngredient> SIZED_INGREDIENT = SizedIngredientComponent.NESTED.inputKey("ingredient");


    RecipeKey<RecipeResult> RECIPE_RESULT = RecipeResultComponent.RECIPE_RESULT.outputKey("result");
    RecipeKey<ItemStack> ITEM_STACK_RESULT = ItemStackComponent.ITEM_STACK.outputKey("result");

    RecipeKey<Ingredient> INGREDIENT = IngredientComponent.INGREDIENT.inputKey("ingredient");

    RecipeKey<TickDuration> CALCINATION_TIME = TimeComponent.TICKS.key("time", ComponentRole.OTHER).optional(new TickDuration(CalcinationRecipe.DEFAULT_TIME));

    RecipeSchema CALCINATION = new RecipeSchema(ITEM_STACK_RESULT, SIZED_INGREDIENT, CALCINATION_TIME);
}
