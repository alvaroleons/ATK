package cl.makinolas.atk.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Background extends Actor {
 
  private TextureRegion backgroundImage;
  private Camera camera;
  
  public Background(String pathImage, Camera camera){
    this.camera = camera;
    backgroundImage = new TextureRegion(new Texture(Gdx.files.internal(pathImage)));
  }
  
  @Override
  public void draw(Batch batch, float alpha){
    batch.draw(backgroundImage, camera.position.x - backgroundImage.getRegionWidth() / 2
                                , camera.position.y - backgroundImage.getRegionHeight() / 2);
  }
}
