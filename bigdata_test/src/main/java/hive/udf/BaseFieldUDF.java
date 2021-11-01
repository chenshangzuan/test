package hive.udf;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.JavaStringObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Kled
 * @date 2021/8/15 7:30 下午
 */
//public class BaseFieldUDF extends GenericUDF {
//    StringObjectInspector line;
//    StringObjectInspector jsonkeysString;
//
//    @Override
//    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
//        if (arguments.length != 2) {
//            throw new UDFArgumentLengthException("BaseFieldUDF only takes 2 arguments: String, String");
//        }
//        ObjectInspector a = arguments[0];
//        ObjectInspector b = arguments[1];
//        if (!(a instanceof StringObjectInspector) || !(b instanceof StringObjectInspector)) {
//            throw new UDFArgumentException("first argument and second argument must be a string");
//        }
//        this.line = (StringObjectInspector) a;
//        this.jsonkeysString = (StringObjectInspector) b;
//
//        return PrimitiveObjectInspectorFactory.javaStringObjectInspector;
//    }
//
//    @Override
//    public Object evaluate(DeferredObject[] arguments) throws HiveException {
//        String line = this.line.getPrimitiveJavaObject(arguments[0].get());
//        String jsonkeysString = this.jsonkeysString.getPrimitiveJavaObject(arguments[1].get());
//        String[] jsonkeys = jsonkeysString.split(",");
//        String[] logContents = line.split("\\|");
//        if (logContents.length != 2 || StringUtils.isBlank(logContents[1])) {
//            return null;
//        }
//        StringBuilder sb = new StringBuilder();
//        try {
//            JSONObject jsonObject = new JSONObject(logContents[1]);
//            JSONObject base = jsonObject.getJSONObject("cm");
//            for (String jsonkey : jsonkeys) {
//                String fieldName = jsonkey.trim();
//                if (base.has(fieldName)) {
//                    sb.append(base.getString(fieldName)).append("\t");
//                } else {
//                    sb.append("\t");
//                }
//            }
////            sb.append(jsonObject.getString("et")).append("\t");
//            sb.append(logContents[0]).append("\t");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return sb.toString();
//    }
//
//    @Override
//    public String getDisplayString(String[] children) {
//        return "BaseFieldUDF()";
//    }
//    // 测试类
//    public static void main(String[] args) throws HiveException {
//        BaseFieldUDF example = new BaseFieldUDF();
//        ObjectInspector stringOI = PrimitiveObjectInspectorFactory.javaStringObjectInspector;
//        String line = "1541217850324|{\"cm\":{\"ln\":\"-90.4\",\"sv\":\"V2.2.7\",\"os\":\"8.2.4\",\"g\":\"DP127CN4@gmail.com\",\"mid\":\"447\",\"nw\":\"WIFI\",\"l\":\"en\",\"vc\":\"0\",\"hw\":\"640*1136\",\"ar\":\"MX\",\"uid\":\"447\",\"t\":\"1604451309064\",\"la\":\"14.7\",\"md\":\"sumsung-17\",\"vn\":\"1.2.4\",\"ba\":\"Sumsung\",\"sr\":\"H\"},\"ap\":\"app\"}";
//        JavaStringObjectInspector resultInspector = (JavaStringObjectInspector) example.initialize(new ObjectInspector[]{stringOI, stringOI});
//        Object result = example.evaluate(new DeferredObject[]{new DeferredJavaObject(line), new DeferredJavaObject("mid,uid,vc,vn,l,sr,os,ar,md,ba,sv,g,hw,nw,ln,la,t")});
////        System.out.println(resultInspector.getPrimitiveJavaObject(result).split("\t").length);
//        System.out.println(resultInspector.getPrimitiveJavaObject(result));
//    }
//}
