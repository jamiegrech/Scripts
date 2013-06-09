package us.scriptwith.scripts.tea;

import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.tab.Skills;
import us.scriptwith.container.NodeContainer;
import us.scriptwith.scripts.tea.nodes.Drop;
import us.scriptwith.scripts.tea.nodes.Steal;

import java.awt.*;

import static us.scriptwith.util.GeneralUtilities.getHourlyRate;

/**
 * Date: 6/8/13
 * Time: 6:23 PM
 */

@Manifest(name = "Tea Thief", authors = "Coma", description = "Steals tea")
public class TeaStall extends ActiveScript implements PaintListener, MessageListener {
    private Tree container = NodeContainer.submit(new Steal(), new Drop());
    private long timeStart;
    private int startXp;

    @Override
    public void onStart() {
        if (!Game.isLoggedIn()) {
            shutdown();
            return;
        }
        timeStart = System.currentTimeMillis();
        startXp = Skills.getExperience(Skills.THIEVING);
    }

    @Override
    public int loop() {
        final Node n = container.state();
        if (n != null) {
            getContainer().submit(n);
            container.set(n);
            n.join();
        }
        return 250;
    }

    @Override
    public void messageReceived(MessageEvent messageEvent) {
        final String msg = messageEvent.getMessage().toLowerCase();
        if (messageEvent.getSender().equals("") && msg.contains("you steal a cup of tea")) {
            cupsStolen++;
        }
    }

    private int cupsStolen = 0;
    private final AlphaComposite background = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .7f);
    private final AlphaComposite text = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);

    @Override
    public void onRepaint(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;

        final long runTime = System.currentTimeMillis() - timeStart;
        final int xpGained = Skills.getExperience(Skills.THIEVING) - startXp;
        final double cupsHr = getHourlyRate(cupsStolen, runTime);
        final double xpHr = getHourlyRate(xpGained, runTime);

        g.setColor(Color.BLACK);
        g.setComposite(background);
        g.fillRect(6, 56, 149, 69);

        g.setColor(Color.WHITE);
        g.setComposite(text);
        g.drawRect(5, 55, 150, 70);

        g.setFont(new Font("Tahoma", Font.PLAIN, 16));
        g.drawString("Tea Stealer", 42, 80);

        g.setFont(new Font("Tahoma", Font.PLAIN, 12));
        g.drawString("XP: " + xpGained + " (" + (int) xpHr + ")", 10, 100);
        g.drawString("Steals: " + cupsStolen + " (" + (int) cupsHr + ")", 10, 120);
    }
}
