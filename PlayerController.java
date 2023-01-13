import java.awt.event.KeyEvent;

public class PlayerController {
    public Rect rect;
    public KL keyListener;

    public PlayerController(Rect rect, KL keyListener) {
        this.rect = rect;
        this.keyListener = keyListener;
    }

    /* public PlayerController(Rect rect) {
        this.rect = rect;
        this.keyListener = null;
    } */

    public void update(double dt) {
        if (keyListener != null) {
            if (keyListener.isKeyPressed(KeyEvent.VK_S)) {
                moveDown(dt);
            } else if (keyListener.isKeyPressed(KeyEvent.VK_W)) {
                moveUp(dt);
            }
        }
    }

    public void update2(double dt) {
        if (keyListener != null) {
            if (keyListener.isKeyPressed(KeyEvent.VK_DOWN)) {
                moveDown(dt);
            } else if (keyListener.isKeyPressed(KeyEvent.VK_UP)) {
                moveUp(dt);
            }
        }
    }

    public void moveDown(double dt) {
        if (this.rect.y + Constants.paddleSpeed * dt < Constants.SCREEN_HEIGHT - Constants.pheight - Constants.BottomCutoff)
            this.rect.y += Constants.paddleSpeed * dt;
    }

    public void moveUp(double dt) {
        if (this.rect.y - Constants.paddleSpeed * dt > Constants.ToolBarHeight)
            this.rect.y -= Constants.paddleSpeed * dt;
    }
}
