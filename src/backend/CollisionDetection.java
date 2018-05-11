package backend;

import java.util.ArrayList;

public class CollisionDetection {

    private ArrayList<Car> cars;

    public CollisionDetection() {
        this.cars = new ArrayList<>();
    }

    public void addCar(Car car) {
        this.cars.add(car);
    }

    public boolean collisionDetection() {

        if (cars.size() == 1) {
            return false;
        }

        for (int i = 0; i < cars.size(); i++) {

            System.out.println(" car number " + i + " X:" + cars.get(i).getLocX() + "  Y:" + cars.get(i).getLocY());

            for (int j = 0; j < cars.size(); j++) {

                 if(cars.get(i).getLocX() <= cars.get(j).getLocX() + 25 &&
                    cars.get(i).getLocX() + 25 >= cars.get(j).getLocX() &&
                    cars.get(i).getLocY() <= cars.get(j).getLocY() + 25 &&
                    cars.get(i).getLocY() + 25 >= cars.get(j).getLocY() &&
                         cars.get(i) != cars.get(j)){
                    return true;
                 }
            }

        }

        return false;

    }

}
