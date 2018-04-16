package com.raymondn.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.raymondn.game.MainGame;
import com.raymondn.game.sprites.PlayerTetrisSprite;
import com.raymondn.game.tools.WorldCreator;

/**
 *
 * @author Raymond Naval <raymondnaval@gmail.com>
 */
public class PlayState implements Screen {

    private PlayerTetrisSprite tetrisPlayer;
    private Viewport gamePort;
    private MainGame game;
    private OrthographicCamera gameView;

    // Map variables.
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Box2d variables.
    private World world;
    private Box2DDebugRenderer box2DRenderer;

    private TextureAtlas atlas;

    private float horzX, descY;

    public PlayState(MainGame game) {
        this.game = game;

        atlas = new TextureAtlas("player_sprite.atlas");

        gameView = new OrthographicCamera();
        gamePort = new FitViewport(MainGame.WIDTH / MainGame.PIXELS_PER_METER, MainGame.HEIGHT / MainGame.PIXELS_PER_METER, gameView);
        gamePort.apply();

        // Load map.
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("well.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MainGame.PIXELS_PER_METER);

        // Center game camera.
        gameView.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -0.1f), true);
        box2DRenderer = new Box2DDebugRenderer();
        box2DRenderer.SHAPE_STATIC.set(1, 0, 0, 1);

        new WorldCreator(this);

        tetrisPlayer = new PlayerTetrisSprite(world, this);

    }

    public World getWorld() {
        return world;
    }

    public TiledMap getMap() {
        return map;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    protected void handleInput(float deltaTime) {

        // DEBUG: press up to keep the Titris block afloat.
//        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
//            descY += MainGame.TILESIZE;
//        } else {
//            descY -= MainGame.TILESIZE/2;
//        }
        // Run button.
//        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
//            user.setMoveSpeed(2.5f);
//        } else {
//            user.setMoveSpeed(UserSprite.DEFAULT_SPEED);
//        }
//
        // Increment/decrement left/right by tile width.
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {

        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

        }
    }

    public void update(float dt) {
        handleInput(dt);
        world.step(1 / 30f, 6, 2);

        // Update game camera with correct coordinates after changes.
        gameView.update();

        // Only render what's seen in the game world.
        renderer.setView(gameView);

        tetrisPlayer.update(dt);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render game map.
        renderer.render();

        // Render Box2D debug lines.
        box2DRenderer.render(world, gameView.combined);

        game.getBatch().setProjectionMatrix(gameView.combined);
        game.getBatch().begin();
        game.getBatch().draw(tetrisPlayer.getTitrisPiece(), tetrisPlayer.getX(), tetrisPlayer.getY(), .16f, .16f);
        game.getBatch().end();

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        box2DRenderer.dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

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

}
