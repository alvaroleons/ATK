package cl.makinolas.atk.actors.attacks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import cl.makinolas.atk.actors.Monsters;

public class ThunderBolt extends Attacks {
  
  private BodyDef myBodyDefinition; 
  private Monsters mySource;
  private float attackTime;
  private float accumulator;
  private float initialPosition;
  private boolean dead;
  
  public ThunderBolt(World myWorld, float x , float y, boolean facingRight, Monsters source){
    
    dead = false;
    mySource = source;
    attackTime = 0;
    accumulator = 0;
    isFacingRight = !facingRight;
    this.initialPosition= (facingRight)? 1f: -1f;
    myBodyDefinition = new BodyDef();
    myBodyDefinition.type = BodyDef.BodyType.DynamicBody;
    myBodyDefinition.position.set(new Vector2(x + initialPosition * source.getMonsterWidth() * 3, y));
    
    Body myBody = myWorld.createBody(myBodyDefinition);
    
    PolygonShape shape = new PolygonShape();
    shape.setAsBox(0.5f, 0.5f);
    
    myBody.setGravityScale(0);
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.isSensor = true;
    fixtureDef.density = 0;
    fixtureDef.shape = shape;
    myBody.createFixture(fixtureDef);
    myBody.resetMassData();
    shape.dispose();
    
    // Guardar body.
    setBody(myBody);
    
    // Guardar animaciones del jugador
    setAnimation();
  }
  
  @Override
  public void act(float delta){
    checkFinish(delta);
  }
  
  private void checkFinish(float delta) {
    accumulator += delta;
    if(accumulator >= attackTime){
      dead = true;
    }
    
  }

  private void setAnimation(){
    setMasterTexture(new TextureRegion(new Texture(Gdx.files.internal("Attacks/Thunderbolt.png"))),36,32);
    addAttackAnimation(0.2f, Animation.PlayMode.LOOP, 0, 4);
    attackTime = 5 * 0.2f;    
  }

  @Override
  public int getAttackDamage() {
    return 30;
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
  
  
}
