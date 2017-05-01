package OOP.Tests;

import OOP.Provided.HamburgerNetwork;
import OOP.Provided.HungryStudent;
import OOP.Provided.Restaurant;

import OOP.Provided.HungryStudent.*;
import OOP.Provided.Restaurant.*;
import OOP.Provided.HamburgerNetwork.*;

import OOP.Solution.HamburgerNetworkImpl;
import OOP.Solution.HungryStudentImpl;
import OOP.Solution.RestaurantImpl;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import static org.junit.Assert.*;

/**
 * Created by Omer on 4/22/2017.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // For ascending order to the tests

public class HamburgerNetworkImplTest {

    private static Restaurant dornishBurgers, mcFreys, starkBurger, theWallBurger, lannisterBurger, baratheonBurger, bravosiChicken; // wat

    private static HungryStudent jon;
    private static HungryStudent arya;
    private static HungryStudent daenerys;
    private static HungryStudent theHound;
    private static HungryStudent jorah;
    private static HungryStudent daario;
    private static HungryStudent bran;
    private static HungryStudent theThreeEyedRaven;
    private static HungryStudent hodor;
    private static HungryStudent meera;

    private static Set<String> menu1, menu2, menu3, menu4, menu5, menu6, menuE;

    static {
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
        menu2.add("Chopped Stark");

        menu3 = new HashSet<>();
        menu3.add("Beer");
        menu3.add("Mereneese Lamb Burger");
        menu3.add("Dornish Fries");

        menu4 = new HashSet<>(); // Jon knows nothing, keep it a secret
        menu4.add("Dornish Fries");
        menu4.add("Hot Dragon Burger");
        menu4.add("Fire Ale");
        menu4.add("Bloodburger");
        menu4.add("McDrogo");

        menu5 = new HashSet<>();
        menu5.add("Incested Fries");
        menu5.add("Dwarf Wine");
        menu5.add("Golden-Handed Burger");
        menu5.add("The Three Children");
        menu5.add("Valonqar's Hands");
        menu5.add("Light of The Burger - now with CHEESE");

        menu6 = new HashSet<>();
        menu6.add("Dornish Chicken");
        menu6.add("Chicken Ale");
        menu6.add("Salted Chicken");
        menu6.add("Extra Holy Chicken");
        menu6.add("Boiled Chicken");
        menu6.add("Chicken Chicken");
        menu6.add("SPECIAL : Chicken Attack");

        menuE = new HashSet<>(); // No Baratheons left :(
    }

    private static HamburgerNetwork network;

    private void clearNetwork() {
        network = new HamburgerNetworkImpl();
    }

    @Before
    public void setup() {
        clearNetwork();
    }

    private void registerBastards() throws StudentAlreadyInSystemException {
        jon = network.joinNetwork(1, "Jon Snow");
        arya = network.joinNetwork(2, "A girl has no name");
        daenerys = network.joinNetwork(3, "Daenerys Stormborn of the House Targaryen, First of Her Name, the Unburnt, Queen of the Andals and the First Men, Khaleesi of the Great Grass Sea, Breaker of Chains, and Mother of Dragons");
        theHound = network.joinNetwork(4, "Sandor Clegane");
        jorah = network.joinNetwork(5, "Jorah Mormont");
        daario = network.joinNetwork(6, "Daario Naharis");
        bran = network.joinNetwork(7, "Bran Stark");
        theThreeEyedRaven = network.joinNetwork(8, "The Three Eyed Raven");
        hodor = network.joinNetwork(9, "Hodor Hodor");
        meera = network.joinNetwork(10, "Meera Reed");
    }

    @Test
    public void test1_joinNetwork() throws Exception {

        try {
            registerBastards();
        } catch (StudentAlreadyInSystemException e) {
            fail("Failed while adding new restaurants!");
        }

        try {
            HungryStudent azorAhay = network.joinNetwork(1, "Jon Snow"); // Hmm
            fail("Successfully added duplicate students!");
        } catch (StudentAlreadyInSystemException e) {
        }


        System.out.println("test1_joinNetwork - V");
    }

    private void registerRestaurants() throws RestaurantAlreadyInSystemException {
        dornishBurgers = network.addRestaurant(1, "Dornish Burgers", 100, menu1);
        mcFreys = network.addRestaurant(2, "McFreys", 100, menu2);
        starkBurger = network.addRestaurant(3, "Starkburger", 400, menu3);
        theWallBurger = network.addRestaurant(4, "Burgers Beyond The Wall", 800, menu4);
        lannisterBurger = network.addRestaurant(5, "The Onion Knights", 0, menu5); // Technion = King's Landing, just because
        baratheonBurger = network.addRestaurant(6, "For The Night is Dark and Full of Burgers™", 70, menuE);
        bravosiChicken = network.addRestaurant(7, "BFC - Bravosi Fried Chicken", 800, menu6);
    }

    @Test
    public void test2_addRestaurant() throws Exception {
        try {
            registerRestaurants();
        } catch (RestaurantAlreadyInSystemException e) {
            fail("Failed while adding new restaurants!");
        }

        try {
            registerRestaurants(); // Should throw
            fail("Successfully added duplicate restaurants into the network!");
        } catch (RestaurantAlreadyInSystemException e) { /* Empty as Cersei's heart */ }

        System.out.println("test2_addRestaurants - V");
    }

    @Test
    public void test3_registeredStudents() throws Exception {
        HungryStudent[] s1 = new HungryStudent[]{};
        assertArrayEquals(s1, network.registeredStudents().stream().sorted().toArray());

        registerBastards();
        HungryStudent[] s2 = new HungryStudent[]{jon, arya, daenerys, theHound, jorah, daario, bran, theThreeEyedRaven, hodor, meera};
        assertArrayEquals(s2, network.registeredStudents().stream().sorted().toArray());

        System.out.println("test3_registeredStudents - V");
    }

    @Test
    public void test4_registeredRestaurants() throws Exception {

        Restaurant[] r1 = new Restaurant[]{};
        assertArrayEquals(r1, network.registeredRestaurants().stream().sorted().toArray());

        registerRestaurants();
        Restaurant[] r2 = new Restaurant[]{dornishBurgers, mcFreys, starkBurger, theWallBurger, lannisterBurger, baratheonBurger, bravosiChicken};
        assertArrayEquals(r2, network.registeredRestaurants().stream().sorted().toArray());

        System.out.println("test4_registeredRestaurants - V");
    }

    @Test
    public void test5_getStudent() throws Exception {

        registerBastards();
        registerRestaurants();

        assertEquals(jon, network.getStudent(1));

        try {
            network.getStudent(-1);
            network.getStudent(19);
        } catch (StudentNotInSystemException e) {

        }

        HungryStudent cersei = network.joinNetwork(20, "Cersei Lannister");
        assertEquals(cersei, network.getStudent(20));

        try {
            network.getRestaurant(20); // Just making sure a new student is added to the students collection only
            fail();
        } catch (RestaurantNotInSystemException e) {
            //Good
        }

        System.out.println("test5_getStudent - V");
    }

    @Test
    public void test6_getRestaurant() throws Exception {

        registerBastards();
        registerRestaurants();

        assertNotEquals(jon, network.getRestaurant(1)); // Jon Snow can't be a restaurant
        assertEquals(starkBurger, network.getRestaurant(3));

        try {
            network.getRestaurant(-1);
            network.getRestaurant(19);
        } catch (RestaurantNotInSystemException e) {

        }

        Restaurant reekOmmended = network.addRestaurant(20, "ReekOmmended - 100% more organs!", 340, menuE); // You really don't want to know what is the menu...
        assertEquals(reekOmmended, network.getRestaurant(20));

        try {
            network.getStudent(20); // Just making sure a new restaurant is added to the restaurants collection only
            fail();
        } catch (StudentNotInSystemException e) {
            //Good
        }

        System.out.println("test6_getRestaurant - V");
    }

    private void makeFriends() throws Exception {
        network.addConnection(jon, arya);
        network.addConnection(jon, bran);
        network.addConnection(arya, theHound);
        network.addConnection(arya, bran);
        network.addConnection(bran, theThreeEyedRaven);
        network.addConnection(bran, hodor);
        network.addConnection(bran, meera);
        network.addConnection(meera, theThreeEyedRaven);
        network.addConnection(meera, hodor);
        network.addConnection(daenerys, daario);
        network.addConnection(daenerys, jorah); // Only friends
    }

    @Test
    public void test7_addConnection() throws Exception {

        registerBastards();
        registerRestaurants();

        try {
            HungryStudent tyrion = new HungryStudentImpl(35, "Tyrion-Badass-Lannister");
            network.addConnection(daenerys, tyrion);
            network.addConnection(tyrion, tyrion); // StudentNotInSystem has higher priority
            fail();
        } catch (StudentNotInSystemException e) {
        }

        try {
            HungryStudent azorAhay = new HungryStudentImpl(1, "Jon Snow");
            network.addConnection(jon, azorAhay);
            fail();
        } catch (SameStudentException e) {
        }

        try {
            try {
                network.addConnection(arya, jon); // Successfully make Arya and Jon friends
            } catch (Exception e) {
                fail();
            }
            network.addConnection(arya, jon);
            network.addConnection(jon, arya); // Friendship is symmetric, Arya is Jon's friend and Jon is Arya's friend
            fail();
        } catch (ConnectionAlreadyExistsException e) {

        }

        clearNetwork();
        registerBastards();

        try {
            makeFriends();
        } catch (Exception e) { // Nothing should go wrong
            fail();
        }

        System.out.println("test7_addConnection - V");
    }

    @Test
    public void test8_favoritesByRating() throws Exception {
        registerBastards();
        registerRestaurants();
        makeFriends();

        HungryStudent mycah = new HungryStudentImpl(25, "Alive Mycah");
        try {
            network.favoritesByRating(mycah);
            fail();
        } catch (StudentNotInSystemException e) {

        }

        mycah = network.joinNetwork(25, "Dead Mycah");
        network.addConnection(mycah, arya);
        network.addConnection(mycah, theHound);

        Restaurant[] restaurantArray1 = new Restaurant[]{};
        assertArrayEquals(restaurantArray1, network.favoritesByRating(mycah).toArray()); // Mycah is only friend to arya and the hound

        bravosiChicken.rate(theHound, 5)
                .rate(arya, 3);
        theHound.favorite(bravosiChicken);
        arya.favorite(bravosiChicken);
        restaurantArray1 = new Restaurant[]{bravosiChicken};
        assertArrayEquals(restaurantArray1, network.favoritesByRating(mycah).toArray()); // Duplicate copies should be removed

        clearNetwork();
        registerRestaurants();
        registerBastards();
        makeFriends();

        bravosiChicken.rate(theHound, 5)
                .rate(arya, 3);

        starkBurger.rate(theHound, 1);
        mcFreys.rate(theHound, 1);
        dornishBurgers.rate(theHound, 1);
        theWallBurger.rate(theHound, 1);

        theHound.favorite(bravosiChicken)
                .favorite(starkBurger)
                .favorite(mcFreys)
                .favorite(dornishBurgers)
                .favorite(theWallBurger);

        arya.favorite(bravosiChicken);

        Restaurant[] restaurantArray2 = new Restaurant[]{bravosiChicken, dornishBurgers, mcFreys, starkBurger, theWallBurger};

        assertArrayEquals(restaurantArray2, network.favoritesByRating(arya).toArray());
        assertArrayEquals(restaurantArray1, network.favoritesByRating(theHound).toArray()); // Should contain only arya's favorites

        starkBurger.rate(jon, 5); // 5 out of 5, would come again
        jon.favorite(starkBurger); // The feelings :'(

        Restaurant[] restaurantArray3 = new Restaurant[]{starkBurger, bravosiChicken, dornishBurgers, mcFreys, theWallBurger}; // Jon comes before the hound (jon.id < theHound.id)
        assertArrayEquals(restaurantArray3, network.favoritesByRating(arya).toArray()); // Now Jon has some favorites

        theWallBurger.rate(bran, 4);
        bran.favorite(theWallBurger);

        Restaurant[] restaurantArray4 = new Restaurant[]{starkBurger, bravosiChicken};
        assertArrayEquals(restaurantArray4, network.favoritesByRating(bran).toArray()); // Only friend's favorites


        bravosiChicken.rate(daenerys, 2) // Too much oil and slaves
                .rate(daario, 5)
                .rate(jorah, 2); // Maybe now she will notice me
        daenerys.favorite(bravosiChicken);

        dornishBurgers.rate(daario, 1); // Sand burger? nah

        lannisterBurger.rate(jorah, 5); // Sneaky spy
        jorah.favorite(bravosiChicken).favorite(lannisterBurger);
        daario.favorite(bravosiChicken).favorite(dornishBurgers);

        Restaurant[] restaurantArray5 = new Restaurant[]{lannisterBurger, bravosiChicken, dornishBurgers};
        assertArrayEquals(restaurantArray5, network.favoritesByRating(daenerys).toArray());

        System.out.println("test8_favoritesByRating - V");
    }

    @Test
    public void test9_0_favoritesByDist() throws Exception {
        registerBastards();
        registerRestaurants();
        makeFriends();

        HungryStudent mycah = new HungryStudentImpl(25, "Alive Mycah");
        try {
            network.favoritesByDist(mycah);
            fail();
        } catch (StudentNotInSystemException e) {

        }

        mycah = network.joinNetwork(25, "Dead Mycah");
        network.addConnection(mycah, arya);
        network.addConnection(mycah, theHound);

        Restaurant[] restaurantArray1 = new Restaurant[]{};
        assertArrayEquals(restaurantArray1, network.favoritesByDist(mycah).toArray()); // Mycah is only friend to arya and the hound

        bravosiChicken.rate(theHound, 5)
                .rate(arya, 3);
        theHound.favorite(bravosiChicken);
        arya.favorite(bravosiChicken);
        restaurantArray1 = new Restaurant[]{bravosiChicken};
        assertArrayEquals(restaurantArray1, network.favoritesByDist(mycah).toArray()); // Duplicate copies are removed

        clearNetwork();
        registerRestaurants();
        registerBastards();
        makeFriends();

        bravosiChicken.rate(theHound, 5)
                .rate(arya, 3);

        starkBurger.rate(theHound, 1);
        mcFreys.rate(theHound, 1);
        dornishBurgers.rate(theHound, 1);
        theWallBurger.rate(theHound, 1);

        theHound.favorite(bravosiChicken)
                .favorite(starkBurger)
                .favorite(mcFreys)
                .favorite(dornishBurgers)
                .favorite(theWallBurger);

        arya.favorite(bravosiChicken);

        Restaurant[] restaurantArray2 = new Restaurant[]{dornishBurgers, mcFreys, starkBurger, bravosiChicken, theWallBurger};

        assertArrayEquals(restaurantArray2, network.favoritesByDist(arya).toArray());
        assertArrayEquals(restaurantArray1, network.favoritesByDist(theHound).toArray()); // Should contain only arya's favorites

        starkBurger.rate(jon, 5); // 5 out of 5, would come again
        jon.favorite(starkBurger); // The feelings :'(

        Restaurant[] restaurantArray3 = new Restaurant[]{starkBurger, dornishBurgers, mcFreys, bravosiChicken, theWallBurger}; // Jon comes before the hound (jon.id < theHound.id)
        assertArrayEquals(restaurantArray3, network.favoritesByDist(arya).toArray()); // Now Jon has some favorites

        theWallBurger.rate(bran, 4);
        bran.favorite(theWallBurger);

        Restaurant[] restaurantArray4 = new Restaurant[]{starkBurger, bravosiChicken};
        assertArrayEquals(restaurantArray4, network.favoritesByDist(bran).toArray()); // Only friend's favorites


        bravosiChicken.rate(daenerys, 2) // Too much oil and slaves
                .rate(daario, 5)
                .rate(jorah, 2); // Maybe now she will notice me
        daenerys.favorite(bravosiChicken);

        dornishBurgers.rate(daario, 1); // Sand burger? nah

        lannisterBurger.rate(jorah, 5); // Sneaky spy
        jorah.favorite(bravosiChicken).favorite(lannisterBurger);
        daario.favorite(bravosiChicken).favorite(dornishBurgers);

        Restaurant[] restaurantArray5 = new Restaurant[]{lannisterBurger, bravosiChicken, dornishBurgers};
        assertArrayEquals(restaurantArray5, network.favoritesByDist(daenerys).toArray());

        System.out.println("test9_0_favoritesByDist - V");
    }

    @Test
    public void test9_1_getRecommendation() throws Exception {

        registerBastards();
        registerRestaurants();
        makeFriends();

        network.addConnection(theHound, jon); // wat

        dornishBurgers.rate(daario, 4);
        mcFreys.rate(arya, 1);
        theWallBurger.rate(jon, 4).rate(meera, 3).rate(bran, 5).rate(hodor, 1);
        starkBurger.rate(jon, 5).rate(arya, 4).rate(bran, 4).rate(hodor, 3);
        lannisterBurger.rate(jorah, 3);
        bravosiChicken.rate(theHound, 5).rate(arya, 3);

        daario.favorite(dornishBurgers);
        arya.favorite(mcFreys).favorite(starkBurger);
        jon.favorite(theWallBurger).favorite(starkBurger);
        meera.favorite(theWallBurger);
        bran.favorite(theWallBurger).favorite(starkBurger);
        hodor.favorite(theWallBurger).favorite(starkBurger);
        jorah.favorite(lannisterBurger);
        theHound.favorite(bravosiChicken);

        try{
            HungryStudent azorAhay = new HungryStudentImpl(30, "Jon Snow");
            network.getRecommendation(azorAhay, dornishBurgers, 100);
            fail("Should throw! azorAhay not in system!");
        }
        catch(StudentNotInSystemException e){

        }

        try{
            Restaurant khalBurger = new RestaurantImpl(30, "Khal Burger", 1200 , menuE);
            network.getRecommendation(jon, khalBurger, 100);
            fail("Should throw! khalBurger not in system!");
        }
        catch(RestaurantNotInSystemException e){

        }

        try{
            network.getRecommendation(jon, starkBurger, -1);
            fail("t is negative!");
        }
        catch(ImpossibleConnectionException e){

        }

        try{
            assertTrue(network.getRecommendation(jon, starkBurger, 0));
            assertTrue(network.getRecommendation(jon, bravosiChicken, 1)); // Jon has two ways to get to bravosiChicken - straight from The Hound or via Arya

            assertFalse(network.getRecommendation(arya, theWallBurger, 0));
            assertTrue(network.getRecommendation(arya, theWallBurger, 1));
            assertFalse(network.getRecommendation(arya, lannisterBurger, Integer.MAX_VALUE));
            assertFalse(network.getRecommendation(daenerys, dornishBurgers, 0));
            assertTrue(network.getRecommendation(daenerys, dornishBurgers, 1));

            assertTrue(network.getRecommendation(theHound, theWallBurger, 1));
            assertTrue(network.getRecommendation(theHound, theWallBurger, 2));

            Restaurant hodorHodors = network.addRestaurant(30, "Hodor Hodor Hodor's Hodors", 10000, menuE);
            hodorHodors.rate(hodor, 5);
            hodor.favorite(hodorHodors);

            assertTrue(network.getRecommendation(arya, hodorHodors, 2));
            assertFalse(network.getRecommendation(arya, hodorHodors, 1));

        }
        catch(Exception e){
            fail("Something is messed up - input is ok, but " + e.getMessage() + " is thrown");
        }

        System.out.println("test9_1_getRecommendation - V");
    }

    @Test
    public void test9_2_toString() throws Exception {
        String empty = "Registered students: .\nRegistered restaurants: .\nStudents:\nEnd students.";
        assertEquals(empty, network.toString());

        String studentsOnly = "Registered students: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10.\nRegistered restaurants: .\nStudents:\n1 -> [].\n2 -> [].\n3 -> [].\n4 -> [].\n5 -> [].\n6 -> [].\n7 -> [].\n8 -> [].\n9 -> [].\n10 -> [].\nEnd students.";
        registerBastards();
        assertEquals(studentsOnly, network.toString());


        String restaurantsOnly = "Registered students: .\nRegistered restaurants: 1, 2, 3, 4, 5, 6, 7.\nStudents:\nEnd students.";
        clearNetwork();
        registerRestaurants();
        assertEquals(restaurantsOnly, network.toString());

        String noFriendsYet = "Registered students: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10.\nRegistered restaurants: 1, 2, 3, 4, 5, 6, 7.\nStudents:\n1 -> [].\n2 -> [].\n3 -> [].\n4 -> [].\n5 -> [].\n6 -> [].\n7 -> [].\n8 -> [].\n9 -> [].\n10 -> [].\nEnd students.";
        registerBastards();
        assertEquals(noFriendsYet, network.toString());

        String allTheThings = "Registered students: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10.\nRegistered restaurants: 1, 2, 3, 4, 5, 6, 7.\nStudents:\n1 -> [2, 7].\n2 -> [1, 4, 7].\n3 -> [5, 6].\n4 -> [2].\n5 -> [3].\n6 -> [3].\n7 -> [1, 2, 8, 9, 10].\n8 -> [7, 10].\n9 -> [7, 10].\n10 -> [7, 8, 9].\nEnd students.";
        makeFriends();
        assertEquals(allTheThings, network.toString());

        System.out.println("test9_2_toString - V");

    }

    @AfterClass
    public static void endCredits(){
        System.out.println("---SUCCESS, and remember, Burgers are coming---");
    }

}