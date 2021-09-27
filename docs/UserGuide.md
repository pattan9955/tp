---
layout: page
title: User Guide
---

Fridgy is a **desktop app for managing an inventory of food as well as a list of recipes, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, Fridgy can get your fridge inventory and recipe sorted out faster than traditional GUI apps.

--------------------------------------------------------------------------------------------------------------------

## UI

![Ui](images/Ui.png)

## Features

**Notes about the Command Format: **

- Words between `<` and`>` are parameters to be supplied by the user.

  e.g. in `add ingredient -n <name>`, `<name>` is a parameter which can be used as `add ingredient -n tomato`.

- Items in square brackets are optional.

  e.g. `add ingredient -n <name> [-d <description>]` can be used as `add ingredient -n tomato -d from Africa` or as `add ingredient -n tomato`.

- Items with `…` after them can be used multiple times.


### Ingredients

#### Add Ingredients: `add ingredient`

Add an ingredient to the inventory of ingredients

Format: `add ingredient -n <name> -t <type> -q <quantity> [-d <description>] -e <expiry date>`

Examples: 
`add ingredient -n tomato -d from africa -t discrete -q 500 -e dd/mm/yyyy`
`add ingredient -n milk -d fresh -t liquid -q 500 -u ml/l/gallons -e dd/mm/yyyy`
`add ingredient -n flour -d fresh -t solid -q 500 -u gr/kg -e dd/mm/yyyy`

#### Delete Ingredients: `delete ingredient`

Delete an ingredient from the inventory of ingredients

Format: `delete ingredient <index>`

Example: 
`delete ingredient 1`

### Recipes

#### Add Recipes

Add a recipe to the library of recipes

Format: `add recipe -n <name> -i <ingredient> <quantity> [-d <optional description>] -s <steps>...`

Examples:
`add recipe -n pasta -i tomato 1 -i milk 100ml -i chicken breast 200g -s 1. Chicken chicken -s 2. Chicken chicken`
`add recipe -n aglio olio -i pasta 200g -d grandmother aglio olio recipe -s 1. aglioli olioli 2. aglioli olioli`

#### Delete Recipes

Delete a recipe from the library of recipes

Format: `delete recipe <index>`

Examples: 
`delete recipe 1`