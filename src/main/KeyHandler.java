package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    // debug
    boolean checkDrawTime = false;
    public boolean enterPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(java.awt.event.KeyEvent e) {
        // Handle key typed event
    }

    // Handle key pressed event
    @Override
    public void keyPressed(java.awt.event.KeyEvent e) {
        // Handle key pressed event
        int code = e.getKeyCode();

        // Title Keys
        if (gp.gameState == gp.titleState) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }

            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }

            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    gp.retry();
                    gp.gameState = gp.playState;
                }
            }

            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 1) {
                    gp.saveload.load();
                    gp.gameState = gp.playState;
                }
            }

            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 2) {
                    System.exit(0);
                }

            }
            return;
        }

        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }

        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }

        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }

        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }

        // enter
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        // close dialogue
        if (gp.gameState == gp.dialogueState) {
            if (code == KeyEvent.VK_ENTER) {
                gp.npc[0].speak();
                enterPressed = false;
            }
        }

        // pause
        if (code == KeyEvent.VK_P) {
            if (gp.gameState == gp.playState)
                gp.gameState = gp.pauseState;
            else if (gp.gameState == gp.pauseState)
                gp.gameState = gp.playState;
        }

        // save
        if (code == KeyEvent.VK_O) {
            if (gp.gameState == gp.pauseState) {
                gp.saveload.save();
                gp.ui.showMessage("Game Saved");
            }
        }

        // debug
        if (code == KeyEvent.VK_T) {
            checkDrawTime = !checkDrawTime;
        }
    }

    // This method is called when a key is released
    @Override
    public void keyReleased(java.awt.event.KeyEvent e) {
        // Handle key released event

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }

        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }

        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }

        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }

        if (code == KeyEvent.VK_ENTER) {
            enterPressed = false;
        }
    }
}