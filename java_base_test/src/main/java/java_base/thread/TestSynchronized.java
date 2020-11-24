package java_base.thread;

/**
 * @author chenpc
 * @version $Id: java_base.TestSynchronized.java, v 0.1 2018-05-02 16:40:12 chenpc Exp $
 */
public class TestSynchronized {

    final SynObj obj = new SynObj();
    final SynObj obj2 = new SynObj();

    public static void main(String[] args) {
        TestSynchronized test = new TestSynchronized();
        //1.成员锁测试，只锁引用成员变量
        //new Thread(test.obj::methodC).start();
        //new Thread(test.obj::methodD).start();
        //2.对象锁测试， methodA的锁颗粒较methodB大
        //new Thread(test.obj::methodA).start();
        //new Thread(test.obj::methodB).start();
        //3.类锁测试，类锁和成员锁共存
        //new Thread(test.obj::methodE).start();
        //new Thread(test.obj::methodB).start();
        //4.wait()释放对象锁, notify()唤醒此对象锁所等待的线程
        new Thread(test.obj::methodF).start();
        new Thread(test.obj::methodG).start();


        //结:
        //1 类锁和对象锁隔离
        //2 对象锁，锁住该锁申明的所有方法，方法比代码块要先加锁
        //3 静态变量具有相同引用，同一对象和不同对象间抢一把锁(注意包装类的特殊性)
        //4 方法内申明的引用(非静态)，同一对象或不同对象在调用方法时在方法栈内申明不同的对象，持有不同的锁
        //5 sleep不释放对象锁。wait()和notify()必须在synchronized代码块中调用。
        //6 Synchronized为可重入锁
    }

}

class SynObj {

    String str = "sss";
    Integer i1= 130; //超过127之后，不同对象会在堆中创建对象
    Integer i2= 150;

    public synchronized void methodA() {
        System.out.println("methodA.....start");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("methodA.....end");
    }


    public void methodB() {
        synchronized(this) {
            System.out.println("methodB.....");
        }
    }

    public void methodE() {
        synchronized(SynObj.class) {
            System.out.println("methodE.....");
        }
    }

    public void methodC() {
        //String str = "sss";   //相同的常量在常量池的引用相同，所以同一对象或者不同对象调用时，均锁相同的引用(同步执行)
        //Integer i = new Integer(1);
        synchronized (i1) { //锁i对象。同一个对象方法内的新建对象也不同，方法栈间隔离。
            System.out.println("methodC.....start");
            try{
                Thread.sleep(2000);
            }catch(Exception ex){

            }
            System.out.println("methodC.....end");
        }
    }

    public void methodD() {
        synchronized (i2) {
            System.out.println("methodD.....start");
            try{
                Thread.sleep(2000);
            }catch(Exception ex){

            }
            System.out.println("methodD.....end");
        }
    }


    public synchronized void methodF() {
        System.out.println("methodF.....start");
        try {
            wait();
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("methodF.....end");
    }

    public void methodG() {
        synchronized(this) {
            System.out.println("methodG.....");
            notify();
        }
    }
}