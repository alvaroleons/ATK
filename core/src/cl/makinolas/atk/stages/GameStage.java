package cl.makinolas.atk.stages;

import java.io.IOException;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import cl.makinolas.atk.actors.Background;
import cl.makinolas.atk.actors.GameActor;
import cl.makinolas.atk.actors.Hero;
import cl.makinolas.atk.actors.InputController;
import cl.makinolas.atk.actors.Portal;
import cl.makinolas.atk.actors.friend.Gastly;
import cl.makinolas.atk.actors.ui.MainBar;
import cl.makinolas.atk.actors.ui.MobileGroup;
import cl.makinolas.atk.screen.GameScreen;
import cl.makinolas.atk.utils.LevelReader;

public class GameStage extends AbstractStage implements ContactListener {

  public static String levelName = "level1";

  private World suMundo;
  private float accumulator;
  private final float frameTime = 1 / 300f;
  private final float enemySpawn = 3f;
  private float nextEnemyAt;
  private Array<GameActor> gameActors;
  private Group ground, mons, ui;
  private GameScreen myScreen;

  private MainBar bar;

  private OrthographicCamera camera;
  private Box2DDebugRenderer renderer;
  
  public GameStage(Viewport v, GameScreen actualScreen, Game myGame){
    super(v);
    myScreen = actualScreen;
    nextEnemyAt = enemySpawn;
    gameActors = new Array<GameActor>();
    suMundo = new World(new Vector2(0, -10), true);
    suMundo.setContactListener(this);

    addActor(new Background("Background/SuPuente.jpg", getCamera()));

    ground = new Group();
    addActor(ground);
    mons = new Group();
    addActor(mons);
    ui = new Group();
    addActor(ui);

    MobileGroup group = new MobileGroup(Gdx.app.getType() == Application.ApplicationType.Android);
    Gdx.input.setInputProcessor(this);
    Portal portal = new Portal(suMundo, new Vector2(49, -6), myGame);
    /* for easy entering boss 1
    Portal portal = new Portal(suMundo, new Vector2(10, 3), myGame);*/
    addGameActor(portal);
    Hero hero =  new Hero(suMundo);
    createPlatforms();
    addGameActor(hero); 
    bar = new MainBar(hero);
    ui.addActor(bar);
    ui.addActor(group);    
    
    addListener(new InputController(hero,group));
    accumulator = 0;
    renderer = new Box2DDebugRenderer();
    setupCamera();
  }

  public void addGameActor(GameActor actor) {
    mons.addActor(actor);
    gameActors.add(actor);
  }

  private void createPlatforms() {
    LevelReader reader = LevelReader.getInstance();   
    reader.setWorld(suMundo);
    try {
      Array<GameActor> platforms = reader.loadLevel(GameStage.levelName);
      for(GameActor p : platforms)
        ground.addActor(p);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void setupCamera() {
    camera = new OrthographicCamera(32, 24);
    camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
    camera.update();
  }
  
  public void changeCamera(float x, float y){
    camera.position.set(x, y, 0);
    getCamera().position.set(x * 20, y * 20, 0);
    getCamera().update();    
    camera.update();
  }
  
  @Override
  public void act(float delta){
    super.act(delta);
    for(GameActor actor : gameActors){
      if(actor.isHero() && actor.isDead()){
        changeDeadMenu();
      }
      if(actor.isMonster() || actor.isAttack() || actor.isBall()){
        Body actorBody = actor.getBody();
        if(actorBody.getPosition().y < -200 || actorBody.getPosition().x < -100 || actorBody.getPosition().x > 200 || actor.isDead()){
          gameActors.removeValue(actor,true);
          suMundo.destroyBody(actorBody);
          actor.remove();
        }
      }
    }

    accumulator += delta;
    elapsedTime += delta;
    nextEnemyAt -= delta;
    
    while(accumulator >= frameTime){
      suMundo.step(frameTime, 6, 2);
      accumulator -= frameTime;
    }
    
    if(nextEnemyAt < 0){
       GameActor enemy = (new Gastly()).returnEnemy(suMundo, (int) getCamera().position.x);
       addGameActor(enemy);
       nextEnemyAt = enemySpawn;
    }
    
    
  }
  
  private void changeDeadMenu() {
    myScreen.mainMenu();
  }

  @Override
  public void draw() {
      super.draw();
      //bar.drawCustom(getBatch(),getCamera().position.x,getCamera().position.y); //Custom draw for MainBar
      camera.update();
      renderer.render(suMundo, camera.combined);
  }

  @Override
  public void beginContact(Contact contact) {
    
    GameActor actor1 = (GameActor) contact.getFixtureA().getBody().getUserData();
    GameActor actor2 = (GameActor) contact.getFixtureB().getBody().getUserData();
    
    actor1.interact(actor2);
  }

  @Override
  public void endContact(Contact contact) {
    
  }

  @Override
  public void preSolve(Contact contact, Manifold oldManifold) {
    
  }

  @Override
  public void postSolve(Contact contact, ContactImpulse impulse) {
    
  }
  
  
}
