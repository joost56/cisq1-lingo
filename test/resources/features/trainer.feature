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