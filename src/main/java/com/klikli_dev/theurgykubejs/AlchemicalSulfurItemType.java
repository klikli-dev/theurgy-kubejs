// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.theurgykubejs;

import com.google.common.base.Suppliers;
import com.klikli_dev.theurgy.TheurgyConstants;
import com.klikli_dev.theurgy.content.item.AlchemicalSulfurItem;
import com.klikli_dev.theurgy.content.item.AlchemicalSulfurTier;
import com.klikli_dev.theurgy.content.item.AlchemicalSulfurType;
import com.klikli_dev.theurgy.tooltips.TooltipHandler;
import dev.latvian.mods.kubejs.client.LangEventJS;
import dev.latvian.mods.kubejs.generator.AssetJsonGenerator;
import dev.latvian.mods.kubejs.generator.DataJsonGenerator;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.util.UtilsJS;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;

public class AlchemicalSulfurItemType extends ItemBuilder {

    public transient ResourceLocation sourceItem;
    public transient String sourceName;
    public transient AlchemicalSulfurTier sulfurTier;
    public transient AlchemicalSulfurType sulfurType;

    public AlchemicalSulfurItemType(ResourceLocation rl) {
        super(rl);

        this.sourceItem = new ResourceLocation("minecraft", "stone");
        this.sourceName = "";
        this.sulfurTier = AlchemicalSulfurTier.ABUNDANT;
        this.sulfurType = AlchemicalSulfurType.MISC;

        this.parentModel("minecraft:builtin/entity");
    }

    @Override
    public Item createObject() {
        var item =  new AlchemicalSulfurItem(
                this.createItemProperties(),
                Suppliers.memoize(() -> new ItemStack(BuiltInRegistries.ITEM.get(sourceItem)))
        )
                .overrideSourceName(true)
                .tier(sulfurTier)
                .type(sulfurType);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            TooltipHandler.registerTooltipDataProvider(item, AlchemicalSulfurItem::getTooltipData);
        }

        return item;
    }

    @Info("Sets the item the sulfur is made from. This will be used for texts, tooltips and icons.")
    public ItemBuilder sourceItem(ResourceLocation id) {
        sourceItem = id;
        return this;
    }

    @Info("Sets the name that will be displayed as the source name for this sulfur")
    public ItemBuilder sourceName(String name) {
        sourceName = name;
        return this;
    }


    @Info("Sets the Sulfur Tier (ABUNDANT, COMMON, RARE, PRECIOUS).")
    public ItemBuilder sulfurTier(AlchemicalSulfurTier tier) {
        sulfurTier = tier;
        return this;
    }

    @Info("Sets the Sulfur Type (MISC, GEMS, METALS, OTHER_MINERALS).")
    public ItemBuilder sulfurType(AlchemicalSulfurType type) {
        sulfurType = type;
        return this;
    }

    @Override
    public void generateDataJsons(DataJsonGenerator generator) {
        super.generateDataJsons(generator);
        //TODO: consider providing some default recipes here
    }

    public void generateLang(LangEventJS lang) {
        super.generateLang(lang);

        lang.add(id.getNamespace(), getBuilderTranslationKey(), "Alchemical Sulfur %s");
        lang.add(id.getNamespace(), getBuilderTranslationKey() + TheurgyConstants.I18n.Item.ALCHEMICAL_SULFUR_SOURCE_SUFFIX, this.sourceName);
        lang.add(id.getNamespace(), getBuilderTranslationKey() + TheurgyConstants.I18n.Tooltip.SUFFIX, "Alchemical Sulfur crafted from %s %s %s.");
        lang.add(id.getNamespace(), getBuilderTranslationKey() + TheurgyConstants.I18n.Tooltip.EXTENDED_SUFFIX, "Sulfur represents the \"idea\" or \"soul\" of an object");
        lang.add(id.getNamespace(), getBuilderTranslationKey() + TheurgyConstants.I18n.Tooltip.USAGE_SUFFIX, "Sulfur is the central element used in Spagyrics processes." +
                "\n\n" + ChatFormatting.ITALIC + "Hint: Sulfurs crafted from different states of the same material (such as from Ore or Ingots) are interchangeable." + ChatFormatting.RESET);
    }


    @Override
    public void generateAssetJsons(AssetJsonGenerator generator) {
        super.generateAssetJsons(generator);
//        if (modelJson != null) {
//            generator.json(AssetJsonGenerator.asItemModelLocation(id), modelJson);
//            return;
//        }
//
//        generator.itemModel(id, m -> {
//            if (!parentModel.isEmpty()) {
//                m.parent(parentModel);
//            } else {
//                m.parent("minecraft:item/generated");
//            }
//
//            if (textureJson.size() == 0) {
//                texture(newID("item/", "").toString());
//            }
//
//            m.textures(textureJson);
//        });
    }


}
