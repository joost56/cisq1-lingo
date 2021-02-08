Feature: Guessing a word
  As a user,
  I want to guess a word,
  In order to become better at Lingo

  Scenario: Start new game
  When I start a new game
  Then I should be able to see the first letter of the word i have to guess

  Scenario: Start new round
  Given I am playing Lingo trainer
  When I guess a word right
  Then I want to be able to start a new round

  Scenario Outline: Start a new round
  Given I am playing a game
  And the round was won
  And the last word had "<previous length>" letters
  When I start a new round
  Then the word to guess has "<next length>" letters

  Examples:
    | previous length | next length |
    | 5               | 6           |
    | 6               | 7           |
    | 7               | 5           |

  # Failure path

  Given I am playing a game
  And the round was lost
  Then i cannot start a new round

  Scenario: Guessing a word
  Given I am playing Lingo trainer
  When I am guessing a word
  Then i want to get feedback

  Scenario Outline: Guessing a word
  Given I am playing a game
  And I am guessing a word
  When the <word> is not the same as <guess>
  Then I get <feedback>

  Examples:
  |  word  |  guess  |  feedback                                             |
  |  BAARD |  BERGEN |  INVALID, INVALID, INVALID, INVALID, INVALID, INVALID |
  |  BAARD |  BONJE  |  CORRECT, ABSENT, ABSENT, ABSENT, ABSENT              |
  |  BAARD |  BARST  |  CORRECT, CORRECT, PRESENT, ABSENT, ABSENT            |
  |  BAARD |  DRAAD  |  ABSENT, PRESENT, CORRECT, PRESENT, CORRECT           |
  |  BAARD |  BAARD  |  CORRECT, CORRECT, CORRECT, CORRECT, CORRECT          |
    
