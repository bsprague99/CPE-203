import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

public class PartTwoTestCases
{
   public static final double DELTA = 0.00001;

   @Test
   public void testPolyPerm()
   {

      List<Point> points = new ArrayList<Point>();
      points.add(new Point(0.0, 0.0));
      points.add(new Point(3.0, 0.0));
      points.add(new Point(0.0, 4));
      Polygon newPolyPerm = new Polygon(points);
      double d = newPolyPerm.perimeter();
      assertEquals(12.0, d, DELTA);
   }

   @Test
   public void testCirclePerm()
   {
      Circle circlePerm = new Circle(new Point(-12,-7), 6.0);

      double d = circlePerm.perimeter();

      assertEquals(12.0 * Math.PI, d, DELTA); //circumference = pi r^2
   }

   @Test
   public void testRectPerm()
   {
      Rectangle rect = new Rectangle(new Point(-16,-8), new Point(7,4));
      double d = rect.perimeter();
      assertEquals(70.0, d, DELTA);
   }

   @Test
   public void testBiggerCirc(){
      Circle circ1 = new Circle(new Point(0,-5), 10.0);
      Rectangle rect = new Rectangle(new Point(-5.0,2.0),new Point(6.0,2.4));
      List<Point> points = new ArrayList<Point>();
      points.add(new Point(1, 1));
      points.add(new Point(1,-1));
      points.add(new Point(-1,-1));
      points.add(new Point(-1,1));
      Polygon poly = new Polygon(points);
      double d = circ1.perimeter();
      assertEquals(20*Math.PI,d,DELTA);
   }

   @Test
   public void testBiggerRect()
   {
      Circle circ = new Circle(new Point(0,-5), 2.0);
      Rectangle rect = new Rectangle(new Point(-5.0,2.0),new Point(5.0,2.4));
      List<Point> points = new ArrayList<Point>();
      points.add(new Point(1, 1));
      points.add(new Point(1,-1));
      points.add(new Point(-1,-1));
      points.add(new Point(-1,1));
      Polygon poly1 = new Polygon(points);
      double d = rect.perimeter();
      assertEquals(20.8,d,DELTA);
   }

   @Test
   public void testBiggerPoly()
   {
      Circle circ = new Circle(new Point(0,-5), 2.0);
      Rectangle rect = new Rectangle(new Point(-5.0,2.0),

              new Point(5.0,2.4));

      List<Point> points = new ArrayList<Point>();
      points.add(new Point(4, 3));
      points.add(new Point(1,-1));
      points.add(new Point(-1,-7));
      points.add(new Point(-3,2));
      Polygon poly2 = new Polygon(points);
      double d = poly2.perimeter();
      assertEquals(27.615167589495123,d,DELTA);
   }

   @Test
   public void testCircleImplSpecifics()
           throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
              "getCenter", "getRadius", "perimeter");

      final List<Class> expectedMethodReturns = Arrays.asList(
              Point.class, double.class, double.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
              new Class[0], new Class[0], new Class[0]);

      verifyImplSpecifics(Circle.class, expectedMethodNames,
              expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testRectangleImplSpecifics()
           throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
              "getTopLeft", "getBottomRight", "perimeter");

      final List<Class> expectedMethodReturns = Arrays.asList(
              Point.class, Point.class, double.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
              new Class[0], new Class[0], new Class[0]);

      verifyImplSpecifics(Rectangle.class, expectedMethodNames,
              expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testPolygonImplSpecifics()
           throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
              "getPoints", "perimeter");

      final List<Class> expectedMethodReturns = Arrays.asList(
              List.class, double.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
              new Class[0], new Class[0]);

      verifyImplSpecifics(Polygon.class, expectedMethodNames,
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
}