package us.scriptwith.scripts.tea.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

/**
 * Author: Aadil Farouk
 * Date: 6/8/13
 * Time: 6:24 PM
 */

public class Steal extends Node {
    private final int stallId = 635;
    private final Tile standOn = new Tile(3268, 3410, 0);
    private SceneObject stall = null;

    @Override
    public boolean activate() {
        if (stall == null || !stall.validate()) {
            stall = SceneEntities.getNearest(stallId);
        }
        return stall != null && stall.validate() && stall.isOnScreen() && !Inventory.isFull();
    }

    @Override
    public void execute() {
        if (!Players.getLocal().getLocation().equals(standOn)) {
            standOn.interact("Walk here");
            do Task.sleep(250); while (Players.getLocal().isMoving());
            return;
        }
        if (stall.interact("Steal")) {
            Task.sleep(750);
            Camera.turnTo(stall);
        }
    }
}
