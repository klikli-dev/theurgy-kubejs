// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.theurgykubejs;

import com.klikli_dev.theurgy.tooltips.TooltipHandler;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;


@Mod(TheurgyKubeJS.MODID)
public class TheurgyKubeJS {
    public static final String MODID = "theurgy_kubejs";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static TheurgyKubeJS INSTANCE;

    public TheurgyKubeJS() {
        INSTANCE = this;

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(TheurgyKubeJS.Client::onClientSetup);
        }
    }

    public static ResourceLocation loc(String path) {
        return new ResourceLocation(MODID, path);
    }

    public static class Client {
        public static void onClientSetup(FMLClientSetupEvent event) {
            TooltipHandler.registerNamespaceToListenTo("kubejs");
        }
    }
}
