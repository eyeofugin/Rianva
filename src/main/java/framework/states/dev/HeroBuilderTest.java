package framework.states.dev;

import framework.Engine;
import framework.graphics.text.Color;
import framework.states.Memory;
import framework.states.State;
import game.entities.HeroBuilder;
import game.libraries.EquipmentLibrary;
import game.libraries.HeroBackgroundLibrary;

public class HeroBuilderTest extends State {

    public HeroBuilderTest(Memory memory) {
        super(memory);
        HeroBuilder.init();
        HeroBackgroundLibrary.init();
        EquipmentLibrary.init();
    }

    @Override
    public void update(int frame) {
        updateKeys();
    }

    private void updateKeys() {
        if (Engine.KeyBoard._enterPressed) {
            HeroBuilder.buildRandom();
        }
    }

    @Override
    public int[] render() {
        background(Color.BLACK);
        return this.pixels;
    }
}
