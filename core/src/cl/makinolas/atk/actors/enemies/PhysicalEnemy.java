package cl.makinolas.atk.actors.enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;

import cl.makinolas.atk.actors.Platform;
import cl.makinolas.atk.actors.friend.Enemies;
import cl.makinolas.atk.actors.friend.Friend;

public class PhysicalEnemy extends Enemy {

	public PhysicalEnemy(World myWorld, TextureRegion friendTexture, int[] cutSprites, int[][] walkingAnimation,
			int[][] hurtAnimation, int health, int heroPosition, int level, Enemies friend, Friend parent) {
		super(myWorld, friendTexture,cutSprites,walkingAnimation,hurtAnimation,health,heroPosition,level,friend, parent);
	}
	
	


}
