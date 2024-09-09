// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.theurgykubejs;

import com.klikli_dev.theurgy.content.item.derivative.AlchemicalDerivativeTier;
import com.klikli_dev.theurgy.registry.DataComponentRegistry;
import dev.latvian.mods.kubejs.client.LangKubeEvent;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;

public abstract class AlchemicalDerivativeItemType extends ItemBuilder {

    public transient ResourceLocation sourceItem;
    public transient ResourceLocation sourceTag;
    public transient ResourceLocation jarIcon;
    public transient String sourceName;
    public transient AlchemicalDerivativeTier derivativeTier;

    public transient boolean generateTooltipLangEntry;
    public transient boolean generateNameLangEntry;
    public transient boolean provideDerivativeInformationAsTooltipParam;
    public transient boolean provideDerivativeInformationAsNameParam;

    public AlchemicalDerivativeItemType(ResourceLocation rl) {
        super(rl);

        this.sourceItem = ResourceLocation.fromNamespaceAndPath("minecraft", "stone");
        this.jarIcon = ResourceLocation.fromNamespaceAndPath("theurgy", "empty_jar_icon");
        this.sourceName = "";
        this.derivativeTier = AlchemicalDerivativeTier.ABUNDANT;
        this.generateTooltipLangEntry = true;
        this.generateNameLangEntry = true;
        this.provideDerivativeInformationAsTooltipParam = true;
        this.provideDerivativeInformationAsNameParam = true;

        this.parentModel(ResourceLocation.fromNamespaceAndPath("minecraft", "builtin/entity"));
    }

    protected Item.Properties decorateWithSource(Item.Properties properties) {
        if (this.sourceItem != null) {
            properties.component(
                    DataComponentRegistry.SOURCE_ITEM,
                    BuiltInRegistries.ITEM.getHolder(this.sourceItem).get()
            );
        } else if (this.sourceTag != null) {
            properties.component(
                    DataComponentRegistry.SOURCE_TAG,
                    ItemTags.create(this.sourceTag)
            );
        }
        return properties;
    }

    @Info("If true, KubeJS will generate a lang file entry for the tooltip of this derivative with default texts.")
    public ItemBuilder generateTooltipLangEntry(boolean value) {
        this.generateTooltipLangEntry = value;
        return this;
    }

    @Info("If true, KubeJS will generate a lang file entry for the name of this derivative with default texts.")
    public ItemBuilder generateNameLangEntry(boolean value) {
        this.generateNameLangEntry = value;
        return this;
    }

    @Info("If true, the tooltip can access alchemical derivative information as \"%s\" params. Should generally always be true.")
    public ItemBuilder provideDerivativeInformationAsTooltipParam(boolean value) {
        this.provideDerivativeInformationAsTooltipParam = value;
        return this;
    }

    @Info("If true, the item name can access alchemical derivative information as \"%s\" params. Should generally always be true.")
    public ItemBuilder provideDerivativeInformationAsNameParam(boolean value) {
        this.provideDerivativeInformationAsNameParam = value;
        return this;
    }

    @Info("Sets the item that will be used as jar icon. This will be rendered as background behind the source item.")
    public ItemBuilder jarIcon(ResourceLocation id) {
        this.jarIcon = id;
        return this;
    }

    @Info("Sets the item the derivative is made from. This will be used for texts, tooltips and icons. Note: Consider using sourceTag() instead.")
    public ItemBuilder sourceItem(ResourceLocation id) {
        this.sourceItem = id;
        return this;
    }

    @Info("Sets the tag the derivative is made from. This will be used for texts, tooltips and icons.")
    public ItemBuilder sourceTag(ResourceLocation id) {
        this.sourceTag = id;
        return this;
    }

    @Info("Sets the name that will be displayed as the source name for this derivative")
    public ItemBuilder sourceName(String name) {
        this.sourceName = name;
        return this;
    }


    @Info("Sets the derivative Tier (ABUNDANT, COMMON, RARE, PRECIOUS).")
    public ItemBuilder derivativeTier(AlchemicalDerivativeTier tier) {
        this.derivativeTier = tier;
        return this;
    }

    @Override
    public void generateLang(LangKubeEvent lang) {
        // call super as we still use the display name for the 'upgrade description'
        // we don't use a custom lang key for that as vanillas format depends on it being an upgrade or trim, and we don't know which it is
        super.generateLang(lang);
    }
}
