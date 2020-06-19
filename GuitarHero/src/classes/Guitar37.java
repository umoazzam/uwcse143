// Usman Moazzam, CSE 143 AK
// Implementation of Guitar interface that models a guitar with 37 strings

public class Guitar37 implements Guitar {

   // class constant defining the number of guitar strings
   private static final int NUM_STRINGS = 37;
   // class constant defining frequency of concert A
   private static final double CONCERT_A = 440.0;
   // class constant defining the keyboard layout
   public static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

   // array containing 37 guitar strings
   private GuitarString[] strings;
   // int keeping track of the number of times the Karplus-Strong update has been performed
   private int time;

   // post: create array of 37 guitar strings
   public Guitar37() {
      strings = new GuitarString[NUM_STRINGS];
      for (int i = 0; i < NUM_STRINGS; i++) {
         strings[i] = new GuitarString(CONCERT_A * Math.pow(2, (i - 24) / 12));
      }
   }

   // pre: pitch >= -24 and <= 12 (ignored if not)
   // post: plucks the desired guitar based on the pitch
   public void playNote(int pitch) {
      if (pitch >= -24 && pitch <= 12) {
         strings[pitch + 24].pluck();
      }
   }

   // post: returns whether or not the key pressed is contained in the keyboard layout
   public boolean hasString(char string) {
      int index = KEYBOARD.indexOf(string);
      return index != -1;
   }

   // pre: string must be part of the keyboard layout (throws IllegalArgumentException if not)
   // post: references pluck method for the desired string
   public void pluck(char string) {
      if (!this.hasString(string)) {
         throw new IllegalArgumentException("Key not located in piano");
      }
      strings[KEYBOARD.indexOf(string)].pluck();
   }

   // post: returns the sum of the current samples
   public double sample() {
      double sum = 0;
      for (GuitarString g : strings) {
         sum += g.sample();
      }
      return sum;
   }

   // post: applies Karplus-Strong update to each string once
   public void tic() {
      for (GuitarString g : strings) {
         g.tic();
      }
      time++;
   }

   // post: returns number of times Karplus-Strong update has been performed
   public int time() {
      return time;
   }
}