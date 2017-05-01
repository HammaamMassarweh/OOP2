package OOP.Solution;
import OOP.Provided.HamburgerNetwork;
import OOP.Provided.HungryStudent;
import OOP.Provided.Restaurant;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by owais on 01-May-17.
 */

public class HamburgerNetworkImpl implements HamburgerNetwork {

    ArrayList<RestaurantImpl> Restaurants;
    ArrayList<HungryStudentImpl> HungryStudents;

    public HamburgerNetworkImpl() {
        Restaurants = new ArrayList<RestaurantImpl>();
        HungryStudents = new ArrayList<HungryStudentImpl>();
    }


    public HungryStudent joinNetwork(int id, String name) throws HungryStudent.StudentAlreadyInSystemException {
        HungryStudentImpl newHungryStudent = new HungryStudentImpl(id,name);
        if(HungryStudents.contains(newHungryStudent)) throw new HungryStudent.StudentAlreadyInSystemException();
        HungryStudents.add(newHungryStudent);
        return newHungryStudent;
    }

    public Restaurant addRestaurant(int id, String name, int dist, Set<String> menu) throws Restaurant.RestaurantAlreadyInSystemException {
        RestaurantImpl newRestaurant = new RestaurantImpl(id,name,dist,menu);
        if ( Restaurants.contains(newRestaurant)) throw  new Restaurant.RestaurantAlreadyInSystemException();
        Restaurants.add(newRestaurant);
        return  newRestaurant;
    }

    public Collection<HungryStudent> registeredStudents() {
        return null;
    }


    public Collection<Restaurant> registeredRestaurants() {
        return null;
    }


    public HungryStudent getStudent(int id) throws HungryStudent.StudentNotInSystemException {
        for ( HungryStudentImpl s : HungryStudents) {
            if ( id == s.id ) return  s;
        }
        throw  new HungryStudent.StudentNotInSystemException();
    }


    public Restaurant getRestaurant(int id) throws Restaurant.RestaurantNotInSystemException {
        for ( RestaurantImpl r : Restaurants) {
            if ( id == r.id ) return  r;
        }
        throw  new Restaurant.RestaurantNotInSystemException();
    }


    public HamburgerNetwork addConnection(HungryStudent s1, HungryStudent s2) throws HungryStudent.StudentNotInSystemException, HungryStudent.ConnectionAlreadyExistsException, HungryStudent.SameStudentException {
        if ( !(HungryStudents.contains(s1)) || !(HungryStudents.contains(s2)  )) {
            throw new HungryStudent.StudentNotInSystemException();
        }
        s1.addFriend(s2);
        s2.addFriend(s1);
        return this;
    }


    public Collection<Restaurant> favoritesByRating(HungryStudent s) throws HungryStudent.StudentNotInSystemException {
       HashSet<Restaurant> res = new  HashSet<Restaurant>();
        List<HungryStudent> friendsList = s.getFriends().stream().sorted().collect(Collectors.toList());
        for (HungryStudent HS : friendsList ) {
            for (Restaurant favoriteRestaurant : HS.favoritesByRating(0) ) {

            }
        }
        return null;
    }


    public Collection<Restaurant> favoritesByDist(HungryStudent s) throws HungryStudent.StudentNotInSystemException {
        return null;
    }


    public boolean getRecommendation(HungryStudent s, Restaurant r, int t) throws HungryStudent.StudentNotInSystemException, Restaurant.RestaurantNotInSystemException, ImpossibleConnectionException {
        return false;
    }
}
