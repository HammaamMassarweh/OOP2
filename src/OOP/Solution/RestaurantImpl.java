package OOP.Solution;

import OOP.Provided.HungryStudent;
import OOP.Provided.Restaurant;

import java.util.Set;
import java.util.stream.Collectors;


public class RestaurantImpl implements Restaurant {

    int id;

    public String getName() {
        return name;
    }

    String name;
    int distFromTech;
    Set<String> menu;
    int numberOfRates;
    double ratesSum;

    public RestaurantImpl(int id, String name, int distFromTech, Set<String> menu) {
        this.id = id;
        this.name = name;
        this.distFromTech = distFromTech;
        this.menu = menu;
        this.numberOfRates=0;
        this.ratesSum=0;
    }


    public int distance() {
        return distFromTech;
    }


    public Restaurant rate(HungryStudent s, int r) throws RateRangeException {

        if(r < 0 || r>5) {
            throw new RateRangeException();
        }

        int oldRate = ((HungryStudentImpl)s).rateResturant(this.id,r);

        if(oldRate == -1){
            numberOfRates++;
            ratesSum += r;
        }
        else{
            ratesSum += r-oldRate;
        }

        return this;
    }


    public int numberOfRates() {
        return numberOfRates;
    }


    public double averageRating() {
        if(numberOfRates == 0){
            return numberOfRates;
        }
        return ratesSum/numberOfRates;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RestaurantImpl)) {
            return false;
        }
        return this.id == ((RestaurantImpl)o).id;
    }
    public int compareTo(Restaurant o) {
        return  this.id - ((RestaurantImpl)o).id ;

    }

    public int getId() {
        return id;
    }

    public int compareByRating(Restaurant o) {
        if ( this.averageRating() > ((RestaurantImpl)o).averageRating()) return  -1;
             else if ( this.averageRating() == ((RestaurantImpl)o).averageRating() ) {
                 if ( this.distance() < ((RestaurantImpl)o).distance()    ) return -1;
                    else if ( this.distance() == ((RestaurantImpl)o).distance()) {
                            if ( this.id > ((RestaurantImpl)o).id ) return -1;
                 }
        }
        return 1;

    }

    @Override
    public String toString(){

        return "Restaurant: " + this.name + ".\n" +
                "Id: " + this.id + ".\n" +
                "Distance: "+ this.distFromTech +".\n" +
                "Menu: "+ this.menu.stream().sorted((s1,s2) -> s1.compareTo(s2)).collect(Collectors.toList()).toString().substring(1,this.menu.toString().length()-1) +".\n";
    }

}
