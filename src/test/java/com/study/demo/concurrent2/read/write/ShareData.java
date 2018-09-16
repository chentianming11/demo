package com.study.demo.concurrent2.read.write;

/**
 * @author 陈添明
 * @date 2018/9/16
 */
public class ShareData {

    private  char[] buffer;

    private final ReadWriteLock lock = new ReadWriteLock();

    public ShareData(int size) {
        buffer = new char[size];
        for (int i =0; i < size; i ++){
            buffer[i] = '*';
        }
    }

    /**
     * 读数据
     * @return
     * @throws InterruptedException
     */
    public char[] read() throws InterruptedException {
        try {
            lock.readLock();
            return doRead();

        }finally {
            lock.readUnlock();
        }
    }

    /**
     * 写入
     * @param c
     * @throws InterruptedException
     */
    public void write(char c) throws InterruptedException {
        try {
            lock.writeLock();
            doWrite(c);
        }finally {
            lock.writeUnlock();
        }
    }

    private void doWrite(char c) {
        for (int i = 0; i < buffer.length; i++ ){
            buffer[i] = c;
            slowly(10);
        }
    }

    private char[] doRead() {
        char[] newBuf = new char[buffer.length];

        for (int i = 0; i < buffer.length; i++ ){
            newBuf[i] = buffer[i];
        }

        slowly(50);

        return newBuf;
    }

    private void slowly(int ms) {

        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
