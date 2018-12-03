package Logic;

import Model.Port;
import org.apache.log4j.Logger;

import java.util.concurrent.locks.ReentrantLock;

public class StockManager extends Thread
{
    private static final Logger logging =Logger.getLogger(PortManager.class);
    public StockManager(Port port)
    {
        this.port=port;
    }
    private Port port;
    private ReentrantLock stockLock=new ReentrantLock();

    public void run()
    {
        while (true)
        {
            try
            {
                sleep(2000);
            }
            catch (InterruptedException e)
            {
                logging.error("Thread was interrupted");
            }
            stockLock.lock();
            if(port.getPortStorage().getCurrentNumberOfContainers()<port.getPortStorage().getMaxNumberOfContainers()/2)
            {
                port.getPortStorage().setCurrentNumberOfContainers(((port.getPortStorage().getMaxNumberOfContainers()/5)*4));
            }
            else if(port.getPortStorage().getCurrentNumberOfContainers()==port.getPortStorage().getMaxNumberOfContainers()/5*4)
            {
                port.getPortStorage().setCurrentNumberOfContainers(port.getPortStorage().getMaxNumberOfContainers()/5);
            }
            stockLock.unlock();

        }
    }
}
