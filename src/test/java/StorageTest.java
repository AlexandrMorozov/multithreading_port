import Model.Storage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StorageTest
{
    @Test
    public void fullStorageTest()
    {
        Storage testStorage=new Storage(8,8);
        boolean expectedResult=true;
        boolean actualResult=testStorage.isStorageFull();
        Assert.assertEquals(actualResult,expectedResult);
    }
    @Test
    public void containersReceivingTest()
    {
        Storage testStorage=new Storage(8,0);
        Storage senderStorage=new Storage(4,4);
        int expectedResult=4;
        int actualResult=testStorage.receiveContainers(senderStorage);
        Assert.assertEquals(actualResult,expectedResult);
    }
}
