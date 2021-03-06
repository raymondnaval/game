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
import com.raymondn.game.sprites.PlayerTitrisSprite2;
import com.raymondn.game.sprites.TitrisSquare;
import com.raymondn.game.tools.ObjectContactListener;
import com.raymondn.game.tools.WorldCreator;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author Raymond Naval <raymondnaval@gmail.com>
 */
public class PlayState implements Screen, InputProcessor {

    private final String TAG = "Class: PlayState";
    private PlayerTitrisSprite2 activeTitrisPiece;
    private ArrayList<PlayerTitrisSprite2> titrisPieces;
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

    // 2D Array of x,y locations for every individual square in the titris well.
    public static final TitrisSquare[][] WELL_SPACES = new TitrisSquare[33][10];

    private float[] increments, verticalIncrements;

    private ObjectContactListener ocl;
    WorldCreator wc;

    public PlayState(MainGame game) {
        this.game = game;

        generateIncrements();

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
        wc = new WorldCreator(this);

        // Fill in all well spaces with Vector2 coordinates.
        for (int row = 0; row < WELL_SPACES.length; row++) {
            for (int col = 0; col < WELL_SPACES[row].length; col++) {
//                Gdx.app.log(TAG, "increment x: " + increments[col]
//                        + " increment y: " + verticalIncrements[row]);
                WELL_SPACES[row][col] = new TitrisSquare(this, body, increments[col], verticalIncrements[row]);
            }
        }

        activeTitrisPiece = new PlayerTitrisSprite2(this);
        titrisPieces = new ArrayList<PlayerTitrisSprite2>();
        titrisPieces.add(activeTitrisPiece);

        Gdx.input.setInputProcessor(this);

        ocl = new ObjectContactListener(activeTitrisPiece);
        world.setContactListener(ocl);
        
//        debugSideWalls();
    }

    /**
     * Creates horizontal and vertical locations for incremental movement. Each
     * value is stored in its respective array.
     */
    private void generateIncrements() {

        // Horizontal increments in well.
        increments = new float[10];
        increments[0] = MainGame.LEFT_WALL / MainGame.PIXELS_PER_METER;
        increments[1] = (MainGame.LEFT_WALL + (((MainGame.RIGHT_WALL - MainGame.LEFT_WALL) / 10))) / MainGame.PIXELS_PER_METER;
        increments[2] = (MainGame.LEFT_WALL + (((MainGame.RIGHT_WALL - MainGame.LEFT_WALL) / 10 * 2))) / MainGame.PIXELS_PER_METER;
        increments[3] = (MainGame.LEFT_WALL + (((MainGame.RIGHT_WALL - MainGame.LEFT_WALL) / 10 * 3))) / MainGame.PIXELS_PER_METER;
        increments[4] = (MainGame.LEFT_WALL + (((MainGame.RIGHT_WALL - MainGame.LEFT_WALL) / 10 * 4))) / MainGame.PIXELS_PER_METER;
        increments[5] = (MainGame.LEFT_WALL + (((MainGame.RIGHT_WALL - MainGame.LEFT_WALL) / 10 * 5))) / MainGame.PIXELS_PER_METER;
        increments[6] = (MainGame.LEFT_WALL + (((MainGame.RIGHT_WALL - MainGame.LEFT_WALL) / 10 * 6))) / MainGame.PIXELS_PER_METER;
        increments[7] = (MainGame.LEFT_WALL + (((MainGame.RIGHT_WALL - MainGame.LEFT_WALL) / 10 * 7))) / MainGame.PIXELS_PER_METER;
        increments[8] = (MainGame.LEFT_WALL + (((MainGame.RIGHT_WALL - MainGame.LEFT_WALL) / 10 * 8))) / MainGame.PIXELS_PER_METER;
        increments[9] = (MainGame.LEFT_WALL + (((MainGame.RIGHT_WALL - MainGame.LEFT_WALL) / 10 * 9))) / MainGame.PIXELS_PER_METER;

        // Vertical increments in well.
        verticalIncrements = new float[MainGame.NUM_VERTICAL_TILES];
        verticalIncrements[0] = MainGame.WELL_DEPTH;
        for (int i = 1; i < verticalIncrements.length; i++) {
            DecimalFormat df = new DecimalFormat("###.##");
            float roundHorzPosTo2DecimalPlaces = Float.valueOf(df.format(verticalIncrements[i - 1] + (MainGame.PIXEL_SIZE / MainGame.PIXELS_PER_METER)));
            verticalIncrements[i] = roundHorzPosTo2DecimalPlaces;
        }
    }

    public float[] getHorizontalIncrements() {
        return increments;
    }

    public float[] getVerticalIncrements() {
        return verticalIncrements;
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
                Thread.sleep(100);
            } catch (Exception ex) {
                Gdx.app.log(TAG, ex.toString());
            }
            activeTitrisPiece = new PlayerTitrisSprite2(this);
            titrisPieces.add(activeTitrisPiece);

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

            for (int j = 0; j < titrisPieces.get(i).getTitrisPiece().length; j++) {

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
    
    private void debugSideWalls() {
        WELL_SPACES[0][0].activatePhysicsSquare(true);
        WELL_SPACES[1][0].activatePhysicsSquare(true);
        WELL_SPACES[2][0].activatePhysicsSquare(true);
        WELL_SPACES[3][0].activatePhysicsSquare(true);
        WELL_SPACES[4][0].activatePhysicsSquare(true);
        WELL_SPACES[5][0].activatePhysicsSquare(true);
        WELL_SPACES[6][0].activatePhysicsSquare(true);
        WELL_SPACES[7][0].activatePhysicsSquare(true);
        WELL_SPACES[8][0].activatePhysicsSquare(true);
        WELL_SPACES[9][0].activatePhysicsSquare(true);
        WELL_SPACES[0][9].activatePhysicsSquare(true);
        WELL_SPACES[1][9].activatePhysicsSquare(true);
        WELL_SPACES[2][9].activatePhysicsSquare(true);
        WELL_SPACES[3][9].activatePhysicsSquare(true);
        WELL_SPACES[4][9].activatePhysicsSquare(true);
        WELL_SPACES[5][9].activatePhysicsSquare(true);
        WELL_SPACES[6][9].activatePhysicsSquare(true);
        WELL_SPACES[7][9].activatePhysicsSquare(true);
        WELL_SPACES[8][9].activatePhysicsSquare(true);
        WELL_SPACES[9][9].activatePhysicsSquare(true);
        WELL_SPACES[9][8].activatePhysicsSquare(true);
        WELL_SPACES[9][7].activatePhysicsSquare(true);
        WELL_SPACES[9][6].activatePhysicsSquare(true);
        WELL_SPACES[8][7].activatePhysicsSquare(true);
        WELL_SPACES[8][6].activatePhysicsSquare(true);
        
    }

}
