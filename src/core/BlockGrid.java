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
        System.out.println("Number of blocks: " + (numBlocks + 1));
        this.blocks=new ArrayList<Block>(numBlocks+1);
        for(int i=0;i<numBlocks;i++){
            //System.out.println("Block "+i);
            this.blocks.add(new Block(coordinatedKmerMap.getSequence(),i*blockSize,i*blockSize+blockSize));
        }

        this.blocks.add(new Block(coordinatedKmerMap.getSequence(),numBlocks*blockSize,coordinatedKmerMap.getSequence().length()));
        System.out.println("Blocks parsed.");
    }

    public List<Block> getBlocks() {
        return blocks;
    }
    public int length(){
        return this.getBlocks().size();
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
                this.kmerFrequency[kSpace.getCode(sequenceChunk.substring(i,i+kSpace.getK())).get()]++;
            }
            final double norm=DoubleStream.of(this.kmerFrequency).sum();
            for(int i=0;i<kSpace.forwardMap.size();i++){
                this.kmerFrequency[i]/=norm;
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

    public static List<Block> getMaximulLikelyBlocks(BlockGrid target,BlockGrid query){
        //System.out.println("Searching..");
        final List<Block>targetBlocks=target.getBlocks();
        final List<Block>queryBlocks=query.getBlocks();
        final int degFreedom=targetBlocks.size()-queryBlocks.size();

        double minErr=0;
        for(int j=0;j<queryBlocks.size();j++){
            minErr+=distance(targetBlocks.get(j).getKmerFrequency(),queryBlocks.get(j).getKmerFrequency());
        }

        int minErrPosition=0;
        outer:
        for(int i=1;i<degFreedom;i++){
            double err=0;
            for(int j=0;j<queryBlocks.size();j++){
                err+=distance(targetBlocks.get(i+j).getKmerFrequency(),queryBlocks.get(j).getKmerFrequency());
                if(err>minErr){
                    continue outer;
                }
            }
            if(err<minErr){
                minErr=err;
                minErrPosition=i;
            }
        }
        if(minErrPosition>0){
            minErrPosition=minErrPosition-1;
        }
        //System.out.println("Done searching.");
        if(minErrPosition+queryBlocks.size()+1<targetBlocks.size()){
            return targetBlocks.subList(minErrPosition,minErrPosition+queryBlocks.size()+1);
        }
        return targetBlocks.subList(minErrPosition,minErrPosition+queryBlocks.size());
    }

    public static double distance(double[]vec1,double[]vec2){

        double d=0;
        for(int i=0;i<vec1.length;i++){
            d+=Math.pow(vec1[i]-vec2[i],2);
        }
        return d;

    }

}
