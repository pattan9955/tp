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


## Ingredients

### Add Ingredients:
`add ingredient`

Add an ingredient to the inventory of ingredients

Format: `add ingredient -n <name> -t <type> -q <quantity> [-d <description>] -e <expiry date>`

Examples:
1. `add ingredient -n tomato -d from africa -t discrete -q 500 -e 27/09/2021`
    </br>Expected Output: `Your ingredient '500 units of tomato' has been added to the inventory.`

2. `add ingredient -n milk -d fresh -t liquid -q 500 -e 27/09/2021`
    </br>Expected Output: `Your ingredient '500ml of milk' has been added to the inventory.`

3. `add ingredient -n flour -d fresh -t solid -q 500 -e 27/09/2021`
    </br>Expected Output: `Your ingredient '500g of flour' has been added to the inventory.`


### Delete Ingredients: `delete ingredient`

Delete an ingredient from the inventory of ingredients

Format: `delete ingredient <index>`

Example:
1. `delete ingredient 1`
    </br>Expected Output: `Your ingredient '500 units of tomato' has been deleted from the inventory.`

## Recipes

### Add Recipes

Add a recipe to the library of recipes

Format: `add recipe -n <name> -i <ingredient> <quantity> [-d <optional description>] -s <steps>...`

Examples:
1. `add recipe -n pasta -i tomato 1 -i milk 100ml -i chicken breast 200g -s 1. Chicken chicken -s 2. Chicken chicken`
    </br>Expected Output: `Your recipe for 'pasta' has been added.`

2. `add recipe -n aglio olio -i pasta 200g -d grandmother aglio olio recipe -s 1. aglioli olioli 2. aglioli olioli`
    </br>Expected Output: `Your recipe for 'aglio olio' has been added.`

### Delete Recipes

Delete a recipe from the library of recipes

Format: `delete recipe <index>`

Examples:
1. `delete recipe 1`
    </br>Expected Output: `Your recipe for 'mom's spaghetti' has been deleted.`
-----
# Command Summary
Action | Format
--------|------------------
**Add Ingredient** | `add ingredient -n <name> -t <type> -q <quantity> [-d <description>] -e <expiry date>`
**Delete Ingredient** | `delete ingredient <index>`
**Add Recipe** | `add recipe -n <name> -i <ingredient> <quantity> [-d <optional description>] -s <steps>...`
**Delete Recipe** | `delete recipe <index>`
