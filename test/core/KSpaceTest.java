package core;

import junit.framework.Assert;
import org.junit.Test;

import java.util.Map;


/**
 * Created by alext on 5/13/15.
 */
public class KSpaceTest {

    @Test
    public void test() {

        final Alphabet alphabet = new Alphabet(new char[]{'A', 'T', 'C', 'G'}) {
        };

        final int k=3;
        final KSpace kSpace=new KSpace(k,alphabet);
        final Map<String,Integer>forwardMap=kSpace.getForwardMap();
        for(Map.Entry<String,Integer>e:forwardMap.entrySet()){
            System.out.println(e.getKey() + "<->" + e.getValue());
        }
        System.out.println(forwardMap.size());
        Assert.assertEquals((int)Math.pow(alphabet.getSymbols().size(),k),forwardMap.size());
    }
}
