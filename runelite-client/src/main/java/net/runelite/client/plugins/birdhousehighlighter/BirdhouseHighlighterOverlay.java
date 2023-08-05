package net.runelite.client.plugins.birdhousehighlighter;

import com.google.inject.Inject;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.List;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;
import net.runelite.client.plugins.timetracking.hunter.BirdHouseTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BirdhouseHighlighterOverlay extends Overlay
{
    private final Client client;
    private final BirdhouseHighlighterPlugin plugin;
    private final BirdhouseHighlighterConfig config;
    private final BirdHouseTracker birdHouseTracker;
    private static final Logger logger = LoggerFactory.getLogger(BirdhouseHighlighterOverlay.class);

    @Inject
    private BirdhouseHighlighterOverlay(Client client, BirdhouseHighlighterPlugin plugin, BirdhouseHighlighterConfig config, BirdHouseTracker birdHouseTracker)
    {
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.UNDER_WIDGETS);
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        this.birdHouseTracker = birdHouseTracker;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (client.getPlane() == 0 && config.highlightBirdhouses() != BirdhouseHighlighterConfig.HighlightBirdhouses.NEITHER)
        {
            renderBirdhouses(graphics);
        }
        return null;
    }

    private void renderBirdhouses(Graphics2D graphics)
    {
        List<GameObject> birdhouses = plugin.getBirdhouses();
        for (GameObject birdhouse : birdhouses)
        {
            int objectId = birdhouse.getId();
            Color color = getColorForBirdhouse(objectId);

            logger.info("birdhouses");
            if (color != null)
            {
                Polygon polygon = (Polygon) birdhouse.getConvexHull();
                if (polygon != null)
                {
                    OverlayUtil.renderPolygon(graphics, polygon, color);
                }
            }
        }
    }

    private Color getColorForBirdhouse(int objectId)
    {
        BirdhouseHighlighterConfig.HighlightBirdhouses highlightOption = config.highlightBirdhouses();

        if (highlightOption == BirdhouseHighlighterConfig.HighlightBirdhouses.NEITHER)
        {
            return null;
        }

        boolean isReady = birdHouseTracker.checkCompletion();

        // Since we are not using isOccupied() anymore, use isReady to determine the color
        if (isReady)
        {
            return config.occupiedBirdhouseColor();
        }

        return null;
    }
}
