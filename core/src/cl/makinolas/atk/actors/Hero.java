package cl.makinolas.atk.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import cl.makinolas.atk.actors.friend.Eevee;
import cl.makinolas.atk.actors.friend.Friend;
import cl.makinolas.atk.actors.friend.Gible;
import cl.makinolas.atk.stages.GameStage;

public class Hero extends Monsters {

  private boolean isJumping;
  private int health;
  private int magic;
  private boolean isDamaged;
  private boolean dead;
  private World myWorld;
  private int walkAnimation;
  private int hurtAnimation;
  private final float hurtTime = 1 / 4f;
  private float accumulator;
  private Array<Friend> allies;
  private Friend actualFriend;
  private int indexFriend;
  
  public Hero(World myWorld) {
    
    isJumping = false;
    isFacingRight = false;
    health = 100;
    magic = 1000;
    isDamaged = false;
    dead = false;
    accumulator = 0;
    
    allies = new Array<Friend>();
    Friend allie = new Eevee();
    Friend allie2 = new Gible();
    allie.setVariables(health, dead);
    allie2.setVariables(health, dead);
    allies.add(allie);
    allies.add(allie2);
    // Definici�n del cuerpo del jugador.
    this.myWorld = myWorld;
    // Definici�n del cuerpo del jugador.
    BodyDef myBodyDefinition = new BodyDef();
    myBodyDefinition.type = BodyDef.BodyType.DynamicBody;
    myBodyDefinition.position.set(new Vector2(4,3));
    
    // Forma del collider del jugador.
    Body myBody = myWorld.createBody(myBodyDefinition);
    
    PolygonShape shape = new PolygonShape();
    shape.setAsBox(0.6f,0.5f);
    ///
    myBody.setGravityScale(1);
    myBody.createFixture(shape, 0.5f);
    myBody.resetMassData();
    shape.dispose();
    
    // Guardar body.
    setBody(myBody);
    
    // Guardar animaciones del jugador
    actualFriend = allies.get(0);
    indexFriend = 0;
    setAnimation();
    changeAnimation(walkAnimation);
  }
  
  @Override
  public void act(float delta){
    int vx = 0;

    boolean touchX = Gdx.input.getX() < Gdx.graphics.getWidth()/2;
    boolean touchY = Gdx.input.getY() < Gdx.graphics.getHeight()/2;
    boolean pressed = Gdx.input.isTouched();

    if (Gdx.input.isKeyPressed(Keys.LEFT) || pressed && touchX && !touchY){
      vx -= 7;
      if (isFacingRight){
        isFacingRight = false;
      }
    }
    if (Gdx.input.isKeyPressed(Keys.RIGHT) || pressed && !touchX && !touchY){
      vx += 7;
      if (!isFacingRight){
        isFacingRight = true;
      }
    }
    if (Gdx.input.isKeyJustPressed(Keys.SPACE) || pressed && touchX && touchY){
      if(!isJumping){
        myBody.applyLinearImpulse(0, 7, myBody.getPosition().x, myBody.getPosition().y, true);
        isJumping = true;
      }
    }
    if (Gdx.input.isKeyJustPressed(Keys.NUM_1)){
      if(indexFriend != 0){
        actualFriend.setVariables(health, dead);
        allies.set(indexFriend, actualFriend);
        actualFriend = allies.get(0);
        indexFriend = 0;
        health = actualFriend.getHealth();
        setAnimation();
      }
    } else if (Gdx.input.isKeyJustPressed(Keys.NUM_2)){
      if(indexFriend != 1){
        actualFriend.setVariables(health, dead);
        allies.set(indexFriend, actualFriend);
        actualFriend = allies.get(1);
        health = actualFriend.getHealth();
        indexFriend = 1;
        setAnimation();
      }
    }
    if ((Gdx.input.isKeyJustPressed(Keys.Z) || pressed && !touchX && touchY) && magic > 100){
      magic -= 100;
      GameActor fireball = new Fireball(myWorld, myBody.getPosition().x,myBody.getPosition().y,isFacingRight, this);
      ((GameStage) getStage()).addGameActor(fireball);
    }
    myBody.setLinearVelocity(vx, myBody.getLinearVelocity().y);
    
    ((GameStage) getStage()).changeCamera(myBody.getPosition().x , myBody.getPosition().y );
    
    if(isDamaged){
      accumulator += delta;
      if(accumulator > hurtTime){
        isDamaged = false;
        changeAnimation(walkAnimation);
        accumulator = 0;
      }
    }
    if(magic < 1000){
      magic = ((magic + 1)%1001);
    }
  }
  
  public void landedPlatform(){
    isJumping = false;
  }

  private void setAnimation(){
    setMasterTexture(actualFriend.getTexture(),actualFriend.getWidth(),actualFriend.getHeight());
    walkAnimation = addAnimation(actualFriend.getWalkFrames(),0.2f, actualFriend.getWalkAnimation());
    hurtAnimation = addAnimation(actualFriend.getHurtFrames(),0.2f, actualFriend.getHurtAnimation());
  }
  
  @Override
  public boolean isHero(){
    return true;
  }

  public int getHealth(){
    return health;
  }
  
  public int getMagic(){
    return magic;
  }


  @Override
  public void damage(int damage, Attacks inflictor)  {
    if(!inflictor.getSource().isHero()){
      health -= damage;   
      isDamaged = true;
      changeAnimation(hurtAnimation);
      inflictor.setDead();
    }
    if(health <= 0){
      dead = true;
    }

  }
  
  @Override
  public boolean isDead(){
    return dead;
  }


  @Override
  public int getMeleeDamage() {
    return 0;
  }
  
  public Friend getFriend(){
    return actualFriend;
  }

  @Override
  public void meleedamage(int damage) {
    health -= damage;   
    isDamaged = true;
    changeAnimation(hurtAnimation);
    if(health <= 0){
      dead = true;
    }   
  }
}
