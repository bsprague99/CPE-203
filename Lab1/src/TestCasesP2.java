import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class TestCasesP2 {
   public static final double DELTA = 0.00001;

   /*
    * This test is just to get you started.
    */

   @Test
   public void testGetX() {
      assertEquals(2.0, new Point(2.0, 2.0).getX(), DELTA);
   }
   @Test
   public void testGetY() {
      assertEquals(-5.5, new Point(2.0, -5.5).getY(), DELTA);}
   @Test
   public void testGetX2() {
      assertEquals(-1.1, new Point(-1.1, 2.0).getX(), DELTA);}
   @Test
   public void testGetY2() {
      assertEquals(6, new Point(2.0, 6).getY(), DELTA);}
   @Test
   public void testGetRadius() {
      assertEquals(7.81024967591, new Point(5.0, 6.0).getRadius(), DELTA);}
   @Test
   public void testGetRadius2() {
      assertEquals(2.2360679775, new Point(1.0, 2.0).getRadius(), DELTA); }
   @Test
   public void testGetAngle() {
      assertEquals(1.10714, new Point(2.0, 4.0).getAngle(), DELTA);} //negative
   @Test
   public void testGetAngle2() {
      assertEquals(Math.PI / 4, new Point(1.0, 1.0).getAngle(), DELTA);
   }
   @Test
   public void testRotate90() {
      assertEquals(-8.0, new Point(2.0, 8.0).rotate90().getX(), DELTA);
      assertEquals(2.0, new Point(2.0, 4.0).rotate90().getY(), DELTA); //more quadrant

   }


   /*
    * The tests below here are to verify the basic requirements regarding
    * the "design" of your class.  These are to remain unchanged.
    */

   @Test
   public void testImplSpecifics()
           throws NoSuchMethodException {
      final List<String> expectedMethodNames = Arrays.asList(
              "getX",
              "getY",
              "getRadius",
              "getAngle",
              "rotate90"
      );

      final List<Class> expectedMethodReturns = Arrays.asList(
              double.class,
              double.class,
              double.class,
              double.class,
              Point.class
      );

      final List<Class[]> expectedMethodParameters = Arrays.asList(
              new Class[0],
              new Class[0],
              new Class[0],
              new Class[0],
              new Class[0]
      );

      verifyImplSpecifics(Point.class, expectedMethodNames,
              expectedMethodReturns, expectedMethodParameters);
   }

   private static void verifyImplSpecifics(
           final Class<?> clazz,
           final List<String> expectedMethodNames,
           final List<Class> expectedMethodReturns,
           final List<Class[]> expectedMethodParameters)
           throws NoSuchMethodException {
      assertEquals("Unexpected number of public fields",
              0, Point.class.getFields().length);

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

      for (int i = 0; i < expectedMethodNames.size(); i++) {
         Method method = clazz.getDeclaredMethod(expectedMethodNames.get(i),
                 expectedMethodParameters.get(i));
         assertEquals(expectedMethodReturns.get(i), method.getReturnType());
      }
   }
}