import java.awt.*;

public class SportCar extends Car {
    public SportCar(String make, String model, Color color) {
        super(make, model, color);
        setWheelsCount(3);
    }

    @Override
    public void movement() {

    }

    @Override
    public void maintenance() {

    }

    @Override
    public boolean gearShifting() {
        return false;
    }

    @Override
    public boolean switchHeadlights() {
        return false;
    }

    @Override
    public boolean switchWipers() {
        return false;
    }
}
