import java.util.*;
import java.util.stream.Stream;

/**
 * Created by Hamam on 4/30/2017.
 */
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

    @Override
    public Collection<Restaurant> favoritesByRating(int rLimit) {

        Stream<Restaurant> favStream= this.favorites.stream();

        favStream = favStream.filter( r1 -> this.resRates.get(this.resRates.get(((RestaurantImpl)r1).getId())) >= rLimit );

        favStream = favStream.sorted( (r1,r2) -> 0 );

        return null;
    }

    @Override
    public Collection<Restaurant> favoritesByDist(int dLimit) {
        return null;
    }

    @Override
    public int compareTo(HungryStudent o) {
        return 0;
    }
}
