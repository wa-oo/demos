package com.example;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {
    private static Timer timer;

    public static void main(String[] args) {
        System.out.println("timer begin....");

        // TimerTest01(3);
        // TimerTest02();
        TimerTest03();
    }

    /**
     * 指定延迟时间执行定时任务
     *
     * @param time
     */
    public static void TimerTest01(int time) {

        timer = new Timer();
        timer.schedule(new TimerTaskTest01(), time * 1000);
    }

    public static class TimerTaskTest01 extends TimerTask {
        public void run() {
            System.out.println("Time's up!!!!");
        }
    }

    //-----------------------------------------------------

    /**
     * 在指定时间执行定时任务
     */
    public static void TimerTest02() {
        Date time = getTime();
        System.out.println("指定时间time=" + time);
        timer = new Timer();
        timer.schedule(new TimerTaskTest02(), time);
    }

    public static Date getTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 50);
        calendar.set(Calendar.SECOND, 00);
        Date time = calendar.getTime();
        return time;
    }

    public static class TimerTaskTest02 extends TimerTask {
        @Override
        public void run() {
            System.out.println("指定时间执行线程任务...");
        }
    }

    //-----------------------------------------------------------------------------

    /**
     * 在延迟指定时间后以指定的间隔时间循环执行定时任务
     */
    public static void TimerTest03() {
        timer = new Timer();
        timer.schedule(new TimerTaskTest03(), 3000, 5000);
    }

    public static class TimerTaskTest03 extends TimerTask {
        @Override
        public void run() {
            Date date = new Date(this.scheduledExecutionTime());
            System.out.println("本次执行该线程的时间为：" + date);
        }
    }

}

