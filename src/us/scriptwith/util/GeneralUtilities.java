package us.scriptwith.util;

import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Time;

/**
 * Author: Aadil Farouk
 * Date: 28/02/2013
 * Time: 9:06 PM
 */

public class GeneralUtilities {
    public static String getTimeToLevel(final int skill, final int goal, final int xpPerHour) {
        if (xpPerHour < 1) {
            return Time.format(0L);
        }
        return Time.format((long)
                (Skills.getExperienceToLevel(skill, goal) * 3600000D / xpPerHour));
    }

    public static int getHpPercent() {
        if (!Players.getLocal().isInCombat()) {
            final int height = Widgets.get(748, 5).getHeight();
            final int curHp = Math.abs(100 - 100 * height / 28) * 120 / 100;
            return curHp >= 100 ? 100 : curHp;
        }
        return Players.getLocal().getHealthPercent();
    }

    public static boolean isInCombat() {
        final int stance = Players.getLocal().getPassiveAnimation();
        return stance == 17970 || stance == 17971 || stance == 18011
                || stance == 18012 || stance == 17981 || stance == 17982
                || stance == 17975;
    }

    public static double getHourlyRate(final int base, final long runTime) {
        if (runTime < 1) {
            return 0;
        }
        return (base * 3600000D) / (runTime);
    }
}
