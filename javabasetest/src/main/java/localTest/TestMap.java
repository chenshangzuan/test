package localTest;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

/**
 * @author kled
 * @version $Id: TestMap.java, v 0.1 2018-12-18 19:24:40 kled Exp $
 */
public class TestMap {
    private HashMap<String, String> map = new HashMap<String, String>() {
        {
            put("test1", "test1");
            put("test2", "test2");
            put("test3", "test3");
        }
    };

    public static void main(String[] args) {
        TestMap testMap = new TestMap();
        //for (String key : testMap.map.keySet()) {
        //    try{
        //        if(key.equals("test2")){
        //            testMap.map.remove(key);
        //        }
        //    }catch(ConcurrentModificationException ex){
        //        System.out.println("xxxx");
        //    }
        //}
        //System.out.println(testMap.map);

        Iterator<Map.Entry<String, String>> iterator = testMap.map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> mapEntry = iterator.next();
            if (mapEntry.getKey().equals("test2")) {
                iterator.remove();
            }
        }
        System.out.println(testMap.map);

        testMap.map.forEach((k, v) -> {
            System.out.println("key=" + k + ", value=" + v);
        });
    }
}
