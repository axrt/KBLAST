package core;

import java.util.*;

/**
 * Created by alext on 5/13/15.
 */
public class KSpace {

    protected final int k;
    protected final Alphabet alphabet;
    protected final Map<String, Integer> forwardMap;

    public KSpace(int k, Alphabet alphabet) {
        this.k = k;
        this.alphabet = alphabet;
        this.forwardMap = new HashMap<String, Integer>(this.alphabet.getSymbols().size() ^ this.k);
        final char[]symbols=new char[this.alphabet.getSymbols().size()];
        int i=0;
        for(Character c:this.alphabet.getSymbols()){
            symbols[i++]=c;
        }
        final char[][] grid = new char[(int)Math.pow(symbols.length,k)][];
        for(i=0;i<grid.length;i++){
            grid[i]=new char[k];
        }
        fillKmers(grid,this.k,symbols,0);
        i=0;
        for(char[]c:grid){
            forwardMap.put(new String(c),i++);
        }
    }

    public Map<String, Integer> getForwardMap() {
        return forwardMap;
    }

    public static void fillKmers(char[][] grid,int k, char[] alphabet, int position) {
        final int devisor=grid.length/((int)Math.pow(alphabet.length,position+1));
        for (int i = 0,j=0; i < grid.length; i++) {
            grid[i][position]=alphabet[j];
            if(i%devisor==0){
                j++;
            }
            if(j==alphabet.length){
                j=0;
            }
        }
        if(position<k-1){
            fillKmers(grid,k,alphabet,position+1);
        }
    }

}
