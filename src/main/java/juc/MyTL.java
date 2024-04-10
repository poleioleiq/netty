package juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyTL {
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(3);
        String[]  users  = {"a", "b", "c", "d", "e"};
        for(String user:users){
            es.submit(new MyT(user));
        }
    }
}
class MyT implements Runnable{
    final String user;

    public MyT(String user) {
        this.user=user;
    }

    public void run() {
        try(var t=new UserContext(this.user)){
            new Task1().process();

//            new Task2().process();
//            new Task1().process();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        new Integer(1).toString();
    }
}
class Task1{
    public void process(){
        System.out.println(Thread.currentThread().getName()+" 1 "+UserContext.get());
    }

}
class Task2{
    public void process(){
        System.out.println(Thread.currentThread().getName()+" 2 "+UserContext.get());
    }

}
class UserContext implements AutoCloseable{
    private static final ThreadLocal<String> threadLocal=new ThreadLocal<String>();
    public static String get(){
        return  threadLocal.get();
    }
    public UserContext(String user){
        threadLocal.set(user);
    }

    public void close() throws Exception {
        threadLocal.remove();
    }
}


