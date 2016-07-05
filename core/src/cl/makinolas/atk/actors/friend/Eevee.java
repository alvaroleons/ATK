package cl.makinolas.atk.actors.friend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import cl.makinolas.atk.actors.Hero;

public class Eevee extends AbstractFriend {
  
  public Eevee(Hero hero) {
    super(hero);
    TextureRegion[][] faces = new TextureRegion(new Texture(Gdx.files.internal("Actors/Eevee_faces.png"))).split(40,40);
    setTexture(new TextureRegion(new Texture(Gdx.files.internal("Actors/Eevee.png"))));
    setAnimations(new int[]{29,24},
                  new int[][]{new int[]{0,1},new int[]{0,2},new int[]{0,3}},
                  new int[][]{new int[]{0,0}});
    setMeleeAnimation(3,5);
    setFaceSprite(faces[0][0]);
    initLevel(5);
    initDead();
    setStats();
    setMaxMagic(1000);
    friend = Enemies.EEVEE;
  }

  @Override
  protected void initLevel(int level) {
    this.level = new Level(level);    
  }
}
