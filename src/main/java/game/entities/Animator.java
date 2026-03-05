package game.entities;

import framework.resources.SpriteLibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Animator {

  public Map<String, Animation> animations = new HashMap<>();
  public String currentAnim;
  private String defaultAnim;
  public boolean onLoop;
  public int animationCounter = 0;
  public int width;
  public int height;

  public List<Animation> effects = new ArrayList<>();
  public boolean waitFor;
  public int[] image;

  public Hero hero;

  public void setupAnimation(String path, String name, int[] ms) {
    this.animations.put(
        name, new Animation(ms, SpriteLibrary.setupSprites(ms.length, width, height, path, false)));
  }

  public void animate() {
    Animation anim = animations.get(currentAnim);
    image = anim.getImage(animationCounter);
    if (animationCounter == anim.length) {
      animationCounter = 0;
      if (waitFor) {
        waitFor = false;
      }
      if (!onLoop) {
        currentAnim = defaultAnim;
        onLoop = true;
      }
    }
    animationCounter++;
  }

  public void playAnimation(String animName, boolean waitFor) {
    if (this.animations.containsKey(animName)) {
      currentAnim = animName;
      animationCounter = 0;
      this.waitFor = waitFor;
      onLoop = false;
    }
  }

  public void setDefaultAnim(String defaultAnim) {
    this.defaultAnim = defaultAnim;
    this.image = this.animations.get(this.defaultAnim).getImage(0);
  }

  public String getDefaultAnim() {
    return this.defaultAnim;
  }
}
