package OOP.Tests;

import OOP.Provided.HungryStudent;
import OOP.Provided.HungryStudent.ConnectionAlreadyExistsException;
import OOP.Provided.HungryStudent.SameStudentException;
import OOP.Provided.HungryStudent.UnratedFavoriteRestaurantException;
import OOP.Provided.Restaurant;
import OOP.Provided.Restaurant.RateRangeException;
import OOP.Solution.HungryStudentImpl;
import OOP.Solution.RestaurantImpl;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;


/**
 * Created by Guy on 22/04/2017.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // For ascending order to the tests

public class HungryStudentImplTest {
	private static Restaurant r1, r2, r3, r4, r5, r6, r7, r8;
	private static Set<String> menu1, menu2, menu3, menu4, menuE;
	private static HungryStudent s1, s2, s3, s4, s5, s6;
	
	private static final int MAX_RATING = 5, MIN_RATING = 0;
	private static final int MAX_DIST = Integer.MAX_VALUE, MIN_DIST = 0;
	
	@Before
	public void setUp() {
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
		menu4 = new HashSet<>();
		menu4.add("Dornish Chicken");
		menu4.add("Chicken Ale");
		menu4.add("Salted Chicken");
		menu4.add("Extra Holy Chicken");
		menu4.add("Boiled Chicken");
		menu4.add("Chicken Chicken");
		menu4.add("SPECIAL : Chicken Attack");
		
		menuE = new HashSet<>();
		
		r1 = new RestaurantImpl(111, "Burgers Of The Seven", 10, menu1);
		r2 = new RestaurantImpl(222, "Burger's Landing", 5, menu2);
		r3 = new RestaurantImpl(333, "Burger Garden", 60, menu3);
		r4 = new RestaurantImpl(444, "House Of Burgers And Fries", 12, menu1);
		r5 = new RestaurantImpl(555, "The Silent Burgers", 1000, menu1); // won't be rated
		r6 = new RestaurantImpl(666, "The Many Face Burger", 1000, menu1); // r5 with different id
		r7 = new RestaurantImpl(666, "The Exiled Burgers", 1000, menu1); //  r7 equals to r6 because id's
		r8 = new RestaurantImpl(777, "BFC - Bravosi Fried Chicken", 1000, menu4);
		
		
		s1 = new HungryStudentImpl(11, "Jon");
		s2 = new HungryStudentImpl(22, "Tyrion");
		s3 = new HungryStudentImpl(33, "Daenerys");
		s4 = new HungryStudentImpl(44, "Lyanna");
		s5 = new HungryStudentImpl(44, "Rhaegar"); // s5 equals to s4 because id's
		s6 = new HungryStudentImpl(55, "The Hound");
	}
	
	
	@Test
	public void test1_favoriteTest() throws UnratedFavoriteRestaurantException, RateRangeException {
		try {
			r1.rate(s1, 1);
			r2.rate(s1, 1).rate(s2, 2);
			r3.rate(s1, 1).rate(s2, 2).rate(s3, 3);
			r4.rate(s1, 1).rate(s2, 2).rate(s3, 3).rate(s4, 4);
			
			try {
				s4.favorite(r1);
				fail("UnratedFavoriteRestaurantException should be thrown");
			} catch (UnratedFavoriteRestaurantException e) {
			}
			
			try {
				s4.favorite(r2).favorite(r2);
				fail("UnratedFavoriteRestaurantException should be thrown");
			} catch (UnratedFavoriteRestaurantException e) {
			}
			try {
				s1.favorite(r1).favorite(r3).favorite(r5);
				fail("UnratedFavoriteRestaurantException should be thrown");
			} catch (UnratedFavoriteRestaurantException e) {
			}
			
			try {
				s1.favorite(r5).favorite(r1).favorite(r3);
				fail("UnratedFavoriteRestaurantException should be thrown");
			} catch (UnratedFavoriteRestaurantException e) {
			}
			
		} catch (RateRangeException e) {
			fail("Error in test1_favoriteTest - 'rate' method throws exception");
		}
		
		System.out.println("test1_favoriteTest - V");
	}
	
	@Test
	public void test2_favoritesTest() throws UnratedFavoriteRestaurantException, RateRangeException {
		try {
			r1.rate(s1, 4);
			r2.rate(s1, 4).rate(s2, 3);
			r3.rate(s1, 4).rate(s2, 3).rate(s3, 2);
			r4.rate(s1, 4).rate(s2, 3).rate(s3, 2).rate(s4, 1);
			
			s1.favorite(r1).favorite(r2).favorite(r3).favorite(r4);
			s2.favorite(r2).favorite(r3);
			s3.favorite(r3);
			Collection<Restaurant> cr1, cr2, cr3;
			cr1 = new HashSet<>();
			cr2 = new HashSet<>();
			cr3 = new HashSet<>();
			
			cr1.add(r1);
			cr1.add(r2);
			cr1.add(r3);
			cr1.add(r4);
			cr2.add(r2);
			cr2.add(r3);
			cr3.add(r3);
			
			assertEquals(4, s1.favorites().size());
			assertEquals(2, s2.favorites().size());
			assertEquals(1, s3.favorites().size());
			assertEquals(0, s4.favorites().size());
			assertTrue(s4.favorites().isEmpty());
			
			assertTrue(s1.favorites().containsAll(cr1));
			assertTrue(s2.favorites().containsAll(cr2));
			assertTrue(s3.favorites().containsAll(cr3));
			
			try {
				//Changes in ratings should not affect the "favorites" collection
				r1.rate(s1, 1);
				r2.rate(s1, 1).rate(s2, 2);
				r3.rate(s1, 1).rate(s2, 2).rate(s3, 3);
				r4.rate(s1, 1).rate(s2, 2).rate(s3, 3).rate(s4, 4);
			} catch (RateRangeException e) {
				fail("Error in test2_favoritesTest - 'rate' method throws exception");
			}
			assertEquals(4, s1.favorites().size());
			assertEquals(2, s2.favorites().size());
			assertEquals(1, s3.favorites().size());
			assertEquals(0, s4.favorites().size());
			assertTrue(s4.favorites().isEmpty());
			
			assertTrue(s1.favorites().containsAll(cr1));
			assertTrue(s2.favorites().containsAll(cr2));
			assertTrue(s3.favorites().containsAll(cr3));
			
		} catch (UnratedFavoriteRestaurantException e) {
			fail("Error in test2_favoritesTest - 'favorite' method throws exception");
		}
		
		System.out.println("test2_favoritesTest - V");
		
	}
	
	@Test
	public void test3_addFriendTest() throws SameStudentException, ConnectionAlreadyExistsException {
		s1.addFriend(s2).addFriend(s3).addFriend(s4);
		s2.addFriend(s3).addFriend(s1);
		s4.addFriend(s2);
		
		try {
			s1.addFriend(s1);
			fail("Error in test3_addFriendTest - SameStudentException should be thrown");
		} catch (SameStudentException e) {
		}
		
		try {
			s4.addFriend(s4);
			fail("Error in test3_addFriendTest - SameStudentException should be thrown");
		} catch (SameStudentException e) {
		}
		
		try {
			s2.addFriend(s2).addFriend(s4);
			fail("Error in test3_addFriendTest - ConnectionAlreadyExistsException should be thrown");
		} catch (SameStudentException e) {
		}
		
		try {
			s1.addFriend(s2);
			fail("Error in test3_addFriendTest - ConnectionAlreadyExistsException should be thrown");
		} catch (ConnectionAlreadyExistsException e) {
		}
		
		try {
			s4.addFriend(s2).addFriend(s1).addFriend(s3);
			fail("Error in test3_addFriendTest - ConnectionAlreadyExistsException should be thrown");
		} catch (ConnectionAlreadyExistsException e) {
		}
		
		System.out.println("test3_addFriendTest - V");
	}
	
	@Test
	public void test4_getFriendsTest() throws SameStudentException, ConnectionAlreadyExistsException {
		try {
			s1.addFriend(s2).addFriend(s3).addFriend(s4);
			s2.addFriend(s3).addFriend(s1);
			s4.addFriend(s2);
		} catch (SameStudentException | ConnectionAlreadyExistsException e) {
			fail("Error in test4_getFriendsTest - 'addFriend' throws exception");
		}
		
		Collection<HungryStudent> chs1, chs2, chs4;
		chs1 = new HashSet<>();
		chs2 = new HashSet<>();
		chs4 = new HashSet<>();
		
		chs1.add(s4);
		chs1.add(s2);
		chs1.add(s3);
		chs2.add(s1);
		chs2.add(s3);
		chs4.add(s2);
		
		
		assertEquals(3, s1.getFriends().size());
		assertEquals(2, s2.getFriends().size());
		assertEquals(1, s4.getFriends().size());
		assertEquals(0, s3.getFriends().size());
		assertTrue(s3.getFriends().isEmpty());
		
		assertTrue(s1.getFriends().containsAll(chs1));
		assertTrue(s2.getFriends().containsAll(chs2));
		assertTrue(s4.getFriends().containsAll(chs4));
		
		// Nothing should be changed
		try {
			s4.addFriend(s2);
			fail("Error in test4_getFriendsTest - ConnectionAlreadyExistsException should be thrown");
		} catch (ConnectionAlreadyExistsException e) {
		}
		assertEquals(1, s4.getFriends().size());
		assertTrue(s4.getFriends().containsAll(chs4));
		
		
		System.out.println("test4_getFriendsTest - V");
	}
	
	@Test
	public void test5_favoritesByRatingTest() throws RateRangeException , UnratedFavoriteRestaurantException {
//		r1 = new RestaurantImpl(111, "Burgers Of The Seven", 10, menu1);
//		r2 = new RestaurantImpl(222, "Burger's Landing", 5, menu2);
//		r3 = new RestaurantImpl(333, "Burger Garden", 60, menu3);
//		r4 = new RestaurantImpl(444, "House Of Burgers And Fries", 12, menu1);
//		r5 = new RestaurantImpl(555, "The Silent Burgers", 1000, menu1); // won't be rated
//		r6 = new RestaurantImpl(666, "The Many Face Burger", 1000, menu1); // r5 with different id
//		r7 = new RestaurantImpl(666, "The Exiled Burgers", 1000, menu1); //  r7 equals to r6 because id's
//		r8 = new RestaurantImpl(777, "BFC - Bravosi Fried Chicken", 1000, menu4);
		
		try {
			r1.rate(s1, 5).rate(s6, 1); // avg = 3
			r2.rate(s1, 2).rate(s2, 3).rate(s6, 1); // avg = 2
			r3.rate(s1, 3).rate(s2, 3).rate(s3, 3).rate(s6, 1); // avg = 2.5
			r4.rate(s1, 3).rate(s2, 1).rate(s3, 1).rate(s4, 4).rate(s6, 1); // avg = 2
			r5.rate(s1, 3).rate(s2, 1).rate(s3, 1).rate(s6, 3); // avg = 2
			r6.rate(s6, 2); // avg = 2
			r8.rate(s6, 5); // avg = 5, bring me one of those chickens
		}
		catch(RateRangeException e){
			fail("Error in test5_favoritesByRatingTest - 'rate' methods throws exception");
		}
		try {
			s1.favorite(r1).favorite(r2).favorite(r4);
			s2.favorite(r2).favorite(r3);
			s3.favorite(r3);
			s6.favorite(r1).favorite(r2).favorite(r3).favorite(r4).favorite(r5).favorite(r6).favorite(r8);
		}
		catch(UnratedFavoriteRestaurantException e){
			fail("Error in test5_favoritesByRatingTest - 'favorite' methods throws exception");
		}
		
		Restaurant[] cr1, cr6, cr2, cr11, cr66, cr666, cr6666;
		cr1 = new Restaurant[]{r1, r2, r4};
		cr2 = new Restaurant[]{};
		cr6 = new Restaurant[]{r8, r1, r3, r2, r4, r5, r6};
		cr11 = new Restaurant[]{r1};
		cr66 = new Restaurant[]{r8}; // only chickens
		cr666 = new Restaurant[]{r8, r6, r1, r3, r2, r4, r5};
		cr6666 = new Restaurant[]{r8, r6};
		
		assertEquals(0, s1.favoritesByRating(MAX_RATING + 1).size());
		assertEquals(3, s1.favoritesByRating(MIN_RATING).size());
		assertEquals(0, s4.favoritesByRating(MAX_RATING + 1).size());
		assertEquals(0, s4.favoritesByRating(MIN_RATING).size());
		
		assertArrayEquals(cr1, s1.favoritesByRating(MIN_RATING).toArray());
		assertArrayEquals(cr6, s6.favoritesByRating(MIN_RATING).toArray());
		
		assertArrayEquals(cr2, s2.favoritesByRating(3).toArray());
		assertArrayEquals(cr11, s1.favoritesByRating(3).toArray());
		assertArrayEquals(cr66, s6.favoritesByRating(MAX_RATING).toArray());
		
		r6.rate(s6, 4); // The hound starts loving r6
		assertArrayEquals(cr666, s6.favoritesByRating(MIN_RATING).toArray()); // It is known
		assertArrayEquals(cr6666, s6.favoritesByRating(4).toArray()); // It is known
		
		
		System.out.println("test5_favoritesByRatingTest - V");
	}
	
	@Test
	public void test6_favoritesByDistTest() throws RateRangeException , UnratedFavoriteRestaurantException {
		//		r1 = new RestaurantImpl(111, "Burgers Of The Seven", 10, menu1);
		//		r2 = new RestaurantImpl(222, "Burger's Landing", 5, menu2);
		//		r3 = new RestaurantImpl(333, "Burger Garden", 60, menu3);
		//		r4 = new RestaurantImpl(444, "House Of Burgers And Fries", 12, menu1);
		//		r5 = new RestaurantImpl(555, "The Silent Burgers", 1000, menu1); // won't be rated
		//		r6 = new RestaurantImpl(666, "The Many Face Burger", 1000, menu1); // r5 with different id
		//		r7 = new RestaurantImpl(666, "The Exiled Burgers", 1000, menu1); //  r7 equals to r6 because id's
		//		r8 = new RestaurantImpl(777, "BFC - Bravosi Fried Chicken", 1000, menu4);
		try {
			r1.rate(s1, 5).rate(s6, 1); // avg = 3
			r2.rate(s1, 2).rate(s2, 3).rate(s6, 1); // avg = 2
			r3.rate(s1, 3).rate(s2, 3).rate(s3, 3).rate(s6, 1); // avg = 2.5
			r4.rate(s1, 3).rate(s2, 1).rate(s3, 1).rate(s4, 4).rate(s6, 1); // avg = 2
			r5.rate(s1, 3).rate(s2, 1).rate(s3, 1).rate(s6, 3); // avg = 2
			r6.rate(s6, 2); // avg = 2
			r8.rate(s6, 5); // avg = 5, bring me one of those chickens
		}
		catch(RateRangeException e){
			fail("Error in test6_favoritesByDistTest - 'rate' methods throws exception");
		}
		
		try {
			s1.favorite(r1).favorite(r2).favorite(r4);
			s2.favorite(r2).favorite(r3);
			s3.favorite(r3);
			s6.favorite(r1).favorite(r2).favorite(r3).favorite(r4).favorite(r5).favorite(r6).favorite(r8);
		}
		catch(UnratedFavoriteRestaurantException e){
			fail("Error in test6_favoritesByDistTest - 'favorite' methods throws exception");
		}
		
		Restaurant[] cr1, cr6, cr2, cr11, cr66, cr666;
		cr1 = new Restaurant[]{r2, r1, r4};
		cr2 = new Restaurant[]{};
		cr6 = new Restaurant[]{r2, r1, r4, r3, r8, r5, r6};
		cr11 = new Restaurant[]{r2};
		cr66 = new Restaurant[]{r2, r1, r4, r3}; // only chickens
		cr666 = new Restaurant[]{r2, r1, r4, r3, r8, r6, r5};
		
		assertEquals(0, s1.favoritesByDist(MIN_DIST - 1).size());
		assertEquals(3, s1.favoritesByDist(MAX_DIST).size());
		assertEquals(0, s4.favoritesByDist(MIN_DIST - 1).size());
		assertEquals(0, s4.favoritesByDist(MAX_DIST).size());
		
		assertArrayEquals(cr1, s1.favoritesByDist(MAX_DIST).toArray());
		assertArrayEquals(cr6, s6.favoritesByDist(MAX_DIST).toArray());
		
		assertArrayEquals(cr2, s2.favoritesByDist(4).toArray()); // distances = [5,60]
		assertArrayEquals(cr11, s1.favoritesByDist(5).toArray());
		assertArrayEquals(cr66, s6.favoritesByDist(999).toArray());
		
		r6.rate(s6, 4); // The hound starts loving r6
		assertArrayEquals(cr666, s6.favoritesByDist(MAX_DIST).toArray()); // It is known
		
		
		System.out.println("test6_favoritesByDistTest - V");
	}
	
	@Test
	public void test7_equalsTest() throws Exception {
		assertTrue(s1.equals(s1) && s2.equals(s2) && s3.equals(s3) && s4.equals(s4) && s5.equals(s5));
		
		assertTrue(!s1.equals(s2) && !s2.equals(s1));
		assertTrue(!s1.equals(s3) && !s3.equals(s1));
		assertTrue(!s1.equals(s4) && !s4.equals(s1));
		assertTrue(!s1.equals(s5) && !s5.equals(s1));
		
		assertTrue(!s3.equals(s2) && !s2.equals(34));
		assertTrue(!s3.equals(r5) && !r5.equals(r3));
		
		assertTrue(s4.equals(s5) && s5.equals(s4)); // id's are equal to 44 => s4 == s5
		
		assertFalse(s1.equals(null));
		
		System.out.println("test7_equalsTest - V");
	}
	
	
	@Test
	public void test8_compareToTest() throws Exception {
		assertTrue(s1.compareTo(s1) == 0
				&& s2.compareTo(s2) == 0
				&& s3.compareTo(s3) == 0
				&& s4.compareTo(s4) == 0
				&& s5.compareTo(s5) == 0);
		
		
		assertTrue(s1.compareTo(s2) < 0 && s2.compareTo(s1) > 0);
		assertTrue(s1.compareTo(s3) < 0 && s3.compareTo(s1) > 0);
		assertTrue(s1.compareTo(s4) < 0 && s4.compareTo(s1) > 0);
		assertTrue(s1.compareTo(s5) < 0 && s5.compareTo(s1) > 0);
		
		
		assertTrue(s3.compareTo(s2) > 0 && s2.compareTo(s3) < 0);
		assertTrue(s3.compareTo(s4) < 0 && s4.compareTo(s3) > 0);
		assertTrue(s3.compareTo(s5) < 0 && s5.compareTo(s3) > 0);
		
		assertTrue(s4.compareTo(s5) == s5.compareTo(s4));
		
		System.out.println("test8_compareToTest - V");
	}
	
	@Test
	public void test9_toStringTest() throws Exception {
//		s1 = new HungryStudentImpl(11, "Jon");
//		s2 = new HungryStudentImpl(22, "Tyrion");
//		s3 = new HungryStudentImpl(33, "Daenerys");
//		s4 = new HungryStudentImpl(44, "Lyanna");
//		s5 = new HungryStudentImpl(44, "Rhaegar");
//		s6 = new HungryStudentImpl(55, "The Hound");
		
		r1.rate(s1, 5).rate(s6, 1); // avg = 3
		r2.rate(s1, 2).rate(s2, 3).rate(s6, 1); // avg = 2
		r3.rate(s1, 3).rate(s2, 3).rate(s3, 3).rate(s6, 1); // avg = 2.5
		r4.rate(s1, 3).rate(s2, 1).rate(s3, 1).rate(s4, 4).rate(s6, 1); // avg = 2
		r5.rate(s1, 3).rate(s2, 1).rate(s3, 1).rate(s6, 3); // avg = 2
		r6.rate(s6, 2); // avg = 2
		r8.rate(s6, 5); // avg = 5, bring me one of those chickens
		
		s1.favorite(r1).favorite(r2).favorite(r3).favorite(r4);
		s2.favorite(r2).favorite(r3);
		s3.favorite(r3);
		s6.favorite(r1).favorite(r2).favorite(r3).favorite(r4).favorite(r5).favorite(r6).favorite(r8);
		
		String s1String = "Hungry student: Jon.\n" +
				"Id: 11.\n" +
				"Favorites: Burger Garden, Burger's Landing, Burgers Of The Seven, House Of Burgers And Fries.";
		assertEquals(s1String, s1.toString());
		
		String s2String = "Hungry student: Tyrion.\n" +
				"Id: 22.\n" +
				"Favorites: Burger Garden, Burger's Landing.";
		assertEquals(s2String, s2.toString());
		
		String s3String = "Hungry student: Daenerys.\n" +
				"Id: 33.\n" +
				"Favorites: Burger Garden.";
		assertEquals(s3String, s3.toString());
		
		String s4String = "Hungry student: Lyanna.\n" +
				"Id: 44.\n" +
				"Favorites: .";
		assertEquals(s4String, s4.toString());
		
		String s5String = "Hungry student: Rhaegar.\n" +
				"Id: 44.\n" +
				"Favorites: .";
		assertEquals(s5String, s5.toString());
		
		String s6String = "Hungry student: The Hound.\n" +
				"Id: 55.\n" +
				"Favorites: BFC - Bravosi Fried Chicken, Burger Garden, Burger's Landing, Burgers Of The Seven, House Of Burgers And Fries, The Many Face Burger, The Silent Burgers.";
		assertEquals(s6String, s6.toString());
		
		System.out.println("test9_toStringTest - V");
		System.out.println("---SUCCESS, and remember, Burgers are coming---");
	}
	
}