package com.klikli_dev.theurgykubejs.kubejs;

import com.klikli_dev.theurgykubejs.AlchemicalSulfurItemType;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.registry.RegistryInfo;

public class TheurgyKubeJSPlugin extends KubeJSPlugin {

    @Override
    public void init() {
        RegistryInfo.ITEM.addType("theurgy:alchemical_sulfur", AlchemicalSulfurItemType.class, AlchemicalSulfurItemType::new);
    }
}
