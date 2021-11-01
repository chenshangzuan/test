package hive.udtf;

import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.util.ArrayList;

/**
 * @author Kled
 * @date 2021/9/6 4:02 下午
 */
//public class ExplodeMapUDTF extends GenericUDTF {
//    @Override
//    public void close() throws HiveException {
//        // TODO Auto-generated method stub
//    }
//
//    @Override
//    public StructObjectInspector initialize(ObjectInspector[] args)
//            throws UDFArgumentException {
//        if (args.length != 1) {
//            throw new UDFArgumentLengthException("ExplodeMap takes only one argument");
//        }
//        if (args[0].getCategory() != ObjectInspector.Category.PRIMITIVE) {
//            throw new UDFArgumentException("ExplodeMap takes string as a parameter");
//        }
//
//        ArrayList<String> fieldNames = new ArrayList<String>();
//        ArrayList<ObjectInspector> fieldOIs = new ArrayList<ObjectInspector>();
//        fieldNames.add("col1");
//        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
//        fieldNames.add("col2");
//        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
//
//        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames,fieldOIs);
//    }
//
//    @Override
//    public void process(Object[] args) throws HiveException {
//        String input = args[0].toString();
//        String[] test = input.split(";");
//        for(int i=0; i<test.length; i++) {
//            try {
//                String[] result = test[i].split(":");
//                forward(result);
//            } catch (Exception e) {
//                continue;
//            }
//        }
//    }
//}
