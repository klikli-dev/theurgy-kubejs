// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.theurgykubejs;

import com.klikli_dev.theurgy.TheurgyConstants;
import com.klikli_dev.theurgy.content.item.sulfur.AlchemicalSulfurType;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.resources.ResourceLocation;

public class AlchemicalNiterItemType extends AlchemicalSulfurItemType {

    public AlchemicalNiterItemType(ResourceLocation rl) {
        super(rl);

        this.jarIcon(new ResourceLocation("theurgy", "empty_ceramic_jar_icon"));
        this.sulfurType(AlchemicalSulfurType.NITER);

        this.provideSulfurInformationAsTooltipParam(true);
        this.provideSulfurInformationAsNameParam(true);
    }

    @Info("Sets the item that will be used as icon for the niter. Ideally a dummy item with a fitting icon should be created for this purpose. Normal KubeJS items can be used for this.")
    public ItemBuilder niterIcon(ResourceLocation id) {
        return this.sourceItem(id);
    }

    public void generateLang(LangEventJS lang) {
        super.generateLang(lang);

        if (this.generateNameLangEntry) {
            lang.add(this.id.getNamespace(), this.getBuilderTranslationKey(), "Alchemical Niter %s");
            lang.add(this.id.getNamespace(), this.getBuilderTranslationKey() + TheurgyConstants.I18n.Item.ALCHEMICAL_SULFUR_SOURCE_SUFFIX, this.sourceName);
        }

        if (this.generateTooltipLangEntry) {
            lang.add(this.id.getNamespace(), this.getBuilderTranslationKey() + TheurgyConstants.I18n.Tooltip.SUFFIX, "Alchemical Niter crafted from Alchemical Sulfur of any %s.");
            lang.add(this.id.getNamespace(), this.getBuilderTranslationKey() + TheurgyConstants.I18n.Tooltip.EXTENDED_SUFFIX, "Niter represents the abstract category and value of an object, thus it is a further abstraction the \"idea\" or \"soul\" represented by Sulfur.");
            lang.add(this.id.getNamespace(), this.getBuilderTranslationKey() + TheurgyConstants.I18n.Tooltip.USAGE_SUFFIX, "Niter extraction is a required intermediate step to transform one type of Sulfur into another type.");
        }
    }
}
