package cl.makinolas.atk.actors.bosses;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import cl.makinolas.atk.GameConstants;
import cl.makinolas.atk.actors.GameActor;
import cl.makinolas.atk.actors.HBar;
import cl.makinolas.atk.actors.Hero;
import cl.makinolas.atk.actors.Monsters;
import cl.makinolas.atk.actors.attacks.Attacks;

public abstract class Boss extends Monsters implements IBoss{
  
  protected int health;
  protected HBar healthBar;
  protected boolean isDamaged;
  protected int width;
  protected int height;
  protected boolean dead;
  protected int walkAnimation;
  protected int hurtAnimation;
  protected int attackAnimation;
  protected final float hurtTime = 1 / 4f;
  protected float accumulator;
  
  
  @Override
  public void act(float delta){         
    if(isDamaged){
      accumulator += delta;
      if(accumulator > hurtTime){
        isDamaged = false;
        changeAnimation(walkAnimation);
        accumulator = 0;
      }
    }
  }
  
  
  @Override
  public void draw(Batch batch, float alpha){
    super.draw(batch,alpha);
    Vector2 myPosition = myBody.getPosition();
    batch.draw(healthBar.getSprite(), myPosition.x * GameConstants.WORLD_FACTOR - getActualSprite().getRegionWidth() / 2 ,
        myPosition.y * GameConstants.WORLD_FACTOR + getActualSprite().getRegionHeight() / 2);
  }
  
  @Override
  public void damage(int damage, Attacks inflictor) {
    health -= damage;   
    isDamaged = true;
    changeAnimation(hurtAnimation);
    inflictor.setDead();
    healthBar.setCurrent(health);
    if(health <= 0){
      dead = true;
    }
  }
  
  protected void setAnimation(TextureRegion enemySprites, int width, int height){
    setMasterTexture(enemySprites, width, height);
  }
  
  @Override
  public boolean isDead(){
    return dead;
  }
  
  @Override
  public void interact(GameActor actor2) {
    actor2.interactWithBoss(this);
  }
  
  @Override
  public void interactWithAttack(Attacks attack){
    this.damage(attack.getAttackDamage(), attack);
  }
  
  @Override
  public void interactWithHero(Hero hero){
    interactWithHero2(hero);
    hero.interactWithMonster(this);
  }
  
  private float getBodySize(int size){
    return (0.5f*size)/22;
  }
  
  @Override
  public float getMonsterWidth() {
    return getBodySize(width);
  }
  
  @Override
  public float getMonsterHeight() {
    return getBodySize(height);
  }
  
  @Override
  public Monsters getBoss(){
    return this;
  }
}
