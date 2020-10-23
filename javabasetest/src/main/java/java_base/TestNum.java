package java_base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestNum {

    private static final Logger logger = LoggerFactory.getLogger(TestNum.class);

    public static void main(String[] args) throws UnsupportedEncodingException {

        Integer isMasterCpe = 0;
        System.out.println( isMasterCpe ^ 1);

        System.out.println("max integer" + Integer.MAX_VALUE);

        List<Integer> missedRecoverTaskIds = new ArrayList<>();
        List<Integer> taskIds = new ArrayList<>();
        taskIds.add(3);
        taskIds.add(4);
        taskIds.add(7);
        int firstId = 0;

        for(int i = 0;i< taskIds.size();){
            if(++firstId<taskIds.get(i)){
                missedRecoverTaskIds.add(firstId);
            }else{
                i++;
            }
        }
        //for(Integer i :missedRecoverTaskIds){
        //    System.out.println(i);
        //}

        System.out.println(missedRecoverTaskIds);

        List<Integer> testIds = new ArrayList<>();
        testIds.add(3);
        testIds.add(4);
        testIds.add(7);
        Iterator<Integer> it = testIds.iterator();
        while(it.hasNext()){
            Integer i = it.next();
            if(i == 3){
                it.remove();
            }
        }
        System.out.println(testIds);

        Integer i1 = new Integer(1);
        Integer i2 = new Integer(1);
        Integer i3 = 1;
        System.out.println(i1==i2);  //引用对象地址比较
        System.out.println(i1==1);
        System.out.println(i1==i3);
    }

}
