---
layout: page
title: Bailin's Project Portfolio Page
---

### Project: Fridgy

Fridgy is a desktop Food Inventory Manager that makes it easy to reduce food waste by keeping track of what you have and when it expires. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 14 kLoC.

Given below are my contributions to the project.

* **New Feature**: The ability view any ingredient / recipe on the main display window.
  * What it does: Allows the user to choose an ingredient / recipe to view them in greater details.
  * Justification: This feature is necessary as original Address Book 3 implementation has limited space to display each information card. For example, it is impossible to display recipe steps in a single information card. It greatly improves the extensibility of the application.
  * Highlights: This enhancement is complex as it requires maintenance of UI state to ensure consistency across different UI components. I made use Observer patterns, functional interfaces and JavaFX events to reduce coupling between existing components while ensuring the behaviour is consistent.
  * Credits: JavaFX events

* **New Feature**: Automatic highlighting of missing ingredient in recipe display.
  * What it does: Allows the user to see which ingredient is missing from their recipe easily
  * Justification: This feature is necessary as there could be many ingredients used in a recipe, it is crucial to convey the information of missing ingredient in recipe to users.


* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=rootkie&tabRepo=AY2122S1-CS2103T-W11-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)

* **Project management**:
  * Designed project architecture, scope and tasks for each milestone.
  * Led project meetings.
  * Allocated tasks to members.
  * Reviewed PRs and made decision on implementation details.
  * Reminded team of current progress and various deadlines.
  * Managed project repository workflow.
  * Lead bug triaging for v1.4.
  * Managed releases `v1.2` - `v1.4` (3 releases) on GitHub

* **Enhancements to existing features**:
  * Implemented Recipe model and storage based on Person model and storage. [\#34](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/34)
  * Made existing Model generic to reduce code duplications. Detailed PR: [\#57](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/57)
  * Major refactor of UI code to fix various bugs and improve code quality by reducing coupling. Detailed PR: [\#208](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/208)

* **Documentation**:
  * User Guide:
    * Review UG and provide feedback to teammates. [#238](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/238)
  * Developer Guide:
    * Added implementation details of generic model [\#81](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/81)
    * Added implementation details of UI [\#234](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/234)
    * Update Design subsection [\#81](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/81)

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#236](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/236) [\#203](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/203), [\#103](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/103), [\#36](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/36), [\#83](https://github.com/AY2122S1-CS2103T-W11-1/tp/pull/83)
  * Help teammates with issues related to git or the workflow
  * Reported 11 bugs for [PED](https://github.com/rootkie/ped/issues) including non-trivial logic bugs. 
  
* **Tools**:
  * Setting up CI and CodeCov for project.

* **Statistics**:
  * PRs reviewed: [61](https://github.com/AY2122S1-CS2103T-W11-1/tp/pulls?q=is%3Apr+reviewed-by%3Arootkie)
  * PRs raised: [26](https://github.com/AY2122S1-CS2103T-W11-1/tp/pulls?q=is%3Apr+is%3Aclosed+author%3Arootkie)
  * Issues commented: [34](https://github.com/AY2122S1-CS2103T-W11-1/tp/issues?q=is%3Aissue+commenter%3Arootkie+is%3Aclosed)
  
