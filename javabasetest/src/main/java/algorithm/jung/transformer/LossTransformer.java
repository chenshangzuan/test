package algorithm.jung.transformer;

import algorithm.jung.Link;

import java.math.BigDecimal;

/**
 * @author: Kled
 * @version: PriorityTransformer.java, v0.1 2020-10-14 21:49 Kled
 */
public class LossTransformer extends MyTransformer<Link, BigDecimal> {

    @Override
    BigDecimal doTransform(Link link) {
        return link.getLoss();
    }

    @Override
    public double calculateNewDist(Link link, double oldDist) {
        return 1 - (1 - doTransform(link).doubleValue()) * (1- oldDist);
    }
}
