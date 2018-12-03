package Model;

import Logic.PortManager;

import java.util.concurrent.atomic.AtomicBoolean;

public class Ship extends Thread
{
    public Ship(Storage shipStorage,PortManager portManager)
    {
        this.shipStorage=shipStorage;
        this.portManager=portManager;
        Unloaded.set(false);
    }
    private Storage shipStorage;
    private int dockNumber;
    private PortManager portManager;
    private AtomicBoolean Unloaded=new AtomicBoolean();

    public int getDockNumber()
    {
        return dockNumber;
    }

    public void setDockNumber(int dockNumber)
    {
        this.dockNumber = dockNumber;
    }


    public Storage getShipStorage()
    {
        return shipStorage;
    }
    public void run()
    {
        portManager.receiveShip(this);
        while (shipStorage.getCurrentNumberOfContainers()>0)
        {
            portManager.unloadContainers(this);
            portManager.unloadToShips(this);
        }
        setUnloaded(true);
        while (shipStorage.getCurrentNumberOfContainers()==0)
        {
            portManager.loadContainers(this);
        }
        portManager.sendShip(this);
    }
    public boolean isUnloaded()
    {
        return Unloaded.get();
    }

    public void setUnloaded(boolean unloaded)
    {
        Unloaded.set(unloaded);
    }
}
