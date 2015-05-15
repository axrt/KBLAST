package core;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public String getSequence() {
        return sequence;
    }

    protected static CoordinatedKmerMap get(String sequence,KSpace kSpace){
        final List<CoordinatedKmer> coordinatedKmerList=parse(sequence,kSpace);
        return new CoordinatedKmerMap(sequence,coordinatedKmerList);
    }

    protected static List<CoordinatedKmer> parse(String sequence,KSpace kSpace){
        final int edge=sequence.length()-kSpace.getK();
        final List<CoordinatedKmer>coordinatedKmerList=new ArrayList<CoordinatedKmer>(edge);
        for(int i=0;i<=edge;i++){
            final String kmer=sequence.substring(i,i+kSpace.getK());
            final Optional<Integer> code=kSpace.getCode(kmer);
            final CoordinatedKmer coordinatedKmer;
            if(code.isPresent()){
                coordinatedKmer=new CoordinatedKmer(kSpace.getK(),i,code.get());
            }else{
                throw new IllegalArgumentException("A corrupt kSpace provided..");
            }
            coordinatedKmerList.add(coordinatedKmer);
        }
        return coordinatedKmerList;
    }

}
