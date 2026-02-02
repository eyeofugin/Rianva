package game.skills.logic;

public class Resource {
    public Stat resource;
    public Stat maxResource;
    public int amount;

    public Resource(Stat resource, Stat maxResource, int amount) {
        this.resource = resource;
        this.amount = amount;
        this.maxResource = maxResource;
    }

}
