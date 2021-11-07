---
layout: page
title: Rebecca's Project Portfolio Page
---

### Project: Fridgy

Fridgy is a food and recipe management system, which aims to help users manage their ingredients and recipes easily.
It warns you about expiring ingredients, and automatically deducts your ingredients when you execute recipes. 

Fridgy is a desktop app, optimized for use via CLI, with an interactive GUI to display ingredients and recipes. It is written in Java, and has about 18 kLoC.

### Summary of Contributions

* **Major Enhancement**: Added the ability to manage ingredients in Fridgy
  * What it does: Allows the user to add, delete, edit and list ingredients to Fridgy.
    * Justification: With this feature, users will be able to organise their inventory of ingredients. This also sets the basis for ingredients to be managed by Recipes.
  * Highlights: This enhancement affects existing commands and commands to be added in future. It required an in-depth analysis of design alternatives.

* **Minor Enhancement**: Added the quantity and optional description attributes to ingredients.
  * What it does: The quantity attribute is essential to the core functionality of both the ingredient and recipe manager. The description attribute is a nice-to-have additional field for tracking of ingredients.
  * Highlights: This enhancement integrates into other commands that need to specify a quantity and description. This attribute was also integrated throughout the storage-related code so that the quantity and description can be exported to a file.

* **Minor Enhancement**: Added automatic sorting of ingredients and their tags
  * What it does: Sorts ingredients by expiry date and name, sorts expiry tags to come before user-inputted tags.
    * Justification: A major feature of Fridgy is the ability for the user to see what ingredients are expired or expiring first.
  * Highlights: This feature was implemented with minimal changes to existing logic and classes.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=rebeccacxy&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=rebeccacxy&tabRepo=AY2122S1-CS2103T-W11-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)

* **Other code contributions**:
    * Significant modification of Address Book code base for use in Fridgy:
      * Renamed Address Book application to Fridgy. Renamed packages, classes, variable names and javadocs [\#26](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/26)
    * Introduced a base ingredient to be used by recipe for managing deductions [\#97](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/97)
    * Minor cosmetic tweaks to GUI [\#228](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/228)

* **Documentation**:
    * User Guide:
        * Added documentation for introduction and quickstart [\#119](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/119)
    * Developer Guide:
        * Added all user stories, use cases, NFRs, glossary, instructions for manual testing. Ensured consistent formatting and phrasing [\#13](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/13), [\#112](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/112)

* **Community**:
    * PRs reviewed (non-trivial review comments): [\#36](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/36), [\#60](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/60), [\#76](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/76), [\#98](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/98), [\#131](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/131), [\#215](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/215)
    * Reported and resolved bugs in Fridgy: [\#63](https://github.com/AY2122S1-CS2103T-W11-1/tp/issues/63), [\#200](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/200), [\#229](https://github.com/AY2122S1-CS2103T-W11-1/tp/issues/229)
    * Reported bugs and suggestions for other teams in the class (examples: [1](https://github.com/rebeccacxy/ped/issues/2), [2](https://github.com/rebeccacxy/ped/issues/4), [3](https://github.com/rebeccacxy/ped/issues/7))
    
