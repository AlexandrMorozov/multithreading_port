package Model;

import java.util.concurrent.atomic.AtomicInteger;

public class Storage
{
    private AtomicInteger maxNumberOfContainers=new AtomicInteger();
    private AtomicInteger currentNumberOfContainers=new AtomicInteger();
    public Storage(int maxNumberOfContainers,int currentNumberOfContainers)
    {
        this.maxNumberOfContainers.set(maxNumberOfContainers);
        this.currentNumberOfContainers.set(currentNumberOfContainers);
    }
    public boolean isStorageFull()
    {
        if(maxNumberOfContainers.get()==currentNumberOfContainers.get())
        {
            return true;
        }
        return false;
    }
    public int receiveContainers(Storage storage)
    {
        if(isStorageFull()!=true)
        {
            if((maxNumberOfContainers.get()-currentNumberOfContainers.get())>=storage.currentNumberOfContainers.get())
            {
                currentNumberOfContainers.set(currentNumberOfContainers.get()+storage.currentNumberOfContainers.get());
                storage.setCurrentNumberOfContainers(0);
            }
            else
            {
                int difference=maxNumberOfContainers.get()-currentNumberOfContainers.get();
                storage.setCurrentNumberOfContainers(storage.currentNumberOfContainers.get()-difference);
                currentNumberOfContainers.set(maxNumberOfContainers.get());
            }
        }
        return currentNumberOfContainers.get();
    }

    public int getCurrentNumberOfContainers()
    {
        return currentNumberOfContainers.get();
    }

    public void setCurrentNumberOfContainers(int currentNumberOfContainers)
    {
        this.currentNumberOfContainers.set(currentNumberOfContainers);
    }

    public int getMaxNumberOfContainers()
    {
        return maxNumberOfContainers.get();
    }
}
