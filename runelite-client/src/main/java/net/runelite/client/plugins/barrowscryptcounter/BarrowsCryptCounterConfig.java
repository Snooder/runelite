package net.runelite.client.plugins.barrowscryptcounter;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("barrowscryptcounter")
public interface BarrowsCryptCounterConfig extends Config
{
    @ConfigItem(
            keyName = "label",
            name = "Target Kill Counts",
            description = "",
            position = 0
    )
    default String label() {
        return null;
    }

    @ConfigItem(
            keyName = "targetCryptRat",
            name = "Crypt Rat",
            description = "Set the target number for Crypt Rat kills"
    )
    default int targetCryptRat() {
        return 0;
    }

    @ConfigItem(
            keyName = "targetBloodworm",
            name = "Bloodworm",
            description = "Set the target number for Bloodworm kills"
    )
    default int targetBloodworm() {
        return 0;
    }

    @ConfigItem(
            keyName = "targetCryptSpider",
            name = "Crypt Spider",
            description = "Set the target number for Crypt Spider kills"
    )
    default int targetCryptSpider() {
        return 0;
    }

    @ConfigItem(
            keyName = "targetGiantCryptRat",
            name = "Giant Crypt Rat",
            description = "Set the target number for Giant Crypt Rat kills"
    )
    default int targetGiantCryptRat() {
        return 0;
    }

    @ConfigItem(
            keyName = "targetSkeleton",
            name = "Skeleton",
            description = "Set the target number for Skeleton kills"
    )
    default int targetSkeleton() {
        return 0;
    }

    @ConfigItem(
            keyName = "targetGiantCryptSpider",
            name = "Giant Crypt Spider",
            description = "Set the target number for Giant Crypt Spider kills"
    )
    default int targetGiantCryptSpider() {
        return 0;
    }
}
