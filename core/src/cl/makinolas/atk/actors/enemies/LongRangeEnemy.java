package cl.makinolas.atk.actors.enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

import cl.makinolas.atk.actors.friend.Enemies;

public class LongRangeEnemy extends Enemy {

	public LongRangeEnemy(World myWorld, TextureRegion friendTexture, int[] cutSprites, int[][] walkingAnimation,
			int[][] hurtAnimation, int health, int heroPosition, int level, Enemies friend) {
		super(myWorld, friendTexture,cutSprites,walkingAnimation,hurtAnimation,health,heroPosition,level,friend);
	}

}
