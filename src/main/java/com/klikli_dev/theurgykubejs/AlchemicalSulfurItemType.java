// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.theurgykubejs;

import com.google.common.base.Suppliers;
import com.klikli_dev.theurgy.TheurgyConstants;
import com.klikli_dev.theurgy.content.item.sulfur.AlchemicalSulfurItem;
import com.klikli_dev.theurgy.content.item.sulfur.AlchemicalSulfurTier;
import com.klikli_dev.theurgy.content.item.sulfur.AlchemicalSulfurType;
import com.klikli_dev.theurgy.registry.DataComponentRegistry;
import com.klikli_dev.theurgy.tooltips.TooltipHandler;
import dev.latvian.mods.kubejs.client.LangKubeEvent;
import dev.latvian.mods.kubejs.generator.AssetJsonGenerator;
import dev.latvian.mods.kubejs.generator.DataJsonGenerator;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;

public class AlchemicalSulfurItemType extends ItemBuilder {

    public transient ResourceLocation sourceItem;
    public transient ResourceLocation sourceTag;
    public transient ResourceLocation jarIcon;
    public transient String sourceName;
    public transient AlchemicalSulfurTier sulfurTier;
    public transient AlchemicalSulfurType sulfurType;

    public transient boolean generateTooltipLangEntry;
    public transient boolean generateNameLangEntry;
    public transient boolean provideSulfurInformationAsTooltipParam;
    public transient boolean provideSulfurInformationAsNameParam;

    public AlchemicalSulfurItemType(ResourceLocation rl) {
        super(rl);

        this.sourceItem = ResourceLocation.fromNamespaceAndPath("minecraft", "stone");
        this.jarIcon = ResourceLocation.fromNamespaceAndPath("theurgy", "empty_jar_icon");
        this.sourceName = "";
        this.sulfurTier = AlchemicalSulfurTier.ABUNDANT;
        this.sulfurType = AlchemicalSulfurType.MISC;
        this.generateTooltipLangEntry = true;
        this.generateNameLangEntry = true;
        this.provideSulfurInformationAsTooltipParam = true;
        this.provideSulfurInformationAsNameParam = true;

        this.parentModel("minecraft:builtin/entity");
    }

    protected Item.Properties decorateWithSource(Item.Properties properties) {
        if (this.sourceItem != null) {
            properties.component(
                    DataComponentRegistry.SULFUR_SOURCE_ITEM,
                    BuiltInRegistries.ITEM.getHolder(this.sourceItem).get()
            );
        } else if (this.sourceTag != null) {
            properties.component(
                    DataComponentRegistry.SULFUR_SOURCE_TAG,
                    ItemTags.create(this.sourceTag)
            );
        }
        return properties;
    }

    @Override
    public Item createObject() {
        var item = new AlchemicalSulfurItem(
                decorateWithSource(this.createItemProperties())
        )
                .overrideSourceName(true)
                .autoTooltip(this.provideSulfurInformationAsTooltipParam, false) //lang gen is always false because theurgy datagen never runs, it is done here in this kubejs adapter class
                .autoName(this.provideSulfurInformationAsNameParam, false) //lang gen is always false because theurgy datagen never runs, it is done here in this kubejs adapter class
                .withJarIcon(Suppliers.memoize(() -> new ItemStack(BuiltInRegistries.ITEM.get(this.jarIcon))))
                .tier(this.sulfurTier)
                .type(this.sulfurType);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            TooltipHandler.registerTooltipDataProvider(item, AlchemicalSulfurItem::getTooltipData);
        }

        return item;
    }

    @Info("If true, KubeJS will generate a lang file entry for the tooltip of sulfur with default texts.")
    public ItemBuilder generateTooltipLangEntry(boolean value) {
        this.generateTooltipLangEntry = value;
        return this;
    }

    @Info("If true, KubeJS will generate a lang file entry for the name of this sulfur with default texts.")
    public ItemBuilder generateNameLangEntry(boolean value) {
        this.generateNameLangEntry = value;
        return this;
    }

    @Info("If true, the tooltip can access sulfur information as \"%s\" params. Should generally always be true.")
    public ItemBuilder provideSulfurInformationAsTooltipParam(boolean value) {
        this.provideSulfurInformationAsTooltipParam = value;
        return this;
    }

    @Info("If true, the item name can access sulfur information as \"%s\" params. Should generally always be true.")
    public ItemBuilder provideSulfurInformationAsNameParam(boolean value) {
        this.provideSulfurInformationAsNameParam = value;
        return this;
    }

    @Info("Sets the item that will be used as jar icon. This will be rendered as background behind the source item.")
    public ItemBuilder jarIcon(ResourceLocation id) {
        this.jarIcon = id;
        return this;
    }

    @Info("Sets the item the sulfur is made from. This will be used for texts, tooltips and icons. Note: Consider using sourceTag() instead.")
    public ItemBuilder sourceItem(ResourceLocation id) {
        this.sourceItem = id;
        return this;
    }

    @Info("Sets the tag the sulfur is made from. This will be used for texts, tooltips and icons.")
    public ItemBuilder sourceTag(ResourceLocation id) {
        this.sourceTag = id;
        return this;
    }

    @Info("Sets the name that will be displayed as the source name for this sulfur")
    public ItemBuilder sourceName(String name) {
        this.sourceName = name;
        return this;
    }


    @Info("Sets the Sulfur Tier (ABUNDANT, COMMON, RARE, PRECIOUS).")
    public ItemBuilder sulfurTier(AlchemicalSulfurTier tier) {
        this.sulfurTier = tier;
        return this;
    }

    @Info("Sets the Sulfur Type (MISC, GEMS, METALS, OTHER_MINERALS).")
    public ItemBuilder sulfurType(AlchemicalSulfurType type) {
        this.sulfurType = type;
        return this;
    }

    @Override
    public void generateDataJsons(DataJsonGenerator generator) {
        super.generateDataJsons(generator);
        //TODO: consider providing some default recipes here
    }

    public void generateLang(LangKubeEvent lang) {
        // call super as we still use the display name for the 'upgrade description'
        // we don't use a custom lang key for that as vanillas format depends on it being an upgrade or trim, and we don't know which it is
        super.generateLang(lang);

        if (this.generateNameLangEntry) {
            lang.add(this.id.getNamespace(), this.getBuilderTranslationKey(), "Alchemical Sulfur %s");
            lang.add(this.id.getNamespace(), this.getBuilderTranslationKey() + TheurgyConstants.I18n.Item.ALCHEMICAL_SULFUR_SOURCE_SUFFIX, this.sourceName);
        }
        if (this.generateTooltipLangEntry) {
            lang.add(this.id.getNamespace(), this.getBuilderTranslationKey() + TheurgyConstants.I18n.Tooltip.SUFFIX, "Alchemical Sulfur crafted from %s %s %s.");
            lang.add(this.id.getNamespace(), this.getBuilderTranslationKey() + TheurgyConstants.I18n.Tooltip.EXTENDED_SUFFIX, "Sulfur represents the \"idea\" or \"soul\" of an object");
            lang.add(this.id.getNamespace(), this.getBuilderTranslationKey() + TheurgyConstants.I18n.Tooltip.USAGE_SUFFIX, "Sulfur is the central element used in Spagyrics processes." +
                    "\n\n" + ChatFormatting.ITALIC + "Hint: Sulfurs crafted from different states of the same material (such as from Ore or Ingots) are interchangeable." + ChatFormatting.RESET);
        }
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
