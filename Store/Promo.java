package ToeShop.Store;

import ToeShop.Data.FileHandler;
import ToeShop.Model.Customer;
import ToeShop.Model.Toys.Toy;
import ToeShop.Model.Toys.ToyCreator;

import java.util.ArrayList;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.Random;

public class Promo {
    private ArrayList<ToyCreator> promotionalToys;
    private PriorityQueue<Customer> customersQueue;

    public Promo(ToyStore toystore) {
        this.promotionalToys = new ArrayList<>();
        this.customersQueue = toystore.getCustomersQueue();

        for (Toy toy : toystore.getToys()) {
            if (toy instanceof ToyCreator) {
                this.promotionalToys.add((ToyCreator) toy);
            }
        }
    }

    public void runPromo() {
        Date date = new Date();
        FileHandler fm = new FileHandler();
        fm.addToFile("Winners.txt", date.toString() + "\n");
        while (this.customersQueue.size() > 0) {
            ToyCreator prize = getPrizeToy();
            if (prize != null) {
                String message = this.customersQueue.poll().toString() + " выиграл Приз №" + prize.getId() + " - "
                        + prize.getName();
                System.out.println(message);
                fm.addToFile("Winners.txt", message);
            } else {
                System.out.println(this.customersQueue.poll().toString() + "\u001B[31m Ничего не выиграл\u001B[0m");
            }
        }
        fm.addToFile("Winners.txt", "___________________________________________________________________");
    }

    public ToyCreator getPrizeToy() {
        Random random = new Random();
        int prizeIndex = random.nextInt(100);
        ToyCreator prize = createPrizeTable()[prizeIndex];
        if (prize != null) {
            prize.setCount(prize.getCount() - 1);
            prize.setPromoCount(prize.getPromoCount() - 1);
            if (!checkPromoCount(prize)) {
                promotionalToys.remove(prize);
            }
        }
        return prize;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Toy toy : promotionalToys) {
            sb.append(toy.toString() + "\n");
        }

        return sb.toString();
    }

    private ToyCreator[] createPrizeTable() {
        if (!checkFrequencySum()) {
            throw new ArrayIndexOutOfBoundsException(
                    "Сумма frequency всех экземпляров игрушек в розыгрыше привышает 100");
        }
        int size = 100;
        int index = 0;
        int count;
        ToyCreator[] prizes = new ToyCreator[size];
        for (ToyCreator toy : promotionalToys) {
            count = toy.getFrequency();
            while (count > 0) {
                if (prizes[index] == null) {
                    prizes[index] = toy;
                    count--;
                    index++;
                }
            }
        }
        return prizes;
    }

    private boolean checkFrequencySum() {
        int sum = 0;
        for (ToyCreator toy : promotionalToys) {
            sum += toy.getFrequency();
        }
        return sum <= 100;
    }

    private boolean checkPromoCount(ToyCreator toy) {
        return toy.getPromoCount() != 0;
    }

}
