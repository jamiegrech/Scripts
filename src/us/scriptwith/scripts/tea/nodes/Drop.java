package us.scriptwith.scripts.tea.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.Item;

/**
 * Author: Aadil Farouk
 * Date: 6/8/13
 * Time: 6:24 PM
 */

public class Drop extends Node {
    private final int cupOfTea = 1978;
    private final int emptyCup = 1980;

    @Override
    public boolean activate() {
        return Inventory.getCount(cupOfTea, emptyCup) > 0;
    }

    @Override
    public void execute() {
        for (Item i : Inventory.getItems()) {
            if (i.getId() == cupOfTea || i.getId() == emptyCup) {
                i.getWidgetChild().interact("Drop");
                Task.sleep(500);
            }
        }
    }
}
