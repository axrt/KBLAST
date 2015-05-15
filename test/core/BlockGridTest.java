package core;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

        final int k=3;
        final int blockSize=10;
        final KSpace kSpace=new KSpace(k,alphabet);
        try{

            final String sequence= Files.lines(Paths.get("testres/k12.fasta")).skip(1).collect(Collectors.joining());
            final CoordinatedKmerMap coordinatedKmerMap=CoordinatedKmerMap.get(sequence, kSpace);
            final BlockGrid blockGrid=BlockGrid.get(coordinatedKmerMap,kSpace,blockSize);

            Assert.assertEquals(blockGrid.getBlocks().get(0).getSequenceChunk(), "AGCTTTTCAT");
            Assert.assertEquals(blockGrid.getBlocks().get(2).getSequenceChunk(), "ACGGGCAATA");

            Assert.assertEquals(DoubleStream.of(blockGrid.getBlocks().get(2).getKmerFrequency()).sum(),1.0,0.001);


        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
