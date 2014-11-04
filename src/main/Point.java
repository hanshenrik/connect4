package main;

/**
 * Created by hanshenrik on 03/11/14.
 */
public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        setX(x);
        setY(y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void increaseX() {
        x++;
    }

    public void decreaseX() {
        x--;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void increaseY() {
        y++;
    }

    public void decreaseY() {
        y--;
    }
}
