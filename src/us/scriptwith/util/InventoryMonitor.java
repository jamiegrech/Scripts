package us.scriptwith.util;

import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Aadil Farouk
 * Date: 4/25/13
 * Time: 1:47 PM
 */

public abstract class InventoryMonitor {
    private Item[] cacheItems = new Item[0];

    public InventoryMonitor() {
        update();
    }

    /**
     * user-defined method determining the actions to take when the inventory changes
     */
    public abstract void onChange();

    /**
     * @return true if the inventory has changed; false otherwise
     */
    public boolean hasChanged() {
        return getChanges().length > 0;
    }

    /**
     * @return an array of items representing the changes in the inventory
     */
    public Item[] getChanges() {
        List<Item> changedItems = new ArrayList<>();
        for (Item inventoryItem : Inventory.getItems()) {
            final int curCount = Inventory.getCount(true, inventoryItem.getId());
            if (curCount > cacheStack(inventoryItem.getId())) {
                if (!contains(changedItems.toArray(new Item[changedItems.size()]), inventoryItem.getId())) {
                    changedItems.add(new Item(inventoryItem.getId(), curCount - cacheStack(inventoryItem.getId())));
                }
            }
        }
        return changedItems.toArray(new Item[changedItems.size()]);
    }

    /**
     * sets the cache to the user's current inventory
     */
    public void update() {
        cacheItems = Inventory.getItems();
    }

    private boolean contains(Item[] arr, int key) {
        for (Item i : arr) {
            if (i.getId() == key) {
                return true;
            }
        }
        return false;
    }

    private int cacheStack(int id) {
        int stack = 0;
        for (Item i : cacheItems) {
            if (i.getId() == id) {
                stack += i.getStackSize();
            }
        }
        return stack;
    }
}
