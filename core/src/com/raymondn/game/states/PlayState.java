package com.raymondn.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.raymondn.game.MainGame;
import com.raymondn.game.sprites.PlayerTitrisSprite;
import com.raymondn.game.tools.ObjectContactListener;
import com.raymondn.game.tools.WorldCreator;
import java.util.ArrayList;

/**
 *
 * @author Raymond Naval <raymondnaval@gmail.com>
 */
public class PlayState implements Screen, InputProcessor {

    private final String TAG = "Class: PlayState";
    private PlayerTitrisSprite activeTitrisPiece;
    private ArrayList<PlayerTitrisSprite> titrisPieces;
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
    private BodyDef bdef = new BodyDef();
    private Body body;
    private Rectangle rect;
    private PolygonShape shape;
    private FixtureDef fixture = new FixtureDef();

    private ObjectContactListener ocl;

    public PlayState(MainGame game) {
        this.game = game;

//        atlas = new TextureAtlas("player_sprite.atlas");
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
        shape = new PolygonShape();
        box2DRenderer = new Box2DDebugRenderer();
        box2DRenderer.SHAPE_STATIC.set(1, 0, 0, 1);
        new WorldCreator(this);

        activeTitrisPiece = new PlayerTitrisSprite(this);
        titrisPieces = new ArrayList<PlayerTitrisSprite>();
        titrisPieces.add(activeTitrisPiece);

        Gdx.input.setInputProcessor(this);

        ocl = new ObjectContactListener(activeTitrisPiece);
        world.setContactListener(ocl);
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

    }

    public void update(float dt) {
        handleInput(dt);
        world.step(1 / 60f, 6, 2);

        // Update game camera with correct coordinates after changes.
        gameView.update();

        // Only render what's seen in the game world.
        renderer.setView(gameView);

        // If titris piece is done descending, create a new titris piece.
        if (titrisPieces.get(titrisPieces.size() - 1).isDoneDescending()) {
            try {
                Thread.sleep(500);
            } catch (Exception ex) {
                Gdx.app.log(TAG, ex.toString());
            }
            activeTitrisPiece = new PlayerTitrisSprite(this);
            titrisPieces.add(activeTitrisPiece);
            ocl = new ObjectContactListener(activeTitrisPiece);
            world.setContactListener(ocl);
            titrisPieces.get(0).debugGetPosition(0);
            for (int i = 0; i < titrisPieces.size(); i++) {
                Gdx.app.log(TAG, i + ": " + titrisPieces.get(i).getBit());
            }

        } else {
            titrisPieces.get(titrisPieces.size() - 1).update(dt);
        }
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

        for (int i = 0; i < titrisPieces.size(); i++) {
//            Gdx.app.log(TAG, "\ngetx: " + titrisPieces.get(i).getX() + 
//                    " getY: " + titrisPieces.get(i).getY()+ 
//                    " getoriginx: " + titrisPieces.get(i).getOriginX()+ 
//                    " getoriginY: " + titrisPieces.get(i).getOriginY()+ 
//                    " \ngettitriswidth: " + titrisPieces.get(i).getTitrisWidth()+ 
//                    " gettitrisheight: " + titrisPieces.get(i).getTitrisHeight()+ 
//                    " getscalex: " + titrisPieces.get(i).getScaleX()+ 
//                    " getscaleY: " + titrisPieces.get(i).getScaleY()+ 
//                    " getrotation: " + titrisPieces.get(i).getRotation());
            
for(int j=0; j<titrisPieces.get(i).getTitrisPiece().length; j++) {
    

            game.getBatch().draw(titrisPieces.get(i).getTitrisPiece()[j],
                    titrisPieces.get(i).getXY()[j].x, 
                    titrisPieces.get(i).getXY()[j].y,
                    titrisPieces.get(i).getOriginX(),
                    titrisPieces.get(i).getOriginY(),
                    titrisPieces.get(i).getTitrisWidth(),
                    titrisPieces.get(i).getTitrisHeight(),
                    titrisPieces.get(i).getScaleX(),
                    titrisPieces.get(i).getScaleY(),
                    titrisPieces.get(i).getRotation());
        }
}
        game.getBatch().end();
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {

        // If not paused, enable controls.
        if (!titrisPieces.get(titrisPieces.size() - 1).isPaused()) {
            if (keycode == Keys.RIGHT) {
                titrisPieces.get(titrisPieces.size() - 1).setX(true);
            }
            if (keycode == Keys.LEFT) {
                titrisPieces.get(titrisPieces.size() - 1).setX(false);
            }
            if (keycode == Keys.DOWN) {
                titrisPieces.get(titrisPieces.size() - 1).accelerateDescent(true);
            }
            if (keycode == Keys.UP) {
                titrisPieces.get(titrisPieces.size() - 1).rotate();
            }
        }

        // Toggle pause.
        if (keycode == Keys.SPACE) {
            if (titrisPieces.get(titrisPieces.size() - 1).isPaused()) {
                titrisPieces.get(titrisPieces.size() - 1).pause(false);
            } else {
                titrisPieces.get(titrisPieces.size() - 1).pause(true);
            }
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Keys.DOWN) {
            titrisPieces.get(titrisPieces.size() - 1).accelerateDescent(false);
        }
        return true;
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

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
