import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

import java.awt.Color;
import java.awt.Point;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.w3c.dom.css.Rect;

public class TestCases
{
   public static final double DELTA = 0.001;

   /* some sample tests but you must write more! see lab write up */

   @Test
   public void testCircleGetArea()
   {
      Circle c = new Circle(7.7, new Point(4, 4), Color.BLACK);

      assertEquals(186.26502843133886, c.getArea(), DELTA);
   }

   @Test
   public void testCircleGetPerimeter()
   {
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);

      assertEquals(35.6759261, c.getPerimeter(), DELTA);
   }

   @Test
   public void testWorkSpaceAreaOfAllShapes()
   {
      WorkSpace ws = new WorkSpace();

      ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Circle(5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
              Color.BLACK));

      assertEquals(114.290606, ws.getAreaOfAllShapes(), DELTA);
   }

   @Test
   public void testWorkSpaceGetCircles()
   {
      WorkSpace ws = new WorkSpace();
      List<Circle> expected = new LinkedList<>();

      // Have to make sure the same objects go into the WorkSpace as
      // into the expected List since we haven't overriden equals in Circle.
      Circle c1 = new Circle(5.678, new Point(2, 3), Color.BLACK);
      Circle c2 = new Circle(1.11, new Point(-5, -3), Color.RED);

      ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      ws.add(c1);
      ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
              Color.BLACK));
      ws.add(c2);

      expected.add(c1);
      expected.add(c2);

      // Doesn't matter if the "type" of lists are different (e.g Linked vs
      // Array).  List equals only looks at the objects in the List.
      assertEquals(expected, ws.getCircles());
   }

   /* HINT - comment out implementation tests for the classes that you have not
    * yet implemented */
   @Test
   public void testCircleImplSpecifics()
           throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
              "getColor", "setColor", "getArea", "getPerimeter", "translate",
              "getRadius", "setRadius", "getCenter");

      final List<Class> expectedMethodReturns = Arrays.asList(
              Color.class, void.class, double.class, double.class, void.class,
              double.class, void.class, Point.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
              new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
              new Class[0], new Class[] {double.class}, new Class[0]);

      verifyImplSpecifics(Circle.class, expectedMethodNames,
              expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testRectangleImplSpecifics()
           throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
              "getColor", "setColor", "getArea", "getPerimeter", "translate",
              "getWidth", "setWidth", "getHeight", "setHeight", "getTopLeftCorner");

      final List<Class> expectedMethodReturns = Arrays.asList(
              Color.class, void.class, double.class, double.class, void.class,
              double.class, void.class, double.class, void.class, Point.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
              new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
              new Class[0], new Class[] {double.class}, new Class[0], new Class[] {double.class}, new Class[0]);

      verifyImplSpecifics(Rectangle.class, expectedMethodNames,
              expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testTriangleImplSpecifics()
           throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
              "getColor", "setColor", "getArea", "getPerimeter", "translate",
              "getVertexA", "getVertexB", "getVertexC");

      final List<Class> expectedMethodReturns = Arrays.asList(
              Color.class, void.class, double.class, double.class, void.class,
              Point.class, Point.class, Point.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
              new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
              new Class[0], new Class[0], new Class[0]);

      verifyImplSpecifics(Triangle.class, expectedMethodNames,
              expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testConvexPolygonImplSpecifics()
           throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
              "getColor", "setColor", "getArea", "getPerimeter", "translate",
              "getVertex");

      final List<Class> expectedMethodReturns = Arrays.asList(
              Color.class, void.class, double.class, double.class, void.class,
              Point.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
              new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
              new Class[] {int.class});

      verifyImplSpecifics(ConvexPolygon.class, expectedMethodNames,
              expectedMethodReturns, expectedMethodParameters);
   }

   private static void verifyImplSpecifics(
           final Class<?> clazz,
           final List<String> expectedMethodNames,
           final List<Class> expectedMethodReturns,
           final List<Class[]> expectedMethodParameters)
           throws NoSuchMethodException
   {
      assertEquals("Unexpected number of public fields",
              0, clazz.getFields().length);

      final List<Method> publicMethods = Arrays.stream(
              clazz.getDeclaredMethods())
              .filter(m -> Modifier.isPublic(m.getModifiers()))
              .collect(Collectors.toList());

      assertEquals("Unexpected number of public methods",
              expectedMethodNames.size(), publicMethods.size());

      assertTrue("Invalid test configuration",
              expectedMethodNames.size() == expectedMethodReturns.size());
      assertTrue("Invalid test configuration",
              expectedMethodNames.size() == expectedMethodParameters.size());

      for (int i = 0; i < expectedMethodNames.size(); i++)
      {
         Method method = clazz.getDeclaredMethod(expectedMethodNames.get(i),
                 expectedMethodParameters.get(i));
         assertEquals(expectedMethodReturns.get(i), method.getReturnType());
      }
   }
   @Test
   public void testGeAreaCircle()
   { assertEquals(101.2839, new Circle(5.678, new Point(2, 3), Color.BLACK).getArea(),DELTA);}
   @Test
   public void testGetPerimeterCircle()
   {assertEquals(35.6759, new Circle(5.678, new Point(2, 3), Color.BLACK).getPerimeter(),DELTA);}
   @Test
   public void testGetRadiusCircle()
   {
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);
      c.setRadius(14.78);
      assertEquals(14.78, c.getRadius(),DELTA);
   }
   @Test
   public void testGetCenterCircle()
   {
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);
      assertEquals(new Point(2,3), c.getCenter());
   }
   @Test
   public void testGetPerimeterRect()
   {assertEquals(13.824,new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK).getPerimeter(),DELTA);}
   @Test
   public void testGetAreaRect()
   {assertEquals(7.0066,new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK).getArea(),DELTA);}
   @Test
   public void testGetColorRect()
   {assertEquals(Color.ORANGE,new Rectangle(1.234, 5.678, new Point(2, 3), Color.ORANGE).getColor());}
   @Test
   public void testSetColorRect()
   {
      Rectangle r = new Rectangle(1.234, 5.678, new Point(2, 3), Color.ORANGE);
      r.setColor(Color.BLACK);
      assertEquals(Color.BLACK, r.getColor());
   }
   @Test
   public void testGetTopL()
   {
      Rectangle r = new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK);
      assertEquals(new Point(2,3),r.getTopLeftCorner());
   }
   @Test
   public void testSetHeight()
   {
      Rectangle r = new Rectangle(1.234, 5.678, new Point(2, 3), Color.ORANGE);
      r.setHeight(1.234);
      assertEquals(1.234, r.getHeight(),DELTA);
   }
   @Test
   public void testGetHeight()
   {assertEquals(9.343,new Rectangle(1.234, 9.343, new Point(2, 3), Color.ORANGE).getHeight(),DELTA);}
   @Test
   public void testSetWidith()
   {
      Rectangle r = new Rectangle(1.234, 5.678, new Point(2, 3), Color.ORANGE);
      r.setWidth(5.555);
      assertEquals(5.555, r.getWidth(),DELTA);
   }
   @Test
   public void testGetWidith()
   {assertEquals(1.234,new Rectangle(1.234, 9.343, new Point(2, 3), Color.ORANGE).getWidth(),DELTA);}
   @Test
   public void testGetVertexA()
   {
      Triangle T = new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
              Color.BLACK);
      assertEquals(new Point(0,0),T.getVertexA());
   }
   @Test
   public void testGetVertexB()
   {
      Triangle T = new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
              Color.BLACK);
      assertEquals(new Point(2,-4),T.getVertexB());
   }
   @Test
   public void testGetVertexC()
   {
      Triangle T = new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
              Color.BLACK);
      assertEquals(new Point(3,0),T.getVertexC());
   }
   @Test
   public void testGetColorTri()
   {assertEquals(Color.ORANGE,new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
           Color.ORANGE).getColor());}
   @Test
   public void testSetColorTri()
   {
      Triangle T = new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
              Color.BLACK);
      T.setColor(Color.ORANGE);
      assertEquals(Color.ORANGE, T.getColor());
   }
   @Test
   public void testGetAreaTri()
   {
      assertEquals(-222.50,new Triangle(new Point(15,15), new Point(23,30), new Point(50, 25),
              Color.ORANGE).getArea(),DELTA);

   }
   @Test
   public void testGetPerimeterTri()
   {
      assertEquals(23.558,new Triangle(new Point(1,2), new Point(3,-4), new Point(-4, 5),
              Color.ORANGE).getPerimeter(),DELTA);
   }
   @Test
   public void testgetVertexPoly()
   {
      ConvexPolygon CP = new ConvexPolygon(new Point[]{new Point(1,2)},Color.BLACK);
      assertEquals(new Point(1,2),CP.getVertex(0));
   }
   @Test
   public void testgetColorPoly()
   {
      ConvexPolygon CP = new ConvexPolygon(new Point[]{new Point(1,2)},Color.ORANGE);
      assertEquals(Color.ORANGE,CP.getColor());
   }
   @Test
   public void testSetColorPoly()
   {
      ConvexPolygon CP = new ConvexPolygon(new Point[]{new Point(1,2)},Color.ORANGE);
      CP.setColor(Color.BLACK);
      assertEquals(Color.BLACK,CP.getColor());
   }
   @Test
   public void testGetAreaPoly()
   {
      ConvexPolygon CP = new ConvexPolygon(new Point[]{new Point(2,5),new Point(-4,3),new Point(5,1),
              new Point(2,5)},Color.BLACK);
      assertEquals(15,CP.getArea(),DELTA);
   }
   @Test
   public void testGetPeriPoly()
   {
      ConvexPolygon CP = new ConvexPolygon(new Point[]{new Point(-4,-3),new Point(-3,3),new Point(2,4),
              new Point(4,-3)}
              ,Color.BLACK);
      assertEquals(26.461,CP.getPerimeter(),DELTA);
   }

   @Test
   public void testWorkSpaceGetRect()
   {
      WorkSpace ws = new WorkSpace();
      List<Rectangle> expected = new LinkedList<>();

      Rectangle c1 = new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK);
      Rectangle c2 = new Rectangle(5.678, 1.374, new Point(4, 4), Color.BLACK);

      ws.add(new Circle(1.11, new Point(-5, -3), Color.RED));
      ws.add(c1);
      ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
              Color.BLACK));
      ws.add(c2);

      expected.add(c1);
      expected.add(c2);

      assertEquals(expected, ws.getRectangles());
   }
   @Test
   public void testWorkSpaceGetTri()
   {
      WorkSpace ws = new WorkSpace();
      List<Triangle> expected = new LinkedList<>();

      Triangle c1 = new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
              Color.BLACK);
      Triangle c2 = new Triangle(new Point(2,3), new Point(24,-24), new Point(23, 4),
              Color.BLACK);

      ws.add(new Circle(1.11, new Point(-5, -3), Color.RED));
      ws.add(c1);
      ws.add(new Circle(12.33, new Point(4, 2), Color.RED));
      ws.add(c2);

      expected.add(c1);
      expected.add(c2);

      assertEquals(expected, ws.getTriangles());
   }
   @Test
   public void testWorkSpaceGetPoly()
   {
      WorkSpace ws = new WorkSpace();
      List<ConvexPolygon> expected = new LinkedList<>();

      ConvexPolygon CP1 = new ConvexPolygon(new Point[]{new Point(2,5),new Point(-4,3),new Point(5,1),
              new Point(2,5)},Color.BLACK);
      ConvexPolygon CP2 = new ConvexPolygon(new Point[]{new Point(2,5),new Point(-4,3),new Point(5,1),
              new Point(2,5)},Color.BLACK);

      ws.add(new Circle(1.11, new Point(-5, -3), Color.RED));
      ws.add(CP1);
      ws.add(new Circle(12.33, new Point(4, 2), Color.RED));
      ws.add(CP2);

      expected.add(CP1);
      expected.add(CP2);

      assertEquals(expected, ws.getConvexPolygons());
   }
   /*
   @Test
   public void testWorkSpaceColor()
   {
      WorkSpace ws = new WorkSpace();
      List<Shape> expected = new LinkedList<>();

      ConvexPolygon CP1 = new ConvexPolygon(new Point[]{new Point(2,5),new Point(-4,3),new Point(5,1),
              new Point(2,5)},Color.BLACK);

      ConvexPolygon CP2 = new ConvexPolygon(new Point[]{new Point(2,5),new Point(-4,3),new Point(5,1),
              new Point(2,5)},Color.ORANGE);

      ws.add(new Circle(1.11, new Point(-5, -3), Color.RED));
      ws.add(CP1);
      ws.add(new Circle(12.33, new Point(4, 2), Color.RED));
      ws.add(CP2);

      expected.add(CP1);

      assertEquals(expected, ws.getShapesByColor(Color.BLACK));
   }

    */
   @Test
   public void testWorkSpaceAreaOfAllShapes2()
   {
      WorkSpace ws = new WorkSpace();

      ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Circle(5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
              Color.BLACK));
      ws.add(new ConvexPolygon(new Point[]{new Point(2,5),new Point(-4,3),new Point(5,1),
              new Point(2,5)},Color.BLACK));

      assertEquals(129.290606, ws.getAreaOfAllShapes(), DELTA);
   }
   @Test
   public void testWSget()
   {
      WorkSpace ws = new WorkSpace();

      ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Circle(5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
              Color.BLACK));
      ws.add(new ConvexPolygon(new Point[]{new Point(2,5),new Point(-4,3),new Point(5,1),
              new Point(2,5)},Color.BLACK));
      Shape expected = ws.get(3);

      assertEquals(expected, ws.get(3));
   }
   @Test
   public void testWSsize()
   {
      List<Shape> expected = new LinkedList<>();
      WorkSpace ws = new WorkSpace();

      ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Circle(5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
              Color.BLACK));
      ws.add(new ConvexPolygon(new Point[]{new Point(2,5),new Point(-4,3),new Point(5,1),
              new Point(2,5)},Color.BLACK));
      ConvexPolygon CP1 = new ConvexPolygon(new Point[]{new Point(2,5),new Point(-4,3),new Point(5,1),
              new Point(2,5)},Color.BLACK);
      ws.add(CP1);
      expected.add(CP1);

      assertEquals(5, ws.size());
   }
   @Test
   public void testWorkSpacePerimiOfAllShapes()
   {
      WorkSpace ws = new WorkSpace();

      ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Circle(5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
              Color.BLACK));
      ws.add(new ConvexPolygon(new Point[]{new Point(2,5),new Point(-4,3),new Point(5,1),
              new Point(2,5)},Color.BLACK));

      assertEquals(81.63926753241257, ws.getPerimeterOfAllShapes(), DELTA);
   }
   @Test
   public void testTranslateCircle()
   {
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);
      c.translate(new Point(3,4));
      assertEquals(new Point(3,4),c.getCenter());
   }
   @Test
   public void testTranslateRect()
   {
      Rectangle r = new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK);
      r.translate(new Point(4,5));
      assertEquals(new Point(4,5),r.getTopLeftCorner());
   }
   @Test
   public void testTranslateTri()
   {
      Triangle T = new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
              Color.BLACK);
      T.translate(new Point(1,1));
      assertEquals(new Point(1,1),T.getVertexA());
      assertEquals(new Point(3,-3),T.getVertexB());
      assertEquals(new Point(4,1),T.getVertexC());

   }
   @Test
   public void testTranslatePoly()
   {
      ConvexPolygon CP = new ConvexPolygon(new Point[]{new Point(2,5),new Point(-4,3),new Point(5,1)}
              ,Color.BLACK);

      CP.translate(new Point(2,2));

      assertEquals(new Point(4,7),CP.getVertex(0));
      assertEquals(new Point(-2,5),CP.getVertex(1));
      assertEquals(new Point(7,3),CP.getVertex(2));
   }


}
