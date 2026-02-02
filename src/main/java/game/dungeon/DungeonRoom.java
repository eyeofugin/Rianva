package game.dungeon;

import framework.resources.SpriteLibrary;
import game.entities.DraftBuilder;
import game.entities.HeroTeam;
import game.objects.Equipment;

import java.util.Random;

public class DungeonRoom {

    public boolean activated;
    public boolean visible = false;
    private int[] icon;
    private HeroTeam enemies;
    private Equipment loot;
    private boolean boss;
    private int money;
    private int damage;
    private int heal;
    //other stuff

    public DungeonRoom(HeroTeam encounter) {
        this.enemies = encounter;
        this.icon = SpriteLibrary.getSprite("encounter").clone();
        //setIcon
    }
    public DungeonRoom(HeroTeam encounter, boolean boss) {
        this.enemies = encounter;
        this.boss = boss;
        this.icon = SpriteLibrary.getSprite("boss").clone();
        //setIcon
    }
    public DungeonRoom(Equipment loot) {
        this.loot = loot;
        this.icon = SpriteLibrary.getSprite("loot").clone();
    }
    public DungeonRoom() {
        this.icon = SpriteLibrary.getSprite("relax").clone();
    }
    public DungeonRoom(int money) {
        this.icon = SpriteLibrary.getSprite("money").clone();
        this.money = money;
    }
    public DungeonRoom(boolean good, int amount) {
        this.icon = SpriteLibrary.getSprite("random").clone();
        if (good) {
            this.heal = amount;
        } else {
            this.damage = amount;
        }
    }

    public static DungeonRoom getStartRoom() {
        return new DungeonRoom();
    }
    public static DungeonRoom getEmptyRoom() {
        return new DungeonRoom();
    }
    public static DungeonRoom getEncounterRoom(int level) {
//        return new DungeonRoom(new HeroTeam(-1, DraftBuilder.getDummyTeam(), 2));
        return new DungeonRoom(new HeroTeam(-1, DraftBuilder.getDungeonEncounterTeam(level), 2));
    }
    public static DungeonRoom getBossRoom() {
        return new DungeonRoom(new HeroTeam(-1,DraftBuilder.getDungeonEncounterTeam(1), 2), true);
    }
    public static DungeonRoom getLootRoom() {
        return new DungeonRoom(new Equipment("",""));
    }
    public static DungeonRoom getMoneyRoom() {
        return new DungeonRoom(12);
    }
    public static DungeonRoom getRandomRoom() {
        Random random = new Random();
        boolean good = random.nextBoolean();
        int amount = random.nextInt(10);
        return new DungeonRoom(good, amount);
    }

    public boolean isBoss() {
        return boss;
    }

    public int[] getIcon() {
        return icon;
    }

    public HeroTeam getEnemies() {
        return enemies;
    }

    public Equipment getLoot() {
        return loot;
    }
}
