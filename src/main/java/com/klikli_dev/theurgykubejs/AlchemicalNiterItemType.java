// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.theurgykubejs;

import com.google.common.base.Suppliers;
import com.klikli_dev.theurgy.TheurgyConstants;
import com.klikli_dev.theurgy.content.item.niter.AlchemicalNiterItem;
import dev.latvian.mods.kubejs.client.LangKubeEvent;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class AlchemicalNiterItemType extends AlchemicalDerivativeItemType {

    public AlchemicalNiterItemType(Identifier id) {
        super(id);

        this.jarIcon(Identifier.fromNamespaceAndPath("theurgy", "empty_ceramic_jar_icon"));
    }

    @Override
    public Item createObject() {
        var item = new AlchemicalNiterItem(
                this.decorateWithSource(this.createItemProperties()),
                this.derivativeTier
        );

        item.useCustomSourceName(true)
                .autoTooltip(this.provideDerivativeInformationAsTooltipParam, false)
                .autoName(this.provideDerivativeInformationAsNameParam, false)
                .withJarIcon(Suppliers.memoize(() -> new ItemStack(BuiltInRegistries.ITEM.get(this.jarIcon).orElseThrow())));

        return item;
    }


    @Info("Sets the item that will be used as icon for the niter. Ideally a dummy item with a fitting icon should be created for this purpose. Normal KubeJS items can be used for this.")
    public ItemBuilder niterIcon(Identifier id) {
        return this.sourceItem(id);
    }

    public void generateLang(LangKubeEvent lang) {
        super.generateLang(lang);

        if (this.generateNameLangEntry) {
            lang.add(this.id.getNamespace(), this.getBuilderTranslationKey(), "Alchemical Niter %s");
            lang.add(this.id.getNamespace(), this.getBuilderTranslationKey() + TheurgyConstants.I18n.Item.ALCHEMICAL_DERIVATIVE_SOURCE_SUFFIX, this.sourceName);
        }

        if (this.generateTooltipLangEntry) {
            lang.add(this.id.getNamespace(), this.getBuilderTranslationKey() + TheurgyConstants.I18n.Tooltip.SUFFIX, "Alchemical Niter is crafted from Alchemical Sulfur of any %s.");
            lang.add(this.id.getNamespace(), this.getBuilderTranslationKey() + TheurgyConstants.I18n.Tooltip.EXTENDED_SUFFIX, "Niter represents the abstract category and value of an object, thus it is a further abstraction the \"idea\" or \"soul\" represented by Sulfur.");
            lang.add(this.id.getNamespace(), this.getBuilderTranslationKey() + TheurgyConstants.I18n.Tooltip.USAGE_SUFFIX, "Niter extraction is a required intermediate step to transform one type of Sulfur into another type.");
        }
    }
}
