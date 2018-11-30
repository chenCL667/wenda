package com.example.wenda;

/**
 * Created by chen on 2018/11/30.
 */
class MyThread extends Thread{
    private int id;

    public MyThread(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
//                Thread.sleep(1000);
                System.out.printf("%d: %d ", id, i);
                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
public class MultiThreadTest {

    public static void testThread(){
        for (int i = 0; i < 10; i++) {
            new MyThread(i).start();
        }
    }
    public static void main(String[] args) {
        MultiThreadTest multiThreadTest = new MultiThreadTest();
        multiThreadTest.testThread();

    }


}
