package algorithm.jung.transformer;

import algorithm.jung.Link;
import org.apache.commons.collections15.Transformer;

/**
 * @author: Kled
 * @version: PriorityTransformer.java, v0.1 2020-10-14 21:49 Kled
 */
public class HopTransformer extends MyTransformer<Link, Integer> {

    @Override
    Integer doTransform(Link link) {
        return 1;
    }

    @Override
    public double calculateNewDist(Link link, double oldDist) {
        return doTransform(link) + oldDist;
    }
}
