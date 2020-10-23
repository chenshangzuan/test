package algorithm.jung.transformer;

import algorithm.jung.Link;

/**
 * @author: Kled
 * @version: PriorityTransformer.java, v0.1 2020-10-14 21:49 Kled
 */
public class PriorityTransformer extends MyTransformer<Link, Integer> {

    @Override
    Integer doTransform(Link link) {
        return link.getPriority();
    }

    @Override
    public double calculateNewDist(Link link, double oldDist) {
        return doTransform(link) + oldDist;
    }
}
