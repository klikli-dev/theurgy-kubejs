// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.theurgykubejs.kubejs;

import com.klikli_dev.theurgykubejs.AlchemicalNiterItemType;
import com.klikli_dev.theurgykubejs.AlchemicalSulfurItemType;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.registry.BuilderTypeRegistry;
import net.minecraft.core.registries.Registries;

public class TheurgyKubeJSPlugin implements KubeJSPlugin {

    @Override
    public void registerBuilderTypes(BuilderTypeRegistry registry) {
        registry.of(Registries.ITEM, reg -> {
            reg.add("theurgy:alchemical_sulfur", AlchemicalSulfurItemType.class, AlchemicalSulfurItemType::new);
            reg.add("theurgy:alchemical_niter", AlchemicalNiterItemType.class, AlchemicalNiterItemType::new);
        });
    }
}
