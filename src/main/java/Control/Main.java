package Control;

import Logic.PortManager;
import Logic.StockManager;
import Model.Port;
import Model.Ship;
import Model.Storage;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Main
{
    public static void main(String args[])
    {
        Port port=new Port(3,8,0);
        Semaphore portSemaphore=new Semaphore(3);
        ReentrantLock lock=new ReentrantLock();
        PortManager portManager=new PortManager(port,portSemaphore,lock);
        StockManager stockManager=new StockManager(port);
        Thread[] threads=new Thread[10];
        threads[0]=new Thread(new Ship(new Storage(7,7),portManager));
        threads[1]=new Thread(new Ship(new Storage(8,8),portManager));
        threads[2]=new Thread(new Ship(new Storage(7,1),portManager));
        threads[3]=new Thread(new Ship(new Storage(5,1),portManager));
        threads[4]=new Thread(new Model.Ship(new Model.Storage(3,3),portManager));
        threads[5]=new Thread(new Model.Ship(new Model.Storage(3,3),portManager));
        threads[6]=new Thread(new Model.Ship(new Model.Storage(3,2),portManager));
        threads[7]=new Thread(new Model.Ship(new Model.Storage(3,2),portManager));
        threads[8]=new Thread(new Model.Ship(new Model.Storage(3,3),portManager));
        threads[9]=new Thread(new Model.Ship(new Model.Storage(1,1),portManager));
        stockManager.setDaemon(true);
        stockManager.start();
        for(int i=0;i<threads.length;i++)
        {
            threads[i].start();
        }


    }
}
