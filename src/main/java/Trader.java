/* Below is the Trader class, which functions similarly to the one from
 * Lab 3, but with a handful of differences
 *
 * This implementation of Trader uses the generic type T instead of
 * Tradable: our Trader can have objects of any type, not just Tradable
 * ones! To have our original Trader class from Lab 3, we can
 * create a Trader<Tradable> object, as that will enforce that every
 * object is Tradable.
 *
 * Go through the TODOs below and complete them.
 */

import java.util.ArrayList;
import java.util.List;

public class Trader<T> {
    private final List<T> inventory;
    private final List<T> wishlist;
    private int money;

    public Trader(List<T> inventory, List<T> wishlist, int money) {
        this.inventory = inventory;
        this.wishlist = wishlist;
        this.money = money;
    }

    // TODO: Add a new constructor that takes a single argument
    // representing the Trader's money. Give the Trader
    // empty ArrayLists for their inventory and wishlist.
    public Trader(int money) {
        this(new ArrayList<>(), new ArrayList<>(), money);
    }

    // TODO: Implement the method addToWishlist that takes an
    // object of type T and adds it to this Trader's wishlist.
    public void addToWishlist(T item) {
        wishlist.add(item);
    }

    // TODO: Implement the method getSellingPrice that takes an
    // object of type T and returns the object's price
    // (via getPrice()) if it's Tradable. If not,
    // return Tradable.MISSING_PRICE.
    public int getSellingPrice(T item) {
        if (item instanceof Tradable) {
            return ((Tradable) item).getPrice();
        } else {
            return Tradable.MISSING_PRICE;
        }
    }

    public boolean exchangeMoney(Trader<T> other, T item) {
        int selling_price = this.getSellingPrice(item);
        if (selling_price == Tradable.MISSING_PRICE) {
            return false;
        }

        if (selling_price <= other.money) {
            other.money -= selling_price;
            this.money += selling_price;
            return true;
        }
        return false;
    }

    public boolean sellTo(Trader<T> other) {
        boolean sold_at_least_one = false;
        List<T> temp = new ArrayList<>();

        for (T item : this.inventory) {
            if (other.wishlist.contains(item) && exchangeMoney(other, item)) {
                temp.add(item);
                sold_at_least_one = true;
            }
        }

        this.inventory.removeAll(temp);
        other.inventory.addAll(temp);
        other.wishlist.removeAll(temp);

        return sold_at_least_one;
    }

    public boolean buyFrom(Trader<T> other) {
        return other.sellTo(this);
    }

    @Override
    public String toString() {
        StringBuilder details = new StringBuilder("-- Inventory --\n");

        for (T item : this.inventory) {
            details.append(item).append("\n");
        }

        details.append("-- Wishlist --\n");
        for (T item : this.wishlist) {
            details.append(item).append("\n");
        }

        return details.toString();
    }

    public List<T> getWishlist(){
        return this.wishlist;
    }

    public List<T> getInventory(){
        return this.inventory;
    }
}
