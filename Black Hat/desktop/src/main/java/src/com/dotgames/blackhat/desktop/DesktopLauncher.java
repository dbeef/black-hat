
package src.com.dotgames.blackhat.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import src.com.dotgames.blackhat.Starter;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1280;
        config.height = 720;
        config.fullscreen = true;
        config.vSyncEnabled = true;
        new LwjglApplication(new Starter(), config);
    }
}