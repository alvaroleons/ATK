package cl.makinolas.atk.screen;

import cl.makinolas.atk.stages.GameStage;
import cl.makinolas.atk.stages.Levels;
import cl.makinolas.atk.stages.MenuStage;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuScreen extends SimpleScreen {
  
  public MenuScreen(Game game){
    super(game, new MenuStage(new FitViewport(640,480)));

    TextButton startButton = new TextButton("Start Game",  new Skin(Gdx.files.internal("Data/uiskin.json")));
    startButton.setPosition(280, 240);
    startButton.addListener(new ClickListener(){
        @Override
        public void clicked(InputEvent event, float x, float y) {
            startGame();
        }
    });
    TextButton shopButton = new TextButton("Enter Shop",  new Skin(Gdx.files.internal("Data/uiskin.json")));
    shopButton.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        enterShop();
      }
    });
    TextButton optionButton = new TextButton("Options",  new Skin(Gdx.files.internal("Data/uiskin.json")));
    optionButton.setWidth(shopButton.getWidth());
    startButton.setWidth(shopButton.getWidth());

    shopButton.setPosition(280, 200);
    optionButton.setPosition(280, 160);
    stage.addActor(startButton);
    stage.addActor(shopButton);
    stage.addActor(optionButton);
  }

  private void startGame() {
    GameScreen gameScreen = new GameScreen(myGame);
    gameScreen.setStage(new GameStage(new FitViewport(640,480), gameScreen, myGame, Levels.LEVEL1));
    myGame.setScreen(gameScreen);
  }

  private void enterShop() {
    myGame.setScreen(new ShopScreen(myGame));
  }

  @Override
  public void render(float delta) {
    super.render(delta);
    if(Gdx.input.isKeyPressed(Input.Keys.ENTER))
      startGame();
  }
  
}
