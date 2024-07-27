// SPDX-FileCopyrightText: 2024 klikli_dev
//
// SPDX-License-Identifier: MIT

// priority: 0

// Visit the wiki for more info - https://kubejs.com/

console.info('Hello, World! (Loaded server scripts)')

ServerEvents.recipes((event) => {
    //Some examples of how to add recipes:

    event.recipes.theurgy.calcination(
        "2x minecraft:iron_ingot",
        '2x #minecraft:swords',
        100
    )

    event.recipes.theurgy.liquefaction(
        "2x minecraft:iron_ingot",
        '#minecraft:swords',
        `1bx minecraft:water`,
        100
    )

    event.recipes.theurgy.distillation(
        "2x minecraft:iron_ingot",
        '2x #minecraft:swords',
        100
    )

    event.recipes.theurgy.incubation(
        RecipeResult.of("#minecraft:swords"), //not a good idea, but just to show tags as output
        'theurgy:mercury_shard',
        'theurgy:alchemical_salt_plant',
        'theurgy:alchemical_sulfur_wheat',
        100
    )

    event.recipes.theurgy.accumulation(
        "1000x minecraft:water",
        '100x minecraft:water',
        'minecraft:ice',
        100
    )

    //No jei/emi integration for catalysation in theurgy, so you have to test that by actually putting it in a catalyst
    event.recipes.theurgy.catalysation(
        "minecraft:ice",
        500,
        20
    )

    event.recipes.theurgy.reformation(
        "minecraft:iron_ingot",
        ["minecraft:copper_ingot"],
        "minecraft:iron_ingot",
        20,
        100
    )

    event.recipes.theurgy.fermentation(
        "2x minecraft:iron_ingot",
        '1bx minecraft:water',
        ['minecraft:iron_ingot', 'minecraft:copper_ingot'],
        100
    )

    event.recipes.theurgy.digestion(
        "2x minecraft:iron_ingot",
        '1bx minecraft:water',
        ['2x minecraft:copper_ingot', 'minecraft:iron_nugget'],
        100
    )
})
