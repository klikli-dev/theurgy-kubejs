// SPDX-FileCopyrightText: 2024 klikli_dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.theurgykubejs;

import com.klikli_dev.theurgy.content.recipe.*;
import com.klikli_dev.theurgy.content.recipe.result.RecipeResult;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.util.TickDuration;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import java.util.List;

public interface TheurgyRecipeSchema {
    RecipeKey<SizedIngredient> SIZED_INGREDIENT = SizedIngredientComponent.NESTED.inputKey("ingredient");
    RecipeKey<SizedFluidIngredient> EVAPORANT = SizedFluidIngredientComponent.NESTED.inputKey("evaporant");
    RecipeKey<Ingredient> SOLUTE = IngredientComponent.INGREDIENT.inputKey("solute").defaultOptional();
    RecipeKey<Ingredient> INGREDIENT = IngredientComponent.INGREDIENT.inputKey("ingredient");
    RecipeKey<SizedFluidIngredient> SOLVENT = SizedFluidIngredientComponent.NESTED.inputKey("solvent");
    RecipeKey<Ingredient> MERCURY = IngredientComponent.INGREDIENT.inputKey("mercury");
    RecipeKey<Ingredient> SALT = IngredientComponent.INGREDIENT.inputKey("salt");
    RecipeKey<Ingredient> SULFUR = IngredientComponent.INGREDIENT.inputKey("sulfur");
    RecipeKey<List<SizedIngredient>> SOURCES = SizedIngredientComponent.NESTED.asList().inputKey("sources");
    RecipeKey<Ingredient> TARGET = IngredientComponent.INGREDIENT.inputKey("target");
    RecipeKey<SizedFluidIngredient> SIZED_FLUID = SizedFluidIngredientComponent.NESTED.inputKey("fluid");
    RecipeKey<List<Ingredient>> INGREDIENTS = IngredientComponent.INGREDIENT.asList().inputKey("ingredients");
    RecipeKey<List<SizedIngredient>> SIZED_INGREDIENTS = SizedIngredientComponent.NESTED.asList().inputKey("ingredients");

    RecipeKey<RecipeResult> RECIPE_RESULT = RecipeResultComponent.RECIPE_RESULT.outputKey("result");
    RecipeKey<ItemStack> ITEM_STACK_RESULT = ItemStackComponent.ITEM_STACK.outputKey("result");
    RecipeKey<FluidStack> FLUID_STACK_RESULT = FluidStackComponent.FLUID_STACK.outputKey("result");

    RecipeKey<Integer> TOTAL_MERCURY_FLUX = NumberComponent.INT.outputKey("totalMercuryFlux");
    RecipeKey<Integer> MERCURY_FLUX = NumberComponent.INT.inputKey("mercuryFlux");

    RecipeKey<Integer> MERCURY_FLUX_PER_TICK = NumberComponent.INT.otherKey("mercuryFluxPerTick").optional(CatalysationRecipe.DEFAULT_MERCURY_FLUX_PER_TICK);

    RecipeKey<TickDuration> CALCINATION_TIME = TimeComponent.TICKS.otherKey("time").optional(new TickDuration(CalcinationRecipe.DEFAULT_TIME));
    RecipeKey<TickDuration> ACCUMULATION_TIME = TimeComponent.TICKS.otherKey("time").optional(new TickDuration(AccumulationRecipe.DEFAULT_TIME));
    RecipeKey<TickDuration> LIQUEFACTION_TIME = TimeComponent.TICKS.otherKey("time").optional(new TickDuration(LiquefactionRecipe.DEFAULT_TIME));
    RecipeKey<TickDuration> DISTILLATION_TIME = TimeComponent.TICKS.otherKey("time").optional(new TickDuration(DistillationRecipe.DEFAULT_TIME));
    RecipeKey<TickDuration> INCUBATION_TIME = TimeComponent.TICKS.otherKey("time").optional(new TickDuration(IncubationRecipe.DEFAULT_TIME));
    RecipeKey<TickDuration> REFORMATION_TIME = TimeComponent.TICKS.otherKey("time").optional(new TickDuration(ReformationRecipe.DEFAULT_TIME));
    RecipeKey<TickDuration> FERMENTATION_TIME = TimeComponent.TICKS.otherKey("time").optional(new TickDuration(FermentationRecipe.DEFAULT_TIME));
    RecipeKey<TickDuration> DIGESTION_TIME = TimeComponent.TICKS.otherKey("time").optional(new TickDuration(DigestionRecipe.DEFAULT_TIME));

    RecipeSchema CALCINATION = new RecipeSchema(ITEM_STACK_RESULT, SIZED_INGREDIENT, CALCINATION_TIME);
    RecipeSchema LIQUEFACTION = new RecipeSchema(ITEM_STACK_RESULT, INGREDIENT, SOLVENT, LIQUEFACTION_TIME);
    RecipeSchema DISTILLATION = new RecipeSchema(ITEM_STACK_RESULT, SIZED_INGREDIENT, DISTILLATION_TIME);
    RecipeSchema INCUBATION = new RecipeSchema(RECIPE_RESULT, MERCURY, SALT, SULFUR, INCUBATION_TIME);
    RecipeSchema ACCUMULATION = new RecipeSchema(FLUID_STACK_RESULT, EVAPORANT, SOLUTE, ACCUMULATION_TIME);
    RecipeSchema CATALYSATION = new RecipeSchema(INGREDIENT, TOTAL_MERCURY_FLUX, MERCURY_FLUX_PER_TICK);
    RecipeSchema REFORMATION = new RecipeSchema(ITEM_STACK_RESULT, SOURCES, TARGET, MERCURY_FLUX, REFORMATION_TIME);
    RecipeSchema FERMENTATION = new RecipeSchema(ITEM_STACK_RESULT, SIZED_FLUID, INGREDIENTS, FERMENTATION_TIME);
    RecipeSchema DIGESTION = new RecipeSchema(ITEM_STACK_RESULT, SIZED_FLUID, SIZED_INGREDIENTS, DIGESTION_TIME);
}
