import java.util.*;

public class Polygon {

    private  List<Point> vertices;

    public Polygon(List<Point> vertices) {
        this.vertices = vertices;
    }

    public List<Point> getPoints() {
        return vertices;

    }
    public double perimeter(){
        double totalPerim = 0.0;
        for (int x = 0; x < vertices.size()-1; x++){
            Point temp1 = vertices.get(x);
            Point temp2 = vertices.get(x+1);
            double distance = Math.sqrt(Math.pow(temp1.getX() - temp2.getX(),2) + Math.pow(temp1.getY() - temp2.getY(),2));
            totalPerim = totalPerim + distance;
        }
        Point lastPt = vertices.get(vertices.size()-1);
        Point firstPt = vertices.get(0);
        double firstAndLast = Math.sqrt(Math.pow(lastPt.getX() - firstPt.getX(),2) + Math.pow(lastPt.getY() - firstPt.getY(),2));

        totalPerim = totalPerim + firstAndLast;
        return totalPerim;
    }
}
