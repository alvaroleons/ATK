package cl.makinolas.atk.actors.friend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Igglypuff extends AbstractFriend {
  
  private TextureRegion[][] faces;
  
  public Igglypuff() {
    faces = new TextureRegion(new Texture(Gdx.files.internal("Actors/Igglypuff_faces.png"))).split(40,40);
    setTexture(new TextureRegion(new Texture(Gdx.files.internal("Actors/Igglypuff.png"))));
    setAnimations(new int[]{17,18}, 3,
                  new int[][]{new int[]{0,3},new int[]{0,4},new int[]{0,5}}, 1,
                  new int[][]{new int[]{0,0}});
    setFaceSprite(faces[0][0]);
    initLevel(5);
    
    setVariables(20, false);
  }
  
  public Igglypuff(float level){
    this();
    initLevel(level);
  }
  
  @Override
  protected void initLevel(float level){
   this.level = new Level(level);
   new Evolution(this.level, 16, 1);
  }
  
  @Override
  protected void evolve(int numberOfLevel){
    if (numberOfLevel == 1){
      setTexture(new TextureRegion(new Texture(Gdx.files.internal("Actors/Jigglypuff.png"))));
      setAnimations(new int[]{23,21}, 3,
          new int[][]{new int[]{0,3},new int[]{0,4},new int[]{0,5}}, 1,
          new int[][]{new int[]{0,0}});
      setFaceSprite(faces[0][1]);
      setVariables(60, false);
    } else if (numberOfLevel == 2){
      setTexture(new TextureRegion(new Texture(Gdx.files.internal("Actors/Wigglypuff.png"))));
      setAnimations(new int[]{26,25}, 4,
          new int[][]{new int[]{0,2},new int[]{0,3},new int[]{0,4},new int[]{0,3}}, 1,
          new int[][]{new int[]{0,0}});
      setFaceSprite(faces[0][2]);
      setVariables(100, false);
    }
  }
  
}
