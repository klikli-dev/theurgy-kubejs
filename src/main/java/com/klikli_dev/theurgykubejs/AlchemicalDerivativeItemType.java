// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.theurgykubejs;

import com.google.gson.JsonObject;
import com.klikli_dev.theurgy.content.item.derivative.AlchemicalDerivativeTier;
import dev.latvian.mods.kubejs.client.LangKubeEvent;
import dev.latvian.mods.kubejs.generator.KubeAssetGenerator;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.util.ID;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;

public abstract class AlchemicalDerivativeItemType extends ItemBuilder {

    public transient Identifier sourceItem;
    public transient Identifier sourceTag;
    public transient Identifier jarIcon;
    public transient String sourceName;
    public transient AlchemicalDerivativeTier derivativeTier;

    public transient boolean generateTooltipLangEntry;
    public transient boolean generateNameLangEntry;
    public transient boolean provideDerivativeInformationAsTooltipParam;
    public transient boolean provideDerivativeInformationAsNameParam;

    public AlchemicalDerivativeItemType(Identifier id) {
        super(id);

        this.sourceItem = Identifier.fromNamespaceAndPath("minecraft", "stone");
        this.jarIcon = Identifier.fromNamespaceAndPath("theurgy", "empty_jar_icon");
        this.sourceName = "";
        this.derivativeTier = AlchemicalDerivativeTier.ABUNDANT;
        this.generateTooltipLangEntry = true;
        this.generateNameLangEntry = true;
        this.provideDerivativeInformationAsTooltipParam = true;
        this.provideDerivativeInformationAsNameParam = true;
    }

    @Override
    public void generateItemModels(KubeAssetGenerator generator) {
        TheurgyKubeJS.LOGGER.info("Generating item model for {}", this.id);

        JsonObject model = new JsonObject();
        model.addProperty("type", "minecraft:special");
        model.addProperty("base", "theurgy:item/derivative_base");

        JsonObject specialModel = new JsonObject();
        specialModel.addProperty("type", "theurgy:alchemical_derivative");
        model.add("model", specialModel);

        JsonObject itemDef = new JsonObject();
        itemDef.add("model", model);

        // Write the item definition to assets/<ns>/items/<path>.json
        var itemDefPath = this.id.withPath(ID.ITEM_DEFINITION);
        TheurgyKubeJS.LOGGER.info("Writing item definition to {}", itemDefPath);
        generator.json(itemDefPath, itemDef);
    }

    protected Item.Properties decorateWithSource(Item.Properties properties) {
        if (this.sourceItem != null) {
            var item = BuiltInRegistries.ITEM.get(this.sourceItem).orElseThrow().value();
            properties.component(
                    com.klikli_dev.theurgy.registry.DataComponentRegistry.SOURCE_ITEM,
                    BuiltInRegistries.ITEM.wrapAsHolder(item)
            );
        } else if (this.sourceTag != null) {
            properties.component(
                    com.klikli_dev.theurgy.registry.DataComponentRegistry.SOURCE_TAG,
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
    public ItemBuilder jarIcon(Identifier id) {
        this.jarIcon = id;
        return this;
    }

    @Info("Sets the item the derivative is made from. This will be used for texts, tooltips and icons. Note: Consider using sourceTag() instead.")
    public ItemBuilder sourceItem(Identifier id) {
        this.sourceItem = id;
        return this;
    }

    @Info("Sets the tag the derivative is made from. This will be used for texts, tooltips and icons.")
    public ItemBuilder sourceTag(Identifier id) {
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
