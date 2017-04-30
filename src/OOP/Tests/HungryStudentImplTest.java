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
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;
import static org.testng.AssertJUnit.*;

/**
 * Created by danie_000 on 4/20/2017.
 */
public class HungryStudentImplTest {
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
    public void favorite() throws Exception {
        try {
            s1.favorite(r1);
            fail("Should be an unrated exception");
        } catch (UnratedFavoriteRestaurantException e) {

        }
        try {
            r1.rate(s1, 1).rate(s2, 2).rate(s3, 3).rate(s4, 4).rate(s5, 5);
            s1.favorite(r1);
        } catch (RateRangeException | UnratedFavoriteRestaurantException e) {
            fail("Rates are legal and no case of favorite(r) where r is not rated by student, weird....");
        }
        System.out.println("HungryStudent::favorite TEST SUCCESSFUL :)");
    }

    @Test
    public void favorites() throws Exception {
        assertTrue(s3.favorites().isEmpty());
        assertTrue(s4.favorites().isEmpty());
        assertTrue(s5.favorites().isEmpty());
        try {
            r2.rate(s1, 3).rate(s2, 2).rate(s3, 3).rate(s4, 3);
            assertTrue(s2.favorites().isEmpty());
            assertFalse(s2.favorites().contains(r3));
            assertFalse(s2.favorites().contains(r5));
            assertFalse(s2.favorites().contains(r4));
            s2.favorite(r2);
            assertTrue(s2.favorites().size() == 1);
            assertTrue(s2.favorites().contains(r2));
            s2.favorite(r2);
            assertTrue(s2.favorites().size() == 1);
            assertTrue(s2.favorites().contains(r2));
            r3.rate(s2, 4);
            s2.favorite(r3);
            assertTrue(s2.favorites().size() == 2);
            assertTrue(s2.favorites().contains(r3));


        } catch (RateRangeException | UnratedFavoriteRestaurantException e) {
            fail("Rates are legal and no case of favorite(r) where r is not rated by student, weird....");
        }
        System.out.println("HungryStudent::favorites TEST SUCCESSFUL :)");

    }

    @Test
    public void addFriend() throws Exception {
        try {
            s1.addFriend(s1);
            fail("Should be same student, forever alone friend-zone!! :(");
        } catch (SameStudentException e) {

        }
        s1.addFriend(s2);
        try {
            s2.addFriend(s1);
        } catch (ConnectionAlreadyExistsException e) {
            fail("Friendship in part A is not necessarily mutual");
        }
        try {
            s1.addFriend(s2);
            fail("Jack already has a friend :)");
        } catch (ConnectionAlreadyExistsException e) {
        }
        System.out.println("HungryStudent::addFriend TEST SUCCESSFUL :)");

    }

    @Test
    public void getFriends() throws Exception {
        assertTrue(s3.getFriends().isEmpty());
        assertFalse(s1.getFriends().contains(s1));
        assertFalse(s2.getFriends().contains(s2));
        assertFalse(s3.getFriends().contains(s3));
        assertFalse(s4.getFriends().contains(s4));
        assertFalse(s5.getFriends().contains(s5));


        try {
            s1.addFriend(s2);
            // To make sure s1 and s2 are friends
        } catch (ConnectionAlreadyExistsException e) {

        }
        assertEquals(1, s1.getFriends().size());
        assertTrue(s1.getFriends().contains(s2));
        assertFalse(s2.getFriends().contains(s1));
        assertTrue(s2.getFriends().isEmpty());
        try {
            s1.addFriend(s3).addFriend(s4).addFriend(s5);
        } catch (ConnectionAlreadyExistsException e) {

        }
        assertEquals(4, s1.getFriends().size());
        assertTrue(s2.getFriends().isEmpty());
        assertFalse(s3.getFriends().contains(s1));
        assertTrue(s1.getFriends().contains(s3));
        assertFalse(s4.getFriends().contains(s1));
        assertTrue(s1.getFriends().contains(s4));
        assertFalse(s5.getFriends().contains(s1));
        assertTrue(s1.getFriends().contains(s5));
        assertFalse(s3.getFriends().contains(s5));
        System.out.println("HungryStudent::getFriends TEST SUCCESSFUL :)");

    }

    @Test
    public void favoritesByRating() throws Exception {
        //        s1 = new OOP.Solution.HungryStudentImpl(117, "John");
        //        s2 = new OOP.Solution.HungryStudentImpl(7, "James");
        //        s3 = new OOP.Solution.HungryStudentImpl(23, "Jack");
        //        s4 = new OOP.Solution.HungryStudentImpl(4, "Locke");
        //        s5 = new OOP.Solution.HungryStudentImpl(51, "Kate");
        //        r1 = new OOP.Solution.RestaurantImpl(4, "BBB", 5, menu1);
        //        r2 = new OOP.Solution.RestaurantImpl(3, "PizzaHut", 8, menu2);
        //        r3 = new OOP.Solution.RestaurantImpl(10, "IceCreamStore", 4, menu3);
        //        r4 = new OOP.Solution.RestaurantImpl(7, "EmptyStore", 10, menu4);
        //        r5 = new OOP.Solution.RestaurantImpl(2, "DominoPizza", 20, menu2);
        try {
            r1.rate(s1, 5).rate(s3, 3).rate(s5, 5);
            r2.rate(s1, 2).rate(s2, 0).rate(s4, 5).rate(s5, 5);
            r3.rate(s1, 2).rate(s2, 3).rate(s4, 5);
            r4.rate(s1, 1).rate(s5, 1).rate(s4, 1);
            r5.rate(s1, 1).rate(s3, 3).rate(s5, 4);
            s1.favorite(r1).favorite(r2).favorite(r3).favorite(r4).favorite(r5);
            s2.favorite(r2).favorite(r3);
            s3.favorite(r1).favorite(r5);
            s4.favorite(r2).favorite(r4);
            s5.favorite(r1).favorite(r2).favorite(r4).favorite(r5);

        } catch (RateRangeException | UnratedFavoriteRestaurantException e) {
            fail("All cool! weird....");
        }
      //  System.out.println(r1.averageRating()+"\n");
      //  System.out.println(r2.averageRating()+"\n");
      //  System.out.println(r3.averageRating()+"\n");
      //  System.out.println(r4.averageRating()+"\n");
      //  System.out.println(r5.averageRating()+"\n");

        List<Restaurant> list1;
        List<Restaurant> list2;
        List<Restaurant> list3;
        List<Restaurant> list4;
        List<Restaurant> list5;

//        s1.favorite(r1).favorite(r2).favorite(r3).favorite(r4).favorite(r5);
//        s2.favorite(r2).favorite(r3);
//        s3.favorite(r1).favorite(r5);
//        s4.favorite(r2).favorite(r4);
//        s5.favorite(r1).favorite(r2).favorite(r4).favorite(r5);

        list1 = s1.favoritesByRating(0).stream().collect(Collectors.toList());
        list2 = s2.favoritesByRating(0).stream().collect(Collectors.toList());
        list3 = s3.favoritesByRating(0).stream().collect(Collectors.toList());
        list4 = s4.favoritesByRating(0).stream().collect(Collectors.toList());
        list5 = s5.favoritesByRating(0).stream().collect(Collectors.toList());

        assertEquals(5, list1.size());
        assertEquals(2, list2.size());
        assertEquals(2, list3.size());
        assertEquals(2, list4.size());
        assertEquals(4, list5.size());

        assertTrue(list1.contains(r1));
        assertTrue(list1.contains(r2));
        assertTrue(list1.contains(r3));
        assertTrue(list1.contains(r4));
        assertTrue(list1.contains(r5));
        assertTrue(list2.contains(r2));
        assertTrue(list2.contains(r3));
        assertTrue(list3.contains(r1));
        assertTrue(list3.contains(r5));
        assertTrue(list3.contains(r5));
        assertTrue(list4.contains(r2));
        assertTrue(list4.contains(r4));
        assertTrue(list5.contains(r1));
        assertTrue(list5.contains(r2));
        assertTrue(list5.contains(r4));
        assertTrue(list5.contains(r5));

        assertEquals(r1, list1.get(0));
        assertEquals(r3, list1.get(1));
        assertEquals(r2, list1.get(2));
        assertEquals(r5, list1.get(3));
        assertEquals(r4, list1.get(4));
        assertEquals(r3, list2.get(0));
        assertEquals(r2, list2.get(1));
        assertEquals(r1, list3.get(0));
        assertEquals(r5, list3.get(1));
        assertEquals(r2, list4.get(0));
        assertEquals(r4, list4.get(1));
        assertEquals(r1, list5.get(0));
        assertEquals(r2, list5.get(1));
        assertEquals(r5, list5.get(2));
        assertEquals(r4, list5.get(3));

        list1 = s1.favoritesByRating(2).stream().collect(Collectors.toList());
        list2 = s2.favoritesByRating(2).stream().collect(Collectors.toList());
        list3 = s3.favoritesByRating(2).stream().collect(Collectors.toList());
        list4 = s4.favoritesByRating(2).stream().collect(Collectors.toList());
        list5 = s5.favoritesByRating(2).stream().collect(Collectors.toList());

        assertEquals(4, list1.size());
        assertEquals(2, list2.size());
        assertEquals(2, list3.size());
        assertEquals(1, list4.size());
        assertEquals(3, list5.size());

        assertFalse(list1.contains(r4));
        assertFalse(list4.contains(r4));
        assertFalse(list5.contains(r4));

        assertEquals(r1, list1.get(0));
        assertEquals(r3, list1.get(1));
        assertEquals(r2, list1.get(2));
        assertEquals(r5, list1.get(3));
        assertEquals(r3, list2.get(0));
        assertEquals(r2, list2.get(1));
        assertEquals(r1, list3.get(0));
        assertEquals(r5, list3.get(1));
        assertEquals(r2, list4.get(0));
        assertEquals(r1, list5.get(0));
        assertEquals(r2, list5.get(1));
        assertEquals(r5, list5.get(2));

        list1 = s1.favoritesByRating(3).stream().collect(Collectors.toList());
        list2 = s2.favoritesByRating(3).stream().collect(Collectors.toList());
        list3 = s3.favoritesByRating(3).stream().collect(Collectors.toList());
        list4 = s4.favoritesByRating(3).stream().collect(Collectors.toList());
        list5 = s5.favoritesByRating(3).stream().collect(Collectors.toList());

        assertEquals(3, list1.size());
        assertEquals(2, list2.size());
        assertEquals(1, list3.size());
        assertEquals(1, list4.size());
        assertEquals(2, list5.size());

        assertFalse(list1.contains(r5));
        assertFalse(list3.contains(r5));
        assertFalse(list5.contains(r5));

        assertEquals(r1, list1.get(0));
        assertEquals(r3, list1.get(1));
        assertEquals(r2, list1.get(2));
        assertEquals(r3, list2.get(0));
        assertEquals(r2, list2.get(1));
        assertEquals(r1, list3.get(0));
        assertEquals(r2, list4.get(0));
        assertEquals(r1, list5.get(0));
        assertEquals(r2, list5.get(1));

        list1 = s1.favoritesByRating(4).stream().collect(Collectors.toList());
        list2 = s2.favoritesByRating(4).stream().collect(Collectors.toList());
        list3 = s3.favoritesByRating(4).stream().collect(Collectors.toList());
        list4 = s4.favoritesByRating(4).stream().collect(Collectors.toList());
        list5 = s5.favoritesByRating(4).stream().collect(Collectors.toList());

        assertEquals(1, list1.size());
        assertEquals(0, list2.size());
        assertEquals(1, list3.size());
        assertEquals(0, list4.size());
        assertEquals(1, list5.size());

        assertFalse(list1.contains(r2));
        assertFalse(list2.contains(r2));
        assertFalse(list4.contains(r2));
        assertFalse(list5.contains(r2));
        assertFalse(list1.contains(r3));
        assertFalse(list2.contains(r3));


        assertEquals(r1, list1.get(0));
        assertEquals(r1, list3.get(0));
        assertEquals(r1, list5.get(0));

        list1 = s1.favoritesByRating(5).stream().collect(Collectors.toList());
        list2 = s2.favoritesByRating(5).stream().collect(Collectors.toList());
        list3 = s3.favoritesByRating(5).stream().collect(Collectors.toList());
        list4 = s4.favoritesByRating(5).stream().collect(Collectors.toList());
        list5 = s5.favoritesByRating(5).stream().collect(Collectors.toList());

        assertEquals(0, list1.size());
        assertEquals(0, list2.size());
        assertEquals(0, list3.size());
        assertEquals(0, list4.size());
        assertEquals(0, list5.size());

        System.out.println("HungryStudent::favoritesByRating TEST SUCCESSFUL :)");

    }

    @Test
    public void favoritesByDist() throws Exception {
        //        s1 = new OOP.Solution.HungryStudentImpl(117, "John");
        //        s2 = new OOP.Solution.HungryStudentImpl(7, "James");
        //        s3 = new OOP.Solution.HungryStudentImpl(23, "Jack");
        //        s4 = new OOP.Solution.HungryStudentImpl(4, "Locke");
        //        s5 = new OOP.Solution.HungryStudentImpl(51, "Kate");
        //        r1 = new OOP.Solution.RestaurantImpl(4, "BBB", 5, menu1);
        //        r2 = new OOP.Solution.RestaurantImpl(3, "PizzaHut", 8, menu2);
        //        r3 = new OOP.Solution.RestaurantImpl(10, "IceCreamStore", 4, menu3);
        //        r4 = new OOP.Solution.RestaurantImpl(7, "EmptyStore", 10, menu4);
        //        r5 = new OOP.Solution.RestaurantImpl(2, "DominoPizza", 20, menu2);
        try {
            r1.rate(s1, 5).rate(s3, 3).rate(s5, 5);
            r2.rate(s1, 2).rate(s2, 0).rate(s4, 5).rate(s5, 5);
            r3.rate(s1, 2).rate(s2, 3).rate(s4, 5);
            r4.rate(s1, 1).rate(s5, 1).rate(s4, 1);
            r5.rate(s1, 1).rate(s3, 3).rate(s5, 4);
            s1.favorite(r1).favorite(r2).favorite(r3).favorite(r4).favorite(r5);
            s2.favorite(r2).favorite(r3);
            s3.favorite(r1).favorite(r5);
            s4.favorite(r2).favorite(r4);
            s5.favorite(r1).favorite(r2).favorite(r4).favorite(r5);

        } catch (RateRangeException | UnratedFavoriteRestaurantException e) {
            fail("All cool! weird....");
        }
//        System.out.println(r1.distance()+"\n");
//        System.out.println(r2.distance()+"\n");
//        System.out.println(r3.distance()+"\n");
//        System.out.println(r4.distance()+"\n");
//        System.out.println(r5.distance()+"\n");

        List<Restaurant> list1;
        List<Restaurant> list2;
        List<Restaurant> list3;
        List<Restaurant> list4;
        List<Restaurant> list5;

//        s1.favorite(r1).favorite(r2).favorite(r3).favorite(r4).favorite(r5);
//        s2.favorite(r2).favorite(r3);
//        s3.favorite(r1).favorite(r5);
//        s4.favorite(r2).favorite(r4);
//        s5.favorite(r1).favorite(r2).favorite(r4).favorite(r5);

        list1 = s1.favoritesByDist(20).stream().collect(Collectors.toList());
        list2 = s2.favoritesByDist(20).stream().collect(Collectors.toList());
        list3 = s3.favoritesByDist(20).stream().collect(Collectors.toList());
        list4 = s4.favoritesByDist(20).stream().collect(Collectors.toList());
        list5 = s5.favoritesByDist(20).stream().collect(Collectors.toList());

        assertEquals(5, list1.size());
        assertEquals(2, list2.size());
        assertEquals(2, list3.size());
        assertEquals(2, list4.size());
        assertEquals(4, list5.size());

        assertTrue(list1.contains(r1));
        assertTrue(list1.contains(r2));
        assertTrue(list1.contains(r3));
        assertTrue(list1.contains(r4));
        assertTrue(list1.contains(r5));
        assertTrue(list2.contains(r2));
        assertTrue(list2.contains(r3));
        assertTrue(list3.contains(r1));
        assertTrue(list3.contains(r5));
        assertTrue(list3.contains(r5));
        assertTrue(list4.contains(r2));
        assertTrue(list4.contains(r4));
        assertTrue(list5.contains(r1));
        assertTrue(list5.contains(r2));
        assertTrue(list5.contains(r4));
        assertTrue(list5.contains(r5));

        assertEquals(r3, list1.get(0));
        assertEquals(r1, list1.get(1));
        assertEquals(r2, list1.get(2));
        assertEquals(r4, list1.get(3));
        assertEquals(r5, list1.get(4));
        assertEquals(r3, list2.get(0));
        assertEquals(r2, list2.get(1));
        assertEquals(r1, list3.get(0));
        assertEquals(r5, list3.get(1));
        assertEquals(r2, list4.get(0));
        assertEquals(r4, list4.get(1));
        assertEquals(r1, list5.get(0));
        assertEquals(r2, list5.get(1));
        assertEquals(r4, list5.get(2));
        assertEquals(r5, list5.get(3));

        list1 = s1.favoritesByDist(11).stream().collect(Collectors.toList());
        list2 = s2.favoritesByDist(11).stream().collect(Collectors.toList());
        list3 = s3.favoritesByDist(11).stream().collect(Collectors.toList());
        list4 = s4.favoritesByDist(11).stream().collect(Collectors.toList());
        list5 = s5.favoritesByDist(11).stream().collect(Collectors.toList());

        assertEquals(4, list1.size());
        assertEquals(2, list2.size());
        assertEquals(1, list3.size());
        assertEquals(2, list4.size());
        assertEquals(3, list5.size());

        assertFalse(list1.contains(r5));
        assertFalse(list3.contains(r5));
        assertFalse(list5.contains(r5));

        assertEquals(r3, list1.get(0));
        assertEquals(r1, list1.get(1));
        assertEquals(r2, list1.get(2));
        assertEquals(r4, list1.get(3));
        assertEquals(r3, list2.get(0));
        assertEquals(r2, list2.get(1));
        assertEquals(r1, list3.get(0));
        assertEquals(r2, list4.get(0));
        assertEquals(r4, list4.get(1));
        assertEquals(r1, list5.get(0));
        assertEquals(r2, list5.get(1));
        assertEquals(r4, list5.get(2));

        list1 = s1.favoritesByDist(8).stream().collect(Collectors.toList());
        list2 = s2.favoritesByDist(8).stream().collect(Collectors.toList());
        list3 = s3.favoritesByDist(8).stream().collect(Collectors.toList());
        list4 = s4.favoritesByDist(8).stream().collect(Collectors.toList());
        list5 = s5.favoritesByDist(8).stream().collect(Collectors.toList());

        assertEquals(3, list1.size());
        assertEquals(2, list2.size());
        assertEquals(1, list3.size());
        assertEquals(1, list4.size());
        assertEquals(2, list5.size());

        assertFalse(list1.contains(r4));
        assertFalse(list4.contains(r4));
        assertFalse(list5.contains(r4));

        assertEquals(r3, list1.get(0));
        assertEquals(r1, list1.get(1));
        assertEquals(r2, list1.get(2));
        assertEquals(r3, list2.get(0));
        assertEquals(r2, list2.get(1));
        assertEquals(r1, list3.get(0));
        assertEquals(r2, list4.get(0));
        assertEquals(r1, list5.get(0));
        assertEquals(r2, list5.get(1));

        list1 = s1.favoritesByDist(4).stream().collect(Collectors.toList());
        list2 = s2.favoritesByDist(4).stream().collect(Collectors.toList());
        list3 = s3.favoritesByDist(4).stream().collect(Collectors.toList());
        list4 = s4.favoritesByDist(4).stream().collect(Collectors.toList());
        list5 = s5.favoritesByDist(4).stream().collect(Collectors.toList());

        assertEquals(1, list1.size());
        assertEquals(1, list2.size());
        assertEquals(0, list3.size());
        assertEquals(0, list4.size());
        assertEquals(0, list5.size());

        assertFalse(list1.contains(r1));
        assertFalse(list3.contains(r1));
        assertFalse(list5.contains(r1));
        assertFalse(list1.contains(r2));
        assertFalse(list2.contains(r2));
        assertFalse(list4.contains(r2));
        assertFalse(list5.contains(r2));

        assertEquals(r3, list1.get(0));
        assertEquals(r3, list2.get(0));


        list1 = s1.favoritesByDist(3).stream().collect(Collectors.toList());
        list2 = s2.favoritesByDist(3).stream().collect(Collectors.toList());
        list3 = s3.favoritesByDist(3).stream().collect(Collectors.toList());
        list4 = s4.favoritesByDist(3).stream().collect(Collectors.toList());
        list5 = s5.favoritesByDist(3).stream().collect(Collectors.toList());

        assertEquals(0, list1.size());
        assertEquals(0, list2.size());
        assertEquals(0, list3.size());
        assertEquals(0, list4.size());
        assertEquals(0, list5.size());
        System.out.println("HungryStudent::favoritesByDist TEST SUCCESSFUL :)");

    }

    @Test
    public void equals() {
        /*
         * Id is the same, however can a student become a restaurant one day? ^^
         */
        assertFalse(s4.equals(r1));

        assertTrue(s1.equals(s1));
        assertTrue(s2.equals(s2));
        assertTrue(s3.equals(s3));
        assertTrue(s4.equals(s4));
        assertTrue(s5.equals(s5));

        assertFalse(s1.equals(s2));
        assertFalse(s2.equals(s1));
        assertFalse(s1.equals(s3));
        assertFalse(s3.equals(s1));
        assertFalse(s1.equals(s4));
        assertFalse(s4.equals(s1));
        assertFalse(s1.equals(s5));
        assertFalse(s5.equals(s1));
        assertFalse(s2.equals(s3));
        assertFalse(s3.equals(s2));
        assertFalse(s2.equals(s4));
        assertFalse(s4.equals(s2));
        assertFalse(s2.equals(s5));
        assertFalse(s5.equals(s2));
        assertFalse(s3.equals(s4));
        assertFalse(s4.equals(s3));
        assertFalse(s3.equals(s5));
        assertFalse(s5.equals(s3));
        assertFalse(s4.equals(s5));
        assertFalse(s5.equals(s4));

        System.out.println("HungryStudent::equals TEST SUCCESSFUL :)");
    }

    @Test
    public void to_string() {
        //        s1 = new OOP.Solution.HungryStudentImpl(117, "John");
        //        s2 = new OOP.Solution.HungryStudentImpl(7, "James");
        //        s3 = new OOP.Solution.HungryStudentImpl(23, "Jack");
        //        s4 = new OOP.Solution.HungryStudentImpl(4, "Locke");
        //        s5 = new OOP.Solution.HungryStudentImpl(51, "Kate");
        //        r1 = new OOP.Solution.RestaurantImpl(4, "BBB", 5, menu1);
        //        r2 = new OOP.Solution.RestaurantImpl(3, "PizzaHut", 8, menu2);
        //        r3 = new OOP.Solution.RestaurantImpl(10, "IceCreamStore", 4, menu3);
        //        r4 = new OOP.Solution.RestaurantImpl(7, "EmptyStore", 10, menu4);
        //        r5 = new OOP.Solution.RestaurantImpl(2, "DominoPizza", 20, menu2);
        try {
            r1.rate(s1, 1).rate(s3, 3).rate(s5, 5);
            r2.rate(s1, 2).rate(s2, 0).rate(s4, 5).rate(s5, 5);
            r3.rate(s1, 2).rate(s2, 3).rate(s4, 5);
            r4.rate(s1, 2).rate(s5, 3);
            r5.rate(s1, 1).rate(s3, 3).rate(s5, 4);
            s1.favorite(r1).favorite(r2).favorite(r3).favorite(r4).favorite(r5);
            s2.favorite(r2).favorite(r3);
            s3.favorite(r1).favorite(r5);
            s4.favorite(r2).favorite(r3);
            s5.favorite(r1).favorite(r2).favorite(r4).favorite(r5);

        } catch (RateRangeException | UnratedFavoriteRestaurantException e) {
            fail("All cool! weird....");
        }
        assertEquals("Hungry student: John.\n" + "Id: 117.\n" + "Favorites: BBB, DominoPizza, EmptyStore, IceCreamStore, PizzaHut.\n", s1.toString());
        assertEquals("Hungry student: James.\n" + "Id: 7.\n" + "Favorites: IceCreamStore, PizzaHut.\n", s2.toString());
        assertEquals("Hungry student: Jack.\n" + "Id: 23.\n" + "Favorites: BBB, DominoPizza.\n", s3.toString());
        assertEquals("Hungry student: Locke.\n" + "Id: 4.\n" + "Favorites: IceCreamStore, PizzaHut.\n", s4.toString());
        assertEquals("Hungry student: Kate.\n" + "Id: 51.\n" + "Favorites: BBB, DominoPizza, EmptyStore, PizzaHut.\n", s5.toString());

        System.out.println("HungryStudent::toString TEST SUCCESSFUL :)");

    }

    @Test
    public void compareTo() {
        //        s1 = new OOP.Solution.HungryStudentImpl(117, "John");
        //        s2 = new OOP.Solution.HungryStudentImpl(7, "James");
        //        s3 = new OOP.Solution.HungryStudentImpl(23, "Jack");
        //        s4 = new OOP.Solution.HungryStudentImpl(4, "Locke");
        //        s5 = new OOP.Solution.HungryStudentImpl(51, "Kate");
        assertTrue(s1.compareTo(s1) == 0);
        assertTrue(s2.compareTo(s2) == 0);
        assertTrue(s3.compareTo(s3) == 0);
        assertTrue(s4.compareTo(s4) == 0);
        assertTrue(s5.compareTo(s5) == 0);

        assertTrue(s1.compareTo(s2) > 0);
        assertTrue(s2.compareTo(s1) < 0);
        assertTrue(s1.compareTo(s3) > 0);
        assertTrue(s3.compareTo(s1) < 0);
        assertTrue(s1.compareTo(s4) > 0);
        assertTrue(s4.compareTo(s1) < 0);
        assertTrue(s1.compareTo(s5) > 0);
        assertTrue(s5.compareTo(s1) < 0);
        assertTrue(s2.compareTo(s3) < 0);
        assertTrue(s3.compareTo(s2) > 0);
        assertTrue(s2.compareTo(s4) > 0);
        assertTrue(s4.compareTo(s2) < 0);
        assertTrue(s2.compareTo(s5) < 0);
        assertTrue(s5.compareTo(s2) > 0);
        assertTrue(s3.compareTo(s4) > 0);
        assertTrue(s4.compareTo(s3) < 0);
        assertTrue(s3.compareTo(s5) < 0);
        assertTrue(s5.compareTo(s3) > 0);
        assertTrue(s4.compareTo(s5) < 0);
        assertTrue(s5.compareTo(s4) > 0);
        System.out.println("HungryStudent::compareTo TEST SUCCESSFUL :)");
    }


}