package Model;

public class Port
{
    public Port(int numberOfDocks,int maxNumberOfContainers,int currentNumberOfContainers)
    {
        docks=new Dock[numberOfDocks];
        setDocks(docks);
        portStorage=new Storage(maxNumberOfContainers, currentNumberOfContainers);
    }
    private Dock[] docks;
    private Storage portStorage;
    private void setDocks(Dock[] docks)
    {
        for(int i=0;i<docks.length;i++)
        {
            docks[i]=new Dock();
        }
    }
    public Dock[] getDocks()
    {
        return docks;
    }

    public Storage getPortStorage()
    {
        return portStorage;
    }
}
