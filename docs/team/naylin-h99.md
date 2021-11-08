---
layout: page
title: Nay Lin's Project Portfolio Page
---

### Project: Fridgy

Fridgy is a desktop Food Inventory Manager that makes it easy to reduce food waste by keeping track of what you have and when it expires. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 14 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added support for `ExpiryDate` of Ingredients.
  * What it does: Allows the user to keep track of the expiry dates of the ingredients stored in the inventory. 
  * Justification: This feature is part of the core features for our inventory management which helps to track the expiry status of the ingredients in the Inventory.
  * Highlights: This addition requires good understanding of the pre-existing `Person` model to be refactored to support `ExpiryDate`, and also allows for additional features related to expiry dates to be added in the future. 
  * Credits: Java LocalDate

* **New Feature**: Added unit support for `Quantity` of Ingredients.
  * What it does: Allows the user to keep track of quantity with or without units for ingredients stored in the inventory.
  * Justification: This enhancement allows users better keep track of the quantity of ingredients with units. 
  * Highlights: This addition requires good understanding of the pre-existing model as well as Java's regex to enforce strict filtering of acceptable units, parsing and converting them to common units for mass and liquids. 

* **New Feature**: Added auto-updating of `Tag` for Ingredients based on their expiry date.
  * What it does: Helps the user keep track of ingredients that are expiring (i.e. within 7 days to expiry date) and expired ingredients.
  * Justification: This enhancement helps to add convenience to users keeping track of the expiry status of their ingredients in the Inventory.
  * Highlights: This enhancement requires good understanding of the existing commands so that tags are automatically added after any addition or modifications to any ingredients, as well as good understanding of the Java Period package.  
  * Credits: Java Period

* **New Feature**: Added `CookRecipeCommand` for Recipes.
  * What it does: Helps the user easily deduct ingredients that are used in a recipe when it is cooked.
  * Justification: This enhancement helps to add convenience to users automatically deduct all the quantity of ingredients used when cooking a recipe.
  * Highlights: This enhancement requires good understanding of the Inventory and RecipeBook for deductions to be able to happen across the two.
  
* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=NayLin-H99&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByAuthors&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=NayLin-H99&tabRepo=AY2122S1-CS2103T-W11-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)

* **Other Contributions**: 
  * Fix CheckStyle for all pre-existing test cases. [\#36](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/36)
  * Help refactor and remove some unnecessary code from pre-existing Person model. [\#51](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/51), [\#58](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/58)

* **Enhancements to existing features**:
  * Enhanced `DeleteCommand` and `DeleteRecipeCommand` to support multiple deletions, allowing users to delete multiple ingredients and recipes at once. [\#122](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/122), [\#209](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/209)
    * Original `DeleteCommand` and `DeleteRecipeCommand` only supported singular deletes.
  * Enhanced `ClearCommand` to support deletion of all expired ingredients. [\#129](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/129)

* **Documentation**:
  * User Guide:
    * Updated the entire User Guide for v1.1. [\#28](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/28)
    * Updated and improved the entire User Guide for v1.2. [\#76](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/76)
    * Updated and improved most of the User Guide for v1.3. [\#114](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/114), [\#137](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/137)
    * Updated and improved User Guide for v1.4. [\#213](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/213), [\#245](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/245), [\#247](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/247)
  * Developer Guide:
    * Added implementation details of the `Quantity` auto-unit-conversion feature. [\#92](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/92)

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#13](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/13), [\#116](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/116), [\#208](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/208), [\#238](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/238) 
  * Reported 11 bugs for [PED](https://github.com/NayLin-H99/ped/issues) including non-trivial logic bugs.

* **Statistics**:
  * PRs reviewed: [38](https://github.com/AY2122S1-CS2103T-W11-1/tp/pulls?q=is%3Apr+reviewed-by%3Anaylin-h99)
  * PRs raised: [26](https://github.com/AY2122S1-CS2103T-W11-1/tp/pulls?q=is%3Apr+is%3Aclosed+author%3Anaylin-h99)
  * Issues taken: [25](https://github.com/AY2122S1-CS2103T-W11-1/tp/issues?q=is%3Aissue+assignee%3ANayLin-H99)
  * Issues commented: [9](https://github.com/AY2122S1-CS2103T-W11-1/tp/issues?q=is%3Aissue+commenter%3Anaylin-h99+is%3Aclosed)
