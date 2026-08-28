// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.theurgykubejs.kubejs;

import com.klikli_dev.theurgy.Theurgy;
import com.klikli_dev.theurgy.content.recipe.result.RecipeResult;
import com.klikli_dev.theurgy.registry.RecipeTypeRegistry;
import com.klikli_dev.theurgykubejs.*;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentTypeRegistry;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaRegistry;
import dev.latvian.mods.kubejs.registry.BuilderTypeRegistry;
import dev.latvian.mods.kubejs.script.BindingRegistry;
import dev.latvian.mods.kubejs.script.TypeWrapperRegistry;
import net.minecraft.core.registries.Registries;

public class TheurgyKubeJSPlugin implements KubeJSPlugin {

    @Override
    public void registerBuilderTypes(BuilderTypeRegistry registry) {
        registry.of(Registries.ITEM, reg -> {
            reg.add(Theurgy.loc("alchemical_sulfur"), AlchemicalSulfurItemType.class, AlchemicalSulfurItemType::new);
            reg.add(Theurgy.loc("alchemical_niter"), AlchemicalNiterItemType.class, AlchemicalNiterItemType::new);
        });
    }

    @Override
    public void registerRecipeSchemas(RecipeSchemaRegistry registry) {
        registry.register(RecipeTypeRegistry.CALCINATION.getId(), TheurgyRecipeSchema.CALCINATION);
        registry.register(RecipeTypeRegistry.LIQUEFACTION.getId(), TheurgyRecipeSchema.LIQUEFACTION);
        registry.register(RecipeTypeRegistry.DISTILLATION.getId(), TheurgyRecipeSchema.DISTILLATION);
        registry.register(RecipeTypeRegistry.INCUBATION.getId(), TheurgyRecipeSchema.INCUBATION);
        registry.register(RecipeTypeRegistry.ACCUMULATION.getId(), TheurgyRecipeSchema.ACCUMULATION);
        registry.register(RecipeTypeRegistry.CATALYSATION.getId(), TheurgyRecipeSchema.CATALYSATION);
        registry.register(RecipeTypeRegistry.REFORMATION.getId(), TheurgyRecipeSchema.REFORMATION);
        registry.register(RecipeTypeRegistry.FERMENTATION.getId(), TheurgyRecipeSchema.FERMENTATION);
        registry.register(RecipeTypeRegistry.DIGESTION.getId(), TheurgyRecipeSchema.DIGESTION);
    }

    @Override
    public void registerRecipeComponents(RecipeComponentTypeRegistry registry) {
        registry.unit(new RecipeResultComponent(RecipeResult.CODEC));
    }

    @Override
    public void registerBindings(BindingRegistry bindings) {
        bindings.add("RecipeResult", RecipeResultWrapper.class);
    }

    @Override
    public void registerTypeWrappers(TypeWrapperRegistry registry) {
        registry.register(RecipeResult.class, RecipeResultWrapper::wrap);
    }
}
