package us.scriptwith.util;

import org.powerbot.core.script.job.LoopTask;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.wrappers.node.Item;

/**
 * Author: Aadil Farouk
 * Date: 5/5/13
 * Time: 1:32 AM
 */

public class ProfitTask extends LoopTask {
    private static int profit = 0;
    private PriceWrapper priceWrapper;
    private InventoryMonitor cache;

    @Override
    public int loop() {
        if (Game.isLoggedIn() && !Bank.isOpen() && cache != null && cache.hasChanged()) {
            cache.onChange();
        }

        return 250;
    }

    /**
     * @param itemIds the item IDs to add to priceMap. Pass known IDs to save connection times.
     */
    public ProfitTask(final int... itemIds) {
        priceWrapper = new PriceWrapper(itemIds);
        cache = new InventoryMonitor() {
            @Override
            public void onChange() {
                priceWrapper.add(getChanges());
                for (Item i : getChanges()) {
                    profit += (priceWrapper.getPrice(i.getId()) * i.getStackSize());
                }
                update();
            }
        };
    }

    /**
     * @return total profit gained
     */
    public static int getTotalProfit() {
        return profit;
    }
}
