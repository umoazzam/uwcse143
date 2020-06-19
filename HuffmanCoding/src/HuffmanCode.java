import java.io.PrintStream;
import java.util.PriorityQueue;
import java.util.Scanner;

public class HuffmanCode {

   // post: stores Huffman tree
   private PriorityQueue<HuffmanNode> letters;

   // post: stores reference to root of the tree
   private HuffmanNode overallRoot;

   // post: initializes a new huffman code from an array of frequencies
   public HuffmanCode(int[] frequencies) {
      letters = new PriorityQueue<>();

      // adds nonzero frequencies to letters
      for(int i = 0; i < 255; i++) {
         if (frequencies[i] > 0) {
            letters.add(new HuffmanNode(i,frequencies[i]));
         }
      }

      // constructs Huffman tree
      while(letters.size() > 1) {
         HuffmanNode node1 = letters.remove();
         HuffmanNode node2 = letters.remove();
         HuffmanNode parent = new HuffmanNode(node1.freq+node2.freq,node1,node2);
         letters.add(parent);
      }

      overallRoot = letters.peek();

   }

   // post: initializes a huffman code using a pre-existing code written in a .code file
   public HuffmanCode(Scanner input) {
      while(input.hasNextLine()) {
         int character = Integer.parseInt(input.nextLine());
         String code = input.nextLine();
         overallRoot = assignNode(character, code, overallRoot);
      }

      letters = new PriorityQueue<>();
      letters.add(overallRoot);
   }

   // post: assigns specific characters to nodes based on Huffman code
   private HuffmanNode assignNode(int character, String code, HuffmanNode current) {
      if (current == null) {
         current = new HuffmanNode(0,null,null);
      }
      
      if (code.length() == 1) {
         if (code.equals("0")) {
            current.l = new HuffmanNode(character, 0);
         } else {
            current.r = new HuffmanNode(character, 0);
         }
      } else {
         if (code.substring(0,1).equals("0")) {
            current.l = assignNode(character,code.substring(1), current.l);
         } else {
            current.r = assignNode(character,code.substring(1), current.r);
         }
      }
      return current;
   }

   // post: stores huffman codes in standard format
   public void save(PrintStream output) {
      save(output,overallRoot,"");
   }

   // post: stores huffman codes, printing character followed by code for character in tree
   private void save(PrintStream output, HuffmanNode node, String code) {
      if (node.l == null && node.r == null) {
         output.println(node.c);
         output.println(code);
      } else {
         save(output,node.l,code + "0");
         save(output,node.r,code + "1");
      }
   }

   // post: translates Huffman code to ASCII characters
   public void translate(BitInputStream input, PrintStream output) {
      HuffmanNode parent = overallRoot;
      while (input.hasNextBit() || (parent.l == null && parent.r == null)) { //(parent.l == null && parent.r == null)
         if (parent.l == null && parent.r == null) {
            output.write(parent.c);
            parent = overallRoot;
         } else if (input.nextBit() == 0) {
            parent = parent.l;
         } else {
            parent = parent.r;
         }
      }
   }

   // post: class representing a single HuffmanNode
   private class HuffmanNode implements Comparable<HuffmanNode>{

      // holds integer referring to ascii character
      public int c;

      // holds reference to the frequency of a specific character in code
      public int freq;

      // contains reference to left child of node
      public HuffmanNode l;

      // contains reference to right child of node
      public HuffmanNode r;

      // constructor for leaf nodes
      public HuffmanNode(int c, int freq) {
         this(freq,null,null);
         this.c = c;
      }

      // constructor for parent/branch nodes
      public HuffmanNode(int freq, HuffmanNode l, HuffmanNode r) {
         this.freq = freq;
         this.l = l;
         this.r = r;
      }

      // compares one node to another in terms of frequency
      public int compareTo(HuffmanNode node) {
         return freq - node.freq;
      }

   }

}
