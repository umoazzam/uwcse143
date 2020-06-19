// Usman Moazzam, CSE 143 AK
/* Homework 4: Game manager for the well known "Hangman" game, with an evil twist. This game is
   "evil" in the sense that rather than using a single word, this program uses the user's guesses
   to pick the largest family of words applicable, making it harder to win the game. This class is
   used to set up the game, pick the largest family of words based on guesses made by the player,
and record the guesses made and number of tries left.
 */

import java.util.*;

public class HangmanManager {

   private Set<String> wordsList;
   private Set<Character> guessesMade;
   private int guessCount;
   private int wordLength;

   // pre: length > 0, max > 0 (throws IllegalArgument Exception if not)
   // post: sets up the manager of the hangman game
   public HangmanManager(Collection<String> dictionary, int length, int max) {
      if (length < 0 || max < 0) {
         throw new IllegalArgumentException("Length can't be zero.");
      }

      wordsList = new TreeSet<String>();
      guessesMade = new TreeSet<Character>();
      guessCount = max;
      wordLength = length;

      // adds every word of dictionary to wordsList
      for (String word: dictionary) {
         if (word.length() == wordLength) {
            wordsList.add(word);
         }
      }

   }

   // post: returns list of applicable words
   public Set<String> words() {
      return wordsList;
   }

   // post: returns the number of guesses left
   public int guessesLeft() {
      return guessCount;
   }

   // post: returns the list of guesses made
   public Set<Character> guesses() {
      return guessesMade;
   }

   // pre: wordsList is not empty (throws IllegalStateException if not)
   // post: returns the pattern for applicable words
   public String pattern() {
      if (wordsList.isEmpty()) {
         throw new IllegalStateException("No words applicable");
      }
      return pattern(wordsList.iterator().next());
   }

   // post: returns the pattern for applicable words
   public String pattern(String word) {
      String patternConstructor = "";

      // constructs pattern for each word
      for (int i = 0; i < word.length(); i++) {
         if (guessesMade.contains(word.charAt(i))) {
            patternConstructor += word.substring(i, i+1);
         } else {
            patternConstructor += "-";
         }
      }
      return patternConstructor;
   }

   // pre: guessesLeft > 1, (throws IllegalStateException if not)
   //      guess not found in guessesMade (throws IllegalArgumentException if not)
   // post: records most recent guess and updates set of applicable words
   public int record(char guess) {
      if (guessesLeft() < 1) {
         throw new IllegalStateException("Player has no more guesses.");
      }
      if (guessesMade.contains(guess)) {
         throw new IllegalArgumentException("This letter was already guessed.");
      }

      Map<String, Set<String>> families = new TreeMap<String, Set<String>>();
      String initialPattern = pattern();
      guessesMade.add(guess);
      updateWordsList(families);

      if (pattern().equals((initialPattern))) {
         guessCount--;
      }

      return getMatches(pattern(), guess);
   }

   // post: updates wordsList for largest set of applicable words
   private void updateWordsList(Map<String, Set<String>> families) {
      // places each word in wordsList into a family
      for (String word: wordsList) {
         String currPattern = pattern(word);
         if (!families.containsKey(currPattern)) {
            families.put(currPattern, new TreeSet<String>());
         }
         families.get(currPattern).add(word);
      }
      wordsList = families.get(getBiggestKey(families));
   }

   // post: returns number of guess instances in a given pattern
   private int getMatches(String pattern, char guess) {
      int matches = 0;

      // runs through the pattern to determine if it matches guess
      for (int i = 0; i < pattern.length(); i++) {
         if (pattern.charAt(i) == guess) {
            matches++;
         }
      }
      return matches;
   }

   // post: returns the key for the largest family of words
   private String getBiggestKey(Map<String, Set<String>> families) {
      int maxLength = 0;
      String bigKey = "";

      // finds the largest family and returns
      for (String key: families.keySet()) {
         if (families.get(key).size() > maxLength) {
            maxLength = families.get(key).size();
            bigKey = key;
         }
      }
      return bigKey;
   }

}
