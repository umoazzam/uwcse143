package classes;// musical instrument that can play various notes.  You can specify exactly
// which note to play (playNote) or you can specify a character that indicates
// which note to play (pluck).  Different guitar objects will have different
// mappings from characters to notes.  There is a hasString method that lets
// you verify that a particular string exists.  It also has methods for getting
// the current sound sample (the sum of all samples from the strings of the
// guitar), to advance the time forward one "tic," and a method for
// determining the current time (the number of times tic has been called).


public interface Guitar {
    public void playNote(int pitch);
    public boolean hasString(char key);
    public void pluck(char key);
    public double sample();
    public void tic();
    public int time();
}
