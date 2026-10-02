package game.libraries;

import framework.graphics.elements.HeroSprite;
import utils.FileWalker;

import java.util.HashMap;
import java.util.Map;

public class HeroSpriteLibrary {
    private static Map<String, String> spritesJson = new HashMap<>();
    public static void init() {
        spritesJson = FileWalker.loadJsonMap("data/sprites.json");
    }

    public static HeroSprite getHeroSprite(String name){
        if (spritesJson != null && spritesJson.containsKey(name)){
            return FileWalker.mapJson(HeroSprite.class, spritesJson.get(name));
        }
        return null;
    }


}
