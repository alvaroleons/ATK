package cl.makinolas.atk.actors.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ShopItem extends Actor {

    private TextureRegion reg;
    private final TextureRegion bg = new TextureRegion(new Texture("Overlays/boxgray.png"));
    private BitmapFont font = new BitmapFont(Gdx.files.internal("Fonts/normal.fnt"),Gdx.files.internal("Fonts/normal.png"),false);
    private int price, quantity;

    public ShopItem(TextureRegion im, int cost, int q){
        reg = im;
        price = cost;
        quantity = q;
        setBounds(0,0,120,48);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float cx = getX();
        float cy = getY();
        batch.draw(bg,cx,cy);
        batch.draw(reg,cx+8,cy+8);
        font.draw(batch,"$"+price,cx+44,cy+34);
        font.draw(batch,"In Bag: "+quantity,cx+40,cy+20);
    }
}
