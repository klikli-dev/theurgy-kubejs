// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

// priority: 0

// Visit the wiki for more info - https://kubejs.com/

console.info('Hello, World! (Loaded startup scripts)')

//NOTE: In order for tooltips to work, the namespace of the item must be registered with the theurgy tooltip handler.
//      This can be done in code (with a mod) using: TooltipHandler.registerNamespaceToListenTo("<my_namespace>");
//      Or via config in theurgy-server.toml (either in ./defaultconfigs or ./saves/<world>/serverconfigs) in the section [tooltipHandler] with the key additionalTooltipHandlerNamespaces = ["<my_namespace>"] (If you load into a world once, an empty config is created for you that you can edit and copy into ./defaultconfigs for your pack.

StartupEvents.registry('item', (event) => {
    event.create('a_test_sulfur', 'theurgy:alchemical_sulfur')
        .sourceItem('minecraft:rotten_flesh')
        .sourceName("Rotten Flesh")
        .derivativeTier("abundant")
        .sulfurType("misc")
})

StartupEvents.registry('item', (event) => {
	event.create('a_test_niter', 'theurgy:alchemical_niter')
		.niterIcon('minecraft:spider_eye') //ideally an item with a custom icon should be used here. Theurgy e.g. uses the item "gems_abundant_icon" for the abundant gems niter
		.sourceName("Monster Parts")
		.derivativeTier("common")
})