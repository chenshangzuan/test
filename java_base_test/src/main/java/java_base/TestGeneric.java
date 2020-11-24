package java_base;


import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author chenpc
 * @version $Id: java_base.TestGeneric.java, v 0.1 2018-05-23 17:43:58 chenpc Exp $
 */
public class TestGeneric<T> {

    private  T s;

    TestGeneric(T s){
        this.s  = s;
    }
    public static void main(String[] args) {

        TestGeneric<Fruit>  testGeneric = new TestGeneric<>(new Orange());
        List<Fruit> list = Lists.newArrayList();
        testGeneric.test1(testGeneric.s,list);

    }

    private <T extends Fruit> void test1( T s, List<? super Apple> l) {  //泛型方法的参数必须声明 <T>
        //List<? super Object> --> consumer  add -->CS --> 从集合中写入类型T的数据，并且不需要读取
        //List<?> 一般用于非null的判断 add(null)  get Object 因为编译器认为存在某种类型，无法确定是什么类型，所以无法add
        //List<E> get和add E  强约束 在使用时需要明确指明那个类
        //List RawType 不推荐使用 可add任意， get Object
        l.add(new Apple());   // 例：List<? super java_base.Apple> 可能指向List<java_base.Fruit> , get时可能会获取一个Fruit，不可由Apple接收
                              // add 一个Apple可被 List<java_base.Fruit>接收  <==>  l.get()无法使用使用一个具体的类接收，l内均为apple的父类
        System.out.println(s);
    }

    private <T> void test2(T s, List<? extends Fruit> l) {
        //List<? extends Object> -->producer get -->PE  --> 从集合中读取类型T的数据，并且不能写入
        l.get(0);   // 例：List<? extends java_base.Fruit>可能指向List<java_base.Apple>/List<java_base.Orange>/List<java_base.Fruit>, add 一个Apple可能被List<java_base.Orange>接收 所以不可使用add
                    // get则可由父类 Fruit接收
        System.out.println(s);
    }

    // List<?> <--> List<? extends Object>
    private void test3(List<?> l){
    }


}
class Fruit {}

class Apple extends Fruit {}

class Orange extends Fruit {}
