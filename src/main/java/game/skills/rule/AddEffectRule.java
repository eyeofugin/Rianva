package game.skills.rule;

import game.effects.Effect;
import game.effects.EffectDTO;
import game.entities.Hero;
import game.skills.Skill;
import utils.TargetResolver;

import java.util.List;


public class AddEffectRule implements SkillRule {
    RuleTargetReference ruleTargetReference;
    EffectDTO effect;

    @Override
    public void enact(Skill skill)
    {
        if (RuleTargetReference.ARENA.equals(ruleTargetReference)) {
            skill.hero.arena.setGlobalEffect(new Effect(effect));
        } else {
            List<Hero> targets = TargetResolver.getTargets(skill.hero, skill, ruleTargetReference);
            targets.forEach(hero -> hero.addEffect(new Effect(effect), skill.hero, skill));
        }
    }
}
