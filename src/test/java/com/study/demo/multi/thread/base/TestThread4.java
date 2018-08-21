package com.study.demo.multi.thread.base;

/**
 * @author 陈添明
 * @date 2018/8/19
 */
class Chat {
    /**
     * 问题状态
     * true：待回答  false：已回答
     */
    boolean flag = false;


    /**
     * 提问
     * @param msg
     */
    public synchronized void Question(String msg) {
        if (flag) {
            try {
                wait();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(msg);
        flag = true;
        notify();
    }

    /**
     * 回答
     * @param msg
     */
    public synchronized void Answer(String msg) {
        if (!flag) {
            try {
                wait();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(msg);
        flag = false;
        notify();
    }
}

class T1 implements Runnable {
    Chat m;
    String[] s1 = { "Hi", "How are you ?", "I am also doing fine!" };

    public T1(Chat m1) {
        this.m = m1;
        new Thread(this, "Question").start();
    }

    public void run() {
        for (int i = 0; i < s1.length; i++) {
            m.Question(s1[i]);
        }
    }
}

class T2 implements Runnable {
    Chat m;
    String[] s2 = { "Hi", "I am good, what about you?", "Great!" };

    public T2(Chat m2) {
        this.m = m2;
        new Thread(this, "Answer").start();
    }

    public void run() {
        for (int i = 0; i < s2.length; i++) {
            m.Answer(s2[i]);
        }
    }
}
public class TestThread4 {
    public static void main(String[] args) {

        Chat m = new Chat();
        // 提问线程
        new T1(m);
        // 回答线程
        new T2(m);
    }
}
