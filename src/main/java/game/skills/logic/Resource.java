package game.skills.logic;

public class Resource {
  public Stat resource;
  public Stat maxResource;
  public int amount;
  public int percentage;

  public Resource() {

  }
  public Resource(Stat resource, Stat maxResource, int amount, int percentage) {
    this.resource = resource;
    this.amount = amount;
    this.maxResource = maxResource;
    this.percentage = percentage;
  }

  public Resource copy() {
    return new Resource(resource, maxResource, amount, percentage);
  }
}
