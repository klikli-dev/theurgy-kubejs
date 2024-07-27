// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.theurgykubejs;

import com.google.common.base.Suppliers;
import com.klikli_dev.theurgy.TheurgyConstants;
import com.klikli_dev.theurgy.content.item.derivative.AlchemicalDerivativeTier;
import com.klikli_dev.theurgy.content.item.sulfur.AlchemicalSulfurItem;
import com.klikli_dev.theurgy.content.item.sulfur.AlchemicalSulfurType;
import com.klikli_dev.theurgy.registry.DataComponentRegistry;
import com.klikli_dev.theurgy.tooltips.TooltipHandler;
import dev.latvian.mods.kubejs.client.LangKubeEvent;
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

public class AlchemicalSulfurItemType extends AlchemicalDerivativeItemType {

    public transient AlchemicalSulfurType sulfurType;

    public AlchemicalSulfurItemType(ResourceLocation rl) {
        super(rl);
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

    @Override
    public Item createObject() {
        var item = new AlchemicalSulfurItem(
                this.decorateWithSource(this.createItemProperties())
        );

        item.useCustomSourceName(true)
                .autoTooltip(this.provideDerivativeInformationAsTooltipParam, false) //lang gen is always false because theurgy datagen never runs, it is done here in this kubejs adapter class
                .autoName(this.provideDerivativeInformationAsNameParam, false) //lang gen is always false because theurgy datagen never runs, it is done here in this kubejs adapter class
                .withJarIcon(Suppliers.memoize(() -> new ItemStack(BuiltInRegistries.ITEM.get(this.jarIcon))))
                .tier(this.derivativeTier);
        item.type(this.sulfurType);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            TooltipHandler.registerTooltipDataProvider(item, item::getTooltipData);
            TheurgyKubeJS.Client.registerAlchemicalDerivativeItem(item);
        }

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
