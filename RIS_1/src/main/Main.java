
package main;

import java.lang.Runnable;
import java.lang.Thread;
import java.util.LinkedList;
import java.util.List;



public class Main {


    private static class Token {

        private int numThreads;
        private int currWaitingThreads;

        public Token(int numThreads) {
            this.numThreads = numThreads;
            this.currWaitingThreads = 0;
        }

        public synchronized boolean areAllWaiting()
        {
            // -1 because if the last threads asks if all other are waiting this should be true
            return (this.numThreads - 1) == this.currWaitingThreads;
        }

        public synchronized void addWaitingThread()
        {
            this.currWaitingThreads += 1;
        }

        public synchronized void reset()
        {
            this.currWaitingThreads = 0;
        }

        public synchronized int getSize()
        {
            return this.currWaitingThreads;
        }
    }

    public static void main(String [] args)
    {

        int endValue = 100;
        int numThreads = 5;

        //Object token = new Object();
        Token token = new Token(numThreads);

        List<Thread> threadPool = new LinkedList<>();

        for(int i = 1; i <= numThreads; ++i) {

            threadPool.add(new Thread(() -> {

                for(int x = 1; x < endValue; ++x)
                {
                    synchronized (token)
                    {
                        if(x % 20 == 0)
                        {
                            if(token.areAllWaiting())
                            {
                                System.out.println("\nWake up waiting threads.\n");
                                token.reset();
                                token.notifyAll();
                            }
                            else
                            {
                                try
                                {
                                    System.out.println(Thread.currentThread().getName()+" now waiting ...");
                                    token.addWaitingThread();
                                    token.wait();
                                    System.out.println(Thread.currentThread().getName()+" working again!");
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        else
                            System.out.println(Thread.currentThread().getName()+" : " + x);
                    }

                }
            }, "thread-"+i));
        }

        for(Thread thread : threadPool)
            thread.start();
    }

    public Main() {



    }
}
