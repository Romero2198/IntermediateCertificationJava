package ToeShop.Model;

public class Customer implements Comparable<Customer>{
    private String name;
    private String surName;

    public Customer(String name, String surName) {
        this.name = name;
        this.surName = surName;
    }

    @Override
    public String toString() {
        return this.name + " " + this.surName;
    }

    @Override
    public int compareTo(Customer nextCustomer) {
        int result = this.name.compareTo(nextCustomer.name);
        if (result == 0) {
            result = this.surName.compareTo(nextCustomer.surName);
        }
        return result;
    }

}
