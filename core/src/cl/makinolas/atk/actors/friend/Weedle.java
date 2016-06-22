package cl.makinolas.atk.actors.friend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Weedle extends AbstractFriend {
  
private TextureRegion[][] faces;
  
  public Weedle() {
    faces = new TextureRegion(new Texture(Gdx.files.internal("Actors/Weedle_faces.png"))).split(40,40);
    setTexture(new TextureRegion(new Texture(Gdx.files.internal("Actors/Weedle.png"))));
    setAnimations(new int[]{30,24}, 4,
                  new int[][]{new int[]{0,1},new int[]{0,2}, new int[]{0,3}, new int[]{0,2}}, 1,
                  new int[][]{new int[]{0,0}});
    setFaceSprite(faces[0][0]);
    initLevel(3);
    
    setVariables(30, false);
  }
  
  public Weedle(float level){
    this();
    initLevel(level);
  }
  
  @Override
  protected void initLevel(float level){
   this.level = new Level(level);
   new Evolution(this.level, 6, 1);
   new Evolution(this.level, 10, 2);
  }
  
  @Override
  protected void evolve(int numberOfLevel){
    if (numberOfLevel == 1){
      setTexture(new TextureRegion(new Texture(Gdx.files.internal("Actors/Kakuna.png"))));
      setAnimations(new int[]{25,27}, 2,
          new int[][]{new int[]{0,3},new int[]{0,4}}, 1,
          new int[][]{new int[]{0,0}});
      setFaceSprite(faces[0][1]);
      setVariables(60, false);
    } else if (numberOfLevel == 2){
      setTexture(new TextureRegion(new Texture(Gdx.files.internal("Actors/Beedrill.png"))));
      setAnimations(new int[]{35,28}, 4,
          new int[][]{new int[]{0,1},new int[]{0,2},new int[]{0,3},new int[]{0,2}}, 1,
          new int[][]{new int[]{0,0}});
      setFaceSprite(faces[0][2]);
      setVariables(80, false);
    }
  }
  
}
