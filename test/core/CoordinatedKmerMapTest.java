package core;

import junit.framework.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * Created by alext on 5/13/15.
 */
public class CoordinatedKmerMapTest {

    @Test
    public void test(){

        final Alphabet alphabet = new Alphabet(new char[]{'A', 'T', 'C', 'G'}) {
        };

        final int k=3;
        final KSpace kSpace=new KSpace(k,alphabet);
        try{
            final String sequence= Files.lines(Paths.get("testres/k12.fasta")).skip(1).collect(Collectors.joining());
            final CoordinatedKmerMap coordinatedKmerMap=CoordinatedKmerMap.get(sequence, kSpace);

            Assert.assertEquals(coordinatedKmerMap.lenght(),sequence.length()-kSpace.getK()+1);
            Assert.assertEquals(coordinatedKmerMap.get(100).getCode(),32);

        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
