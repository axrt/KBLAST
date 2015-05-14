package core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by alext on 5/13/15.
 */
public abstract class Alphabet {
    public final Set<Character> symbols;

    public Alphabet(Set<Character> symbols) {
        this.symbols = symbols;
    }
    public Alphabet(Character[] symbols) {
        this.symbols = new HashSet<Character>(Arrays.asList(symbols));
    }
    public Alphabet(char[] symbols) {
        this.symbols =new HashSet<Character>();
        for(char c:symbols){
            this.symbols.add(c);
        }
    }
    public int length(){
        return this.symbols.size();
    }

    public Set<Character> getSymbols() {
        return symbols;
    }
}
