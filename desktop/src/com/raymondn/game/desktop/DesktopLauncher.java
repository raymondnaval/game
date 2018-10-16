package com.raymondn.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.raymondn.game.MainGame;

public class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = MainGame.TITLE;
        config.width = MainGame.WIDTH;
        config.height = MainGame.HEIGHT;
        new LwjglApplication(new MainGame(), config);
        // Hey! Remove the physics from the titris pieces and setup an array or
        // something of inactive static bodies in each square. Activate the
        // static bodies when a sprite fills up its square. You fucking idiot.
//         TODO: Fix descent. 
    }
}
