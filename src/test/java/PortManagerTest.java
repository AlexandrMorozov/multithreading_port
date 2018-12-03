import Logic.PortManager;
import Model.Port;
import Model.Ship;
import Model.Storage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class PortManagerTest
{
    Semaphore sem=new Semaphore(1);
    ReentrantLock lock=new ReentrantLock();
    Port port=new Port(1,8,0);
    PortManager portManager=new PortManager(port,sem,lock);
    Ship ship=new Ship(new Storage(8,8),portManager);
    @Test
    public void enterPortTest()
    {
        Ship exRes=ship;
        Ship res=portManager.receiveShip(ship);
        Assert.assertEquals(res,exRes);

    }
    @Test(dependsOnMethods={"enterPortTest"})
    public void unloadContainersTest()
    {
        int exRes=8;
        int res=portManager.unloadContainers(ship);
        Assert.assertEquals(res,exRes);
    }
    @Test(dependsOnMethods={"unloadContainersTest"})
    public void loadContainersTest()
    {
        int exRes=8;
        int res=portManager.loadContainers(ship);
        Assert.assertEquals(res,exRes);
    }
    @Test(dependsOnMethods={"loadContainersTest"})
    public void leavePortTest()
    {
        boolean exRes=false;
        boolean res=portManager.sendShip(ship);
        Assert.assertEquals(res,exRes);
    }
}
