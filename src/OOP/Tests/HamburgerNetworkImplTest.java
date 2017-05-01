package OOP.Tests;

import OOP.Provided.HamburgerNetwork;
import OOP.Provided.HungryStudent;
import OOP.Provided.HungryStudent.StudentAlreadyInSystemException;
import OOP.Provided.Restaurant;
import OOP.Provided.Restaurant.RestaurantAlreadyInSystemException;
import OOP.Solution.HamburgerNetworkImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

/**
 * Created by owais on 01-May-17.
 */
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


    @Before
    public void setup() {
        clearNetwork();
    }

    @Test
    public void joinNetwork() throws Exception {
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
    public void addRestaurant() throws Exception {
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
    public void registeredStudents() throws Exception {
        HungryStudent[] s1 = new HungryStudent[]{};
        assertArrayEquals(s1, network.registeredStudents().stream().sorted().toArray());

        registerBastards();
        HungryStudent[] s2 = new HungryStudent[]{jon, arya, daenerys, theHound, jorah, daario, bran, theThreeEyedRaven, hodor, meera};
        assertArrayEquals(s2, network.registeredStudents().stream().sorted().toArray());

        System.out.println("test3_registeredStudents - V");
    }

    @Test
    public void registeredRestaurants() throws Exception {
    }

    @Test
    public void getStudent() throws Exception {
    }

    @Test
    public void getRestaurant() throws Exception {
    }

    @Test
    public void addConnection() throws Exception {
    }

    @Test
    public void favoritesByRating() throws Exception {
    }

    @Test
    public void favoritesByDist() throws Exception {
    }

    @Test
    public void BFS_friends() throws Exception {
    }

    @Test
    public void getRecommendation() throws Exception {
    }



}