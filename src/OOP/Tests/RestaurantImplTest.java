package OOP.Tests;

import OOP.Provided.HungryStudent;
import OOP.Provided.Restaurant;
import OOP.Provided.Restaurant.RateRangeException;
import OOP.Solution.HungryStudentImpl;
import OOP.Solution.RestaurantImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.testng.AssertJUnit.*;


/**
 * Created by danie_000 on 4/20/2017.
 */
public class RestaurantImplTest {
    private static Set<String> menu1;
    private static Set<String> menu2;
    private static Set<String> menu3;
    private static Set<String> menu4;
    private static Restaurant r1;
    private static Restaurant r2;
    private static Restaurant r3;
    private static Restaurant r4;
    private static Restaurant r5;
    private static HungryStudent s1;
    private static HungryStudent s2;
    private static HungryStudent s3;
    private static HungryStudent s4;
    private static HungryStudent s5;


    @Before
    public void init() {
        menu1 = new HashSet<String>();
        menu1.add("French Fries");
        menu1.add("Steak");
        menu1.add("Cola");
        menu2 = new HashSet<String>();
        menu2.add("Pizza");
        menu2.add("Cola");
        menu2.add("Juice");
        menu2.add("Salad");
        menu3 = new HashSet<String>();
        menu3.add("Vanilla");
        menu3.add("Strawberry");
        menu3.add("Cherry");
        menu3.add("Chocolate");
        menu3.add("Apple");
        menu3.add("Lemon");
        menu3.add("Honey");
        menu4 = new HashSet<String>();

        r1 = new RestaurantImpl(4, "BBB", 5, menu1);
        r2 = new RestaurantImpl(3, "PizzaHut", 8, menu2);
        r3 = new RestaurantImpl(10, "IceCreamStore", 4, menu3);
        r4 = new RestaurantImpl(7, "EmptyStore", 10, menu4);
        r5 = new RestaurantImpl(2, "DominoPizza", 20, menu2);

        s1 = new HungryStudentImpl(117, "John");
        s2 = new HungryStudentImpl(7, "James");
        s3 = new HungryStudentImpl(23, "Jack");
        s4 = new HungryStudentImpl(4, "Locke");
        s5 = new HungryStudentImpl(51, "Kate");

    }

    @Test
    public void distance() {
        assertEquals(5, r1.distance());
        assertEquals(8, r2.distance());
        assertEquals(4, r3.distance());
        assertEquals(10, r4.distance());
        assertEquals(20, r5.distance());
        System.out.println("Restaurant::distance TEST SUCCESSFUL");
    }

    @Test
    public void rate() throws Exception {
        try {
            r1.rate(s1, 0).rate(s2, 1).rate(s3, 2).rate(s4, 3).rate(s5, 5);
        } catch (RateRangeException e) {
            fail("Was supposed to be a legal rating");
        }
        try {
            r2.rate(s1, -1);
            fail("Supposed to be a rate range exception");
        } catch (RateRangeException e) {

        }
        try {
            r2.rate(s1, 6);
            fail("Supposed to be a rate range exception");
        } catch (RateRangeException e) {

        }
        try {
            r2.rate(s1, -100);
            fail("Supposed to be a rate range exception");
        } catch (RateRangeException e) {

        }
        try {
            r2.rate(s1, 10);
            fail("Supposed to be a rate range exception");
        } catch (RateRangeException e) {

        }
        System.out.println("Restaurant::rate TEST SUCCESSFUL");
    }

    @Test
    public void numberOfRates() throws Exception {
        try {
            assertEquals(0, r3.numberOfRates());
            r3.rate(s4, 3).rate(s4, 3);
            assertEquals(1, r3.numberOfRates());
            r3.rate(s4, 2);
            assertEquals(1, r3.numberOfRates());
            r3.rate(s3, 5);
            assertEquals(2, r3.numberOfRates());
            r1.rate(s1, 0).rate(s2, 1).rate(s3, 2).rate(s4, 3).rate(s5, 5);
            assertEquals(5, r1.numberOfRates());
        } catch (RateRangeException e) {
            fail("All rates are legal,weird...");
        }
        System.out.println("Restaurant::numberOfRates TEST SUCCESSFUL");
    }

    @Test
    public void averageRating() throws Exception {
        assertEquals(0.0, r4.averageRating());
        try {
            r3.rate(s4, 3).rate(s3, 5);
            assertEquals(4.0, r3.averageRating());
            r3.rate(s4, 3).rate(s3, 4);
            assertEquals(3.5, r3.averageRating());
            r3.rate(s4, 4).rate(s3, 1);
            assertEquals(2.5, r3.averageRating());

            r1.rate(s1, 0).rate(s2, 1).rate(s3, 2).rate(s4, 3).rate(s5, 5);
            assertEquals(2.2, r1.averageRating());
            r1.rate(s1, 4);
            assertEquals(3.0, r1.averageRating());
        } catch (RateRangeException e) {
            fail("All rates are legal,weird...");
        }
        System.out.println("Restaurant::averageRating TEST SUCCESSFUL");
    }

    @Test
    public void equals() {
        /*
         * Id is the same, however can a restaurant become a student one day? ^^
         */
        assertFalse(r1.equals(s4));

        assertTrue(r1.equals(r1));
        assertTrue(r2.equals(r2));
        assertTrue(r3.equals(r3));
        assertTrue(r4.equals(r4));
        assertTrue(r5.equals(r5));

        assertFalse(r1.equals(r2));
        assertFalse(r2.equals(r1));
        assertFalse(r1.equals(r3));
        assertFalse(r3.equals(r1));
        assertFalse(r1.equals(r4));
        assertFalse(r4.equals(r1));
        assertFalse(r1.equals(r5));
        assertFalse(r5.equals(r1));
        assertFalse(r2.equals(r3));
        assertFalse(r3.equals(r2));
        assertFalse(r2.equals(r4));
        assertFalse(r4.equals(r2));
        assertFalse(r2.equals(r5));
        assertFalse(r5.equals(r2));
        assertFalse(r3.equals(r4));
        assertFalse(r4.equals(r3));
        assertFalse(r3.equals(r5));
        assertFalse(r5.equals(r3));
        assertFalse(r4.equals(r5));
        assertFalse(r5.equals(r4));

        System.out.println("Restaurant::equals TEST SUCCESSFUL");
    }

    @Test
    public void to_String() {
        //      r1 = new OOP.Solution.RestaurantImpl(4, "BBB", 5, menu1);
        //      r2 = new OOP.Solution.RestaurantImpl(3, "PizzaHut", 8, menu2);
        //      r3 = new OOP.Solution.RestaurantImpl(10, "IceCreamStore", 4, menu3);
        //      r4 = new OOP.Solution.RestaurantImpl(7, "EmptyStore", 10, menu4); SPECIAL CASE since EMPTY MENU :)
        //      r5 = new OOP.Solution.RestaurantImpl(2, "DominoPizza", 20, menu2);
        assertEquals("Restaurant: BBB.\n" +
                "Id: 4.\n" +
                "Distance: 5.\n" +
                "Menu: Cola, French Fries, Steak.\n", r1.toString());
        assertEquals("Restaurant: PizzaHut.\n" +
                "Id: 3.\n" +
                "Distance: 8.\n" +
                "Menu: Cola, Juice, Pizza, Salad.\n", r2.toString());
        assertEquals("Restaurant: IceCreamStore.\n" +
                "Id: 10.\n" +
                "Distance: 4.\n" +
                "Menu: Apple, Cherry, Chocolate, Honey, Lemon, Strawberry, Vanilla.\n", r3.toString());
        assertEquals("Restaurant: EmptyStore.\n" +
                "Id: 7.\n" +
                "Distance: 10.\n" +
                "Menu: .\n", r4.toString());
        assertEquals("Restaurant: DominoPizza.\n" +
                "Id: 2.\n" +
                "Distance: 20.\n" +
                "Menu: Cola, Juice, Pizza, Salad.\n", r5.toString());
        System.out.println("Restaurant::toString TEST SUCCESSFUL");
    }

    @Test
    public void compareTo() throws Exception {
//      r1 = new OOP.Solution.RestaurantImpl(4, "BBB", 5, menu1);
//      r2 = new OOP.Solution.RestaurantImpl(3, "PizzaHut", 8, menu2);
//      r3 = new OOP.Solution.RestaurantImpl(10, "IceCreamStore", 4, menu3);
//      r4 = new OOP.Solution.RestaurantImpl(7, "EmptyStore", 10, menu4);
//      r5 = new OOP.Solution.RestaurantImpl(2, "DominoPizza", 20, menu2);
        assertTrue(r1.compareTo(r1) == 0);
        assertTrue(r2.compareTo(r2) == 0);
        assertTrue(r3.compareTo(r3) == 0);
        assertTrue(r4.compareTo(r4) == 0);
        assertTrue(r5.compareTo(r5) == 0);

        assertTrue(r1.compareTo(r2) > 0);
        assertTrue(r2.compareTo(r1) < 0);
        assertTrue(r1.compareTo(r3) < 0);
        assertTrue(r3.compareTo(r1) > 0);
        assertTrue(r1.compareTo(r4) < 0);
        assertTrue(r4.compareTo(r1) > 0);
        assertTrue(r1.compareTo(r5) > 0);
        assertTrue(r5.compareTo(r1) < 0);
        assertTrue(r2.compareTo(r3) < 0);
        assertTrue(r3.compareTo(r2) > 0);
        assertTrue(r2.compareTo(r4) < 0);
        assertTrue(r4.compareTo(r2) > 0);
        assertTrue(r2.compareTo(r5) > 0);
        assertTrue(r5.compareTo(r2) < 0);
        assertTrue(r3.compareTo(r4) > 0);
        assertTrue(r4.compareTo(r3) < 0);
        assertTrue(r3.compareTo(r5) > 0);
        assertTrue(r5.compareTo(r3) < 0);
        assertTrue(r4.compareTo(r5) > 0);
        assertTrue(r5.compareTo(r4) < 0);


        System.out.println("Restaurant::compareTo TEST SUCCESSFUL :)");

    }

}