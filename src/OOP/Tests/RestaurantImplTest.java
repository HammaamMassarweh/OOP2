package OOP.Tests;

import OOP.Provided.HungryStudent;
import OOP.Provided.Restaurant;
import OOP.Provided.Restaurant.RateRangeException;
import OOP.Solution.HungryStudentImpl;
import OOP.Solution.RestaurantImpl;

import org.junit.*;
import org.junit.runners.MethodSorters;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Guy on 4/21/2017.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // For ascending order to the tests

public class RestaurantImplTest {
	private static Restaurant r1, r2, r3, r4, r5, r6, r7;
	private static Set<String> menu1, menu2, menu3, menuE;
	private static HungryStudent s1, s2, s3, s4;
	private static final double DELTA = 0.01;
	
	@BeforeClass
	public static void setUp() {
		menu1 = new HashSet<>();
		menu1.add("Dornish Fries");
		menu1.add("Ale");
		menu1.add("Holy Burger");
		menu1.add("Extra Holy Burger");
		menu2 = new HashSet<>();
		menu2.add("Hot Wine");
		menu2.add("Mutton Burger");
		menu2.add("Salted Pork");
		menu2.add("Dornish Fries");
		menu3 = new HashSet<>();
		menu3.add("Beer");
		menu3.add("Mereneese Lamb Burger");
		menu3.add("Dornish Fries");
		menuE = new HashSet<>();
		
		r1 = new RestaurantImpl(111, "Burgers Of The Seven", 10, menu1);
		r2 = new RestaurantImpl(222, "Burger's Landing", 5, menu2);
		r3 = new RestaurantImpl(333, "Burger Garden", 60, menu3);
		r4 = new RestaurantImpl(444, "House Of Burgers And Fries", 12, menu1);
		r5 = new RestaurantImpl(555, "The Silent Burgers", 1000, menu1); // won't be rated
		r6 = new RestaurantImpl(666, "The Many Face Burger", 1000, menu1); // r5 with different id
		r7 = new RestaurantImpl(666, "The Exiled Burgers", 1000, menu1); //  r7 equals to r6 because id's
		
		
		s1 = new HungryStudentImpl(11, "Jon");
		s2 = new HungryStudentImpl(22, "Tyrion");
		s3 = new HungryStudentImpl(33, "Daenerys");
		s4 = new HungryStudentImpl(44, "Lyanna");
	}
	
	@Test
	public void test1_distanceTest() {
		assertEquals(10, r1.distance());
		assertEquals(5, r2.distance());
		assertEquals(60, r3.distance());
		assertEquals(12, r4.distance());
		
		System.out.println("test1_distanceTest - V");
	}
	
	@Test
	public void test2_rateTest() throws RateRangeException {
		try {
			r1.rate(s1, -1);
			fail("RateRangeException should be thrown");
		} catch (RateRangeException e) {
		}
		
		try {
			r2.rate(s2, 6);
			fail("RateRangeException should be thrown");
		} catch (RateRangeException e) {
		}
		
		try {
			r1.rate(s1, -1984);
			fail("RateRangeException should be thrown");
		} catch (RateRangeException e) {
		}
		
		try {
			r2.rate(s2, 1984);
			fail("RateRangeException should be thrown");
		} catch (RateRangeException e) {
		}
		
		try {
			r1.rate(s1, 2).rate(s2, 4).rate(s3, 3).rate(s4, 5);
			r2.rate(s1, 5).rate(s3, 1).rate(s1, 1).rate(s1, 3);
			r3.rate(s2, 4).rate(s4, 2).rate(s4, 4).rate(s2, 2);
		} catch (RateRangeException e) {
			fail("Error in test2_rateTest");
		}
		
		System.out.println("test2_rateTest - V");
	}
	
	@Test
	public void test3_numberOfRatesTest() throws RateRangeException {
		try {
			assertEquals(0, r5.numberOfRates());
			
			r1.rate(s1, 2).rate(s2, 4).rate(s3, 3).rate(s4, 5);
			r2.rate(s1, 5).rate(s3, 1).rate(s1, 1).rate(s1, 3);
			r3.rate(s2, 4).rate(s4, 2).rate(s4, 4).rate(s2, 2);
			r4.rate(s1, 4).rate(s2, 2).rate(s3, 5).rate(s2, 3);
			
			assertEquals(4, r1.numberOfRates());
			assertEquals(2, r2.numberOfRates());
			assertEquals(2, r3.numberOfRates());
			assertEquals(3, r4.numberOfRates());
		} catch (RateRangeException e) {
			fail("Error in test3_numberOfRatesTest");
		}
		
		System.out.println("test3_numberOfRatesTest - V");
	}
	
	@Test
	public void test4_averageRatingTest() throws RateRangeException {
		try {
			assertEquals(0.0, r5.averageRating(), DELTA);
			
			r1.rate(s1, 2).rate(s2, 4).rate(s3, 3).rate(s4, 5);
			r2.rate(s1, 5).rate(s3, 1).rate(s1, 1).rate(s1, 3);
			r3.rate(s2, 4).rate(s4, 2).rate(s4, 4).rate(s2, 2);
			r4.rate(s1, 4).rate(s2, 2).rate(s3, 5).rate(s2, 3);
			
			assertEquals(3.5, r1.averageRating(), DELTA);
			assertEquals(2.0, r2.averageRating(), DELTA);
			assertEquals(3.0, r3.averageRating(), DELTA);
			assertEquals(4.0, r4.averageRating(), DELTA);
		} catch (RateRangeException e) {
			fail("Error in test4_averageRatingTest");
		}
		
		System.out.println("test4_averageRatingTest - V");
	}
	
	@Test
	public void test5_equalsTest() {
		
		assertTrue(r1.equals(r1) && r2.equals(r2) && r3.equals(r3) && r4.equals(r4) && r5.equals(r5) && r6.equals(r6) && r7.equals(r7));
		
		assertTrue(!r1.equals(r2) && !r2.equals(r1));
		assertTrue(!r1.equals(r3) && !r3.equals(r1));
		assertTrue(!r1.equals(r4) && !r4.equals(r1));
		assertTrue(!r1.equals(r5) && !r5.equals(r1));
		assertTrue(!r1.equals(r6) && !r6.equals(r1));
		
		assertTrue(!r4.equals(r2) && !r2.equals(r4));
		assertTrue(!r4.equals(r5) && !r5.equals(r4));
		assertTrue(!r4.equals(r6) && !r4.equals(r6));
		assertTrue(!r4.equals(r7) && !r7.equals(r4));
		
		assertTrue(!r5.equals(r6) && !r6.equals(r4));
		assertTrue(!r5.equals(r7) && !r7.equals(r5));
		
		assertTrue(r6.equals(r7) && r7.equals(r6)); // id's are equal to 666 => r6 == r7
		
		assertFalse(r1.equals(null));
		
		System.out.println("test5_equalsTest - V");
		
	}
	
	
	@Test
	public void test6_toStringTest() throws Exception {
//		r1 = new RestaurantImpl(111, "Burgers Of The Seven", 10, menu1);
//		r2 = new RestaurantImpl(222, "Burger's Landing", 5, menu2);
//		r3 = new RestaurantImpl(333, "Burger Garden", 60, menu3);
//		r4 = new RestaurantImpl(444, "House Of Burgers And Fries", 12, menu1);
//		r5 = new RestaurantImpl(555, "The Silent Burgers", 1000, menu1);
//		r6 = new RestaurantImpl(666, "The Many Face Burger", 1000, menu1);
//		r7 = new RestaurantImpl(666, "The Exiled Burgers", 1000, menu1);
		
		String r1String = "Restaurant: Burgers Of The Seven.\n" +
				"Id: 111.\n" +
				"Distance: 10.\n" +
				"Menu: Ale, Dornish Fries, Extra Holy Burger, Holy Burger.";
//		System.out.println(r1String);
//		System.out.println(r1.toString());
		assertEquals(r1String, r1.toString());
		
		String r2String = "Restaurant: Burger's Landing.\n" +
				"Id: 222.\n" +
				"Distance: 5.\n" +
				"Menu: Dornish Fries, Hot Wine, Mutton Burger, Salted Pork.";
		assertEquals(r2String, r2.toString());
		
		String r3String = "Restaurant: Burger Garden.\n" +
				"Id: 333.\n" +
				"Distance: 60.\n" +
				"Menu: Beer, Dornish Fries, Mereneese Lamb Burger.";
		assertEquals(r3String, r3.toString());
		
		String r4String = "Restaurant: House Of Burgers And Fries.\n" +
				"Id: 444.\n" +
				"Distance: 12.\n" +
				"Menu: Ale, Dornish Fries, Extra Holy Burger, Holy Burger.";
		assertEquals(r4String, r4.toString());
		
		String r5String = "Restaurant: The Silent Burgers.\n" +
				"Id: 555.\n" +
				"Distance: 1000.\n" +
				"Menu: Ale, Dornish Fries, Extra Holy Burger, Holy Burger.";
		assertEquals(r5String, r5.toString());
		
		String r6String = "Restaurant: The Many Face Burger.\n" +
				"Id: 666.\n" +
				"Distance: 1000.\n" +
				"Menu: Ale, Dornish Fries, Extra Holy Burger, Holy Burger.";
		assertEquals(r6String, r6.toString());
		
		String r7String = "Restaurant: The Exiled Burgers.\n" +
				"Id: 666.\n" +
				"Distance: 1000.\n" +
				"Menu: Ale, Dornish Fries, Extra Holy Burger, Holy Burger.";
		assertEquals(r7String, r7.toString());
		
		System.out.println("test6_toStringTest - V");
		
	}
	
	@Test
	public void test7_compareToTest() {
		assertTrue(r1.compareTo(r1) == 0
				&& r2.compareTo(r2) == 0
				&& r3.compareTo(r3) == 0
				&& r4.compareTo(r4) == 0
				&& r4.compareTo(r4) == 0
				&& r5.compareTo(r5) == 0
				&& r6.compareTo(r6) == 0
				&& r7.compareTo(r7) == 0);
		
		
		assertTrue(r1.compareTo(r2) < 0 && r2.compareTo(r1) > 0);
		assertTrue(r1.compareTo(r3) < 0 && r3.compareTo(r1) > 0);
		assertTrue(r1.compareTo(r4) < 0 && r4.compareTo(r1) > 0);
		assertTrue(r1.compareTo(r5) < 0 && r5.compareTo(r1) > 0);
		assertTrue(r1.compareTo(r6) < 0 && r6.compareTo(r1) > 0);
		assertTrue(r1.compareTo(r7) < 0 && r7.compareTo(r1) > 0);
		
		
		assertTrue(r5.compareTo(r6) < 0 && r6.compareTo(r5) > 0);
		assertTrue(r5.compareTo(r7) < 0 && r7.compareTo(r5) > 0);
		
		assertTrue(r6.compareTo(r7) == r7.compareTo(r6));
		
		System.out.println("test7_compareToTest - V");
		System.out.println("---SUCCESS, and remember, Burgers are coming---");
		
	}
	
}