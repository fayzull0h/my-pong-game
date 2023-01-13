public class Ball {
    public Rect rect, leftPaddle, rightPaddle;
    public Text leftScoreText, rightScoreText;

    public double vy = 0;
    public double vx = 0;

    public Ball(Rect rect, Rect leftPaddle, Rect rightPaddle, Text leftScoreText, Text rightScoreText) {
       this.rect = rect;
       this.leftPaddle = leftPaddle;
       this.rightPaddle = rightPaddle;
       this.leftScoreText = leftScoreText;
       this.rightScoreText = rightScoreText;
    }

    public double calculateNewVelocityAngle(Rect paddle) {
        double relativeIntersect = (paddle.y + (paddle.height / 2.0)) - (this.rect.y + (this.rect.height / 2.0));
        double normalIntersect = relativeIntersect / (paddle.height/2.0);
        double theta = normalIntersect * Constants.maxAngle;
        return Math.toRadians(theta);
    }

    public void update(double dt) {
        if (vx < 0) {
            if (this.rect.x <= this.leftPaddle.x + this.leftPaddle.width && this.rect.x >= this.leftPaddle.x &&
                    this.rect.y + this.rect.height >= this.leftPaddle.y && this.rect.y <= this.leftPaddle.y + this.leftPaddle.height) {
                double theta = calculateNewVelocityAngle(leftPaddle);
                double newVx = Math.abs((Math.cos(theta)) * Constants.ballSpeed);
                this.vy = (-Math.sin(theta)) * Constants.ballSpeed;

                double oldSign = Math.signum(vx);
                this.vx = newVx * (-1.0 * oldSign);
            }
        } else if (vx > 0) {
            if (this.rect.x + this.rect.width >= this.rightPaddle.x && this.rect.x + this.rect.width <= this.rightPaddle.x + this.rightPaddle.width &&
                    this.rect.y + this.rect.height >= this.rightPaddle.y && this.rect.y <= this.rightPaddle.y + this.leftPaddle.height) {
                double theta = calculateNewVelocityAngle(rightPaddle);
                double newVx = Math.abs((Math.cos(theta)) * Constants.ballSpeed);
                this.vy = (-Math.sin(theta)) * Constants.ballSpeed;

                double oldSign = Math.signum(vx);
                this.vx = newVx * (-1.0 * oldSign);
            }
        }

        if (this.rect.y + this.vy * dt < Constants.ToolBarHeight) {
            this.vy = -this.vy;
        } else if (this.rect.y + this.rect.height + this.vy * dt> Constants.SCREEN_HEIGHT) {
            this.vy = -this.vy;
        }

        this.rect.x += vx * dt;
        this.rect.y += vy * dt;

        if (this.rect.x < leftPaddle.x) {
            int rightScore = Integer.parseInt(rightScoreText.text);
            rightScoreText.text = "" + ++rightScore;

            this.rect.x = Constants.SCREEN_WIDTH / 2.0;
            this.rect.y = Constants.SCREEN_HEIGHT / 2.0;
            this.vx = Constants.ballSpeed;
            this.vy = 0.0;
            this.rightPaddle.y = Constants.SCREEN_HEIGHT / 2.0 - Constants.pheight / 2.0;
            if (rightScore >= Constants.WIN) {
                System.out.println("Right player won");
            }
        } else if (this.rect.x > rightPaddle.x + Constants.pwidth) {
            int leftScore = Integer.parseInt(leftScoreText.text);
            leftScoreText.text = "" + ++leftScore;

            this.rect.x = Constants.SCREEN_WIDTH / 2.0;
            this.rect.y = Constants.SCREEN_HEIGHT / 2.0;
            this.vx = -Constants.ballSpeed;
            this.vy = 0.0;
            this.leftPaddle.y = Constants.SCREEN_HEIGHT / 2.0 - Constants.pheight / 2.0;
            if (leftScore >= Constants.WIN) {
                System.out.println("Left player won");
            }
        }
    }
}
