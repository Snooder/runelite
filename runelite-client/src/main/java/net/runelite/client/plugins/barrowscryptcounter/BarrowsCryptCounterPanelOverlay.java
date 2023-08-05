package net.runelite.client.plugins.barrowscryptcounter;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.List;
import javax.inject.Inject;

import net.runelite.api.Client;
import net.runelite.api.Varbits;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;

public class BarrowsCryptCounterPanelOverlay extends Overlay {
    private final BarrowsCryptCounterPlugin plugin;
    private final PanelComponent panelComponent;
    private static final DecimalFormat REWARD_POTENTIAL_FORMATTER = new DecimalFormat("##0.00%");
    private final Client client;
    private final BarrowsCryptCounterConfig config;

    @Inject
    private BarrowsCryptCounterPanelOverlay(BarrowsCryptCounterPlugin plugin, Client client, BarrowsCryptCounterConfig config) {
        setPosition(OverlayPosition.TOP_LEFT);
        this.plugin = plugin;
        this.client = client;
        this.config = config;
        panelComponent = new PanelComponent();
        panelComponent.setBorder(new Rectangle(5, 5, 5, 5));
        panelComponent.setBackgroundColor(new Color(0, 0, 0, 150));
    }

    private int rewardPotential()
    {
        // this is from [proc,barrows_overlay_reward]
        int brothers = client.getVarbitValue(Varbits.BARROWS_KILLED_AHRIM)
                + client.getVarbitValue(Varbits.BARROWS_KILLED_DHAROK)
                + client.getVarbitValue(Varbits.BARROWS_KILLED_GUTHAN)
                + client.getVarbitValue(Varbits.BARROWS_KILLED_KARIL)
                + client.getVarbitValue(Varbits.BARROWS_KILLED_TORAG)
                + client.getVarbitValue(Varbits.BARROWS_KILLED_VERAC);
        return client.getVarbitValue(Varbits.BARROWS_REWARD_POTENTIAL) + brothers * 2;
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        // Fetch the kill count data from the plugin
        Map<String, Integer> killCounts = plugin.getKillCounts();
        List npcList = plugin.getNpcList();

        // Clear the panel before adding components to avoid duplicates
        panelComponent.getChildren().clear();

        panelComponent.getChildren().add(LineComponent.builder()
                .left("Barrows Kill Count")
                .build());

        for (Object npcName : npcList) {
            int kills = killCounts.getOrDefault(npcName, 0);
            int targetKills;

            switch (npcName.toString()) {
                case "Crypt Rat":
                    targetKills = config.targetCryptRat();
                    break;
                case "Bloodworm":
                    targetKills = config.targetBloodworm();
                    break;
                case "Crypt Spider":
                    targetKills = config.targetCryptSpider();
                    break;
                case "Giant Crypt Rat":
                    targetKills = config.targetGiantCryptRat();
                    break;
                case "Skeleton":
                    targetKills = config.targetSkeleton();
                    break;
                case "Giant Crypt Spider":
                    targetKills = config.targetGiantCryptSpider();
                    break;
                default:
                    targetKills = 0;
                    break;
            }

            // Change color based on whether target has been reached
            Color textColor = (kills >= targetKills) ? Color.GREEN : Color.WHITE;

            String lineText = npcName + ":" + kills + " / " + targetKills;
            panelComponent.getChildren().add(LineComponent.builder()
                    .left(lineText)
                    .leftColor(textColor)
                    .build());
        }

        final int rewardPotential = rewardPotential();
        panelComponent.getChildren().add(LineComponent.builder()
                .left("Potential")
                .right(REWARD_POTENTIAL_FORMATTER.format(rewardPotential / 1012f))
                .rightColor(rewardPotential >= 756 && rewardPotential < 881 ? Color.GREEN : rewardPotential < 631 ? Color.WHITE : Color.YELLOW)
                .build());

        return panelComponent.render(graphics);
    }


    // Other methods for configuration, etc. if needed
}
