package cl.makinolas.atk.actors.friend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

import cl.makinolas.atk.actors.Monsters;
import cl.makinolas.atk.actors.attacks.Attacks;
import cl.makinolas.atk.actors.attacks.VineWhip;

public class Snivy extends AbstractFriend {
  
  private TextureRegion[][] faces;
  
  public Snivy() {
    faces = new TextureRegion(new Texture(Gdx.files.internal("Actors/Snivy_faces.png"))).split(40,40);
    setTexture(new TextureRegion(new Texture(Gdx.files.internal("Actors/Snivy.png"))));
    setCutSprites(22,21);
    setWalkAnimation(3,4,5,4);
    setHurtAnimation(0);
    setMeleeAnimation(6,8);
    setFaceSprite(faces[0][0]);
    initLevel(10);
    initDead();
    setActualEvolution(0);
    setMaxHealth(30);
    setMaxMagic(100);
  }
  
  public Snivy(float level){
    this();
    initLevel(level);
  }
  
  @Override
  protected void initLevel(float level){
   this.level = new Level(level);
   new Evolution(this.level, 16, 1);
   new Evolution(this.level, 32, 2);
  }
  
  @Override
  protected void evolve(int numberOfLevel){
    if (numberOfLevel == 1 && getActualEvolution() < 1){
      setTexture(new TextureRegion(new Texture(Gdx.files.internal("Actors/Servine.png"))));
      setCutSprites(34,29);
      setWalkAnimation(2,3,4,3);
      setHurtAnimation(0);
      setMeleeAnimation(5,7);
      setFaceSprite(faces[0][1]);
      setActualEvolution(1);
      setMaxHealth(60);
      setMaxMagic(100);
    } else if (numberOfLevel == 2 && getActualEvolution() < 2){
      setTexture(new TextureRegion(new Texture(Gdx.files.internal("Actors/Serperior.png"))));
      setCutSprites(57,50);
      setWalkAnimation(2,3);
      setHurtAnimation(0);
      setMeleeAnimation(4,5);
      setFaceSprite(faces[0][2]);
      setActualEvolution(2);
      setMaxHealth(80);
      setMaxMagic(100);
      
    }
  }
  
  @Override
  public Attacks getFriendAttack(World myWorld, float x , float y, boolean facingRight, Monsters source){
    return new VineWhip(myWorld, x, y, facingRight, source);
  }
}
