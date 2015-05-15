package core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

/**
 * Created by alext on 5/14/15.
 */
public class BlockGrid implements Serializable{

    protected final int blockSize;
    protected final CoordinatedKmerMap coordinatedKmerMap;
    protected final KSpace kSpace;
    protected final List<Block>blocks;

    protected BlockGrid(CoordinatedKmerMap coordinatedKmerMap, int blockSize, KSpace kSpace){
        this.coordinatedKmerMap=coordinatedKmerMap;
        this.blockSize=blockSize;
        this.kSpace=kSpace;
        final int numBlocks=coordinatedKmerMap.lenght()/blockSize;
        this.blocks=new ArrayList<Block>(numBlocks+1);
        for(int i=0;i<numBlocks;i++){
            this.blocks.add(new Block(coordinatedKmerMap.getSequence(),i,i+blockSize));
        }
        this.blocks.add(new Block(coordinatedKmerMap.getSequence(),numBlocks*blockSize,coordinatedKmerMap.getSequence().length()));
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    protected class Block implements Serializable{

        protected final String sequence;
        protected final int start;
        protected final int end;
        protected final double[]kmerFrequency;
        protected final double meanFrequency;

        protected Block(String sequence,int start, int end){
            this.sequence=sequence;
            this.start=start;
            this.end=end;
            this.kmerFrequency=new double[kSpace.forwardMap.size()];
            final String sequenceChunk=getSequenceChunk();
            final int numberOfKmers=sequenceChunk.length()-kSpace.getK();
            for(int i=0;i<=numberOfKmers;i++){
                this.kmerFrequency[kSpace.getCode(sequenceChunk.substring(i,i+kSpace.getK()+1)).get()]++;
            }
            for(int i=0;i<=numberOfKmers;i++){
                this.kmerFrequency[i]/=this.kmerFrequency.length;
            }
            this.meanFrequency= DoubleStream.of(this.kmerFrequency).sum()/this.kmerFrequency.length;
        }

        public double getMeanFrequency() {
            return meanFrequency;
        }

        public double[] getKmerFrequency() {
            return kmerFrequency;
        }

        public String getSequenceChunk(){
            return this.sequence.substring(this.start,this.end);
        }
    }

    public static BlockGrid get(CoordinatedKmerMap coordinatedKmerMap, KSpace kSpace, int blockSize){
        return new BlockGrid(coordinatedKmerMap,blockSize, kSpace);
    }

}
