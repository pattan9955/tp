---
layout: page
title: Alissa's Project Portfolio Page
---

### Project: Fridgy

Fridgy is a desktop Food Inventory Manager that makes it easy to reduce food waste by keeping track of what you have and when it expires. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 14 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the ability to find recipes / ingredients based on their name.
    * What it does: allows the user to find recipes / ingredients that contain the keywords provided by the user
    * Justification: This feature improves the product significantly because a user having many recipes / ingredients will definitely need to have a way to search for the right recipe / ingredient fast
    * Highlights: This feature modifies the existing command syntax to allow them to be executed on either ingredient / recipe depending on the command syntax used by the user. It required an in-depth analysis of design alternatives and deep understanding of the existing codebase

* **New Feature**: Added the ability to list recipes
  * What it does: allows the user to list all recipes in the Recipe Book
  * Justification: This feature is necessary for Fridgy users because after executing the `find recipe` command, the recipe list will be filtered accordingly and the user may want to see the full list of recipes back again afterwards
  * Highlights: This feature modifies the existing `list` command syntax to accommodate execution on different models (eg: `Recipe` model or `Ingredient` model) depending on the command syntax used. As this is an extension to the existing `list` command in the codebase, implementing this feature requires great understanding of the original codebase

* **Major Enhancements**: Rework the GUI
  * What it does: Improves the user interface to use a cleaner and minimalistic style
      * Justification: This feature enhances the product aesthetics to become more pleasing to the eye
      * Highlights: UI design. Reworking the GUI also requires a deep understanding of JavaFX and how each component works

* **Minor Enhancements**: More appropriate error messages for commands in Fridgy
* **Minor Refactoring**: Remove traces of address book from the codebase [\#205](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/205), [\#220](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/220), [\#232](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/232)

* **Code contributed**: [RepoSense Code Dashboard](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=alissayarmantho)

* **Project management**:
    * Add deadlines to milestones

* **Documentation**:
    * User Guide:
        * Did cosmetic tweaks to existing UG [\#135](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/135), [\#226](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/226)
    * Developer Guide:
        * Added more details for the documentation on `UI` component [\#89](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/89)
        * Remove / fix other parts of the documentation referred in the Developer Guide [\#243](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/243)

* **Community**:
    * PRs reviewed (with non-trivial review comments): [\#76](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/76), [\#78](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/78), [\#200](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/200), [\#247](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/247)
    * Contributed to forum discussions [\#172](https://github.com/nus-cs2103-AY2122S1/forum/issues/172)
    * Reported 12 bugs / suggestions during [PED](https://github.com/alissayarmantho/ped/issues)

* **Tools**:
    * Integrated Netlify to the team's repository for easy previewing of PRs on documentation changes

* **Statistics**:
  * PRs reviewed: [20](https://github.com/AY2122S1-CS2103T-W11-1/tp/pulls?q=is%3Apr+is%3Aclosed+reviewed-by%3Aalissayarmantho)
  * PRs raised: [30](https://github.com/AY2122S1-CS2103T-W11-1/tp/pulls?q=is%3Apr+is%3Aclosed+author%3Aalissayarmantho)
  * Issues raised: [18](https://github.com/AY2122S1-CS2103T-W11-1/tp/issues?q=is%3Aissue+author%3Aalissayarmantho+is%3Aclosed)
