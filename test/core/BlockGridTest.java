package core;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * Created by alext on 5/15/15.
 */
public class BlockGridTest {

    @Test
    public void test(){
        final Alphabet alphabet = new Alphabet(new char[]{'A', 'T', 'C', 'G'}) {
        };

        final int k=7;
        final int blockSize=10000;
        final KSpace kSpace=new KSpace(k,alphabet);
        try{

            final String sequence= Files.lines(Paths.get("testres/k12.fasta")).skip(1).collect(Collectors.joining());
            final CoordinatedKmerMap coordinatedKmerMap=CoordinatedKmerMap.get(sequence, kSpace);
            final BlockGrid blockGrid=BlockGrid.get(coordinatedKmerMap,kSpace,blockSize);

            //Assert.assertEquals(blockGrid.getBlocks().get(0).getSequenceChunk(), "AGCTTTTCATTCTGACTGCAACGGGCAATA");
            //Assert.assertEquals(blockGrid.getBlocks().get(2).getSequenceChunk(), "ACGGGCAATATGTCTCTGTGTGGATTAAAA");

            //Assert.assertEquals(DoubleStream.of(blockGrid.getBlocks().get(2).getKmerFrequency()).sum(),1.0,0.001);

            //Search
            final String querySequence="ACTGGAGCCGCTGGCAGTGACGGAACGGCTGGCCATTATCTCGGTGGTAGGTGATGGTATGCGCACCTTGCGTGGGATCTCGGCGAAATTCTTTGCCGCACTGGCCCGCGCCAATAT";

            final CoordinatedKmerMap queryCoordinatedKmerMap=CoordinatedKmerMap.get(querySequence,kSpace);
            final BlockGrid queryBlockGrid=BlockGrid.get(queryCoordinatedKmerMap,kSpace,blockSize);
            List<BlockGrid.Block> hitBlocks=null;
            final long start=System.currentTimeMillis();
            for(int i=0;i<10000;i++){
                hitBlocks=BlockGrid.getMaximulLikelyBlocks(blockGrid,queryBlockGrid);
            }
            final long stop=System.currentTimeMillis();
            System.out.println("Took: "+(stop-start));
            final String hit=hitBlocks.stream().map(hb -> {
                return hb.getSequenceChunk();
            }).collect(Collectors.joining());
            System.out.println(hit);
            Assert.assertTrue(hit.contains(querySequence));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
