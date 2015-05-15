package core;

import java.io.Serializable;

/**
 * Created by alext on 5/13/15.
 */
public class CoordinatedKmer implements Serializable{

    protected final int k;
    protected final int coordinate;
    protected final int code;

    public CoordinatedKmer(int k, int coordinate, int code) {
        this.k = k;
        this.coordinate = coordinate;
        this.code = code;
    }

    public int getK() {
        return this.k;
    }

    public int getCoordinate() {
        return this.coordinate;
    }

    public int getCode() {
        return this.code;
    }
}
