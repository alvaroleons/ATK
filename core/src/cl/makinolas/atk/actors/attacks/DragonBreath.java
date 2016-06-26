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

public class DragonBreath extends Attacks {
  
  private BodyDef myBodyDefinition; 
  private Monsters mySource;
  private float xVelocity;
  private float initialPosition;
  private boolean dead;
  
  public DragonBreath(World myWorld, float x , float y, boolean facingRight, Monsters source){
    
    dead = false;
    mySource = source;
    isFacingRight = !facingRight;
    this.xVelocity = (facingRight)? 10: -10;
    this.initialPosition= (facingRight)? .5f: -.5f;
    myBodyDefinition = new BodyDef();
    myBodyDefinition.type = BodyDef.BodyType.DynamicBody;
    myBodyDefinition.position.set(new Vector2(x + initialPosition,y));
    
    Body myBody = myWorld.createBody(myBodyDefinition);
    
    PolygonShape shape = new PolygonShape();
    shape.setAsBox(0.3f, 0.3f);
    
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
    myBody.setLinearVelocity(xVelocity, 0);
  }
  
  @Override
  public int getAttackDamage() {
    return 30;
  }
  
  @Override
  public Monsters getSource() {
    return mySource;
  }
  
  private void setAnimation(){
    setMasterTexture(new TextureRegion(new Texture(Gdx.files.internal("Attacks/DragonBreath.png"))),38,30);
    addAttackAnimation(0.1f, Animation.PlayMode.LOOP, 0, 5);
  }
  
  @Override
  public void setDead(){
    dead = true;
  }
  
  @Override
  public boolean isDead(){
    return dead;
  }
  
}
