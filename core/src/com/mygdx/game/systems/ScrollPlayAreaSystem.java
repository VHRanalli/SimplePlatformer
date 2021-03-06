package com.mygdx.game.systems;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.ScrollsAroundComponent;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class ScrollPlayAreaSystem extends AbstractSystem {

	private MyGdxGame game;
	private float dist;
	private int highestPos, lowestPos;
	private int dir = 1;
	//private float timeUntilChange;
	
	public ScrollPlayAreaSystem(MyGdxGame _game, BasicECS ecs) {
		super(ecs);

		game = _game;

	}


	@Override
	public Class<?> getComponentClass() {
		return ScrollsAroundComponent.class;
	}


	@Override
	public void process() {
		/*timeUntilChange -= Gdx.graphics.getDeltaTime();  NO!  When scrolling up, the player falls through the platform.
		if (timeUntilChange < 0) {
			dir = NumberFunctions.rnd(0, 1) == 1 ? 1 : -1;
			timeUntilChange = NumberFunctions.rndFloat(2, 10);
		}*/
		
		dist = 20 * Gdx.graphics.getDeltaTime() * dir;
		highestPos = 0;
		lowestPos = Settings.LOGICAL_HEIGHT_PIXELS;

		super.process();

		if (highestPos < Settings.MAX_PLATFORM_HEIGHT - Settings.PLATFORM_SPACING) {
			game.levelGenerator.generateRow(highestPos + Settings.PLATFORM_SPACING, true);
		}
		/*if (lowestPos > Settings.PLATFORM_SPACING) {
			game.lvl.generateRow(lowestPos - Settings.PLATFORM_SPACING, true);
		}*/

	}


	@Override
	public void processEntity(AbstractEntity entity) {
		ScrollsAroundComponent gic = (ScrollsAroundComponent)entity.getComponent(ScrollsAroundComponent.class);
		if (gic != null) {
			PositionComponent pos = (PositionComponent)entity.getComponent(PositionComponent.class);
			if (gic.removeWhenNearEdge) { // Only use these types to detemine high
				if (pos.rect.top < 0 || pos.rect.top > Settings.MAX_PLATFORM_HEIGHT) {
					entity.remove();
					return;
				}
				if (pos.rect.top < lowestPos) {
					lowestPos = (int)pos.rect.top; 
				}
				if (pos.rect.top > highestPos) {
					highestPos = (int)pos.rect.top; 
				}			
			} else {
				if (pos.rect.top < 0 || pos.rect.bottom > Settings.LOGICAL_HEIGHT_PIXELS) {
					entity.remove();
					return;
				}
			}
			pos.rect.move(0, -dist);
		}
	}

}
