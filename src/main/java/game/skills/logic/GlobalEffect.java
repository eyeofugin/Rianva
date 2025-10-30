package game.skills.logic;

public abstract class GlobalEffect {

    private final String name;
    private final String description;

    protected GlobalEffect(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract void initSubscriptions();
    public abstract void turn();

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
