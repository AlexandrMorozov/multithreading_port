package Logic;

import Model.Port;
import Model.Ship;
import org.apache.log4j.Logger;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class PortManager
{
    private static final Logger logging =Logger.getLogger(PortManager.class);
    public PortManager(Port port, Semaphore portSemaphore, ReentrantLock portLock)
    {
        this.port=port;
        this.portLock=portLock;
        this.portSemaphore=portSemaphore;
        condition=portLock.newCondition();
    }
    private Port port;
    private Semaphore portSemaphore;
    private ReentrantLock portLock;
    private Condition condition;
    public Ship receiveShip(Ship arrivingShip)
    {
        Ship result=null;
        try
        {
            portSemaphore.acquire();
            portLock.lock();
            for(int i=0;i<port.getDocks().length;i++)
            {
                if(port.getDocks()[i].isBusy()==false)
                {
                    port.getDocks()[i].setBusy(true);
                    port.getDocks()[i].setCurrentShip(arrivingShip);
                    arrivingShip.setDockNumber(i);
                    result=port.getDocks()[i].getCurrentShip();
                    break;
                }
            }
        }
        catch (InterruptedException e)
        {
            logging.error("Thread was interrupted");
        }
        finally
        {
            portLock.unlock();
        }
        return result;
    }
    public int loadContainers(Ship ship)
    {
        portLock.lock();
        if(port.getPortStorage().getCurrentNumberOfContainers()>0)
        {
            ship.getShipStorage().receiveContainers(port.getPortStorage());
        }
        portLock.unlock();
        return ship.getShipStorage().getCurrentNumberOfContainers();
    }
    public int unloadContainers(Ship ship)
    {
        portLock.lock();
        if(port.getPortStorage().isStorageFull()==false)
        {
            System.out.println("Model.Port storage: "+port.getPortStorage().getCurrentNumberOfContainers());
            port.getPortStorage().receiveContainers(ship.getShipStorage());
            System.out.println("After unloading: "+port.getPortStorage().getCurrentNumberOfContainers());
            System.out.println(ship.getShipStorage().getCurrentNumberOfContainers());
        }
        else
        {
            try
            {
                condition.await();
            }
            catch (InterruptedException e)
            {
                logging.error("Thread was interrupted");
            }
        }

        portLock.unlock();
        return port.getPortStorage().getCurrentNumberOfContainers();
    }
    public Ship unloadToShips(Ship ship)
    {
        portLock.lock();
        boolean hasShips=false;
        for(int f=0;f<port.getDocks().length;f++)
        {
            if(port.getDocks()[f].isBusy()==true && port.getDocks()[f].getCurrentShip().equals(ship)==false)
            {
                hasShips=true;
                break;
            }
        }
        if(hasShips==true)
        {
            for(int f=0;f<port.getDocks().length;f++)
            {
                if(port.getDocks()[f].isBusy()==true && port.getDocks()[f].getCurrentShip().equals(ship)==false &&
                        port.getDocks()[f].getCurrentShip().isUnloaded()==true && ship.getShipStorage().getCurrentNumberOfContainers()!=0)
                {
                    port.getDocks()[f].getCurrentShip().getShipStorage().receiveContainers(ship.getShipStorage());
                }
            }
        }
        else
        {
            try
            {
                condition.await();
            }
            catch (InterruptedException e)
            {
                logging.error("Thread was interrupted");
            }
        }
        condition.signalAll();
        portLock.unlock();
        return ship;

    }
    public boolean sendShip(Ship leavingShip)
    {
        port.getDocks()[leavingShip.getDockNumber()].setBusy(false);
        port.getDocks()[leavingShip.getDockNumber()].setCurrentShip(null);
        portSemaphore.release();
        return port.getDocks()[leavingShip.getDockNumber()].isBusy();
    }
}
