package core;

import java.io.Serializable;

/**
 * Created by alext on 5/13/15.
 */
public class CoordinatedKmer implements Serializable{

    protected final int k;
    protected final int coordinate;
    protected final String sequence;

    public CoordinatedKmer(int k, int coordinate, String sequence) {
        this.k = k;
        this.coordinate = coordinate;
        this.sequence = sequence;
    }
    public CoordinatedKmer(int k, int coordinate, char[] sequence) {
        this(k,coordinate,new String(sequence));
    }

    public int getK() {
        return k;
    }

    public int getCoordinate() {
        return coordinate;
    }

    public String getSequence() {
        return sequence;
    }

}
