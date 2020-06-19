/* Usman Moazzam, CSE 143 AK
   QuestionsGame:

 */

import java.io.PrintStream;
import java.util.Scanner;

public class QuestionsGame {

   // scanner used to read user responses
   private Scanner console;

   // primary/root node of 20 Questions tree
   private QuestionNode root;

   // post: initializes new 20 Questions game with root node "computer"
   public QuestionsGame() {
      console = new Scanner(System.in);
      root = new QuestionNode("computer");
   }

   // post: processes a file to import an external tree
   public void read(Scanner input) {
      // processes each line of text in file
      while (input.hasNextLine()) {
         root = reader(input);
      }
   }

   // post: reads file and interprets for translation to question tree
   public QuestionNode reader(Scanner input) {
      String qOrA = input.nextLine();
      QuestionNode node = new QuestionNode(input.nextLine());

      if (qOrA == "Q:") {
         node.y = reader(input);
         node.n = reader(input);
      }

      return node;
   }

   // post: exports current question tree to a file beginning with root node
   public void write(PrintStream output) {
      write(output, root);
   }

   // post: exports current question tree to a file
   public void write(PrintStream output, QuestionNode qNode) {
      if (qNode.y == null || qNode.n == null) {       // base case
         output.println("A:\n" + qNode.description);
      } else {                                        // recursive case
         output.println("Q:\n" + qNode.description);
         write(output, qNode.y);
         write(output, qNode.n);
      }
   }

   // post: uses question tree to play one game of 20 questions with user
   public void askQuestions() {
      root = askQuestions(root);
   }

   // post: asks user a series of questions to guess the object referenced by the user
   private QuestionNode askQuestions(QuestionNode qNode) {
      if (qNode.y == null && qNode.n == null) {
         if (yesTo("Would your object happen to be a(n) " + qNode)) {
            System.out.println("Great, I got it right!");

         } else {
            System.out.print("What object were you thinking of? ");
            QuestionNode object = new QuestionNode(console.nextLine());
            System.out.print("Please give me a yes/no question that " +
                  "distinguishes your object and mine. ");
            String question = console.nextLine();

            if (yesTo("And what is the answer for your object?")) {
               qNode = new QuestionNode(question, object, qNode);

            } else {
               qNode = new QuestionNode(question, qNode, object);
            }
         }

      } else {
         System.out.println("mariya");
         if (yesTo(qNode.description)) {
            qNode.y = askQuestions(qNode.y);
         } else {
            qNode.n = askQuestions(qNode.n);
         }
      }

      return qNode;
   }

   // post: asks the user a question, forcing an answer of "y" or "n";
   //       returns true if the answer was yes, returns false otherwise
   public boolean yesTo(String prompt) {
      System.out.print(prompt + " (y/n)? ");
      String response = console.nextLine().trim().toLowerCase();
      while (!response.equals("y") && !response.equals("n")) {
         System.out.println("Please answer y or n.");
         System.out.print(prompt + " (y/n)? ");
         response = console.nextLine().trim().toLowerCase();
      }
      return response.equals("y");
   }

   // class representing a single node of questions tree
   private static class QuestionNode {

      // branch/leaf of question branch
      public String description;

      // next branch (if players response to description is yes)
      public QuestionNode y;

      // next branch (if players response to description is no)
      public QuestionNode n;

      // initializes question node containing Q or A without reference to further nodes
      public QuestionNode(String description) {
         this(description, null, null);
      }

      // post: initializes question node containing Q or A with reference to next nodes
      public QuestionNode(String description, QuestionNode y, QuestionNode n) {
         this.description = description;
         this.y = y;
         this.n = n;
      }

      // post: creates customized conversion of QuestionNode to String
      public String toString() {
         return description;
      }
   }
}
