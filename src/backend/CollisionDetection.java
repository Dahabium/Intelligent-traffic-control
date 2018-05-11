package backend;


import java.awt.*;
import java.util.ArrayList;

public class CollisionDetection {

    private ArrayList<Car> cars;
    private int width = 25, height = 12;

    public CollisionDetection(){
        this.cars = new ArrayList<>();
    }

    public void addCar(Car car){
        this.cars.add(car);
    }



    public boolean collisionDetection()
    {

//        Car[] intersect = new Car[2];
//        ArrayList<Rectangle> rectangles = new ArrayList<>();
//        //bounding boxes, if box1.contains(box2) true;
//        for(Car c : cars)
//        {
//            Rectangle temp = new Rectangle(width, height, (int)c.getLocX(), (int)c.getLocY());
//            rectangles.add(temp);
//        }
//
//        for(int i = 0; i<rectangles.size(); i++)
//        {
//            for(int j = 0; j<rectangles.size(); j++)
//            {
//                if(rectangles.get(i).intersects(rectangles.get(j)))
//                {
//                    intersect[0] = cars.get(i);
//                    intersect[1] = cars.get(j);
//
//                    return true;
//                }
//            }
//        }
        if(cars.size() == 2){
            if(Math.abs(cars.get(0).getLocX() - cars.get(1).getLocX()) < 20 && Math.abs(cars.get(0).getLocY() - cars.get(1).getLocY()) < 20){
                return true;
            }
            else return false;
        }

        return false;

    }
}
