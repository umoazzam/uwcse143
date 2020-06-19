// Usman Moazzam, CSE 143 AK
/* Game manager for the popular game "Assassin," used to create the kill ring and resulting
   graveyard, print the ring and graveyard in the console, check the contents of the ring and
   graveyard, check if the game is over, find the winner, and remove victims from the ring
* */

import java.util.List;

public class AssassinManager {

   private AssassinNode frontKill;
   private AssassinNode frontGrave;

   // pre: names is not empty (throws IllegalArgumentException otherwise)
   // post: constructs kill ring of assassins
   public AssassinManager(List<String> names) {
      if (names.isEmpty()) {
         throw new IllegalArgumentException("There are no names in this list.");
      }

      // runs through list of names to construct kill ring, starting from 0
      for (int i = 0; i < names.size(); i++) {
         AssassinNode temp = new AssassinNode(names.get(i));
         if (frontKill == null){
            frontKill = temp;
         } else {
            AssassinNode current = frontKill;

            // finds the last node of the linked list and sets is equal to
            while (current.next != null){
               current = current.next;
            }
            current.next = temp;
         }
      }

   }

   // post: prints the contents and connections in the kill ring
   public void printKillRing() {
      AssassinNode current = frontKill;

      // prints the players in the kill ring
      while (current != null){
         System.out.println("\t" + current.name + " is stalking " + frontKill.name);
         current = current.next;
      }
      System.out.println();
   }

   // post: prints the contents and connections in the kill ring
   public void printGraveyard() {
      AssassinNode current = frontGrave;

      // prints the players in the graveyard
      while (current != null) {
         System.out.println ("\t" + current.name + " was killed by " + current.killer);
         current = current.next;
      }
      System.out.println();
   }

   // post: returns whether or not the string given is in the kill ring
   public boolean killRingContains(String name) {
      AssassinNode current = frontKill;
      name = name.toLowerCase();

      // checks if inputted name matches the name of any players
      while(current != null){
         if(current.name.toLowerCase().equals(name)){
            return true;
         }
         current = current.next;
      }
      return false;
   }

   // post: returns whether or not the string given is in the graveyard
   public boolean graveyardContains(String name) {
      AssassinNode current = frontGrave;
      name = name.toLowerCase();

      // checks if inputted name matches the name of any players
      while(current != null){
         if(current.name.toLowerCase().equals(name)){
            return true;
         }
         current = current.next;
      }
      return false;
   }

   // post: checks if the game is over
   public boolean gameOver() {
      return frontKill.next == null;
   }

   // pre: game isn't over (throws IllegalStateException if not), inputted name is a player in
   //      the kill ring (throws IllegalArgumentException if not)
   // post: kills the inputted player (removes them from the ring, places in graveyard)
   public void kill(String name) {
      if(gameOver()){
         throw new IllegalStateException("Game is over, no one left to kill");
      }
      if(!killRingContains(name)){
         throw new IllegalArgumentException("This player is not in the kill ring");
      }
      name = name.toLowerCase();
      AssassinNode killed = null;
      if (frontKill.name.toLowerCase().equals(name)) {
         String temp = null;
         AssassinNode current = frontKill;

         // isolates the victim at the front of the killRing
         while (current != null) {
            if (current.next == null) {
               temp = current.name;
            }
            current = current.next;
         }

         killed = frontKill;
         killed.killer = temp;
         frontKill = frontKill.next;
      } else {
         AssassinNode current = frontKill;

         // isolates the victim (not at the front of the killRing)
         while (current.next != null) {
            if (current.next.name.toLowerCase().equals(name)) {
               String temp = current.name;
               killed = current.next;
               killed.killer = temp;
               if (current.next.next != null) {
                  current.next = current.next.next;
                  break;
               } else {
                  current.next = null;
                  break;
               }
            }
            current = current.next;
         }
      }

      // places the victim in the graveyard
      if (frontGrave != null) {
         killed.next = frontGrave;
      } else {
         killed.next = null;
      }

      frontGrave = killed;
   }

   // pre: game is over (returns null if not)
   // post: returns the name of the winner
   public String winner() {
      if (gameOver()) {
         return frontKill.name;
      }
      return null;
   }

}
