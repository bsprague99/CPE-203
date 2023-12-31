import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;

import java.awt.Color;
import java.awt.Point;


public class Triangle implements Shape{

    private Point A,B,C;
    private Color color;

    public Triangle(Point v1,Point v2, Point v3, Color c){
        this.A = v1;
        this.B = v2;
        this.C = v3;
        color = c;
    }
    //- A constructor with parameters to initialize all its instance variables. Do not implement a default constructor. The first Point passed to the constructor will be vertex A, the second Point vertex B, and so on.
    public Point getVertexA(){ return A;} // - Returns the Point representing vertex A of the Triangle.
    public Point getVertexB(){return B;} //- Returns the Point representing vertex B of the Triangle.
    public Point getVertexC(){return C;} // - Returns the Point representing vertex C of the Triangle.
    public Color getColor(){return color;} //- Returns the java.awt.Color of the Shape.
    public void setColor(Color c){color = c;} // Sets the java.awt.Color of the Shape.
    public double getArea(){return 0.5*(A.x*(B.y-C.y)+B.x*(C.y - A.y ) + C.x * ( A.y - B.y) ); } // - Returns the area of the Shape

    public double getPerimeter(){

        return Math.sqrt(Math.pow(C.x-B.x,2) +  Math.pow(C.y-B.y,2)) + Math.sqrt(Math.pow(A.x-C.x,2) +  Math.pow(A.y-C.y,2)
        ) + Math.sqrt( Math.pow(A.x-B.x,2) +  Math.pow(A.y-B.y,2)); } // - Returns the perimeter of the Shape

    public void translate(Point p){

        A.x = A.x + p.x;
        A.y = A.y + p.y;
        B.x = B.x + p.x;
        B.y = B.y + p.y;
        C.x = C.x + p.x;
        C.y = C.y + p.y;

    }// - Translates the entire shape by the (x,y) coordinates of a given java.awt.Point



}