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
    private final int[] tea = {1978, 1980};

    @Override
    public boolean activate() {
        return Inventory.getCount(tea) > 0;
    }

    @Override
    public void execute() {
        final Item teaCup = Inventory.getItem(tea);
        if (teaCup != null && teaCup.getWidgetChild().interact("Drop")) {
            Task.sleep(250);
        }
    }
}
