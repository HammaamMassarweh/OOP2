package OOP.Solution;

import OOP.Provided.HungryStudent;
import OOP.Provided.Restaurant;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HungryStudentImpl implements HungryStudent {

    int id;
    String name;
    HashSet<Restaurant> favorites;
    HashSet<HungryStudent> friends;


    HashMap<Integer,Integer> resRates;

    public HungryStudentImpl(int id, String name) {
        this.id = id;
        this.name = name;
        this.favorites = new HashSet<Restaurant>();
        this.friends = new HashSet<HungryStudent>();
        this.resRates = new HashMap<Integer,Integer>();
    }

    public HashMap<Integer,Integer> getResRates(){
        return resRates;
    }

    // adds the rate to the resturant id and returns the old rate, or -1 if he's rating for the first time.
    public int rateResturant(int resId,int rate) throws Restaurant.RateRangeException{

        if(rate > 5 || rate < 0){
            throw new Restaurant.RateRangeException();
        }

        int oldrate = -1;
        if (resRates.containsKey(resId)){
            oldrate = resRates.get(resId);
        }

        resRates.put(resId,rate);

        return oldrate;
    }

    @Override
    public HungryStudent favorite(Restaurant r) throws UnratedFavoriteRestaurantException {

        if(resRates.containsKey(((RestaurantImpl)r).getId())){

            this.favorites.add(r);

        }else {
            throw new UnratedFavoriteRestaurantException();
        }

        return this;
    }

    @Override
    public Collection<Restaurant> favorites() {
        return this.favorites;
    }

    @Override
    public HungryStudent addFriend(HungryStudent s) throws SameStudentException, ConnectionAlreadyExistsException {

        if(this == s){
            throw new SameStudentException();
        }
        if(this.friends.contains(s)){
            throw new ConnectionAlreadyExistsException();
        }
        this.friends.add(s);


        return null;
    }

    @Override
    public Set<HungryStudent> getFriends() {
        return this.friends;
    }

    private int compareRes(Restaurant r1,Restaurant r2,String RateBy){
        RestaurantImpl myR1 = (RestaurantImpl)r1;
        RestaurantImpl myR2 = (RestaurantImpl)r1;

        int r1Rate = this.resRates.get(myR1.getId());
        int r2Rate = this.resRates.get(myR2.getId());

        int ratingDiff;
        if(RateBy == "distance") {
            ratingDiff = myR1.distance() - myR2.distance();
        }else{
            ratingDiff = r2Rate - r1Rate;
        }
        if(ratingDiff != 0) {
            return ratingDiff;
        }

        int distDiff;
        if(RateBy == "distance") {
            distDiff = r2Rate - r1Rate;
        }else{
            distDiff = myR1.distance() - myR2.distance();
        }
        if(distDiff != 0) {
            return distDiff;
        }

        int idDiff = myR1.getId() - myR2.getId();
        if(idDiff != 0) {
            return idDiff;
        }

        return 0;
    }

    @Override
    public Collection<Restaurant> favoritesByRating(int rLimit) {

        Stream<Restaurant> favStream= this.favorites.stream();

        favStream = favStream.filter( r1 -> this.resRates.get(this.resRates.get(((RestaurantImpl)r1).averageRating())) >= rLimit );

        favStream = favStream.sorted( (r1,r2) -> compareRes(r1,r2,"rating") );

        return favStream.collect(Collectors.toSet());
    }

    @Override
    public Collection<Restaurant> favoritesByDist(int dLimit) {

        Stream<Restaurant> favStream= this.favorites.stream();

        if(favStream == null){
            return null;
        }

        favStream = favStream.filter( r1 -> this.resRates.get(this.resRates.get(r1.distance())) <= dLimit );

        favStream = favStream.sorted( (r1,r2) -> compareRes(r1,r2,"distance") );

        return favStream.collect(Collectors.toSet());
    }

    @Override
    public int compareTo(HungryStudent o) {
        return  this.id - ((HungryStudentImpl)o).id ;
    }

    @Override
    public String toString(){

        ArrayList<String> names = new ArrayList<String>();

        for (Restaurant res: this.favorites) {
            names.add(((RestaurantImpl)res).getName());
        }

        return "Hungry student: " + this.name + ".\n" +
                "Id: " + this.id + ".\n" +
                "Favorites: "+ names.stream().sorted((s1,s2) -> s1.compareTo(s2)).collect(Collectors.toList()).toString().substring(1,names.toString().length()-1) +".\n";
    }
}
