// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.theurgykubejs;

import com.klikli_dev.theurgy.Theurgy;
import com.klikli_dev.theurgy.content.item.derivative.render.AlchemicalDerivativeBEWLR;
import com.klikli_dev.theurgy.registry.SulfurRegistry;
import com.klikli_dev.theurgy.tooltips.TooltipHandler;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;
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

        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(TheurgyKubeJS.Client::onClientSetup);
            modEventBus.addListener(TheurgyKubeJS.Client::onRegisterClientExtensions);
        }
    }

    public static ResourceLocation loc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public static class Client {
        public static List<Item> alchemicalDerivativeItems = new ArrayList<>();

        public static void onClientSetup(FMLClientSetupEvent event) {
            TooltipHandler.registerNamespaceToListenTo("kubejs");
        }

        public static void registerAlchemicalDerivativeItem(Item item) {
            alchemicalDerivativeItems.add(item);
        }

        public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
            var alchemicalDerivativeItemExtension = new IClientItemExtensions() {
                @Override
                public @NotNull BlockEntityWithoutLevelRenderer getCustomRenderer() {
                    return AlchemicalDerivativeBEWLR.get();
                }
            };

            alchemicalDerivativeItems.forEach(derivative -> {
                event.registerItem(alchemicalDerivativeItemExtension, derivative);
            });

            alchemicalDerivativeItems.clear();
        }
    }
}
