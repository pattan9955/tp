---
layout: page
title: Patrick's Project Portfolio Page
---

### Project: Fridgy

Fridgy is a desktop Food Inventory Manager that makes it easy to reduce food waste by keeping track of what you have and
when it expires. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java,
and has about 14 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the ability to add/delete recipes.
  * What it does: Allows the user to issue commands to add or delete a recipe for tracking.
  * Justification: This feature is a core feature of the product. Previously, AddressBook-3 only allowed addition/removal of persons which
    required edits to the command format and parsing in order to fit the needs of Fridgy.
  * Highlights: This enhancement was done in parallel with changes to the Recipe model used in Fridgy. Regex was used to 
  ensure clean and proper parsing of the command format.
  * Credits: Java Regex Utility

* **New Feature**: Added the ability to edit recipes.
  * What it does: Allows the user to edit the data fields of a specified recipe.
  * Justification: This feature is necessary as users may have recipes that change over time, or they could have simply
  mistyped something in the recipe. As such, Fridgy should provide a convenient way to remedy these situations.
    
* **New Feature**: Added the ability to clear all recipes.
  * What it does: Allows the user to clear all recipes in the Recipe Book with a single command.
  * Justification: This feature is necessary as on first start-up, users will see a list of pre-populated recipes in
  Fridgy that they may not need. This feature thus makes it convenient for users to start using Fridgy for themselves.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=pattan9955&tabRepo=AY2122S1-CS2103T-W11-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false)

* **Other Contributions**:
  * Overhaul of AddressBook-3 parsing system for Fridgy [\#78](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/78)
    * The pre-existing parser for AddressBook-3 was not flexible enough to parse the command formats that were to be 
  used with Fridgy. As such, a new parsing system and architecture had to be developed for Fridgy specifically.
    * This was complicated as a functional interface had to be used to bypass type issues with Recipe and Ingredient yet
      allow commands to be executed.
  * Ensured written code was properly tested throughout the course of the project.

* **Enhancements to existing features**:
  * Updated the find functionality for Recipes and Ingredients to allow partial matching of search terms. [\#125](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/125)
  * Updated comparison methods to allow for case-insensitive comparison of ingredients and recipes. [\#233](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/233)
  * Updated add ingredient command behaviour to allow for adding of ingredients with same name but different expiry dates. [\#215](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/215)
  * Made minor fixes to display of recipe steps in UI. [\#69](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/69)

* **Documentation**:
  * User Guide:
    * Added documentation for the feature `clear recipe`. [\#130](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/130)
    * Updated documentation for the `find ingredient` and `find recipe` features. [\#130](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/130)
    * Help to add screenshots for UG v1.3. [\#137](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/137)
    * Fixed documentation bugs in overall User Guide. [\#238](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/238)
    * Improved documentation quality for UG v1.4. [\#246](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/246) [\#247](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/247)
  * Developer Guide:
    * Added implementation details pertaining to architecture for the overarching Fridgy, Recipe and Ingredient Parsers. [\#88](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/88)
    * Added implementation details pertaining to the CommandParsers for Recipe and Ingredient commands. [\#88](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/88)

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#138](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/138), 
    [\#76](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/76)
  * Reported 7 bugs for other teams in the class during [PED](https://github.com/pattan9955/ped/issues)
  
* **Statistics**:
  * PRs reviewed: [33](https://github.com/AY2122S1-CS2103T-W11-1/tp/pulls?q=is%3Apr+reviewed-by%3Apattan9955+is%3Aclosed)
  * PRs raised: [16](https://github.com/AY2122S1-CS2103T-W11-1/tp/pulls?q=is%3Apr++is%3Aclosed+author%3Apattan9955+)
  * Issues taken: [22](https://github.com/AY2122S1-CS2103T-W11-1/tp/issues?q=is%3Aissue+assignee%3Apattan9955+is%3Aclosed)
    
