/* Usman Moazzam, CSE 143 AK, 11/14/19
   AnagramSolver: This class uses a dictionary to find all combinations of words that can be made
   using the letters in a phrase. This class utilizes the  LetterInventory class to compute an
   inventory for each dictionary term, isolate dictionary terms applicable to the desired anagrams,
   and print a complete list of passing anagrams based on the inputted phrase and max number of
   allowed terms.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnagramSolver {

   // stores dictionary
   private List<String> dict;

   // links dictionary terms to corresponding inventory of letters
   private Map<String, LetterInventory> anagrams;

   // post: computes letter inventory for each word in dictionary and initializes solver
   public AnagramSolver(List<String> dictionary) {
      dict = new ArrayList<>();
      anagrams = new HashMap<>();

      // dictionary "pre-process"
      for (String s: dictionary) {
         dict.add(s);
         LetterInventory li = new LetterInventory(s);
         anagrams.put(s,li);
      }
   }

   // pre: max >= 0 (throws IllegalArgumentException if not)
   // post: initializes tree and command for printing
   public void print(String text, int max) {
      if (max < 0) {
         throw new IllegalArgumentException("Max must be greater than 0");
      }

      LetterInventory rootNode = new LetterInventory(text);
      List<String> prunedList = prune(text);
      List<String> wordPath = new ArrayList<>();
      print(rootNode, wordPath, prunedList, max);
   }

   // post: reads dictionary and isolates usable words for anagrams
   private List<String> prune(String text) {
      List<String> pruned = new ArrayList<>();
      LetterInventory textLetters = new LetterInventory(text);

      // reads through dictionary and isolates words applicable to anagram
      for (String s: dict) {
         if (textLetters.subtract(anagrams.get(s)) != null) {
            pruned.add(s);
         }
      }
      return pruned;
   }

   // post: prints feasible anagrams corresponding to inputted phrase and max number of words
   private void print(LetterInventory rNode, List<String> wordPath, List<String> pList, int max) {
      if ((max == 0 || wordPath.size() < max) && rNode != null) {
         if (rNode.isEmpty()) {
            System.out.println(wordPath);

         } else {
            // reads through usable words and tracks possibility for anagram building
            for (String s : pList) {
               wordPath.add(s);
               print(rNode.subtract(anagrams.get(s)), wordPath, pList, max);
               wordPath.remove(wordPath.size()-1);
            }

         }

      }

   }


}
