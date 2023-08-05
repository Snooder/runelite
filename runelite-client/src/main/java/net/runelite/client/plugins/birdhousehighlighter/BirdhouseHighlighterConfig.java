package net.runelite.client.plugins.birdhousehighlighter;

import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import java.awt.*;

@ConfigGroup("BirdhouseHighlighter")
public interface BirdhouseHighlighterConfig extends Config {
    enum HighlightBirdhouses {
        OCCUPIED,
        EMPTY,
        BOTH,
        NEITHER,
    }

    @ConfigItem(
            keyName = "highlightBirdhouses",
            name = "Highlight Birdhouses",
            description = "Select which type of birdhouses to highlight.",
            position = 0
    )
    default HighlightBirdhouses highlightBirdhouses() {
        return HighlightBirdhouses.BOTH;
    }

    @Alpha
    @ConfigItem(
            keyName = "occupiedBirdhouseColor",
            name = "Occupied Birdhouse Color",
            description = "Select the color for occupied birdhouses.",
            position = 1
    )
    default Color occupiedBirdhouseColor() {
        return Color.GREEN;
    }

    @Alpha
    @ConfigItem(
            keyName = "emptyBirdhouseColor",
            name = "Empty Birdhouse Color",
            description = "Select the color for empty birdhouses.",
            position = 2
    )
    default Color emptyBirdhouseColor() {
        return Color.RED;
    }

    @Alpha
    @ConfigItem(
            keyName = "completedBirdhouseColor",
            name = "Completed Birdhouse Color",
            description = "Select the color for completed birdhouses.",
            position = 3
    )
    default Color completedBirdhouseColor() {
        return Color.YELLOW;
    }
}
