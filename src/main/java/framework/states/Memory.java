package framework.states;

import game.entities.Hero;
import game.entities.HeroTeam;
import game.objects.Equipment;

public class Memory {
    public GameMode mode;
    public int dungeonLevel = 0;
    public int pvpRound = 0;
    public HeroTeam dungeonTeam;
    public HeroTeam[] teams = new HeroTeam[2];
    public Equipment dungeonLoot;
    public HeroTeam dungeonEnemies;
    public boolean bossEncounter;

    public Memory(GameMode mode) {
        this.mode = mode;
    }
}
