package juc;

import java.util.concurrent.TimeUnit;

public class MyVolatileTest {
    /*volatile */ static boolean flag=true;
    int i=0;

    public void write(){
        i=2;
        flag=false;
//        System.out.println(Thread.currentThread().getName()+"修改完成   "+i+"   "+flag);
    }
    public void read(){
        System.out.println(Thread.currentThread().getName() + "进入read");
        while(flag){

        }
        System.out.println(Thread.currentThread().getName()+"i的值是"+i);

    }


    public static void main(String[] args) throws InterruptedException {
        System.out.println("主线程开始");
        MyVolatileTest myVolatileTest = new MyVolatileTest();
//        for (int i = 0; i < 1000; i++) {
            new Thread(()->{myVolatileTest.write();}).start();
            new Thread(()->{myVolatileTest.read();}).start();
//        }


        Thread.sleep(3000);
        System.out.println("主线程结束");
    }

}
