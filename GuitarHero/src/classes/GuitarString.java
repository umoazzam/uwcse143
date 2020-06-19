// Usman Moazzam, CSE 143 AK
// Object that models a vibrating guitar string of a given frequency

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class GuitarString {

   // class constant representing Karplus-Strong energy decay factor
   public static double ENERGY_DECAY = 0.996;

   // data structure that creates a ring of buffer values
   private Queue<Double> ringBuffer;

   // pre: frequency >= 0, calculated capacity >= 2 (throws IllegalArgumentException if not)
   // post: creates a ring buffer of the desired capacity full of zeroes
   public GuitarString(double frequency) {
      if(frequency < 0) {
         throw new IllegalArgumentException();
      }
      long capacity = Math.round(StdAudio.SAMPLE_RATE/frequency);
      if(capacity < 2) {
         throw new IllegalArgumentException();
      }
      ringBuffer = new LinkedList<Double>();
      for (int i = 0; i < capacity; i++){
         ringBuffer.add(0.0);
      }
   }

   // pre: init >= 2 (throws IllegalArgumentException if not)
   // post: creates a ring buffer with the capacity and content of init
   public GuitarString(double[] init) {
      if (init.length < 2) {
         throw new IllegalArgumentException();
      }
      ringBuffer = new LinkedList<Double>();
      for (int i = 0; i < init.length; i++){
         ringBuffer.add(init[i]);
      }
   }

   //post: loads the capacity of ring buffer with rand values between -0.5 to 0.5
   public void pluck() {
      Random rand = new Random();
      for (int i = 0; i < ringBuffer.size(); i++) {
         ringBuffer.remove();
         ringBuffer.add(rand.nextDouble() - 0.5);
      }
   }

   // post: applies the Karplus-Strong update once
   public void tic() {
      ringBuffer.add(((ringBuffer.remove() + ringBuffer.peek())/2.0)*ENERGY_DECAY);
   }

   // post: returns the current sample
   public double sample() {
      return ringBuffer.peek();
   }


}
