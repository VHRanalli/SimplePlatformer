package com.mygdx.game.models;

import com.mygdx.game.input.IPlayerInput;
import com.scs.basicecs.AbstractEntity;

public class PlayerData {

	public static int nextImageId = 1;

	public IPlayerInput controller;
	private boolean in_game = false;
	public boolean quit = false; // If they've removed their controller; prevent them re-attaching to start again
	public AbstractEntity avatar;
	public float timeUntilAvatar;
	public int score;
	public int lives;
	public int imageId;

	public PlayerData(IPlayerInput _controller) {
		this.controller = _controller;
	}


	public void setInGame(boolean b) {
		if (b) {
			/*if (Settings.RELEASE_MODE == false) {
				if (in_game) {
					throw new RuntimeException("Player already in game!");
				}
			}*/
			this.in_game = true;
			this.lives = 3;
			if (imageId <= 0) {
				imageId = nextImageId++;
			}
		} else {
			this.in_game = false;
		}
	}


	public boolean isInGame() {
		return this.in_game;
	}

}
