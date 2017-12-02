package com.geek.rpg.game.sceens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.geek.rpg.game.Assets;
import com.geek.rpg.game.RpgGame;
import com.geek.rpg.game.ScreenManager;

/**
 * Created by SlowProg on 20.11.2017.
 */

public class LoadingScreen implements Screen {
    private Texture texture;
    private SpriteBatch batch;

    public LoadingScreen(SpriteBatch batch) {
        this.batch = batch;

        Pixmap pixmap = new Pixmap(1280, 20, Pixmap.Format.RGB888);
        pixmap.setColor(Color.CORAL);
        pixmap.fill();

        this.texture = new Texture(pixmap);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(texture, 0,0, RpgGame.SCREEN_WIDTH * Assets.getInstance().getAssetManager().getProgress(), 20);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        ScreenManager.getInstance().onResize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
