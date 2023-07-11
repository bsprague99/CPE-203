import java.lang.Math;

public class Point
{
    private double x;
    private double y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getRadius(){
        double xDist = getX() - 0;
        double yDist  = getY() - 0;
        double hypotenuse = Math.pow(xDist,2) + Math.pow(yDist,2);
        return Math.sqrt(hypotenuse);
    }

    public double getAngle(){
        double yDist = getY();
        double xDist = getX();

        double answer = Math.atan2(yDist,xDist);
        return answer;
    }

    public Point rotate90(){

        double invertedY = (getY() * -1);
        Point returnP = new Point(invertedY,getX());
        return returnP;
    }
}