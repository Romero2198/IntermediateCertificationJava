package ToeShop.Store;

import ToeShop.Model.Customer;
import ToeShop.Model.Toys.Toy;
import ToeShop.Model.Toys.ToyCreator;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class ToyStore {
    private ArrayList<Toy> toys;
    private PriorityQueue<Customer> customersQueue;

    public ToyStore() {
        this.toys = new ArrayList<>();
        this.customersQueue = new PriorityQueue<>();
    }

    public void putToy(Toy toy) {
        this.toys.add(toy);
    }

    public void putCustomer(Customer customer) {
        this.customersQueue.add(customer);
    }

    public ArrayList<Toy> getToys() {
        return this.toys;
    }

    public PriorityQueue<Customer> getCustomersQueue() {
        return customersQueue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\u001B[32mНаличие игрушек в магазине:\u001B[0m\n");
        for (Toy toy : toys) {
            if (toy instanceof ToyCreator) {
                ToyCreator toyCreator= (ToyCreator) toy;
                if (toyCreator.getPromoCount() == 0) {
                    sb.append(toy.toString() + "\u001B[31m РАЗЫГРАНЫ\u001B[0m\n");
                } else {
                    sb.append(toy.toString() + "\u001B[36m Участвует в розыгрыше(остаток "
                            + toyCreator.getPromoCount()
                            + ")\u001B[0m\n");
                }
            } else {
                sb.append(toy.toString() + "\n");
            }
        }
        return sb.toString();
    }

}
