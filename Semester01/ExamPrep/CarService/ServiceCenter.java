import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ServiceCenter {

    private List<Car> serviceQueue;
    private Map<String, Integer> carsPriority; // licence plate and value of priority, high value higher priority.
    private int gasPrice; // price of each liter of gas

    public ServiceCenter(int gasPrice) {
        this.gasPrice = gasPrice;
        this.carsPriority = new HashMap<>();
        this.serviceQueue = new ArrayList<>();
    }

    // 9
    public void addToServiceQueue(Car c) {

        if (!carsPriority.containsKey(c.getPlate())) {
            System.out.printf("Car %s not found\n", c.getPlate());
            return;
        }
        serviceQueue.add(c);
    }

    // 10
    public int fillGas(Car c) {
        int missingGas = (c.getTankCapacity() - c.getGasLevel());
        int totalPrice = missingGas * gasPrice;

        c.fillTank(missingGas); 
        return totalPrice;
    }

    // 11
    public void updatePriority(Car c, int p) {
        carsPriority.put(c.getPlate(), p);
    }

    // 12
    public void serviceCar(Car c) {
        int fillingCost = fillGas(c);
        if (c.needsService()) {
            c.service();
            fillingCost += 500;
        }
        System.out.printf("Car %s serviced for %d dollars\n", c.getPlate(), fillingCost);
    }

    // 13
    public void serviceAll() {
        Iterator<Car> serviceQIterator = serviceQueue.iterator();
        while (serviceQIterator.hasNext()) {
            Car car = serviceQIterator.next();
            serviceCar(car);
            serviceQIterator.remove();
        }
    }

    // 14   The strictly greater-than ensures that its the lowest / smallest index 

    public int findHighestPriority() {

        int highestPrio = -1;
        int highestPrioIndex = 0;

        if (serviceQueue.size() <= 0) {
            return highestPrio;
        }

        for (int i = 0; i < serviceQueue.size(); i++) {
            String plate = serviceQueue.get(i).getPlate();
            int currentPriority = carsPriority.get(plate);

            if (currentPriority > highestPrio) {
                highestPrio = currentPriority;
                highestPrioIndex = i;
            }
        }

        return highestPrioIndex;
    }

    // 15
    public void serviceAllWithPriority() {
        // A while loop works for removing here because it 
        // looks ever iteration for emptyness not length of a list. 
        // ".isEmpty" is good when all items are being removed. 
        while (!serviceQueue.isEmpty()) { 
            int index = findHighestPriority();
            Car c = serviceQueue.get(index);
            serviceCar(c);
            serviceQueue.remove(index);
        }
    }
}
