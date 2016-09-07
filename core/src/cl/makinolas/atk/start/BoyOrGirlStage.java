package cl.makinolas.atk.start;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import cl.makinolas.atk.actors.Background;
import cl.makinolas.atk.actors.Title;
import cl.makinolas.atk.screen.GameScreen;
import cl.makinolas.atk.stages.AbstractStage;

public class BoyOrGirlStage extends AbstractStage {

  private Game myGame;
  
  private OrthographicCamera camera;

  private TextActor actualText;
  private Title images;
  private int sceneCount;
  private Title arrow;
  private int lastSelected;
  private Title images2;

  protected String myName;
  
  public BoyOrGirlStage(Viewport v, GameScreen gameScreen, Game myGame) {
    super(v);
    
    sceneCount = 0;
    lastSelected = 0;
    this.myGame = myGame;
    myScreen = gameScreen;
    addActor(new Background("Background/SuPuente.jpg", getCamera()));
    
    images = new Title("CharacterImages/Sensei.png",350, 300);
    addActor(images);
    
    //MobileGroup group = new MobileGroup(Gdx.app.getType() == Application.ApplicationType.Android);
    Gdx.input.setInputProcessor(this);
  
    
    actualText = new TextActor(GameText.chooseSexText);
    addActor(actualText);
    setupCamera();
  }
  
  public void act(float delta){
    super.act(delta);
    
    if(sceneCount == 4){
      GameScreen gameScreen = new GameScreen(myGame);
      gameScreen.setStage(new ChooseStage(new FitViewport(640,480), gameScreen, myGame));
      myGame.setScreen(gameScreen);
    }
    
    if(Gdx.input.isKeyJustPressed(Keys.Z) && sceneCount == 1){
      arrow.remove();
      images.remove();
      images2.remove();
      sceneCount = 2;
    }
    
    if(sceneCount == 2){
      sceneCount = 3;
      Gdx.input.getTextInput(new TextInputListener() {
        
        @Override
        public void input(String text) {
          myName = text;
          sceneCount = 4;
        }
        
        @Override
        public void canceled() {
          myName = "Alex";
          
        }
      }, "Choose your name", "", "Write your name here");
    }
    
    if(Gdx.input.isKeyJustPressed(Keys.Z) && actualText.hasFinished() && sceneCount == 0){
      sceneCount = 1;
      actualText.remove();    
      images.remove();
      images = new Title("CharacterImages/hombre.png",200, 300);
      images2 = new Title("CharacterImages/mujer.png",400, 300);
      addActor(images);
      addActor(images2);
      arrow = new Title("CharacterImages/arrow.png", 100, 300);
      addActor(arrow);      
    }
    
    if (Gdx.input.isKeyJustPressed(Keys.LEFT) && sceneCount == 1){
      lastSelected = (lastSelected == 0)? 1 : (lastSelected - 1);
      changeArrow();
    } if (Gdx.input.isKeyJustPressed(Keys.RIGHT) && sceneCount == 1){
      lastSelected = (lastSelected == 1)? 0 : (lastSelected + 1);
      changeArrow();
    }
  }

  private void changeArrow() {
    int actual = lastSelected;
    if(actual == 0){
      arrow.changeCoordinates(100, 300);
    } else if( actual == 1){
      arrow.changeCoordinates(300, 300);
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
  
}
