package cl.makinolas.atk.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import cl.makinolas.atk.GameConstants;
import cl.makinolas.atk.stages.GameStage;

public class Hero extends Monsters {

  private boolean isJumping;
  private int health;
  private HBar healthBar;
  private boolean isDamaged;
  private boolean dead;
  private World myWorld;
  
  public Hero(World myWorld) {
    
    isJumping = false;
    isFacingRight = false;
    health = 100;    
    healthBar = new HBar(100, health, 22, new TextureRegion( new Texture(Gdx.files.internal("bar_green.png"))));
    isDamaged = false;
    dead = false;
    // Definici�n del cuerpo del jugador.
    this.myWorld = myWorld;
    // Definici�n del cuerpo del jugador.
    BodyDef myBodyDefinition = new BodyDef();
    myBodyDefinition.type = BodyDef.BodyType.DynamicBody;
    myBodyDefinition.position.set(new Vector2(4,10));
    
    // Forma del collider del jugador.
    Body myBody = myWorld.createBody(myBodyDefinition);
    
    PolygonShape shape = new PolygonShape();
    shape.setAsBox(0.6f,0.7f);
    ///
    myBody.setGravityScale(1);
    myBody.createFixture(shape, 0.5f);
    myBody.resetMassData();
    shape.dispose();
    
    // Guardar body.
    setBody(myBody);
    
    // Guardar animaciones del jugador
    setAnimation();
  }
  
  @Override
  public void act(float delta){
    int vx = 0;
    if (Gdx.input.isKeyPressed(Keys.LEFT) && myBody.getPosition().x > 0.5){
      vx -= 7;
      if (isFacingRight){
        isFacingRight = false;
      }
    }
    if (Gdx.input.isKeyPressed(Keys.RIGHT) && myBody.getPosition().x < 31.5){
      vx += 7;
      if (!isFacingRight){
        isFacingRight = true;
      }
    }
    if (Gdx.input.isKeyJustPressed(Keys.SPACE)){
      if(!isJumping){
        myBody.applyLinearImpulse(0, 8, myBody.getPosition().x, myBody.getPosition().y, true);
        isJumping = true;
      }
    }
    if (Gdx.input.isKeyJustPressed(Keys.Z)){
      GameActor fireball = new Fireball(myWorld, myBody.getPosition().x,myBody.getPosition().y,isFacingRight, this);
      getStage().addActor(fireball);
    }
    myBody.setLinearVelocity(vx, myBody.getLinearVelocity().y);
    
    ((GameStage) getStage()).changeCamera(myBody.getPosition().x , myBody.getPosition().y );
  }
  
  public void landedPlatform(){
    isJumping = false;
  }

  private void setAnimation(){
    setMasterTexture(new TextureRegion(new Texture(Gdx.files.internal("Actors/charmander.png"))),22,22);
    addAnimation(4,0.2f, new int[]{0, 0}, new int[]{0, 1}, new int[]{0, 2}, new int[]{0, 1});
  }
  
  @Override
  public boolean isHero(){
    return true;
  }

  @Override
  public void damage(int damage, Attacks inflictor)  {
    if(!inflictor.getSource().isHero()){
      health -= damage;   
      isDamaged = true;
      healthBar.setCurrent(health);
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
  public void draw(Batch batch, float alpha){
    super.draw(batch, alpha);
    TextureRegion actualSprite = getActualSprite();
    Vector2 myPosition = myBody.getPosition();
    batch.draw(healthBar.getSprite(), myPosition.x * GameConstants.WORLD_FACTOR - actualSprite.getRegionWidth() / 2 , myPosition.y * GameConstants.WORLD_FACTOR + actualSprite.getRegionHeight() / 2);
  }

  @Override
  public int getMeleeDamage() {
    return 0;
  }

  @Override
  public void meleedamage(int damage) {
    health -= damage;   
    isDamaged = true;
    healthBar.setCurrent(health);
    if(health <= 0){
      dead = true;
    }   
  }
}
