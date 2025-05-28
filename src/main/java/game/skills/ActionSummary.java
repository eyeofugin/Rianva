package game.skills;

import java.util.List;
import java.util.Map;

public class ActionSummary {
    public String caster;
    public String[] targets;
    public int[] damage;
    public int[] heal;
    public int[] shield;
    public Map<Integer, List<Effect>> effects;
    public List<Effect> casterEffects;
}
