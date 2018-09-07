package com.wt.concurrent;

/**
 * thread舞台
 */
public class Stage extends Thread {

    public static void main(String[] args) {
        new Stage().start();
    }

    public void run(){

        ArmyRunnable armyTask1 = new ArmyRunnable();
        ArmyRunnable armyTask2 = new ArmyRunnable();

        //使用Runnable接口创建接口
        Thread army1 = new Thread(armyTask1,"army1");
        Thread army2 = new Thread(armyTask2,"army2");

        //启动线程
        army1.start();
        army2.start();

        //线程休眠
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        armyTask1.keepRunning = false;
        armyTask2.keepRunning = false;

        try {
            army2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
