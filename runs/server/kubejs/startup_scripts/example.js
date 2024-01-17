// priority: 0

// Visit the wiki for more info - https://kubejs.com/

console.info('Hello, World! (Loaded startup scripts)')

StartupEvents.registry('item', (event) => {
    event.create('a_test_sulfur', 'theurgy:alchemical_sulfur')
        .sourceItem('minecraft:rotten_flesh')
        .sourceName("Rotten Flesh")
        .sulfurTier("abundant")
        .sulfurType("misc")
})
