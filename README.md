## Emote Clue Items v4.3.3 [![Plugin Installs](http://img.shields.io/endpoint?url=https://api.runelite.net/pluginhub/shields/installs/plugin/emote-clue-items&label=Active%20installs)](https://runelite.net/plugin-hub/Lars%20van%20Soest)

Emote Clue Items is a RuneLite plugin which highlights items required for emote clue steps and provides a user-friendly
item collection log with STASHUnit integration.

### Take back your bank space

Maintaining bank space can be quite cumbersome, especially when you are not sure which items should be kept for future
clue scrolls. With this plugin, throwing away items may be a bit less stressful, as this plugin aims to highlight all
emote clue items.

### 4.2.0 Feature overivew
- The new 4.2.0 patch enables fill status synchronisation for the noticeboard in Watson's house. 
  > To enable this feature, enable "Sync Watson Board" in the plugin settings.

![Watson notice board sync](/readme/watson-sync.gif)

- In previous news, the recent [v4.1.0 update](https://github.com/larsvansoest/emote-clue-items/releases/tag/v4.1.0) allows [marking STASH unit locations](#mark-stash-unit-locations-on-the-map) on the map and highlights construction level requirements.


#### Filling stashes updates the emote clue item requirement status

Listed under each emote clue item requirement, the original overview now shows which related stash units are built
and/or filled. With this integration, next to the original inventory item tracking, requirement completion can also be
performed through filling stash units.

![Filling stashes updates status](/readme/filling-stashes-updates-status.gif)

#### Status management and updates

Whats more, STASHUnit fill statuses are stored in your Runelite's config manager for each runescape account that you
have. By logging into your Runelite account on the client, even when you log in on another computer, your fill statuses
are saved. STASHUnit build statuses are automatically updated when building a STASHUnit, and the results are immediately
visible in the overview.

![Status management updates](/readme/status-management-updates.gif)

### Other plugin features

Among other interfaces, items in your bank, inventory and equipment are highlighted by their respective tier colours.
Individually, for each supported in-game interface, overlay display may be switched on or off in the plugin settings.

![Interface item highlighting](/readme/interface-item-highlighting.gif)

#### Mark STASH unit locations on the map
STASH location descriptions may not always be enough to remember their location. In the collection log STASH overview, locations can be marked using `mark map` button of a STASH unit.

![Highlighting a stash unit location on the map](/readme/highlighting-a-stash-unit-location-on-the-map.gif)

#### Dynamic item collection log caching

Whenever the user picks up an item, the item collection log of related emote clue item requirement status are updated,
cached, and displayed.

![Item collection log caching](/readme/item-collection-log-caching.gif)

#### Requirement lookup

The overview panel allows for querying and sorting the data. Moreover, the input header allows for any combination of
requirement name, emoteClueDifficulty and completion status filtering.

![Requirement lookup](/readme/requirement-lookup.gif)

### Planned future updates
- Include more types of item requirements

### Donating
Buy me a coffee with [this PayPal donation link](https://www.paypal.com/donate/?hosted_button_id=72AFNGL28LFEN)!
