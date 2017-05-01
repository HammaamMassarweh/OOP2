package OOP.Solution;

import OOP.Provided.HungryStudent;
import OOP.Provided.Restaurant;

import java.util.Set;
import java.util.stream.Collectors;


public class RestaurantImpl implements Restaurant {

    private int id;
    private String name;
    private int distFromTech;
    private Set<String> menu;
    private int numberOfRates;
    private double ratesSum;

    public RestaurantImpl(int id, String name, int distFromTech, Set<String> menu) {
        this.id = id;
        this.name = name;
        this.distFromTech = distFromTech;
        this.menu = menu;
        this.numberOfRates=0;
        this.ratesSum=0;
    }


    String getName() {
        return name;
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

    int getId() {
        return id;
    }

    @Override
    public String toString(){

        return "Restaurant: " + this.name + ".\n" +
                "Id: " + this.id + ".\n" +
                "Distance: "+ this.distFromTech +".\n" +
                "Menu: "+ this.menu.stream().sorted().collect(Collectors.toList()).toString().substring(1,this.menu.toString().length()-1) +".";
    }

}
