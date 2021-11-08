---
layout: page
title: Rebecca's Project Portfolio Page
---

### Project: Fridgy

Fridgy is a desktop Food Inventory Manager that makes it easy to reduce food waste by keeping track of what you have and when it expires. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 14 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the ability to manage ingredients in Fridgy
  * What it does: It allows the user to add, delete, edit and list ingredients to Fridgy.
    * Justification: With this feature, users will be able to organise their inventory of ingredients. This also sets the basis for ingredients to be managed by Recipes.
  * Highlights: This feature required amendments to the existing command syntax to allow the respective commands to be associated with ingredient type. It required an in-depth analysis of design alternatives.

* **New Feature**: Added the quantity and optional description attributes to ingredients.
  * What it does: The quantity attribute is essential to the core functionality of both the ingredient and recipe manager. The description attribute is a nice-to-have additional field for tracking of ingredients.
  * Highlights: These attributes integrate into other commands that need to specify a quantity and description. They were also integrated throughout the storage-related code so that the quantity and description can be exported to a file.

* **New Feature**: Added automatic sorting of ingredients and their tags
  * What it does: Sorts ingredients by expiry date and name, sorts expiry tags to precede other tags.
    * Justification: A major feature of Fridgy is the ability for the user to know what ingredients are expired or expiring first. The sort functionality enables the user to view ingredients which expire the earliest at the top of the list, indicated by the expired or expiring tag. This feature is also used in the execution of recipes to choose which ingredients to cook when there are multiple ingredients with the same name, i.e. the earliest expiring ingredient is chosen.
  * Highlights: This feature was implemented with minimal changes to existing logic and classes.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=rebeccacxy&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=rebeccacxy&tabRepo=AY2122S1-CS2103T-W11-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)

* **Other contributions**:
  * Significant modification of Address Book code base for use in Fridgy:
    * Renamed Address Book application to Fridgy. Renamed packages, classes, variable names and javadocs [\#26](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/26)
  * Introduced a base ingredient to be used by recipe for managing deductions, which ingredient extends from [\#97](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/97)
  * Minor cosmetic tweaks to GUI [\#228](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/228)

* **Documentation**:
  * User Guide:
    * Added documentation for introduction and quickstart. [\#119](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/119)
  * Developer Guide:
    * Added all user stories, use cases, NFRs, glossary, instructions for manual testing and overview. Ensured consistent formatting and phrasing. [\#13](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/13), [\#80](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/80), [\#112](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/112), [\#239](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/239)

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#36](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/36), [\#60](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/60), [\#98](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/98), [\#131](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/131), [\#215](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/215)
  * Reported and resolved bugs in Fridgy: [\#63](https://github.com/AY2122S1-CS2103T-W11-1/tp/issues/63), [\#200](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/200), [\#229](https://github.com/AY2122S1-CS2103T-W11-1/tp/issues/229)
  * Reported 11 bugs for other teams in the class during [PED](https://github.com/rebeccacxy/ped/issues) and raised detailed suggestions.

* **Statistics**:
  * PRs reviewed: [40](https://github.com/AY2122S1-CS2103T-W11-1/tp/pulls?q=is%3Apr+is%3Aclosed+reviewed-by%3Arebeccacxy)
  * PRs raised: [22](https://github.com/AY2122S1-CS2103T-W11-1/tp/pulls?q=is%3Apr+is%3Aclosed+author%3Arebeccacxy)
  * Issues raised: [25](https://github.com/AY2122S1-CS2103T-W11-1/tp/issues?q=is%3Aissue+author%3Arebeccacxy+is%3Aclosed)

