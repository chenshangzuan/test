package java_base;

import org.javatuples.KeyValue;
import org.javatuples.LabelValue;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.javatuples.Unit;

/**
 * @author chenpc
 * @version $Id: java_base.TestTuples.java, v 0.1 2018-06-25 16:28:54 chenpc Exp $
 */
public class TestTuples {
    public static void main(String[] args) {
        // 1元组
        Unit<String> u = new Unit<String>("rensanning.iteye.com");
        // 2元组
        Pair<String,Integer> p = Pair.with("rensanning.iteye.com", 9527);
        // 3元组
        Triplet<String,Integer,Double> triplet = Triplet.with("rensanning.iteye.com", 9527, 1.0);
        //...

        KeyValue<String,String> kv = KeyValue.with("rensanning.iteye.com", "9527");
        LabelValue<String,String> lv = LabelValue.with("rensanning.iteye.com", "9527");


    }
}
