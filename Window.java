import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Window extends JFrame implements Runnable {

    public Graphics2D g2;
    public KL keyListener = new KL();
    public Rect player, ai, ballRect;
    public Ball ball;
    public PlayerController playerController;
    public PlayerController aiController;
    public Text leftScoreText, rightScoreText;

    public Window() {
        this.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        this.setTitle(Constants.SCREEN_TITLE);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(keyListener);
        g2 = (Graphics2D)this.getGraphics();

        Constants.ToolBarHeight = this.getInsets().top;
        Constants.BottomCutoff = this.getInsets().bottom;

        player = new Rect(Constants.hz_padding,Constants.SCREEN_HEIGHT/2 - Constants.pheight/2, Constants.pwidth, Constants.pheight, Color.WHITE);
        playerController = new PlayerController(player, keyListener);

        leftScoreText = new Text(0,
                new Font("Times New Roman", Font.PLAIN, Constants.TEXT_SIZE), Constants.TEXT_X_POS, Constants.TEXT_Y_POS);
        rightScoreText = new Text(0,
                new Font("Times New Roman", Font.PLAIN, Constants.TEXT_SIZE),
                Constants.SCREEN_WIDTH - Constants.TEXT_X_POS - Constants.TEXT_SIZE, Constants.TEXT_Y_POS);

        ai = new Rect(Constants.SCREEN_WIDTH - Constants.pwidth - Constants.hz_padding, Constants.SCREEN_HEIGHT/2 - Constants.pheight/2, Constants.pwidth, Constants.pheight, Color.WHITE);
        aiController = new PlayerController(ai, keyListener);
        ballRect = new Rect(Constants.SCREEN_WIDTH/2 - 15, Constants.SCREEN_HEIGHT/2 - 15, Constants.ballradius, Constants.ballradius, Color.WHITE);
        ball = new Ball(ballRect, player, ai, leftScoreText, rightScoreText);

        //aiController = new AIController(new PlayerController(ai), ballRect);
    }

    public void update(double dt) {
        Image dbImage = createImage(getWidth(), getHeight());
        Graphics dbg = dbImage.getGraphics();
        this.draw(dbg);
        g2.drawImage(dbImage, 0, 0, this);

        playerController.update(dt);
        aiController.update2(dt);
        ball.update(dt);
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        Font font = new Font("Arial", Font.ITALIC, 14);
        Text text = new Text("Press Space to start", font, 100, 100);
        if (!Constants.GAME)
            text.draw(g2);

        leftScoreText.draw(g2);
        rightScoreText.draw(g2);
        player.draw(g2);
        ai.draw(g2);
        ballRect.draw(g2);
    }

    public void run() {
        double lastFrameTime = 0.0;

        while (true) {
            double time = Time.getTime();
            double deltaTime = time - lastFrameTime;
            lastFrameTime = time;

            if (Constants.GAME) {
                ball.vx = -Constants.ballSpeed;
                break;
            }

            if (keyListener.isKeyPressed(KeyEvent.VK_SPACE)) {
                Constants.GAME = true;
            }

            update(deltaTime);
        }

        while (true) {
            double time = Time.getTime();
            double deltaTime = time - lastFrameTime;
            lastFrameTime = time;

            update(deltaTime);

            //try {Thread.sleep(60);} catch (Exception e) {}
        }
    }
}
