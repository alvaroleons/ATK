package cl.makinolas.atk.actors.attacks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import cl.makinolas.atk.actors.Monsters;

public class VineWhip extends Attacks {
  
  private float attackTime;
  private float accumulator;
  
  public VineWhip(World myWorld, float x , float y, boolean facingRight, Monsters source){
    super(myWorld, x, y, facingRight, source);
    
    accumulator = 0;
    this.initialPosition= (facingRight)? 1f: -1f;
    this.initialPosition *= source.getMonsterWidth();
    initializeBody(x, y, 0.5f, 0.5f);
    // Guardar animaciones del jugador
    setAnimation();
  }
  
  @Override
  public void act(float delta){
    checkFinish(delta);
    myBody.setTransform(new Vector2((mySource.getBody().getPosition().x + initialPosition * mySource.getMonsterWidth()),mySource.getBody().getPosition().y)
                          , myBody.getAngle());
  }
  
  private void checkFinish(float delta) {
    accumulator += delta;
    if(accumulator >= attackTime){
      dead = true;
    }
  }

  protected void setAnimation(){
    setMasterTexture(new TextureRegion(new Texture(Gdx.files.internal("Attacks/Latigo_cepa.png"))),39,32);
    addAttackAnimation(0.1f, Animation.PlayMode.LOOP, 0, 4);
    attackTime = 5 * 0.1f;    
  }

  @Override
  public int getAttackDamage() {
    return 45;
  }

  @Override
  public Monsters getSource() {
    return mySource;
  }
  
  @Override
  public void setDead(){
    
  }
  
  @Override
  public boolean isDead(){
    return dead;
  }
  
   @Override
   public float getXVelocity(){
	   return isFacingRight? -3 : 3;
   }
   
   @Override
   public void setSource(Monsters monsters){
     this.mySource = monsters;
   }

}
