package net.runelite.client.plugins.birdhousehighlighter;

import com.google.inject.Provides;
import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.timetracking.hunter.BirdHouseTracker;
import net.runelite.client.ui.overlay.OverlayManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

//@PluginDescriptor(
//        name = "Birdhouse Highlighter",
//        description = "Highlight birdhouses based on their occupation state",
//        tags = {"birdhouse", "highlight", "overlay"},
//        type = PluginType.SKILLING
//)
public class BirdhouseHighlighterPlugin extends Plugin
{
    private static final List<Integer> BIRDHOUSE_IDS = new ArrayList<>();

    static {
        BIRDHOUSE_IDS.add(22100);   // Regular Bird House
        BIRDHOUSE_IDS.add(22879);   // Oak Bird House
        BIRDHOUSE_IDS.add(22880);   // Willow Bird House
        BIRDHOUSE_IDS.add(22881);   // Teak Bird House
        BIRDHOUSE_IDS.add(22882);   // Mahogany Bird House
        BIRDHOUSE_IDS.add(22883);   // Yew Bird House
        BIRDHOUSE_IDS.add(22884);   // Magic Bird House
        BIRDHOUSE_IDS.add(23619);   // Redwood Bird House
    }

    @Inject
    private Client client;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private BirdhouseHighlighterOverlay overlay;

    @Inject
    private ItemManager itemManager;

    @Inject
    private BirdHouseTracker birdHouseTracker;

    private final List<GameObject> birdhouses = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(BirdhouseHighlighterPlugin.class);

    @Provides
    BirdhouseHighlighterConfig getConfig(ConfigManager configManager)
    {
        return configManager.getConfig(BirdhouseHighlighterConfig.class);
    }

    @Override
    protected void startUp()
    {
        overlayManager.add(overlay);
        birdhouses.clear();
    }

    @Override
    protected void shutDown()
    {
        overlayManager.remove(overlay);
        birdhouses.clear();
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged event)
    {
        GameState state = event.getGameState();

        if (state == GameState.LOGIN_SCREEN || state == GameState.HOPPING)
        {
            birdhouses.clear();
        }
    }

    @Subscribe
    public void onGameObjectSpawned(GameObjectSpawned event)
    {
        GameObject gameObject = event.getGameObject();
        int objectId = gameObject.getId();

        if (isBirdhouse(objectId))
        {
            birdhouses.add(gameObject);
            logger.info("birdhouse spawns");
        }
    }

    private boolean isBirdhouse(int objectId)
    {
        return BIRDHOUSE_IDS.contains(objectId);
    }

    public List<GameObject> getBirdhouses()
    {
        return birdhouses;
    }
}
