import java.util.*;
public class Rectangle {
    private final Point leftTop;
    private final Point rightLower;

    public Rectangle() {
        leftTop = new Point(0, 0);
        rightLower = new Point(0, 0);
    }

    public Rectangle(Point topLeft, Point bottomRight) {
        this.leftTop = topLeft;

        this.rightLower = bottomRight;
    }

    public Point getTopLeft() {
        return leftTop;
    }

    public Point getBottomRight() {
        return rightLower;
    }

    public double perimeter() {

        double edgeOne = Math.abs((leftTop.getX() - rightLower.getX()) * 2);
        double edgeTwo = Math.abs((leftTop.getY() - rightLower.getY()) * 2);
        double perim = edgeOne + edgeTwo;
        return perim;
    }
}