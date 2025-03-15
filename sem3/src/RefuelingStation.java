import Enums.FuelType;

public class RefuelingStation implements Refueling {
    @Override
    public void fuel(FuelType fuelType) {
        switch (fuelType) {
            case Diesel -> System.out.println("Now refueling diesel");
            case Gasoline -> System.out.println("Now refueling gasoline");
        }
    }
}
