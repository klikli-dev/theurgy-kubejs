// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.theurgykubejs;

import com.google.common.base.Suppliers;
import com.klikli_dev.theurgy.TheurgyConstants;
import com.klikli_dev.theurgy.content.item.sulfur.AlchemicalSulfurItem;
import com.klikli_dev.theurgy.content.item.sulfur.AlchemicalSulfurType;
import com.klikli_dev.theurgy.registry.DataComponentRegistry;
import dev.latvian.mods.kubejs.client.LangKubeEvent;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class AlchemicalSulfurItemType extends AlchemicalDerivativeItemType {

    public transient AlchemicalSulfurType sulfurType;

    public AlchemicalSulfurItemType(Identifier id) {
        super(id);
    }

    protected Item.Properties decorateWithSource(Item.Properties properties) {
        if (this.sourceItem != null) {
            var item = BuiltInRegistries.ITEM.get(this.sourceItem).orElseThrow().value();
            properties.component(
                    DataComponentRegistry.SOURCE_ITEM,
                    BuiltInRegistries.ITEM.wrapAsHolder(item)
            );
        } else if (this.sourceTag != null) {
            properties.component(
                    DataComponentRegistry.SOURCE_TAG,
                    ItemTags.create(this.sourceTag)
            );
        }
        return properties;
    }

    @Override
    public Item createObject() {
        var item = new AlchemicalSulfurItem(
                this.decorateWithSource(this.createItemProperties())
        );

        item.useCustomSourceName(true)
                .autoTooltip(this.provideDerivativeInformationAsTooltipParam, false)
                .autoName(this.provideDerivativeInformationAsNameParam, false)
                .withJarIcon(Suppliers.memoize(() -> new ItemStack(BuiltInRegistries.ITEM.get(this.jarIcon).orElseThrow())))
                .tier(this.derivativeTier);
        item.type(this.sulfurType);

        return item;
    }

    @Info("Sets the Sulfur Type (MISC, GEMS, METALS, OTHER_MINERALS, LOGS, CROPS, ANIMALS, MOBS).")
    public ItemBuilder sulfurType(AlchemicalSulfurType type) {
        this.sulfurType = type;
        return this;
    }

    public void generateLang(LangKubeEvent lang) {
        // call super as we still use the display name for the 'upgrade description'
        // we don't use a custom lang key for that as vanillas format depends on it being an upgrade or trim, and we don't know which it is
        super.generateLang(lang);

        if (this.generateNameLangEntry) {
            lang.add(this.id.getNamespace(), this.getBuilderTranslationKey(), "Alchemical Sulfur %s");
            lang.add(this.id.getNamespace(), this.getBuilderTranslationKey() + TheurgyConstants.I18n.Item.ALCHEMICAL_DERIVATIVE_SOURCE_SUFFIX, this.sourceName);
        }
        if (this.generateTooltipLangEntry) {
            lang.add(this.id.getNamespace(), this.getBuilderTranslationKey() + TheurgyConstants.I18n.Tooltip.SUFFIX, "Alchemical Sulfur crafted from %s %s %s.");
            lang.add(this.id.getNamespace(), this.getBuilderTranslationKey() + TheurgyConstants.I18n.Tooltip.EXTENDED_SUFFIX, "Sulfur represents the \"idea\" or \"soul\" of an object");
            lang.add(this.id.getNamespace(), this.getBuilderTranslationKey() + TheurgyConstants.I18n.Tooltip.USAGE_SUFFIX, "Sulfur is the central element used in Spagyrics processes." +
                    "\n\n" + ChatFormatting.ITALIC + "Hint: Sulfurs crafted from different states of the same material (such as from Ore or Ingots) are interchangeable." + ChatFormatting.RESET);
        }
    }
}
