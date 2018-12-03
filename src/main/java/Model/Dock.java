package Model;

public class Dock
{
    private boolean isBusy=false;
    private Ship currentShip;
    public boolean isBusy()
    {
        return isBusy;
    }
    public void setBusy(boolean busy)
    {
        isBusy = busy;
    }
    public Ship getCurrentShip()
    {
        return currentShip;
    }
    public void setCurrentShip(Ship currentShip)
    {
        this.currentShip = currentShip;
    }
}
