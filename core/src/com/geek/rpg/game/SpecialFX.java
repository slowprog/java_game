package com.geek.rpg.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class SpecialFX {
    private Vector2 position;
    private float time;
    private float speed;
    private float maxTime;
    private int maxFrames;
    private Texture texture;
    private TextureRegion[] regions;

    public SpecialFX() {
        this.maxFrames = 64;
        this.speed = 0.05f;
        this.time = -1.0f;
        this.maxTime = this.maxFrames * this.speed;
        this.texture = new Texture("explosion64.png");

        TextureRegion[][] tr = new TextureRegion(this.texture).split(64, 64);

        this.regions = new TextureRegion[this.maxFrames];
        int counter = 0;
        for (int i = 0; i < tr.length; i++) {
            for (int j = 0; j < tr[0].length; j++) {
                regions[counter] = tr[i][j];
                counter++;
            }
        }
    }

    public boolean isActive() {
        return this.time > 0.0f;
    }

    public void setup(float x, float y) {
        this.position.set(x, y);
        this.time = 0.01f;
    }

    public void render(SpriteBatch batch) {
        if (this.isActive()) {
            batch.draw(this.regions[(int) (time / speed)], position.x, position.y);
        }
    }

    public void update(float dt) {
        if (this.isActive()) {
            time += dt;

            if (this.time > this.maxTime) {
                this.time = -1.0f;
            }
        }
    }
}
