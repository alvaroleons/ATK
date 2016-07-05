package cl.makinolas.atk.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.utils.Array;

import cl.makinolas.atk.GameConstants;
import cl.makinolas.atk.actors.attacks.Attacks;
import cl.makinolas.atk.actors.attacks.Puff;
import cl.makinolas.atk.actors.bosses.IBoss;
import cl.makinolas.atk.actors.friend.Bagon;
import cl.makinolas.atk.actors.friend.Enemies;
import cl.makinolas.atk.actors.friend.Friend;
import cl.makinolas.atk.actors.friend.Weedle;
import cl.makinolas.atk.actors.items.Ball;
import cl.makinolas.atk.actors.items.BallActor;
import cl.makinolas.atk.actors.items.Inventory;
import cl.makinolas.atk.stages.AbstractStage;
import cl.makinolas.atk.utils.SaveInstance;
import cl.makinolas.atk.utils.SaveManager;


public class Hero extends Monsters {

  public static Hero player = new Hero();
  private boolean changing;
  private int changeIndex;
  private boolean isJumping;
  private boolean isDamaged;
  private boolean isAttacking;
  private float numberOfAttackingFrames;
  private float countMeleeFrames;
  private boolean dead;
  private World myWorld;
  private int walkAnimation;
  private int hurtAnimation;
  private int meleeAnimation;
  private final float hurtTime = 1 / 4f;
  private float accumulator;
  private Array<Friend> allies;
  private Friend actualFriend;
  private int indexFriend;
  private BodyDef myBodyDefinition;
  private Inventory inventory;
  private int vx;
  private boolean inertia;
  private boolean hasEvolved;

  private Hero() {

    isJumping = false;
    isFacingRight = false;
    isDamaged = false;
    isAttacking = false;
    hasEvolved = false;
    dead = false;
    changing = false;
    changeIndex = 0;
    accumulator = 0;
    inventory = new Inventory(this);
    vx = 0;
    inertia = false;

    // Set team for player;
    allies = new Array<Friend>();
    addAllie(new Bagon(this));
    addAllie(new Weedle(this));

    // Set actual allie
    actualFriend = allies.get(1);
    indexFriend = 1;
    
    // Set correct collider.
    myBodyDefinition = new BodyDef();
    myBodyDefinition.type = BodyDef.BodyType.DynamicBody;
    
    // Guardar animaciones del jugador
    setAnimation();
    changeAnimation(walkAnimation);
  }
  
  public static Hero getInstance(){
    return player;
  }
  
  public void reset(){
    player = new Hero();
  }
  
  private void addAllie(Friend friend) {
    allies.add(friend);
  }
  
  public void setWorld(World myWorld){
    this.myWorld = myWorld;
    isJumping = false;
    
    // Load position from saved instance (only for production)
    SaveManager.getInstance().loadData("ATK.sav");
    if (SaveManager.getInstance().hasSaveInstance()){
      SaveInstance lsi = SaveManager.getInstance().getSaveInstance();
      setSizeCollider(new Vector2(lsi.heroX, lsi.heroY), true);
    }
    else{
      setSizeCollider(new Vector2(2, 3), true);
    }
  }

  @Override
  public void act(float delta){
    checkChangingAllie();
    myBody.setLinearVelocity(vx, myBody.getLinearVelocity().y);
    
    ((AbstractStage) getStage()).changeCamera(myBody.getPosition().x , myBody.getPosition().y );
    
    checkDamage(delta);
    checkMelee(delta);
    checkEvolution();
    giveMagic();
    
  }
  
  private void checkEvolution() {
    if(hasEvolved){
      Attacks attack = new Puff(myWorld, myBody.getPosition().x,myBody.getPosition().y,isFacingRight, this);
      ((AbstractStage) getStage()).addGameActor(attack);
      hasEvolved = false;
    }
    
  }

  private void checkChangingAllie() {
    if(changing){
      setNewAllie(changeIndex);
      changing = false;
    }
  }

  private void changeAllie() {
    changing = true;
    actualFriend.isDead();
    lookForAliveAllie();
  }

  private void lookForAliveAllie() {
    for(int i = 0; i < allies.size; i++){
      if(!allies.get(i).getDead()){
        changeIndex = i; 
      }
    }
    if(allies.get(changeIndex).getDead()){
      heroIsDead();
    }
  }

  private void heroIsDead() {
    dead = true;   
  }

  @Override
  public boolean isDead(){
    return dead;
  }
  
  private void checkDamage(float delta) {
    if(isDamaged){
      accumulator += delta;
      if(accumulator > hurtTime){
        isDamaged = false;
        changeAnimation(walkAnimation);
        accumulator = 0;
      }
    }
  }

  private void giveMagic() {
    if(actualFriend.getMagic() < 1000){
      actualFriend.setMagic(((actualFriend.getMagic() + 1)%1001));
    }    
  }

  private void checkMelee(float delta) {
    if(isAttacking && countMeleeFrames <= numberOfAttackingFrames){
      countMeleeFrames += Math.floor(delta*100)/100;
    }
    else if(!isDamaged){
      countMeleeFrames = 0;
      isAttacking = false;
      reloadAnimation(meleeAnimation, 0.2f, actualFriend.getMeleeAnimation());
      changeAnimation(walkAnimation);
    } else {
      countMeleeFrames = 0;
      reloadAnimation(meleeAnimation, 0.2f, actualFriend.getMeleeAnimation());
    }
  }

  private float getImpulse() {
    return getBody().getMass()*12; // El 12 se busc� por testing.
  }

  public void landedPlatform(WorldManifold worldManifold, Platform platform){
    if(worldManifold.getNormal().y < -0.95){
      isJumping = false;
    }
  }

  private void setAnimation(){
    setMasterTexture(actualFriend.getTexture(),actualFriend.getWidth(),actualFriend.getHeight());
    walkAnimation = addAnimation(0.2f, actualFriend.getWalkAnimation());
    hurtAnimation = addAnimation(0.2f, actualFriend.getHurtAnimation());
    meleeAnimation = addAnimation(0.2f, actualFriend.getMeleeAnimation());
    numberOfAttackingFrames = actualFriend.getMeleeFrame() * 0.2f;
  }
  
  @Override
  public boolean isHero(){
    return true;
  }

  public int getHealth(){
    return actualFriend.getHealth();
  }
  
  public int getMagic(){
    return actualFriend.getMagic();
  }


  @Override
  public void damage(int damage, Attacks inflictor)  {
    if(!inflictor.getSource().isHero()){
      actualFriend.setHealth(actualFriend.getHealth() - damage);
      isDamaged = true;
      changeAnimation(hurtAnimation);
      inflictor.setDead();
    }
    if(actualFriend.getHealth() <= 0){
      changeAllie();
    }
  }


  @Override
  public int getMeleeDamage() {
    return 10;
  }
  
  public Friend getFriend(){
    return actualFriend;
  }
  
  private void setNewAllie(int index){
    if(!isJumping){
      Attacks attack = new Puff(myWorld, myBody.getPosition().x,myBody.getPosition().y,isFacingRight, this);
      ((AbstractStage) getStage()).addGameActor(attack);
      allies.set(indexFriend, actualFriend);
      actualFriend = allies.get(index);
      indexFriend = index;
      setSizeCollider(getBody().getPosition(), false);
      setAnimation();
    }
  }
  
  private void setSizeCollider(Vector2 position, boolean first) {
    myBodyDefinition.position.set(position);
    if(!first){
      myWorld.destroyBody(getBody());
    }
    Body myBody = myWorld.createBody(myBodyDefinition);
    PolygonShape shape = new PolygonShape();
    shape.setAsBox(getBodySize(actualFriend.getWidth()), getBodySize(actualFriend.getHeight()));
    myBody.setGravityScale(1);
    myBody.createFixture(shape, 0.5f);
    myBody.resetMassData();
    shape.dispose();
    
    // Change Body.
    setBody(myBody);
  }

  // This is used to get body width and height.
  private float getBodySize(int size){
    return (0.5f*size)/22;
  }
  
  public void evolved(){
    setAnimation();
    hasEvolved = true;
  }

  @Override
  public void interact(GameActor actor2, WorldManifold worldManifold){
    actor2.interactWithHero(this, worldManifold);
  }
  
  @Override
  public void interactWithPlatform(Platform platform, WorldManifold worldManifold){
    landedPlatform(worldManifold, platform);
  }
  
  @Override
  public void interactWithAttack(Attacks attack, WorldManifold worldManifold){
    this.damage(attack.getAttackDamage(), attack);
  }
  
  @Override
  public void interactWithBoss(IBoss boss){
    interactWithMonster(boss.getBoss());
    boss.getBoss().interactWithHero2(this);
  }
  
  @Override
  public void interactWithEnemy(Enemy enemy){
    interactWithMonster(enemy);
    enemy.interactWithHero2(this);
  }

  public void interactWithMonster(Monsters monster) {
    meleeAttack(monster, isAttacking);  
  }
  
  @Override
  public void interactWithPortal(Portal portal){
    portal.nextStage();
  }

  @Override
  public float getMonsterWidth() {
    return getBodySize(actualFriend.getWidth());
  }

  @Override
  public float getMonsterHeight() {
    return getBodySize(actualFriend.getHeight());
  }


  public Inventory getInventory() {
    return inventory;
  }

  public void moveHorizontal(int i, boolean restitutive) {
    if(restitutive && !inertia) return;
    vx += 7*i;
    if(vx!=0)
      isFacingRight = (vx>0);
    inertia = true;
  }


  public void jump() {
    if(!isJumping){
      myBody.applyLinearImpulse(0, getImpulse(), myBody.getPosition().x, myBody.getPosition().y, true);
      isJumping = true;
    }
  }

  public void attackPrimary() {
    if(actualFriend.getMagic() >= 100){
      actualFriend.setMagic(actualFriend.getMagic() - 100);
      GameActor fireball = actualFriend.getFriendAttack(myWorld, myBody.getPosition().x,myBody.getPosition().y,isFacingRight, this);
      ((AbstractStage) getStage()).addGameActor(fireball);
    }
  }

  public void attackSecondary() {
    if(!isAttacking){
      isAttacking = true;
      changeAnimation(meleeAnimation);
    }
  }


  public void changeAllie(int index) {
    if(indexFriend != index && !allies.get(index).getDead()){
      setNewAllie(index);
    }
  }

  @Override
  protected void gainExp(int enemyLevel, Enemies type) {
    actualFriend.gainExperience(enemyLevel, type);
  }

  public void throwBall(Ball.BallType type) {
    BallActor ball = new BallActor(type, myWorld, myBody.getPosition().x + ((isFacingRight)?0.6f:-0.6f)*actualFriend.getWidth()/ GameConstants.WORLD_FACTOR,
            myBody.getPosition().y);
    ball.setThrowImpulse((isFacingRight)?1:-1);
    ((AbstractStage) getStage()).addGameActor(ball);
  }

}
