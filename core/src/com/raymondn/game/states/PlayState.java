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
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.raymondn.game.MainGame;
import com.raymondn.game.sprites.PlayerTitrisSprite;
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
        shapeBounds();

        Gdx.input.setInputProcessor(this);
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
        world.step(1 / 30f, 6, 2);

        // Update game camera with correct coordinates after changes.
        gameView.update();

        // Only render what's seen in the game world.
        renderer.setView(gameView);

        // If titris piece is done descending, create a new titris piece.
        if (titrisPieces.get(titrisPieces.size() - 1).isDoneDescending()) {
            titrisPieces.add(new PlayerTitrisSprite(this));
            shapeBounds();
        } else {
            titrisPieces.get(titrisPieces.size() - 1).update(dt);

            // Set anchor of piece for rotation.
            Body rotationAnchor = boxAnchor(titrisPieces.get(titrisPieces.size() - 1).getX(), titrisPieces.get(titrisPieces.size() - 1).getY(),titrisPieces.get(titrisPieces.size() - 1).getTitrisWidth(),titrisPieces.get(titrisPieces.size() - 1).getTitrisHeight());
            Vector2 newCoords = new Vector2(titrisPieces.get(titrisPieces.size() - 1).getX() + (titrisPieces.get(titrisPieces.size() - 1).getTitrisWidth()/2),
                    titrisPieces.get(titrisPieces.size() - 1).getY()  + (titrisPieces.get(titrisPieces.size() - 1).getTitrisHeight()/2));
            double degToRads = Math.toRadians(titrisPieces.get(titrisPieces.size() - 1).getRotation());
            
            RevoluteJointDef rDef = new RevoluteJointDef();
            rDef.bodyA = rotationAnchor;
            rDef.bodyB = body;
            world.createJoint(rDef);
            
//            body.setTransform(newCoords, (float) degToRads);
            
            
            
        }
        Gdx.app.log(TAG, "titrisPieces.getrotation: " + titrisPieces.get(titrisPieces.size() - 1).getRotation() + " radians: " + Math.toRadians(titrisPieces.get(titrisPieces.size() - 1).getRotation()));
    }
    
    private Body boxAnchor(float x, float y, float width, float height) {
        Body mBody;
        BodyDef mBDef = new BodyDef();
        FixtureDef mFDef = new FixtureDef();
        mBDef.type = BodyDef.BodyType.KinematicBody;
        
        mBDef.position.set(x, y);
        mBDef.fixedRotation = true;
        mBody = world.createBody(mBDef);
        
        PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(width/2, height/2);
        mFDef.shape = pShape;
        mBody.createFixture(mFDef);
        pShape.dispose();
        
        return mBody;
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
            game.getBatch().draw(titrisPieces.get(i).getTitrisPiece(),
                    titrisPieces.get(i).getX(), titrisPieces.get(i).getY(),
                    titrisPieces.get(i).getOriginX(),
                    titrisPieces.get(i).getOriginY(),
                    titrisPieces.get(i).getTitrisWidth(),
                    titrisPieces.get(i).getTitrisHeight(),
                    titrisPieces.get(i).getScaleX(),
                    titrisPieces.get(i).getScaleY(),
                    titrisPieces.get(i).getRotation());
        }

        game.getBatch().end();
    }

    private void shapeBounds() {

        

        // Create a rectangle object because the the objects in the loop are all rectangles.
        rect = new Rectangle(
                titrisPieces.get(titrisPieces.size() - 1).getX(), titrisPieces.get(titrisPieces.size() - 1).getY(),
                titrisPieces.get(titrisPieces.size() - 1).getTitrisWidth(),
                titrisPieces.get(titrisPieces.size() - 1).getTitrisHeight());

        Gdx.app.log(TAG, "getx: " + titrisPieces.get(titrisPieces.size() - 1).getX() + " width: " + titrisPieces.get(titrisPieces.size() - 1).getTitrisWidth());

        bdef.type = BodyDef.BodyType.KinematicBody;

        // Position the rectangle exactly where its drawn on the Tile map.
        bdef.position.set((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2));
        
        // @TODO Add comments to these below...
        body = world.createBody(bdef);
        shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
        fixture.shape = shape;
        body.createFixture(fixture);
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
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