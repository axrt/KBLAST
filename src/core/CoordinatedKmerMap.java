package core;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alext on 5/13/15.
 */
public class CoordinatedKmerMap implements Serializable {

    protected final String sequence;
    protected final List<CoordinatedKmer> coordinatedKmers;

    protected CoordinatedKmerMap(String sequence,List<CoordinatedKmer> coordinatedKmers) {
        this.sequence=sequence;
        this.coordinatedKmers = coordinatedKmers;
    }

    public CoordinatedKmer get(int i){
        return this.coordinatedKmers.get(i);
    }

    public int lenght(){
        return this.coordinatedKmers.size();
    }

    protected static CoordinatedKmerMap get(String sequence,KSpace kSpace){
        final List<CoordinatedKmer> coordinatedKmerList=parse(sequence,kSpace);
        return new CoordinatedKmerMap(sequence,coordinatedKmerList);
    }

    protected static List<CoordinatedKmer> parse(String sequence,KSpace kSpace){
        final int edge=sequence.length()-kSpace.getK();
        final List<CoordinatedKmer>coordinatedKmerList=new ArrayList<CoordinatedKmer>(edge);
        final char[]chars=sequence.toCharArray();
        for(int i=0;i<=edge;i++){
            final char[]kmer=new char[3];
            System.arraycopy(chars,i,kmer,0,kSpace.getK());
            final CoordinatedKmer coordinatedKmer=new CoordinatedKmer(kSpace.getK(),i,kmer);
            coordinatedKmerList.add(coordinatedKmer);
        }
        return coordinatedKmerList;
    }

}
