package OOP.Tests;

import OOP.Provided.HamburgerNetwork;
import OOP.Provided.HamburgerNetwork.*;
import OOP.Provided.HungryStudent;
import OOP.Provided.HungryStudent.*;
import OOP.Provided.Restaurant;
import OOP.Provided.Restaurant.*;
import OOP.Solution.HamburgerNetworkImpl;
import OOP.Solution.HungryStudentImpl;
import OOP.Solution.RestaurantImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.testng.AssertJUnit.*;

/**
 * Created by danie_000 on 4/21/2017.
 */
public class HamburgerNetworkImplTest {
    private static HamburgerNetwork network;

    private static String STUDENT_NAMES[] =

            {
                    "Hunter", "Suzann", "Valrie", "Silas", "Taneka", "Jan", "Jonell", "Jonna", "Loura", "Ardis",
                    "Temple", "Gaylene", "Melynda", "Christine", "Catherin", "Margarette", "Fran", "Karlyn", "Sharilyn", "Rafael",
                    "Torrie", "Laree", "Carolina", "Myesha", "Jacqulyn", "Irene", "Romelia", "Claudette", "Esmeralda", "Eve",
                    "Shante", "Guillermina", "Alden", "Marge", "Cedrick", "Melina", "Alyse", "Magnolia", "Grant", "Chere",
                    "Domonique", "Shawnna", "Merideth", "Delora", "Ilda", "Alanna", "Arletha", "Celsa", "Lindsay", "Woodrow",
                    "David", "Woody", "Derek", "Zoro", "Suzan", "Christina", "Rachel", "Zoey", "Victoria", "John",
                    "Kate", "Josh", "Drake", "Megan", "Mark", "Evelynn", "Inna", "Robin", "Lily", "Ted",
                    "Cortana", "Monica", "Amelia", "Clarke", "Octavia", "Jasper", "Bellamy", "Penny", "Amy", "Sheldon",
                    "Howard", "Rose", "Rick", "Lexa", "Daryl", "Becka", "Stella", "Zeus", "Nami", "Lexus",
                    "Shaw", "Harold", "Luke", "Rosita", "Sasha", "Kenny", "Stan", "Eric", "Kyle", "Kimchi"
            };

    private static String RESTAURANT_NAMES[] = {
            "AngelWalk", "Arctica", "BagelPalace", "BBB", "BeerInMind", "BigWay", "Bravo", "BringIt", "ButterFish", "CafeVisual",
            "CaptainSincerity", "Cash", "ClestialPudding", "ChocolateBondage", "ChuckCity", "ClubHellYea", "CoffeeCat", "CoffeeFarm", "CoffeeGalaxy", "CoffeeMatrix",
            "CoffeeScience", "CoffeeSquad", "CoffeeTime", "CoffeeTrip", "CoffeeWorld", "CoffeeZone", "Colony", "CompleteMeat", "ConstantCafe", "ConstantTaco",
            "Dingo", "Domina", "DotCand", "DoubleJump", "ExoPlanet", "Fishland", "FlyingHorse", "FlyingPotato", "FoodStorm", "FreshFlesh",
            "FriendlyFire", "FruitNotFries", "FudgementDay", "FuelForThought", "FuzzyLogic", "GeekChicken", "Guilt", "HotBox", "IceCan", "NewYorkCab"
    };


    @Before
    public void init() {
        network = new HamburgerNetworkImpl();
    }

    @Test
    public void joinNetwork() throws Exception {
        IntStream.range(1, 101).forEach(value -> {
                    try {
                        network.joinNetwork(value, "" + STUDENT_NAMES[value - 1]);
                    } catch (StudentAlreadyInSystemException e) {
                        fail("Should work");
                    }


                }
        );
        try {
            network.joinNetwork(50, "Benjamin");
            fail("Id already in system!!!!! + Name's taken :) ");
        } catch (StudentAlreadyInSystemException e) {

        }
        System.out.println("HamburgerNetwork::joinNetwork TEST SUCCESSFUL :)");

    }

    @Test
    public void addRestaurant() throws Exception {
        IntStream.range(1, 51).forEach(value -> {
                    try {
                        network.addRestaurant(value, "" + RESTAURANT_NAMES[value - 1], value % 5, new HashSet<String>());
                    } catch (RestaurantAlreadyInSystemException e) {
                        fail("Should work :)");

                    }

                }
        );
        try {
            network.addRestaurant(50, "NewYorkCab", 50 % 5, new HashSet<String>());
            fail("Id already in system!!!!! + Haven't you known that New York Cab as a restaurant exists?!? :) ");
        } catch (RestaurantAlreadyInSystemException e) {

        }
        System.out.println("HamburgerNetwork::addRestaurant TEST SUCCESSFUL :)");

    }


    @Test
    public void registeredStudents() throws Exception {

        IntStream.range(1, 101).forEach(value -> {
                    try {
                        HungryStudent s = network.joinNetwork(value, "" + STUDENT_NAMES[value - 1]);
                        assertTrue(network.registeredStudents().contains(s));
                    } catch (StudentAlreadyInSystemException e) {
                        fail("Should work :)");

                    }

                }
        );
        assertFalse(network.registeredStudents().contains(new HungryStudentImpl(101, "Epsilon")));
        assertFalse(network.registeredStudents().contains(new RestaurantImpl(15, "Delta", 3, new HashSet<String>()))); // Restaurant is a student?!?!
        System.out.println("HamburgerNetwork::registeredStudents TEST SUCCESSFUL :)");

    }

    @Test
    public void registeredRestaurants() throws Exception {
        IntStream.range(1, 51).forEach(value -> {
                    try {
                        Restaurant r = network.addRestaurant(value, "" + RESTAURANT_NAMES[value - 1], value % 5, new HashSet<String>());
                        assertTrue(network.registeredRestaurants().contains(r));
                    } catch (RestaurantAlreadyInSystemException e) {
                        fail("Should work :)");

                    }
                }
        );


        assertFalse(network.registeredRestaurants().contains(new RestaurantImpl(51, "Delta", 3, new HashSet<String>())));
        assertFalse(network.registeredRestaurants().contains(new HungryStudentImpl(15, "Epsilon"))); // Student is a restaurant?!?!

        System.out.println("HamburgerNetwork::registeredRestaurants TEST SUCCESSFUL :)");

    }

    @Test
    public void getStudent() throws Exception {
        try {
            network.getStudent(10);
            fail("No students have been added yet :(");
        } catch (StudentNotInSystemException e) {

        }
        IntStream.range(1, 101).forEach(value -> {
                    try {
                        HungryStudent s = network.joinNetwork(value, "" + STUDENT_NAMES[value - 1]);
                        assertEquals(s, network.getStudent(value));
                    } catch (StudentAlreadyInSystemException | StudentNotInSystemException e) {
                        fail("Can't your network find the student? Is he doing OOP?!?!?");
                    }

                }
        );
        try {
            network.getStudent(55);
        } catch (StudentNotInSystemException e) {
            fail("Can't you find Suzan in system?? :( No date for you!!");

        }
        System.out.println("HamburgerNetwork::getStudent TEST SUCCESSFUL :)");

    }

    @Test
    public void getRestaurant() throws Exception {
        try {
            network.getRestaurant(10);
            fail("No restaurants have been added yet :(");
        } catch (RestaurantNotInSystemException e) {

        }
        IntStream.range(1, 51).forEach(value -> {
                    try {
                        Restaurant r = network.addRestaurant(value, "" + RESTAURANT_NAMES[value - 1], value % 5, new HashSet<String>());
                        assertEquals(r, network.getRestaurant(value));
                    } catch (RestaurantAlreadyInSystemException | RestaurantNotInSystemException e) {
                        fail("Can't your network find the student? Is he doing OOP?!?!?");
                    }

                }
        );
        try {
            network.getRestaurant(10);
        } catch (RestaurantNotInSystemException e) {
            fail("Can't you find Cafe Visual in system?? Make your own coffee!!!!!");

        }
        System.out.println("HamburgerNetwork::getRestaurant TEST SUCCESSFUL :)");

    }

    @Test
    public void addConnection() throws Exception {

        IntStream.range(1, 101).forEach(value -> {
                    try {
                        network.joinNetwork(value, "" + STUDENT_NAMES[value - 1]);
                    } catch (StudentAlreadyInSystemException e) {
                        fail("Should work :)");

                    }
                }
        );
        try {
            assertFalse(network.getStudent(26).getFriends().contains(network.getStudent(27)));
            assertFalse(network.getStudent(27).getFriends().contains(network.getStudent(26)));
            network.addConnection(network.getStudent(27), network.getStudent(26));
            assertTrue(network.getStudent(26).getFriends().contains(network.getStudent(27)));
            assertTrue(network.getStudent(27).getFriends().contains(network.getStudent(26)));
            network.addConnection(network.getStudent(27), network.getStudent(28));
            assertTrue(network.getStudent(28).getFriends().contains(network.getStudent(27)));
            assertTrue(network.getStudent(27).getFriends().contains(network.getStudent(28)));
            // Intransitive check
            assertFalse(network.getStudent(28).getFriends().contains(network.getStudent(26)));
            assertFalse(network.getStudent(26).getFriends().contains(network.getStudent(28)));
        } catch (ConnectionAlreadyExistsException e) {
            fail("I didn't know I already have friends!");
        }
        try {
            network.addConnection(new HungryStudentImpl(1001, "Epsilon"), network.getStudent(26));
            fail("But epsilon hasn't joined the system :(");
        } catch (StudentNotInSystemException e) {

        }
        try {
            network.addConnection(network.getStudent(26), new HungryStudentImpl(10000, "Epsilon"));
            fail("But epsilon hasn't joined the system :(");
        } catch (StudentNotInSystemException e) {

        }
        try {
            network.addConnection(network.getStudent(26), network.getStudent(27));
            fail("Did you forget that we are friends? :(");
        } catch (ConnectionAlreadyExistsException e) {

        }
        try {
            network.addConnection(network.getStudent(27), network.getStudent(26));
            fail("Did you forget that we are friends? :(");
        } catch (ConnectionAlreadyExistsException e) {

        }
        System.out.println("HamburgerNetwork::addConnection TEST SUCCESSFUL :)");

    }

    @Test
    public void favoritesByRating() throws Exception {
        try {
            network.favoritesByRating(new HungryStudentImpl(1001, "Epsilon"));
            fail("But epsilon hasn't joined the system :(");
        } catch (StudentNotInSystemException e) {

        }
        IntStream.range(1, 11).forEach(value -> {
                    try {
                        network.joinNetwork(value, "" + STUDENT_NAMES[value - 1]);
                    } catch (StudentAlreadyInSystemException e) {
                        fail("Should work :)");

                    }
                }
        );
        IntStream.range(1, 16).forEach(value -> {
                    try {
                        network.addRestaurant(value, "" + RESTAURANT_NAMES[value - 1], value % 5, new HashSet<String>());
                    } catch (RestaurantAlreadyInSystemException e) {
                        fail("Should work :)");


                    }

                }
        );
        try {
            Restaurant r1 = network.getRestaurant(1);
            Restaurant r2 = network.getRestaurant(2);
            Restaurant r3 = network.getRestaurant(3);
            Restaurant r4 = network.getRestaurant(4);
            Restaurant r5 = network.getRestaurant(5);
            Restaurant r6 = network.getRestaurant(6);
            Restaurant r7 = network.getRestaurant(7);
            Restaurant r8 = network.getRestaurant(8);
            Restaurant r9 = network.getRestaurant(9);
            Restaurant r10 = network.getRestaurant(10);
            Restaurant r11 = network.getRestaurant(11);
            Restaurant r12 = network.getRestaurant(12);
            Restaurant r13 = network.getRestaurant(13);
            Restaurant r14 = network.getRestaurant(14);
            Restaurant r15 = network.getRestaurant(15);

            HungryStudent s1 = network.getStudent(1);
            HungryStudent s2 = network.getStudent(2);
            HungryStudent s3 = network.getStudent(3);
            HungryStudent s4 = network.getStudent(4);
            HungryStudent s5 = network.getStudent(5);
            HungryStudent s6 = network.getStudent(6);
            HungryStudent s7 = network.getStudent(7);
            HungryStudent s8 = network.getStudent(8);
            HungryStudent s9 = network.getStudent(9);
            HungryStudent s10 = network.getStudent(10);

            //  network.getRestaurant(1).rate();
            //  network.getRestaurant(2).rate(network.getStudent());
            r3.rate(s4, 3);
            r4.rate(s5, 3);
            //  r5).rate();
            //  r6).rate();
            r7.rate(s1, 3).rate(s2, 3);
            r8.rate(s1, 3).rate(s2, 3).rate(s4, 3);
            r9.rate(s1, 3).rate(s2, 3).rate(s5, 3);
            r10.rate(s1, 3).rate(s2, 3).rate(s3, 3);
            //  r11).rate();
            //  r12).rate();
            r13.rate(s3, 3).rate(s4, 4); // Notice rating :)
            r14.rate(s3, 3).rate(s5, 3);
            r15.rate(s3, 3);

            s1.favorite(r7).favorite(r8).favorite(r9).favorite(r10);
            s2.favorite(r7).favorite(r8).favorite(r9).favorite(r10);
            s3.favorite(r10).favorite(r13).favorite(r14).favorite(r15);
            s4.favorite(r3).favorite(r8).favorite(r13);
            s5.favorite(r4).favorite(r9).favorite(r14);

            network.addConnection(s1, s2);
            network.addConnection(s1, s3);
            network.addConnection(s1, s4);
            network.addConnection(s2, s3);
            network.addConnection(s2, s4);
            network.addConnection(s3, s4);
            network.addConnection(s4, s5);


            List<Restaurant> list1 = network.favoritesByRating(s1).stream().collect(Collectors.toList());
            List<Restaurant> list2 = network.favoritesByRating(s2).stream().collect(Collectors.toList());
            List<Restaurant> list3 = network.favoritesByRating(s3).stream().collect(Collectors.toList());
            List<Restaurant> list4 = network.favoritesByRating(s4).stream().collect(Collectors.toList());
            List<Restaurant> list5 = network.favoritesByRating(s5).stream().collect(Collectors.toList());
            List<Restaurant> list6 = network.favoritesByRating(s6).stream().collect(Collectors.toList());
            List<Restaurant> list7 = network.favoritesByRating(s7).stream().collect(Collectors.toList());
            List<Restaurant> list8 = network.favoritesByRating(s8).stream().collect(Collectors.toList());
            List<Restaurant> list9 = network.favoritesByRating(s9).stream().collect(Collectors.toList());
            List<Restaurant> list10 = network.favoritesByRating(s10).stream().collect(Collectors.toList());

            assertEquals(8, list1.size());
            assertEquals(8, list2.size());
            assertEquals(6, list3.size());
            assertEquals(8, list4.size());
            assertEquals(3, list5.size());

            assertEquals(r10, list1.get(0));
            assertEquals(r7, list1.get(1));
            assertEquals(r8, list1.get(2));
            assertEquals(r9, list1.get(3));
            assertEquals(r13, list1.get(4));
            assertEquals(r15, list1.get(5));
            assertEquals(r14, list1.get(6));
            assertEquals(r3, list1.get(7));

            assertEquals(r10, list2.get(0));
            assertEquals(r7, list2.get(1));
            assertEquals(r8, list2.get(2));
            assertEquals(r9, list2.get(3));
            assertEquals(r13, list2.get(4));
            assertEquals(r15, list2.get(5));
            assertEquals(r14, list2.get(6));
            assertEquals(r3, list2.get(7));

            assertEquals(r10, list3.get(0));
            assertEquals(r7, list3.get(1));
            assertEquals(r8, list3.get(2));
            assertEquals(r9, list3.get(3));
            assertEquals(r13, list3.get(4));
            assertEquals(r3, list3.get(5));

            assertEquals(r10, list4.get(0));
            assertEquals(r7, list4.get(1));
            assertEquals(r8, list4.get(2));
            assertEquals(r9, list4.get(3));
            assertEquals(r13, list4.get(4));
            assertEquals(r15, list4.get(5));
            assertEquals(r14, list4.get(6));
            assertEquals(r4, list4.get(7));

            assertEquals(r13, list5.get(0)); // Notice Rating ;)
            assertEquals(r3, list5.get(1));
            assertEquals(r8, list5.get(2));


        } catch (StudentNotInSystemException | RestaurantNotInSystemException e) {
            fail("ERROR shouldn't be here :)");
        }
        System.out.println("HamburgerNetwork::favoritesByRating TEST SUCCESSFUL :)");
    }

    @Test
    public void favoritesByDist() throws Exception {
        try {
            network.favoritesByRating(new HungryStudentImpl(1001, "Epsilon"));
            fail("But epsilon hasn't joined the system :(");
        } catch (StudentNotInSystemException e) {

        }
        IntStream.range(1, 11).forEach(value -> {
                    try {
                        network.joinNetwork(value, "" + STUDENT_NAMES[value - 1]);
                    } catch (StudentAlreadyInSystemException e) {
                        fail("Should work :)");

                    }
                }
        );
        IntStream.range(1, 16).forEach(value -> {
                    try {
                        network.addRestaurant(value, "" + RESTAURANT_NAMES[value - 1], value % 5, new HashSet<String>());
                    } catch (RestaurantAlreadyInSystemException e) {

                        fail("Should work :)");

                    }

                }
        );
        try {
            Restaurant r1 = network.getRestaurant(1);
            Restaurant r2 = network.getRestaurant(2);
            Restaurant r3 = network.getRestaurant(3);
            Restaurant r4 = network.getRestaurant(4);
            Restaurant r5 = network.getRestaurant(5);
            Restaurant r6 = network.getRestaurant(6);
            Restaurant r7 = network.getRestaurant(7);
            Restaurant r8 = network.getRestaurant(8);
            Restaurant r9 = network.getRestaurant(9);
            Restaurant r10 = network.getRestaurant(10);
            Restaurant r11 = network.getRestaurant(11);
            Restaurant r12 = network.getRestaurant(12);
            Restaurant r13 = network.getRestaurant(13);
            Restaurant r14 = network.getRestaurant(14);
            Restaurant r15 = network.getRestaurant(15);

            HungryStudent s1 = network.getStudent(1);
            HungryStudent s2 = network.getStudent(2);
            HungryStudent s3 = network.getStudent(3);
            HungryStudent s4 = network.getStudent(4);
            HungryStudent s5 = network.getStudent(5);
            HungryStudent s6 = network.getStudent(6);
            HungryStudent s7 = network.getStudent(7);
            HungryStudent s8 = network.getStudent(8);
            HungryStudent s9 = network.getStudent(9);
            HungryStudent s10 = network.getStudent(10);

            //  network.getRestaurant(1).rate();
            //  network.getRestaurant(2).rate(network.getStudent());
            r3.rate(s4, 3);
            r4.rate(s5, 3);
            //  r5).rate();
            //  r6).rate();
            r7.rate(s1, 3).rate(s2, 3);
            r8.rate(s1, 3).rate(s2, 3).rate(s4, 3);
            r9.rate(s1, 3).rate(s2, 3).rate(s5, 3);
            r10.rate(s1, 3).rate(s2, 3).rate(s3, 3);
            //  r11).rate();
            //  r12).rate();
            r13.rate(s3, 3).rate(s4, 4); // Notice rating :)
            r14.rate(s3, 3).rate(s5, 3);
            r15.rate(s3, 3);

            s1.favorite(r7).favorite(r8).favorite(r9).favorite(r10);
            s2.favorite(r7).favorite(r8).favorite(r9).favorite(r10);
            s3.favorite(r10).favorite(r13).favorite(r14).favorite(r15);
            s4.favorite(r3).favorite(r8).favorite(r13);
            s5.favorite(r4).favorite(r9).favorite(r14);

            network.addConnection(s1, s2);
            network.addConnection(s1, s3);
            network.addConnection(s1, s4);
            network.addConnection(s2, s3);
            network.addConnection(s2, s4);
            network.addConnection(s3, s4);
            network.addConnection(s4, s5);

            List<Restaurant> list1 = network.favoritesByDist(s1).stream().collect(Collectors.toList());
            List<Restaurant> list2 = network.favoritesByDist(s2).stream().collect(Collectors.toList());
            List<Restaurant> list3 = network.favoritesByDist(s3).stream().collect(Collectors.toList());
            List<Restaurant> list4 = network.favoritesByDist(s4).stream().collect(Collectors.toList());
            List<Restaurant> list5 = network.favoritesByDist(s5).stream().collect(Collectors.toList());
            List<Restaurant> list6 = network.favoritesByDist(s6).stream().collect(Collectors.toList());
            List<Restaurant> list7 = network.favoritesByDist(s7).stream().collect(Collectors.toList());
            List<Restaurant> list8 = network.favoritesByDist(s8).stream().collect(Collectors.toList());
            List<Restaurant> list9 = network.favoritesByDist(s9).stream().collect(Collectors.toList());
            List<Restaurant> list10 = network.favoritesByDist(s10).stream().collect(Collectors.toList());

            assertEquals(8, list1.size());
            assertEquals(8, list2.size());
            assertEquals(6, list3.size());
            assertEquals(8, list4.size());
            assertEquals(3, list5.size());

            assertEquals(r10, list1.get(0));
            assertEquals(r7, list1.get(1));
            assertEquals(r8, list1.get(2));
            assertEquals(r9, list1.get(3));
            assertEquals(r15, list1.get(4));
            assertEquals(r13, list1.get(5));
            assertEquals(r14, list1.get(6));
            assertEquals(r3, list1.get(7));

            assertEquals(r10, list2.get(0));
            assertEquals(r7, list2.get(1));
            assertEquals(r8, list2.get(2));
            assertEquals(r9, list2.get(3));
            assertEquals(r15, list2.get(4));
            assertEquals(r13, list2.get(5));
            assertEquals(r14, list2.get(6));
            assertEquals(r3, list2.get(7));

            assertEquals(r10, list3.get(0));
            assertEquals(r7, list3.get(1));
            assertEquals(r8, list3.get(2));
            assertEquals(r9, list3.get(3));
            assertEquals(r13, list3.get(4));
            assertEquals(r3, list3.get(5));

            assertEquals(r10, list4.get(0));
            assertEquals(r7, list4.get(1));
            assertEquals(r8, list4.get(2));
            assertEquals(r9, list4.get(3));
            assertEquals(r15, list4.get(4));
            assertEquals(r13, list4.get(5));
            assertEquals(r14, list4.get(6));
            assertEquals(r4, list4.get(7));

            assertEquals(r13, list5.get(0)); // Notice Rating ;)
            assertEquals(r3, list5.get(1));
            assertEquals(r8, list5.get(2));


        } catch (StudentNotInSystemException | RestaurantNotInSystemException e) {
            fail("ERROR shouldn't be here :)");
        }

        System.out.println("HamburgerNetwork::favoritesByDist TEST SUCCESSFUL :)");

    }

    @Test
    public void getRecommendation() throws Exception {
        int default_rating = 3; // Doesn't truly matter
        int test = 0;
        int LENGTH = 10;
        Restaurant r;
        /*
         * Test 1 :)
         */
        network = new HamburgerNetworkImpl();
        IntStream.range(1, 101).forEach(value -> {
                    try {
                        network.joinNetwork(value, "" + STUDENT_NAMES[value - 1]);
                    } catch (StudentAlreadyInSystemException e) {
                        fail("Weird,should work!");
                    }
                }
        );
        ++test;
        try {
            network.addRestaurant(test, "" + RESTAURANT_NAMES[test - 1], test % 5, new HashSet<String>());
        } catch (RestaurantAlreadyInSystemException e) {
            fail("Weird,should work!");

        }
        try {
            r = network.getRestaurant(test);
            for (int row = 1; row <= LENGTH; row++)
                for (int col = 1; col <= LENGTH; col++) {
                    if (col < LENGTH)
                        network.addConnection(network.getStudent(((row) - 1) * LENGTH + col), network.getStudent(((row) - 1) * LENGTH + col + 1));
                    if (row < LENGTH)
                        network.addConnection(network.getStudent(((row) - 1) * LENGTH + col), network.getStudent((row) * LENGTH + col));
                }
            r.rate(network.getStudent(1), default_rating);
            network.getStudent(1).favorite(r);
            r.rate(network.getStudent(55), default_rating);
            network.getStudent(55).favorite(r);
            r.rate(network.getStudent(100), default_rating);
            network.getStudent(100).favorite(r);

            for (int i = 1; i <= 100; i++) {
                if (i == 1 || i == 55 || i == 100)
                    assertTrue(network.getRecommendation(network.getStudent(i), r, 0));
                else
                    assertFalse(network.getRecommendation(network.getStudent(i), r, 0));
            }
        } catch (Exception e) {
            fail("Test failed due to weird exception :(");
        }

        /*
         * Test 2 :)
         */
        network = new HamburgerNetworkImpl();
        IntStream.range(1, 101).forEach(value -> {
                    try {
                        network.joinNetwork(value, "" + STUDENT_NAMES[value - 1]);
                    } catch (StudentAlreadyInSystemException e) {
                        fail("Weird,should work!");

                    }
                }
        );
        ++test;
        try {
            network.addRestaurant(test, "" + RESTAURANT_NAMES[test - 1], test % 5, new HashSet<String>());
        } catch (RestaurantAlreadyInSystemException e) {
            fail("Weird,should work!");


        }
        try {
            r = network.getRestaurant(test);
            for (int row = 1; row <= LENGTH; row++)
                for (int col = 1; col <= LENGTH; col++) {
                    if (col < LENGTH)
                        network.addConnection(network.getStudent(((row) - 1) * LENGTH + col), network.getStudent(((row) - 1) * LENGTH + col + 1));
                    if (row < LENGTH)
                        network.addConnection(network.getStudent(((row) - 1) * LENGTH + col), network.getStudent((row) * LENGTH + col));
                }
            r.rate(network.getStudent(1), default_rating);
            network.getStudent(1).favorite(r);
            r.rate(network.getStudent(55), default_rating);
            network.getStudent(55).favorite(r);
            r.rate(network.getStudent(100), default_rating);
            network.getStudent(100).favorite(r);
            for (int i = 1; i <= 100; i++) {
                if (i == 1 || i == 2 || i == 11 || i == 45 || i == 54 || i == 55 || i == 56 || i == 65 || i == 90 || i == 99 || i == 100) {
                    assertTrue(network.getRecommendation(network.getStudent(i), r, 1));
                } else {
                    assertFalse(network.getRecommendation(network.getStudent(i), r, 1));
                }

            }
        } catch (Exception e) {
            fail("Test failed due to weird exception :(");
        }
        /*
         * Test 3 :)
         */
        network = new HamburgerNetworkImpl();
        IntStream.range(1, 101).forEach(value -> {
                    try {
                        network.joinNetwork(value, "" + STUDENT_NAMES[value - 1]);
                    } catch (StudentAlreadyInSystemException e) {
                        fail("Weird,should work!");

                    }
                }
        );
        ++test;
        try {
            network.addRestaurant(test, "" + RESTAURANT_NAMES[test - 1], test % 5, new HashSet<String>());
        } catch (RestaurantAlreadyInSystemException e) {
            fail("Weird,should work!");


        }
        try {
            r = network.getRestaurant(test);
            for (int row = 1; row <= LENGTH; row++)
                for (int col = 1; col <= LENGTH; col++) {
                    if (col < LENGTH)
                        network.addConnection(network.getStudent(((row) - 1) * LENGTH + col), network.getStudent(((row) - 1) * LENGTH + col + 1));
                    if (row < LENGTH)
                        network.addConnection(network.getStudent(((row) - 1) * LENGTH + col), network.getStudent((row) * LENGTH + col));
                }
            r.rate(network.getStudent(1), default_rating);
            network.getStudent(1).favorite(r);
            r.rate(network.getStudent(55), default_rating);
            network.getStudent(55).favorite(r);
            r.rate(network.getStudent(100), default_rating);
            network.getStudent(100).favorite(r);
            for (int i = 1; i <= 100; i++) {
                if (i == 1 || i == 2 || i == 3 || i == 11 || i == 12 || i == 21 || i == 35 || i == 44 || i == 45
                        || i == 46 || i == 53 || i == 54 || i == 55 || i == 56 || i == 57 || i == 64 || i == 65 || i == 66
                        || i == 75 || i == 80 || i == 89 || i == 90 || i == 98 || i == 99 || i == 100
                        )
                    assertTrue(network.getRecommendation(network.getStudent(i), r, 2));

                else
                    assertFalse(network.getRecommendation(network.getStudent(i), r, 2));
            }
        } catch (Exception e) {
            fail("Test failed due to weird exception :(");
        }
        /*
         * Test 4 :)
         */
        network = new HamburgerNetworkImpl();
        IntStream.range(1, 101).forEach(value -> {
                    try {
                        network.joinNetwork(value, "" + STUDENT_NAMES[value - 1]);
                    } catch (StudentAlreadyInSystemException e) {
                        fail("Weird,should work!");

                    }
                }
        );
        ++test;
        try {
            network.addRestaurant(test, "" + RESTAURANT_NAMES[test - 1], test % 5, new HashSet<String>());
        } catch (RestaurantAlreadyInSystemException e) {
            fail("Weird,should work!");


        }
        try {
            r = network.getRestaurant(test);
            for (int row = 1; row <= LENGTH; row++)
                for (int col = 1; col <= LENGTH; col++) {
                    if (col < LENGTH)
                        network.addConnection(network.getStudent(((row) - 1) * LENGTH + col), network.getStudent(((row) - 1) * LENGTH + col + 1));
                    if (row < LENGTH)
                        network.addConnection(network.getStudent(((row) - 1) * LENGTH + col), network.getStudent((row) * LENGTH + col));
                }
            r.rate(network.getStudent(1), default_rating);
            network.getStudent(1).favorite(r);
            r.rate(network.getStudent(55), default_rating);
            network.getStudent(55).favorite(r);
            r.rate(network.getStudent(100), default_rating);
            network.getStudent(100).favorite(r);

            //Awesome tank right? :)
            for (int i = 1; i <= 100; i++) {
                if (i == 1 || i == 2 || i == 3 || i == 4 || i == 5
                        || i == 11 || i == 12 || i == 13 || i == 14 || i == 15
                        || i == 21 || i == 22 || i == 23 || i == 24 || i == 25 || i == 26
                        || i == 31 || i == 32 || i == 33 || i == 34 || i == 35 || i == 36 || i == 37
                        || i == 41 || i == 42 || i == 43 || i == 44 || i == 45 || i == 46 || i == 47 || i == 48
                        || i == 51 || i == 52 || i == 53 || i == 54 || i == 55 || i == 56 || i == 57 || i == 58 || i == 59
                        || i == 60 || i == 62 || i == 63 || i == 64 || i == 65 || i == 66 || i == 67 || i == 68 || i == 69
                        || i == 70 || i == 73 || i == 74 || i == 75 || i == 76 || i == 77 || i == 78 || i == 79
                        || i == 80 || i == 84 || i == 85 || i == 86 || i == 87 || i == 88 || i == 89
                        || i == 90 || i == 95 || i == 96 || i == 97 || i == 98 || i == 99 || i == 100
                        ) {
                    assertTrue(network.getRecommendation(network.getStudent(i), r, 4));
                } else
                    assertFalse(network.getRecommendation(network.getStudent(i), r, 4));
            }
        } catch (Exception e) {
            fail("Test failed due to weird exception :(");
        }
        /*
         * Test 5 :)
         */
        network = new HamburgerNetworkImpl();
        IntStream.range(1, 101).forEach(value -> {
                    try {
                        network.joinNetwork(value, "" + STUDENT_NAMES[value - 1]);
                    } catch (StudentAlreadyInSystemException e) {
                        fail("Weird,should work!");

                    }
                }
        );
        ++test;
        try {
            network.addRestaurant(test, "" + RESTAURANT_NAMES[test - 1], test % 5, new HashSet<String>());
        } catch (RestaurantAlreadyInSystemException e) {
            fail("Weird,should work!");


        }
        try {
            r = network.getRestaurant(test);
            for (int row = 1; row <= LENGTH; row++)
                for (int col = 1; col <= LENGTH; col++) {
                    if (col < LENGTH)
                        network.addConnection(network.getStudent(((row) - 1) * LENGTH + col), network.getStudent(((row) - 1) * LENGTH + col + 1));
                    if (row < LENGTH)
                        network.addConnection(network.getStudent(((row) - 1) * LENGTH + col), network.getStudent((row) * LENGTH + col));
                }
            r.rate(network.getStudent(1), default_rating);
            network.getStudent(1).favorite(r);
            r.rate(network.getStudent(55), default_rating);
            network.getStudent(55).favorite(r);
            r.rate(network.getStudent(100), default_rating);
            network.getStudent(100).favorite(r);
            for (int i = 1; i <= 100; i++) {
                assertTrue(network.getRecommendation(network.getStudent(i), r, 9));
            }

        } catch (Exception e) {
            fail("Test failed due to weird exception :(");
        }
        /*
         * Test 6 :)
         */
        network = new HamburgerNetworkImpl();
        IntStream.range(1, 101).forEach(value -> {
                    try {
                        network.joinNetwork(value, "" + STUDENT_NAMES[value - 1]);
                    } catch (StudentAlreadyInSystemException e) {
                        fail("Weird,should work!");

                    }
                }
        );
        ++test;
        try {
            network.addRestaurant(test, "" + RESTAURANT_NAMES[test - 1], test % 5, new HashSet<String>());
        } catch (RestaurantAlreadyInSystemException e) {
            fail("Weird,should work!");
        }
        try {
            r = network.getRestaurant(test);
            for (int row = 1; row <= LENGTH; row++)
                for (int col = 1; col <= LENGTH; col++) {
                    if (col < LENGTH)
                        network.addConnection(network.getStudent(((row) - 1) * LENGTH + col), network.getStudent(((row) - 1) * LENGTH + col + 1));
                    if (row < LENGTH)
                        network.addConnection(network.getStudent(((row) - 1) * LENGTH + col), network.getStudent((row) * LENGTH + col));
                }
            r.rate(network.getStudent(1), default_rating);
            network.getStudent(1).favorite(r);
            network.addConnection(network.getStudent(1), network.getStudent(55));
            network.addConnection(network.getStudent(1), network.getStudent(100));
            network.addConnection(network.getStudent(55), network.getStudent(100));
            for (int i = 1; i <= 100; i++) {
                if (i == 1 || i == 2 || i == 11 || i == 55 || i == 100)
                    assertTrue(network.getRecommendation(network.getStudent(i), r, 1));
                else
                    assertFalse(network.getRecommendation(network.getStudent(i), r, 1));

            }

        } catch (Exception e) {
            fail("Test failed due to weird exception :(");
        }


        /*
         * Test 7 :)
         */
        network = new HamburgerNetworkImpl();
        IntStream.range(1, 101).forEach(value -> {
                    try {
                        network.joinNetwork(value, "" + STUDENT_NAMES[value - 1].toString());
                    } catch (StudentAlreadyInSystemException e) {
                        fail("Weird,should work!");

                    }
                }
        );
        ++test;
        try {
            network.addRestaurant(test, "" + RESTAURANT_NAMES[test - 1], test % 5, new HashSet<String>());
        } catch (RestaurantAlreadyInSystemException e) {
            fail("Weird,should work!");


        }
        try {
            r = network.getRestaurant(test);
            for (int row = 1; row <= LENGTH; row++)
                for (int col = 1; col <= LENGTH; col++) {
                    if (col < LENGTH)
                        network.addConnection(network.getStudent(((row) - 1) * LENGTH + col), network.getStudent(((row) - 1) * LENGTH + col + 1));
                    if (row < LENGTH)
                        network.addConnection(network.getStudent(((row) - 1) * LENGTH + col), network.getStudent((row) * LENGTH + col));
                }
            for (int i = 1; i <= 100; i++) {
                assertFalse(network.getRecommendation(network.getStudent(i), r, 0));


            }

        } catch (Exception e) {
            fail("Test failed due to weird exception :(");
        }

        /*
         * Test 8 :)
         */
        network = new HamburgerNetworkImpl();
        IntStream.range(1, 101).forEach(value -> {
                    try {
                        network.joinNetwork(value, "" + STUDENT_NAMES[value - 1]);
                    } catch (StudentAlreadyInSystemException e) {
                        fail("Weird,should work!");

                    }
                }
        );
        ++test;
        try {
            network.addRestaurant(test, "" + RESTAURANT_NAMES[test - 1], test % 5, new HashSet<String>());
        } catch (RestaurantAlreadyInSystemException e) {
            fail("Weird,should work!");


        }
        try {
            r = network.getRestaurant(test);
            for (int row = 1; row <= LENGTH; row++)
                for (int col = 1; col <= LENGTH; col++) {
                    if (col < LENGTH)
                        network.addConnection(network.getStudent(((row) - 1) * LENGTH + col), network.getStudent(((row) - 1) * LENGTH + col + 1));
                    if (row < LENGTH)
                        network.addConnection(network.getStudent(((row) - 1) * LENGTH + col), network.getStudent((row) * LENGTH + col));
                }
            r.rate(network.getStudent(1), default_rating);
            network.getStudent(1).favorite(r);
            for (int i = 1; i <= 100; i++) {
                if (i == 100)
                    assertFalse(network.getRecommendation(network.getStudent(i), r, 17));
                else
                    assertTrue(network.getRecommendation(network.getStudent(i), r, 17));

            }

        } catch (Exception e) {
            fail("Test failed due to weird exception :(");
        }

        /*
         * Test 9 :)
         */
        network = new HamburgerNetworkImpl();
        IntStream.range(1, 101).forEach(value -> {
                    try {
                        network.joinNetwork(value, "" + STUDENT_NAMES[value - 1]);
                    } catch (StudentAlreadyInSystemException e) {
                        fail("Weird,should work!");

                    }
                }
        );
        ++test;
        try {
            network.addRestaurant(test, "" + RESTAURANT_NAMES[test - 1], test % 5, new HashSet<String>());
        } catch (RestaurantAlreadyInSystemException e) {
            fail("Weird,should work!");


        }
        try {
            r = network.getRestaurant(test);
            for (int row = 1; row <= LENGTH; row++)
                for (int col = 1; col <= LENGTH; col++) {
                    if (col < LENGTH)
                        network.addConnection(network.getStudent(((row) - 1) * LENGTH + col), network.getStudent(((row) - 1) * LENGTH + col + 1));
                    if (row < LENGTH)
                        network.addConnection(network.getStudent(((row) - 1) * LENGTH + col), network.getStudent((row) * LENGTH + col));
                }
            for (int i = 1; i < 101; i += 2) {
                r.rate(network.getStudent(i), default_rating);
                network.getStudent(i).favorite(r);
            }
            for (int i = 1; i <= 100; i++) {
                if (i % 2 == 0)
                    assertFalse(network.getRecommendation(network.getStudent(i), r, 0));
                else
                    assertTrue(network.getRecommendation(network.getStudent(i), r, 0));
                assertTrue(network.getRecommendation(network.getStudent(i), r, 1));

            }

        } catch (Exception e) {
            fail("Test failed due to weird exception :(");
        }

        /*
         * Test 10 :)
         */
        network = new HamburgerNetworkImpl();
        IntStream.range(1, 101).forEach(value -> {
                    try {
                        network.joinNetwork(value, "" + STUDENT_NAMES[value - 1]);
                    } catch (StudentAlreadyInSystemException e) {
                        fail("Weird,should work!");

                    }
                }
        );
        ++test;
        try {
            network.addRestaurant(test, "" + RESTAURANT_NAMES[test - 1], test % 5, new HashSet<String>());
        } catch (RestaurantAlreadyInSystemException e) {
            fail("Weird,should work!");


        }
        r = network.getRestaurant(test);
        try {
            network.getRecommendation(new HungryStudentImpl(101, "Epsilon"), r, 0);
            fail("Student epsilon doesn't exist in system :(");
        } catch (StudentNotInSystemException e) {

        }
        try {
            network.getRecommendation(network.getStudent(1), new RestaurantImpl(15, "Delta", 3, new HashSet<String>()), 0);
            fail("Restaurant delta doesn't exist in system :(");
        } catch (RestaurantNotInSystemException e) {

        }
        try {
            network.getRecommendation(network.getStudent(1), r, -1);
            fail("Illegal t :(");
        } catch (ImpossibleConnectionException e) {

        }

        System.out.println("CONGRATULATIONS!!! Share your 10 secret mazes victory with your friends :)");
        System.out.println("HamburgerNetwork::getRecommendation TEST SUCCESSFUL :)");

    }

    @Test

    public void to_String() {
        assertEquals(
                "Registered students: .\n" +
                        "Registered restaurants: .\n" +
                        "Students:\n" +
                        "End students.\n"
                , network.toString());
        IntStream.range(1, 6).forEach(value -> {
                    try {
                        network.joinNetwork(value, "" + STUDENT_NAMES[value - 1]);
                    } catch (StudentAlreadyInSystemException e) {
                        fail("Should work");
                    }
                }
        );
        IntStream.range(1, 51).forEach(value -> {
            try {
                network.addRestaurant(value, "" + RESTAURANT_NAMES[value - 1], value % 5, new HashSet<String>());
            } catch (RestaurantAlreadyInSystemException e) {
                fail("Should work");


            }

        });
        try {

            HungryStudent s1 = network.getStudent(1);
            HungryStudent s2 = network.getStudent(2);
            HungryStudent s3 = network.getStudent(3);
            HungryStudent s4 = network.getStudent(4);
            HungryStudent s5 = network.getStudent(5);
            network.addConnection(s3, s4);
            network.addConnection(s1, s2);
            network.addConnection(s1, s3);
            network.addConnection(s1, s4);
            network.addConnection(s2, s3);
            network.addConnection(s2, s4);
            network.addConnection(s4, s5);
            assertEquals(
                    "Registered students: 1, 2, 3, 4, 5.\n" +
                            "Registered restaurants: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50.\n" +
                            "Students:\n" +
                            "1 -> [2, 3, 4].\n" +
                            "2 -> [1, 3, 4].\n" +
                            "3 -> [1, 2, 4].\n" +
                            "4 -> [1, 2, 3, 5].\n" +
                            "5 -> [4].\n" +
                            "End students.\n"
                    , network.toString());
        } catch (StudentNotInSystemException | ConnectionAlreadyExistsException | SameStudentException e) {
            fail("Shouldn't be an error");
        }
        System.out.println("HamburgerNetwork::toString TEST SUCCESSFUL :)");
    }
}