package game.entities;

import framework.connector.Connector;
import framework.connector.payloads.DeathTriggerPayload;
import game.skills.logic.Effect;
import game.skills.logic.Stat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HeroTeam {

    public final int fillUpDirection;
    public  Hero[] heroes;
    public  List<Hero> deadHeroes = new ArrayList<>();
    public final int teamNumber;

    public HeroTeam(int fillUpDirection,  Hero[] heroes, int teamNumber) {
        this.heroes = heroes;
        this.teamNumber = teamNumber;
        for (Hero hero: this.heroes) {
            if (hero != null) {
                hero.team = this;
            }
        }
        this.fillUpDirection = fillUpDirection;
    }

    public void updateAnimations(int frame) {
        Arrays.stream(this.heroes).filter(Objects::nonNull).forEach(e -> e.update(frame));
    }

    private int getPos(int i) {
        return this.teamNumber==2 ? 5 - i: i;
    }

    private int getEnemyOffset() {
        return this.teamNumber == 2 ? this.heroes.length : 0;
    }

    public List<Hero> removeTheDead() {
        List<Hero> removed = new ArrayList<>();
        for (int i = 0; i < this.heroes.length; i++) {
            if (this.heroes[i] != null && this.heroes[i].getStat(Stat.CURRENT_LIFE) < 1) {
                this.deadHeroes.add(this.heroes[i]);
                removed.add(this.heroes[i]);
                DeathTriggerPayload pl = new DeathTriggerPayload()
                        .setDead(this.heroes[i]);
                Connector.fireTopic(Connector.DEATH_TRIGGER, pl);
                this.heroes[i] = null;
                for (int j = i - 1; j >= 0 && j < this.heroes.length; j--) {
                    if (this.heroes[j] != null) {
                        this.heroes[j+1] = this.heroes[j];
                        this.heroes[j+1].setPosition(getPos(j+1));
                        this.heroes[j] = null;
                    }
                }
            }
        }
        return removed;
    }

    public List<Hero> getHeroesAsList() {
        List<Hero> list = new ArrayList<>();
        for (Hero hero : this.heroes) {
            if (hero != null) {
                list.add(hero);
            }
        }
        return list;
    }

    public void setHeroes(Hero[] heroes) {
        this.heroes = heroes;
    }

    public List<Hero> getDeadHeroes() {
        return deadHeroes;
    }

    public void setDeadHeroes(List<Hero> deadHeroes) {
        this.deadHeroes = deadHeroes;
    }

    public int getFirstPosition() {
        return fillUpDirection > 0 ? this.heroes.length-1: getEnemyOffset();
    }
    public int getLastPosition() {
        return fillUpDirection > 0 ? 0: this.heroes.length + getEnemyOffset() - 1;
    }

    public int amountEffects(String effect) {
        int amnt = 0;
        for (Hero hero : getHeroesAsList()) {
            amnt += hero.hasPermanentEffect(effect);
        }
        return amnt;

    }
}
