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


    private ArrayList<Restaurant> Restaurants;
    private ArrayList<HungryStudent> HungryStudents;

    public HamburgerNetworkImpl() {
        Restaurants = new ArrayList<Restaurant>();
        HungryStudents = new ArrayList<HungryStudent>();
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
        return HungryStudents;
    }

    public Collection<Restaurant> registeredRestaurants() {
        return Restaurants;
    }

    public HungryStudent getStudent(int id) throws HungryStudent.StudentNotInSystemException {
        for ( HungryStudent s : HungryStudents) {
            if ( id == ((HungryStudentImpl)s).getId() ) return  s;

        }
        throw  new HungryStudent.StudentNotInSystemException();
    }

    public Restaurant getRestaurant(int id) throws Restaurant.RestaurantNotInSystemException {

        for ( Restaurant r : Restaurants) {
            if ( id == ((RestaurantImpl)r).getId() ) return  r;

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

   // duple code !!!!!!!!!!
    public Collection<Restaurant> favoritesByRating(HungryStudent s) throws HungryStudent.StudentNotInSystemException {
        if (!HungryStudents.contains(s)) { throw new HungryStudent.StudentNotInSystemException() ;}
        ArrayList<Restaurant> res = new  ArrayList<Restaurant>();
        List<HungryStudent> friendsList = s.getFriends().stream().sorted().collect(Collectors.toList());
        for (HungryStudent HS : friendsList ) {

            for (Restaurant favoriteRestaurant : HS.favoritesByRating(0) ) {
                if(res.contains(favoriteRestaurant)){
                    continue;
                }
                res.add(favoriteRestaurant);
            }

            //res.addAll(HS.favoritesByRating(0));
        }

        return res;
    }


    public Collection<Restaurant> favoritesByDist(HungryStudent s) throws HungryStudent.StudentNotInSystemException {
        if (!HungryStudents.contains(s)) { throw new HungryStudent.StudentNotInSystemException() ;}
        ArrayList<Restaurant> res = new ArrayList<Restaurant>();
        List<HungryStudent> friendsList = s.getFriends().stream().sorted().collect(Collectors.toList());
        for (HungryStudent HS : friendsList ) {

            for (Restaurant favoriteRestaurant : HS.favoritesByDist( Integer.MAX_VALUE) ) {
                if(res.contains(favoriteRestaurant)){
                    continue;
                }
                res.add(favoriteRestaurant);
            }

            //res.addAll(HS.favoritesByDist( Integer.MAX_VALUE));
        }

        return res;
    }


    static  boolean BFS_friends(HungryStudent s, Restaurant r, int t,HashSet<HungryStudent> dontPass) {
        if( t < 0 ) return  false;
        if (dontPass.contains(s)) return  false;
        if ( s.favorites().contains(r)) return true;
        dontPass.add(s);
        for (HungryStudent f : s.getFriends() ) {
            if(BFS_friends(f,r,t-1,dontPass)) return true;
        }
        dontPass.remove(s);
        return false;
    }

 /*
    static  boolean BFS_friends(HungryStudent s, Restaurant r, int t,HashSet<HungryStudent> dontPass) {
        if( t < 0 ) return  false;
        if (dontPass.contains(s)) return  false;
        if ( s.favorites().contains(r)) return true;
        dontPass.add(s);
        for (HungryStudent f : s.getFriends(). ) {
            return BFS_friends(f,r,t-1,dontPass);
        }
        return false;
    }
*/
    public boolean getRecommendation(HungryStudent s, Restaurant r, int t) throws HungryStudent.StudentNotInSystemException, Restaurant.RestaurantNotInSystemException, ImpossibleConnectionException {
        if (!HungryStudents.contains(s)) { throw new HungryStudent.StudentNotInSystemException() ;}
        if (!Restaurants.contains(r)) { throw new Restaurant.RestaurantNotInSystemException() ;}
        if ( t < 0 ) throw new ImpossibleConnectionException();
        HashSet<HungryStudent> dontPass = new HashSet<HungryStudent>();
        return BFS_friends(s,r,t,dontPass);
    }

    @Override
    public String toString(){

        return "Registered students: " + registeredStudents().toString() + ".\n"  +
                "Registered restaurants: " ;

        /**
         * @return the network's description as a string in the following format:
         * <format>
         * Registered students: <studentId1, studentId2, studentId3...>.
         * Registered restaurants: <resId1, resId2, resId3...>.
         * Students:
         * <student1Id> -> [<friend1Id, friend2Id, friend3Id...>].
         * <student2Id> -> [<friend1Id, friend2Id, friend3Id...>].
         * ...
         * End students.
         * </format>
         * Note: students, restaurants and friends' ids are ordered by natural integer order, asc.*
         * Example:
         *
         * Registered students: 1, 236703, 555555.
         * Registered restaurants: 12, 13.
         * Students:
         * 1 -> [236703, 555555555].
         * 236703 -> [1].
         * 555555 -> [1].
         * End students.
         * */

    }
}
