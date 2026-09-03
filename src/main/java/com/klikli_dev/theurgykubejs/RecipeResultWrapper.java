// SPDX-FileCopyrightText: 2024 klikli_dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.theurgykubejs;

import com.klikli_dev.theurgy.content.recipe.result.RecipeResult;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.latvian.mods.kubejs.KubeJS;
import dev.latvian.mods.kubejs.component.DataComponentWrapper;
import dev.latvian.mods.kubejs.plugin.builtin.wrapper.ItemWrapper;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.util.RegistryAccessContainer;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.Wrapper;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

@Info("Various recipe result related helper methods")
public interface RecipeResultWrapper {

    @Info("Returns an RecipeResult of the input")
    static RecipeResult of(RecipeResult in) {
        return in;
    }

    @Info("Returns an RecipeResult of the input")
    static RecipeResult of(RecipeResult in, int count) {
        return in.copyWithCount(count);
    }

    static RecipeResult wrap(Context cx, @Nullable Object o) {
        while (o instanceof Wrapper w) {
            o = w.unwrap();
        }

        if (o == null || o == ItemStack.EMPTY || o == Items.AIR) {
            return RecipeResult.of(ItemStack.EMPTY);
        } else if (o instanceof TagKey<?> tag) {
            return RecipeResult.of(ItemTags.create(tag.location()));
        }
        else if (o instanceof CharSequence) {
            return ofString(cx, o.toString());
        }


        return RecipeResult.of(ItemWrapper.wrap(cx, o));
    }

    static RecipeResult ofString(Context cx, String s) {
        if (s.isEmpty() || s.equals("-") || s.equals("air") || s.equals("minecraft:air")) {
            return RecipeResult.of(ItemStack.EMPTY);
        } else if (s.equals("*")) {
            throw new UnsupportedOperationException("Wildcard recipe results are not supported");
        } else {
            try {
                return read(cx, new StringReader(s));
            } catch (CommandSyntaxException | RuntimeException e) {
                KubeJS.LOGGER.error("Failed to read recipe result from '" + s + "': " + e);
                return RecipeResult.of(ItemStack.EMPTY);
            }
        }
    }

    static RecipeResult read(Context cx, StringReader reader) throws CommandSyntaxException {
        if (!reader.canRead()) {
            return RecipeResult.of(ItemStack.EMPTY);
        }

        return switch (reader.peek()) {
            case '-' -> {
                reader.skip();
                yield RecipeResult.of(ItemStack.EMPTY);
            }
            case '*' -> {
                reader.skip();
                throw new UnsupportedOperationException("Wildcard recipe results are not supported");
            }
            case '#' -> {
                reader.skip();
                yield RecipeResult.of(ItemTags.create(Identifier.tryParse(reader.readUnquotedString())));
            }
            case '@' -> {
                reader.skip();
                throw new UnsupportedOperationException("Namespaced recipe results are not supported");
            }
            case '%' -> {
                reader.skip();
                throw new UnsupportedOperationException("Creative tab recipe results are not supported");
            }
            case '/' -> {
                throw new UnsupportedOperationException("Regex recipe results are not supported");
            }
            case '[' -> {
                throw new UnsupportedOperationException("Compound recipe results are not supported");
            }
            default -> {
                var itemId = Identifier.tryParse(reader.readUnquotedString());
                var item = BuiltInRegistries.ITEM.get(itemId).orElseThrow().value();

                var next = reader.canRead() ? reader.peek() : 0;

                if (next == '[' || next == '{') {
                    DataComponentMap components = DataComponentWrapper.readMap(RegistryAccessContainer.of(cx).nbt(), reader);

                    if (!components.isEmpty()) {
                        var stack = new ItemStack(item);
                        stack.applyComponents(components);
                        yield RecipeResult.of(stack);
                    }
                }

                yield RecipeResult.of(new ItemStack(item));
            }
        };
    }
}
