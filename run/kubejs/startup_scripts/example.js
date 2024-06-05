// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

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

StartupEvents.registry('item', (event) => {
	event.create('a_test_niter', 'theurgy:alchemical_niter')
		.niterIcon('minecraft:spider_eye') //ideally an item with a custom icon should be used here. Theurgy e.g. uses the item "gems_abundant_icon" for the abundant gems niter
		.sourceName("Monster Parts")
		.sulfurTier("common")
})