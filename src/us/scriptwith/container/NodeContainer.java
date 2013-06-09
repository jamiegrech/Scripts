package us.scriptwith.container;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 6/8/13
 * Time: 6:34 PM
 */

public class NodeContainer extends Tree {
    private static List<Node> nodeList = new ArrayList<>();

    public NodeContainer() {
        super(nodeList.toArray(new Node[nodeList.size()]));
    }

    public static NodeContainer submit(final Node... nodes) {
        for (Node n : nodes) {
            if (!nodeList.contains(n)) {
                nodeList.add(n);
            }
        }
        return new NodeContainer();
    }
}
