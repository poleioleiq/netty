package juc;

public class MyVolatile2 {
      volatile static boolean flag=true;
    static int i=0;


    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            while(flag){

            }
            System.out.println(Thread.currentThread().getName()+" "+i);
        }).start();

        Thread.sleep(3000);

        new Thread(()->{
            i=2;
            flag=false;
            System.out.println(Thread.currentThread().getName() + " 修改完成");
        }).start();

        System.out.println("主线程退出");

    }
}
