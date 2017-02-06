Character Creation
==================

Character Creation works by simply selecting a Race, Gender and Class, and everything else is driven from these.

Character Creation Workflow
---------------------------
* Enter Name
* Select Race
* Select Gender as viable for Race
* Select Class
* Create

Details
-------
Initial character creation works by allocating all Attributes and Skills to their default values. These may be 0.

Based on the Race, the Attributes and Skills are modified, and potential initial Traits are added.

Based on the Gender, the Attributes and Skills are modified, and potential initial Traits are added or removed.

Based on the Class, the Attributes and Skills are modified, and potential initial Traits are added or removed.

Example
-------
* Allocate initial Attributes
  * Strength: 10
  * Swords: 25
  * Clubs: 25
  * Blacksmithing: 20
* Selected race: Dwarf
  * Strength: +5
  * Swords: +10
  * Clubs: +10
* Selected gender: Female
  * Swords: -5
  * Clubs: +5
  * Trait: Clubmaster
* Selected class: Blacksmith
  * Strength: +5
  * Blacksmithing: +10
* End result:
  * Strength: 20
  * Swords: 30
  * Clubs: 40
  * Traits: Clubmaster

Character Advancement
=====================

Character Advancement works based on use. There is no concept of Experience or Character Levels. Instead, as you use an Attribute or a Skill you will become better with it.

Details
-------
Every time a Skill is used, a Skill and Attribute check is performed. Based on the success chance, points will be allocated towards both the Attribute and Skill, such that more points are awarded for lower chance of success.

When the points for a given Skill or Attribute pass a certain threshold, based on the current value, then the value increases. This is designed to slow down as the level increases so that early gains are very fast and later gains are much slower.

Example
-------
* Attribute usage gains (100 - Success Chance) / 10 points on a successful skill use
* Skill usage gains (100 - Success Chance) / 10 points on a successful skill use
* Skill usage gains (100 - Success Chance) / 25 points on an unsuccessful skill use
* Points are only gained if the success chance is between 10% and 90%
* Skill Increases happen when points exceed (Level * 10). Excess points are lost.
* Attribute Increases happen when points exceed (Level * 25). Excess points are lost.

Worked Example
--------------
* Success Rate is 50%
* On Success:
  * Attribute gains 5 points
  * Skill gains 5 points
* On failure:
  * Attribute gains 0 points
  * Skill gains 2 points

### Points needed for attribute levels
| Attribute Level | Points to increase | 50% Successes Needed |
|-----------------|--------------------|----------------------|
| 0               | 0                  | 1                    |
| 1               | 25                 | 5                    |
| 2               | 50                 | 10                   |
| 3               | 75                 | 15                   |
| 4               | 100                | 20                   |
| 5               | 125                | 25                   |
| 10              | 250                | 50                   |
| 25              | 625                | 125                  |

### Points needed for attribute levels
| Skill Level | Points to increase | 50% Successes Needed | 50% Failures Needed |
|-------------|--------------------|----------------------|---------------------|
| 0           | 0                  | 1                    | 1                   |
| 1           | 10                 | 2                    | 5                   |
| 2           | 20                 | 4                    | 10                  |
| 3           | 30                 | 6                    | 15                  |
| 4           | 40                 | 8                    | 20                  |
| 5           | 50                 | 10                   | 25                  |
| 10          | 100                | 20                   | 50                  |
| 25          | 250                | 50                   | 125                 |
