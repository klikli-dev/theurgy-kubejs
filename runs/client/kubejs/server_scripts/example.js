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
})
