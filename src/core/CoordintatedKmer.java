package core;

/**
 * Created by alext on 5/13/15.
 */
public class CoordintatedKmer {

    protected final int k;
    protected final int coordinate;
    protected final String sequence;

    public CoordintatedKmer(int k, int coordinate, String sequence) {
        this.k = k;
        this.coordinate = coordinate;
        this.sequence = sequence;
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
