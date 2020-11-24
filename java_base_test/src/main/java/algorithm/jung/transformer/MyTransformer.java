package algorithm.jung.transformer;

import org.apache.commons.collections15.Transformer;

/**
 * @author: Kled
 * @version: MyTransformer.java, v0.1 2020-10-15 10:03 Kled
 */
public abstract class MyTransformer<I, O> implements Transformer<I, O> {

    @Override
    public O transform(I i) {
        O edge_weight_number = this.doTransform(i);
        double edge_weight = ((Number)edge_weight_number).doubleValue();
        if (edge_weight < 0.0D) {
            throw new IllegalArgumentException("Edges weights must be non-negative");
        }
        return edge_weight_number;
    }

    abstract O doTransform(I i);

    public abstract double calculateNewDist(I i, double oldDist);
}
