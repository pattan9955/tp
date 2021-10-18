---
layout: page
title: User Guide
---
# Introduction

Fridgy is a **desktop app for managing an inventory of food as well as a list of recipes, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, Fridgy can get your fridge inventory and recipe sorted out faster than traditional GUI apps.

--------------------------------------------------------------------------------------------------------------------
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------
# UI

![Ui](images/Ui.png)

# Features

**Notes about the Command Format:**

- Words between `<` and`>` are parameters to be supplied by the user.

  e.g. in `add ingredient -n <name>`, `<name>` is a parameter which can be used as `add ingredient -n tomato`.

- Items in square brackets are optional.

  e.g. `add ingredient -n <name> [-d <description>]` can be used as
     1. `add ingredient -n tomato -d from Africa` or as
     2. `add ingredient -n tomato`

- Items with `…` after them can be used multiple times.

## General Commands

### Help:
`help`

Pops out a window that leads the user to [User Guide](https://ay2122s1-cs2103t-w11-1.github.io/tp/UserGuide.html).

### Exit:
`exit`

Closes the program.

## Ingredients

### Add Ingredients:
`add ingredient`

Add an ingredient to the inventory of ingredients. 
- Any expired ingredients will be automatically tagged as `expired`.
- Any expiring (within 7 days from current date) will be automatically tagged as `expiring`.
- Acceptable ingredient units are:
  1. grams: `g`
  2. litres: `l`
- Acceptable prefixes for units are:
  1. milli- : `m` (i.e. `ml` for millilitres)
  2. kilo- : `k` (i.e. `kg` for kilograms)
- All units will be converted to grams and litres, to 3 decimal places.

Format: `add ingredient -n <name> -q <quantity>[<units>] [-d <description>] -e <expiry date> [t <tags>]`

Examples:
1. `add ingredient -n tomato -d from africa -q 500 -e 27-09-2021 -t sweet`
    <br />Expected Output:<br />`New ingredient added: tomato; Quantity: 500; Expiry Date: 27-09-2021; Description: from africa; Tags: [sweet][expired]`


2. `add ingredient -n milk -d fresh -q 500ml -e 27-09-2021`
    <br />Expected Output:<br />`New ingredient added: milk; Quantity: 0.500 l; Expiry Date: 27-09-2021; Description: fresh; Tags: [expired]`


3. `add ingredient -n flour -d fresh -q 500g -e 27/09/2021`
    <br />Expected Output:<br />`New ingredient added: flour; Quantity: 500.000 g; Expiry Date: 27-09-2021; Description: fresh; Tags: [expired]`


### Delete Ingredients:
`delete ingredient`

Delete an ingredient from the inventory of ingredients

Format: `delete ingredient <index>`

Example:
1. `delete ingredient 1`
    <br />Expected Output:<br />`Deleted Ingredient: flour; Quantity: 500.000 g; Expiry Date: 27-09-2021; Description: fresh; Tags: [expired]`

### Edit Ingredients:
`edit ingredient`

Edit an ingredient from the inventory of ingredients

Format: `edit ingredient <index> -<field flag> <new data>`

Example: 
1. `edit ingredient 1 -d juicy`
<br />Expected Output:<br />`Edited Ingredient: Watermelon; Quantity: 1; Expiry Date: 20-10-2021; Description: juicy; Tags: [expiring][melon][water]`

### Clear Ingredients:
`clear`

Clear all the ingredients from the inventory of ingredients

Format: `clear`

Example:
1. `clear`
<br />Expected Output:<br />`Description book has been cleared!`

### Find Ingredients:
`find ingredient`

Find an ingredient in the inventory based on keyword(s) from the name of the item

Format: `find ingredient <keyword>`

Examples:
1. `find ingredient potato`
Expected Output:
![img.png](images/findIngredient1.png)

### List Ingredients:
`list ingredient`

List all the ingredients again after `Find` Operation.

Format: `list ingredient`

## Recipes

### Add Recipes:
`add recipe`

Add a recipe to the library of recipes

Format: `add recipe -n <name> -i <ingredient> <quantity> [-d <optional description>] -s <steps>...`

Examples:
1. `add recipe -n pasta -i tomato 1 -i milk 100ml -i chicken breast 200g -s 1. Chicken chicken -s 2. Chicken chicken`
    <br />Expected Output: `New recipe added: pasta; recipe ingredients: [tomato 1, chicken breast 200g, milk 100ml]; step: [1. Chicken chicken, 2. Chicken chicken]`


2. `add recipe -n aglio olio -i pasta 200g -d grandmother aglio olio recipe -s 1. aglioli olioli 2. aglioli olioli`
    <br />Expected Output: `New recipe added: aglio olio; recipe ingredients: [pasta 200g]; step: [1. aglioli olioli 2. aglioli olioli]; description: grandmother aglio olio recipe`

### Delete Recipes:
`delete recipe`

Delete a recipe from the library of recipes

Format: `delete recipe <index>`

Examples:
1. `delete recipe 1`
    <br />Expected Output:<br />`Your recipe for 'mom's spaghetti' has been deleted.`

### View Recipes:
`view recipe`

Expand the recipe and view the detailed steps in a bigger window.

Format: `view recipe <index>`

Examples: 
1. `view recipe 2`<br />Expected Output:<br />
![viewRecipe](images/viewRecipe1.png)
-----
# Command Summary

Action | Format
--------|------------------
**Add Ingredient** | `add ingredient -n <name> -q <quantity>[<units>] [-d <description>] -e <expiry date>`
**Delete Ingredient** | `delete ingredient <index>`
**Edit Ingredient** | `edit ingredient <index> -<field flag> <new data>`
**Clear Ingredient** | `clear`
**Find Ingredient** | `find ingredient <keyword>`
**List Ingredient** | `list ingredient`
**Add Recipe** | `add recipe -n <name> -i <ingredient> <quantity> [-d <optional description>] -s <steps>...`
**Delete Recipe** | `delete recipe <index>`
**View Recipe** | `view recipe <index>`
