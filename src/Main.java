import OOP.Provided.HungryStudent;
import OOP.Provided.Restaurant;
import OOP.Solution.HamburgerNetworkImpl;
import OOP.Solution.HungryStudentImpl;
import OOP.Solution.RestaurantImpl;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by owais on 01-May-17.
 */
public class Main {
    public static void main(String [ ] args) {
        HamburgerNetworkImpl HN = new HamburgerNetworkImpl();
        try {
            HN.joinNetwork(1,"s1");
            HN.joinNetwork(2,"s2");
            HN.joinNetwork(3,"s1");
        } catch (HungryStudent.StudentAlreadyInSystemException e) {
            System.out.println(" ? ? ?");
        }

        Set<String> menu1;
        Set<String> menu2;
        Set<String> menu3;
        Set<String> menu4;

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

        try {
            HN.addRestaurant(1,"1r",100,menu1);
            HN.addRestaurant(2,"1r",100,menu1);
        } catch (RestaurantImpl.RestaurantAlreadyInSystemException e) {
            System.out.println(" + + + + +");
        }

        for (HungryStudent a : HN.registeredStudents() ) {
            System.out.println( a.toString());
        }

        for (Restaurant a : HN.registeredRestaurants() ) {
            System.out.println( a.toString());
        }

        try {
            HungryStudent aa =  HN.getStudent(1);
            System.out.println ( aa.toString());
         } catch (HungryStudentImpl.StudentNotInSystemException e ) {
            System.out.println(" ooooo " );
        }


        System.out.println("aaaaa");

    }
}
