package framework.connector;

import game.entities.Hero;
import game.skills.Skill;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SubscriptionTriggerTest {

  @Test
  public void test_targetHeroReference_SAME() {

    Hero hero = new Hero();
    Hero payloadHero = new Hero();

    Skill skill = new Skill();
    skill.hero = hero;

    SubscriptionTrigger trigger = new SubscriptionTrigger();
    trigger.targetHeroReference = SubscriptionTriggerHeroReference.SAME;
    trigger.setSkill(skill);

    ConnectionPayload otherPayload = new ConnectionPayload(0).setTarget(payloadHero);
    ConnectionPayload samePayload = new ConnectionPayload(0).setTarget(hero);

    assertFalse(trigger.triggered(otherPayload));
    assertTrue(trigger.triggered(samePayload));
  }
}
