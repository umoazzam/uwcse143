/* Usman Moazzam, CSE 143 AK, 10/31/2019
   GrammarSolver: Generates random grammars based on a given set of rules. This class is used
   to set up the rules pertaining to each individual non-terminal, check if a given phrase is a
   non-terminal, print out all non-terminals established by the rules, and generate an indicated
   number of random non-terminals.
 */

import java.util.*;

public class GrammarSolver {

   // orders non-terminals alphabetically and assigns rules
   private SortedMap<String, List<String>> grammar;

   // pre: rules is not empty, throws IllegalArgumentException if not
   //      one set of rules per key, throws IllegalArgumentException if not
   // post: initializes system to organize non-terminals and corresponding rules
   public GrammarSolver(List<String> rules) {
      if (rules.isEmpty()) {
         throw new IllegalArgumentException();
      }

      grammar = new TreeMap<>();

      // initializes system of non-terminals and corresponding rules
      for (int i = 0; i < rules.size(); i++) {
         String fullLine = rules.get(i);
         String[] fullPieces = fullLine.split("::=");
         String key = fullPieces[0];

         if (grammar.containsKey(key)) {
            throw new IllegalArgumentException();
         }

         List<String> keyRules = new ArrayList<String>();
         keyRules = Arrays.asList(fullPieces[1].split("\\|"));
         grammar.put(key, keyRules);
      }
   }

   // post: returns whether or not a specific symbol is a non-terminal
   public boolean grammarContains(String symbol) {
      if (grammar.containsKey(symbol)) {
         return true;
      }

      return false;
   }

   // post: prints all non-terminals
   public String getSymbols() {
      String s = "[";

      // prints individual non-terminals
      for (Map.Entry<String, List<String>> entry : grammar.entrySet()) {
         s += entry.getKey() + ", ";
      }

      s = s.substring(0, s.length() - 2);
      return s + "]";
   }

   // pre: times > 0, throws IllegalArgumentException if not
   //      symbol is a non-terminal, throws IllegalArgumentException if not
   // post: generates "times" number of random non-terminals
   public String[] generate(String symbol, int times) {
      if (times < 0 || !grammar.containsKey(symbol)) {
         throw new IllegalArgumentException();
      }

      String[] nonTerm = new String[times];

      // initializes iterations of non-terminal
      for (int i = 0; i < nonTerm.length; i++) {
         nonTerm[i] = "";
      }

      // constructs and generates structure of non-terminal
      for (int i = 0; i < times; i++) {
         String[] ruleParts = getRuleParts(symbol);

         // generates terminals and non-terminals
         for (String s : ruleParts) {
            if (!grammarContains(s)) {
               nonTerm[i] += s + " ";
            } else {
               nonTerm[i] += generate(s);
            }
         }

         nonTerm[i] = nonTerm[i].substring(0, nonTerm[i].length() - 1);
      }

      return nonTerm;
   }

   // post: generates individual non-terminals/terminals within overarching non-terminal
   private String generate(String symbol) {
      String nonTerm = "";
      String[] ruleParts = getRuleParts(symbol);

      // generates terminals and non-terminals
      for (String s : ruleParts) {
         if (!grammarContains(s)) {
            nonTerm += s + " ";
         } else {
            nonTerm += generate(s);
         }
      }

      return nonTerm;
   }

   // post: isolates separate rule cases and components
   private String[] getRuleParts (String symbol) {
      List<String> rules = grammar.get(symbol);
      Random rand = new Random();
      String randRule = rules.get(rand.nextInt(rules.size()));
      return randRule.trim().split("\\s+");
   }

}
