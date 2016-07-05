package cl.makinolas.atk.actors.friend;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

import cl.makinolas.atk.actors.Enemy;
import cl.makinolas.atk.actors.Hero;
import cl.makinolas.atk.actors.Monsters;
import cl.makinolas.atk.actors.attacks.Attacks;
import cl.makinolas.atk.actors.attacks.DragonBreath;
import cl.makinolas.atk.utils.Formulas;

public abstract class AbstractFriend implements Friend {
  
  private int health;
  private int maxHealth;
  private int magic;
  private int maxMagic;
  private boolean dead;
  private TextureRegion friendTexture;
  private int[] cutSprites;
  private int[][] walkingAnimation;
  private int[][] hurtAnimation;
  private int[][] meleeAnimation;
  private TextureRegion faceSprite;
  protected Level level;
  private int actualEvolution;
  private Hero myHero;
  public Enemies friend;
  
  public AbstractFriend(Hero hero){
    myHero = hero;
  }
  
  protected void setCutSprites(int width, int height){
    this.cutSprites = new int[]{width, height};
  }
  
  protected void setAnimations(int[] cutSprites, int[][] walkingAnimation,
      int[][] hurtAnimation){
    this.cutSprites = cutSprites;
    this.walkingAnimation = walkingAnimation;
    this.hurtAnimation = hurtAnimation; 
  }
  
  protected void setMeleeAnimation(int beginMeleeAnimation, int endMeleeAnimation){
    this.meleeAnimation = new int[endMeleeAnimation - beginMeleeAnimation + 1][];
    for (int i = beginMeleeAnimation; i <= endMeleeAnimation; i++ ){
      this.meleeAnimation[i - beginMeleeAnimation] = new int[]{0,i};
    }
  }
  
  protected void setWalkAnimation(int beginWalkAnimation, int endWalkAnimation){
    this.walkingAnimation = new int[endWalkAnimation - beginWalkAnimation + 1][];
    for (int i = beginWalkAnimation; i <= endWalkAnimation; i++ ){
      this.walkingAnimation[i - beginWalkAnimation] = new int[]{0,i};
    }
  }
  
  protected void setHurtAnimation(int beginHurtAnimation, int endHurtAnimation){
    this.hurtAnimation = new int[endHurtAnimation - beginHurtAnimation + 1][];
    for (int i = beginHurtAnimation; i <= endHurtAnimation; i++ ){
      this.hurtAnimation[i - beginHurtAnimation] = new int[]{0,i};
    }
  }
  
  protected void setMeleeAnimation(int... positions){
    this.meleeAnimation = new int[positions.length][];
    for (int i = 0; i < positions.length; i++ ){
      this.meleeAnimation[i] = new int[]{0,positions[i]};
    }
  }
  
  protected void setWalkAnimation(int... positions){
    this.walkingAnimation = new int[positions.length][];
    for (int i = 0; i < positions.length; i++ ){
      this.walkingAnimation[i] = new int[]{0,positions[i]};
    }
  }
  
  protected void setHurtAnimation(int... positions){
    this.hurtAnimation = new int[positions.length][];
    for (int i = 0; i < positions.length; i++ ){
      this.hurtAnimation[i] = new int[]{0,positions[i]};
    }
  }
  
  protected void setAnimations(int[] cutSprites, int beginWalkAnimation, int endWalkAnimation,
      int beginHurtAnimation, int endHurtAnimation, int beginMeleeAnimation, int endMeleeAnimation){
    
    this.cutSprites = cutSprites;
    this.walkingAnimation = new int[1][];
    this.hurtAnimation = new int[1][];
    this.meleeAnimation = new int[1][];
    for (int i = beginWalkAnimation; i <= endWalkAnimation; i++ ){
      this.walkingAnimation[i - beginWalkAnimation] = new int[]{0,i};
    }
    for (int i = beginHurtAnimation; i <= endHurtAnimation; i++ ){
      this.hurtAnimation[i - beginHurtAnimation] = new int[]{0,i};
    }
    for (int i = beginMeleeAnimation; i <= endMeleeAnimation; i++ ){
      this.meleeAnimation[i - beginMeleeAnimation] = new int[]{0,i};
    }
  }
  
  protected void setFaceSprite(TextureRegion faceSprite){
    this.faceSprite = faceSprite;
  }
  
  protected void setTexture(TextureRegion setTexture){
    friendTexture = setTexture;
  }
  
  @Override
  public void gainExperience(int wildLevel, Enemies type){
    level.gainExp(wildLevel, type);
  }
  
  @Override
  public double thisLevelExp(){
    return level.getLevelMax();
  }
  
  protected void setActualEvolution(int evolution){
    actualEvolution = evolution;
  }
  
  protected int getActualEvolution(){
    return actualEvolution;
  }
  
  protected abstract void initLevel(int level);
  
  @Override
  public void setVariables(int health, int magic) {
   this.health = health;  
   this.magic = magic;
  }
  
  @Override
  public void setMagic(int magic){
    this.magic = magic;
  }
  
  @Override
  public int getMagic(){
    return magic;
  }
  
  @Override
  public boolean getDead(){
    return dead;
  }
  
  @Override
  public void isDead(){
    dead = true;
  }
  
  protected void initDead(){
    dead = false;
  }
  
  @Override
  public int getHealth() {
    return health;
  }
  
  @Override
  public Enemy returnEnemy(World myWorld, int heroPosition) {
    return new Enemy(myWorld, friendTexture, cutSprites, 
                walkingAnimation, hurtAnimation,  getHealth(), heroPosition, getLevel(), friend);
  }
  
  @Override
  public int[][] getHurtAnimation() {
    return hurtAnimation;
  }

  @Override
  public int[][] getWalkAnimation() {
    return walkingAnimation;
  }

  @Override
  public int[][] getMeleeAnimation() {
    return meleeAnimation;
  }
  
  @Override
  public int getMeleeFrame() {
    return meleeAnimation.length;
  }
    
  @Override
  public TextureRegion getTexture() {
    return friendTexture;
  }

  @Override
  public int getWidth() {
    return cutSprites[0];
  }

  @Override
  public int getHeight() {
    return cutSprites[1];
  }

  @Override
  public TextureRegion getFriendFaceSprite() {
    return faceSprite;
  }

  @Override
  public int getLevel() {
    return level.getLevel();
  }
  
  protected class Level extends Observable {
    private double nextExpLevel;
    private double expLevelMax;
    private int level;
    
    public Level(int level){
      this.level = level;
      nextExpLevel = Formulas.nextExpLevel(level);
      expLevelMax = nextExpLevel;
    }
    
    public void gainExp(int wildPokemonLevel, Enemies type){
      this.nextExpLevel -= Formulas.gainExp(level, wildPokemonLevel, type);
      if(nextExpLevel < 0 && level < 100){
        double freeExp = Math.abs(nextExpLevel);
        levelUp(level + 1);
        this.nextExpLevel -= freeExp;
      }
    }
    
    private void levelUp(int newLevel) {
      synchronized (this) {
        this.level = newLevel;
        this.nextExpLevel = Formulas.nextExpLevel(newLevel);
        expLevelMax = nextExpLevel;
      }
      setChanged();
      notifyObservers();
    }

    public synchronized int getLevel() {
      return level;
    }
    
    public double getLevelMax(){
      return expLevelMax;
    }
  }
  
  protected class Evolution implements Observer {
    
    private float evolLevel;
    private int numberOfEvolution;
    private boolean evolved;
    
    public Evolution(Level level, float evolLevel, int numberOfEvolution){
      observe(level);
      this.evolLevel = evolLevel;
      this.numberOfEvolution = numberOfEvolution;
      evolved = false;
    }
    
    public void observe(Observable o) {
      o.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
      float newLevel = ((Level) o).getLevel();
      if(newLevel >= evolLevel && !evolved){
       evolve(this.numberOfEvolution);
       myHero.evolved();
       evolved = true;
      }
    }
  }

  // Override if it has an evolution.
  protected void evolve(int numberOfEvolution){
    
  }
  
  @Override
  public int getMaxHealth(){
    return maxHealth;
  }
  
  @Override
  public void setHealth(int health){
    this.health = health > maxHealth? maxHealth:health;
  }
  
  protected void setMaxHealth(int maxHealth){
    this.maxHealth = maxHealth;
    this.health = maxHealth;
  }
  
  @Override
  public int getMaxMagic(){
    return maxMagic;
  }
  
  protected void setMaxMagic(int maxMagic){
    this.maxMagic = maxMagic;
    this.magic = maxMagic;
  }
  
  @Override
  public Attacks getFriendAttack(World myWorld, float x , float y, boolean facingRight, Monsters source){
    return new DragonBreath(myWorld, x, y, facingRight, source);
  }
  
  @Override
  public Enemies getType(){
    return friend;
  }
}
