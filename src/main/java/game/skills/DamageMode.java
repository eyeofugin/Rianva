package game.skills;

import framework.graphics.text.Color;

public enum DamageMode {
    PHYSICAL(Color.FORCE_RED),
    MAGICAL(Color.MAGIC_BLUE),
    TRUE(Color.WHITE),
    EFFECT(Color.WHITE);

    private final Color color;

    DamageMode(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}
