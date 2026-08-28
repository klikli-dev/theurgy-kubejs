// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.theurgykubejs;

import com.klikli_dev.theurgy.content.item.derivative.AlchemicalDerivativeItem;
import com.klikli_dev.theurgy.tooltips.TooltipHandler;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;


@Mod(TheurgyKubeJS.MODID)
public class TheurgyKubeJS {
    public static final String MODID = "theurgy_kubejs";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static TheurgyKubeJS INSTANCE;

    public TheurgyKubeJS(IEventBus modEventBus, ModContainer modContainer) {
        INSTANCE = this;

        if (FMLEnvironment.getDist() == Dist.CLIENT) {
            modEventBus.addListener(TheurgyKubeJS.Client::onClientSetup);
        }
    }

    public static Identifier loc(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }

    public static class Client {
        public static List<Item> alchemicalDerivativeItems = new ArrayList<>();

        public static void onClientSetup(FMLClientSetupEvent event) {
            TooltipHandler.registerNamespaceToListenTo("kubejs");

            BuiltInRegistries.ITEM.stream()
                    .filter(item -> item instanceof AlchemicalDerivativeItem derivative && derivative.provideAutomaticTooltipData)
                    .map(AlchemicalDerivativeItem.class::cast)
                    .forEach(derivative -> TooltipHandler.registerTooltipDataProvider(derivative, derivative::getTooltipData));
        }

        public static void registerAlchemicalDerivativeItem(Item item) {
            alchemicalDerivativeItems.add(item);
        }
    }
}
