package cl.makinolas.atk.actors.ui;

import cl.makinolas.atk.actors.HBar;
import cl.makinolas.atk.actors.Hero;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MainBar extends Actor{

    private Hero hero;
    private HBar healthBar, magicBar;
    private Sprite base;
    private BitmapFont font;

    public MainBar(Hero h){
        hero = h;
        healthBar = new HBar(100,100,150,10,new TextureRegion(new Texture(Gdx.files.internal("Overlays/bar_green.png"))));
        magicBar = new HBar(100,100,150,10,new TextureRegion(new Texture(Gdx.files.internal("Overlays/bar_blue.png"))));;
        base = new Sprite(new Texture(Gdx.files.internal("Overlays/bar_base.png")));
        base.setRegionWidth(640); //Full width
        font = new BitmapFont(Gdx.files.internal("Fonts/normal.fnt"),Gdx.files.internal("Fonts/normal.png"),false);
        setZIndex(1000);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float cx = getStage().getCamera().position.x - 320;
        float cy = getStage().getCamera().position.y - 240;
        healthBar.setCurrent(hero.getHealth());
        batch.draw(base,cx,cy);
        batch.draw(healthBar.getSprite(),cx+50,cy+26);
        batch.draw(magicBar.getSprite(),cx+50,cy+8);
        font.draw(batch,"HP",cx+18,cy+36);
        font.draw(batch,"Magic",cx+4,cy+20);
    }
}
