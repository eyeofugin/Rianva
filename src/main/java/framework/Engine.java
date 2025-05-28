package framework;

import framework.resources.Sound;
import framework.resources.SpriteUtils;
import framework.states.Memory;
import framework.states.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Engine extends JPanel implements Runnable{

    private final JFrame frame;
    public final int multiplier = 3;
    public KeyBoard keyB = new KeyBoard(this);
    private final BufferedImage image = new BufferedImage(Engine.X * multiplier, Engine.Y*multiplier, BufferedImage.TYPE_INT_RGB);
    private final int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    public StateManager stateManager;
    public Memory memory;
    private Thread gameThread;
    public static final int X = 640;
    public static final int Y = 360;
    public final int fps = 60;


    Sound music = new Sound();
    Sound effect = new Sound();
    boolean test = false;

    public Engine(JFrame window) {
        setPreferredSize(new Dimension(X*multiplier,Y*multiplier));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyB);
        this.setFocusable(true);
        this.frame = window;
    }

    public void start() {

        stateManager = new StateManager(this);
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        int frame = 0;
        double drawInterval = 1000000000/(double)this.fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (this.gameThread != null) {
            if (keyB.close) {
                this.gameThread = null;
                this.frame.dispose();
            }
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                stateManager.update(frame++);
                keyB.resetOnceClicks();
                render();
                delta=0;
            }
            if (frame >= this.fps) {
                if (test) {
                    test = false;
                    this.frame.setTitle("---------------");
                } else {
                    test = true;
                    this.frame.setTitle("");
                }
                frame = 0;
            }
        }
    }
    public void render() {
        int[] p = this.stateManager.render();
        p = SpriteUtils.upscale(p, X, Y, multiplier);

        paintP(p);
        Graphics g = getGraphics();
        g.drawImage(image,0,0,getWidth(),getHeight(),null);
        g.dispose();
        paint(g);
    }
    private void paintP(int[] p) {
        for(int i = 0; i < pixels.length; i++) {
            pixels[i] = p[i];
        }
    }
    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic() {
        music.stop();
    }
    public void playSoundEffect(int i) {
        if (effect.clip != null) {
            effect.stop();
        }
        effect.setFile(i);
        effect.play();
    }

    public static class KeyBoard implements KeyListener {

        Engine e;
        public boolean upPressed, downPressed, leftPressed, rightPressed, close, enterPressed, backPressed,
                shoulderLeftPressed, shoulderRightPressed, contextPressed, lPressed;
        public boolean _upPressed, _downPressed, _leftPressed, _rightPressed, _close, _enterPressed, _backPressed,
                _shoulderLeftPressed, _shoulderRightPressed, _contextPressed, _lPressed;
        public KeyBoard(Engine e) {
            this.e = e;
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();

            if (code == KeyEvent.VK_W) {
                if (!upPressed) {_upPressed=true;}
                upPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                if (!leftPressed) {_leftPressed=true;}
                leftPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                if (!downPressed) {_downPressed=true;}
                downPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                if (!rightPressed) {_rightPressed=true;}
                rightPressed = true;
            }
            if (code == KeyEvent.VK_Q) {
                if (!shoulderLeftPressed) {_shoulderLeftPressed=true;}
                shoulderLeftPressed = true;
            }
            if (code == KeyEvent.VK_E) {
                if (!shoulderRightPressed) {_shoulderRightPressed=true;}
                shoulderRightPressed = true;
            }
            if (code == KeyEvent.VK_ENTER) {
                if (!enterPressed) {_enterPressed=true;}
                enterPressed = true;
            }
            if (code == KeyEvent.VK_BACK_SPACE) {
                if (!backPressed) {_backPressed=true;}
                backPressed = true;
            }
            if (code == KeyEvent.VK_I) {
                if (!contextPressed) {_contextPressed=true;}
                contextPressed = true;
            }
            if (code == KeyEvent.VK_L) {
                if (!lPressed) {_lPressed=true;}
                lPressed = true;
            }

            if (code == KeyEvent.VK_ESCAPE) {
                close = true;
            }
        }
        private void resetOnceClicks() {
            this._downPressed = false;
            this._enterPressed = false;
            this._leftPressed = false;
            this._rightPressed = false;
            this._shoulderRightPressed = false;
            this._shoulderLeftPressed = false;
            this._upPressed = false;
            this._close = false;
            this._backPressed = false;
            this._contextPressed = false;
            this._lPressed = false;
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int code = e.getKeyCode();

            if (code == KeyEvent.VK_W) {
                upPressed = false;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = false;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = false;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = false;
            }
            if (code == KeyEvent.VK_Q) {
                shoulderLeftPressed = false;
            }
            if (code == KeyEvent.VK_E) {
                shoulderRightPressed = false;
            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = false;
            }
            if (code == KeyEvent.VK_BACK_SPACE) {
                backPressed = false;
            }
            if (code == KeyEvent.VK_I) {
                contextPressed = false;
            }
            if (code == KeyEvent.VK_L) {
                lPressed = false;
            }
        }
    }
}
