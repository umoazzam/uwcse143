// Usman Moazzam, CSE 143 AK
// Inventory to keep track of letter counts in a given string
public class LetterInventory {

   // constant for conversion between lowercase chars and the counter[] indexes
   private static final int CHAR_TO_INT = 97;
   // constant for making all inputted chars lowercase
   private static final int UPPER_TO_LOWER = 32;

   // integer array that stores the count for every letter of the alphabet
   private int[] counter = new int[26];
   // integer that stores the sum of the counts of each letter
   private int sum;

   // post: outputs a LetterInventory storing counts for each letter in the string
   public LetterInventory(String data) {
      //isolates every character and adds to count
      for (int i = 0; i < data.length(); i++) {
         char letter = this.toLower(data.charAt(i));
         if (letter >= 'a' && letter <= 'z') {
            counter[(int) letter - CHAR_TO_INT]++;
            sum++;
         }
      } 
   }

   // pre: letter corresponds to alphabet, throws IllegalArgumentException if not
   // post: returns count value corresponding to input letter
   public int get(char letter) {
      letter = this.toLower(letter);
      if (letter >= 'a' && letter <= 'z') {
         return counter[(int) this.toLower(letter) - CHAR_TO_INT];
      }
      throw new IllegalArgumentException("Must be a letter");

   }

   // pre: letter corresponds to alphabet and value >= 0, throws IllegalArgumentException if not
   // post: adjusts the sum and sets the counter for letter to value
   public void set(char letter, int value) {
      letter = this.toLower(letter);
      if (letter >= 'a' && letter <= 'z' && value >= 0) {
         sum += value - counter[(int) letter - CHAR_TO_INT];
         counter[(int) letter - CHAR_TO_INT] = value;
      } else {
         throw new IllegalArgumentException("Must be a letter");
      }
   }

   // post: returns size
   public int size() {
      return sum;
   }

   // post: returns true if the counter for every letter is 0, false otherwise
   public boolean isEmpty() {
      return sum == 0;

   }

   // post: creates a bracketed version of the counter array
   public String toString() {
      String output = "[";
      //isolates each index and adds to the output based on count
      for (int i = 0; i < counter.length; i++) {
         for (int j = 0; j < counter[i]; j++) {
            output += (char) (i + CHAR_TO_INT);
         }
      }
      return output + "]";
   }

   // post: returns uppercase characters as lower case
   private char toLower(char letter) {
      if (letter >= 'A' && letter <= 'Z') {
         letter += UPPER_TO_LOWER;
      }
      return letter;
   }

   // post: returns new LetterInventory representing sum of this inventory and other
   public LetterInventory add(LetterInventory other) {
      return new LetterInventory(other.toString() + this.toString());
   }

   // post: returns new LetterInventory representing the subtraction of other from this inventory
   //       while all letter counts remain positive, otherwise returns null
   public LetterInventory subtract(LetterInventory other) {
      LetterInventory result = new LetterInventory("");
      //isolates each count, checks if the difference will get a negative value
      //(returns null if so) and adds to the resulting inventory
      for (int i = 0; i < counter.length; i++) {
         int diff = this.counter[i] - other.get((char)(i + CHAR_TO_INT));
         if (diff < 0) {
            return null;
         } else {
            result.set((char)(i + CHAR_TO_INT), diff);
         } 
      }
      return result;  
   } 

}