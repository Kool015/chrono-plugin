package com.chrono;

import com.chrono.regionlocker.RegionTypes;
import com.google.common.annotations.VisibleForTesting;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Provides;
import javax.inject.Inject;

import com.chrono.regionlocker.RegionBorderOverlay;
import com.chrono.regionlocker.RegionLocker;
import com.chrono.regionlocker.RegionLockerOverlay;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.ItemSpawned;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.*;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.callback.Hooks;
import net.runelite.client.chat.ChatColorType;
import net.runelite.client.chat.ChatMessageBuilder;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.*;
import java.util.List;

@Slf4j
@PluginDescriptor(
		name = "Chrono",
		description = "Restrict your account by release",
		tags = {"time traveler", "by release"},
		conflicts = {"Region Locker"}
)
public class ChronoPlugin extends Plugin {
	public static final String CONFIG_KEY = "chrono";

	private static final int SOUND_EFFECT_FAIL = 2277;
	private static final List<String> MENU_BLACKLIST = Arrays.asList("Use", "Take", "Wield", "Open", "Empty", "Eat", "Wear", "Read", "Check", "Teleport", "Commune", "Drink");

	@Inject
	private Client client;

	@Inject
	@Getter
	private ChronoConfig config;

	@Inject
	private RegionLockerOverlay regionLockerOverlay;

	@Inject
	private RegionBorderOverlay regionBorderOverlay;

	@Inject
	@Getter
	private ConfigManager configManager;

	@Inject
	private ClientThread clientThread;

	@Inject
	private ChatMessageManager chatMessageManager;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private ChronoItemOverlay itemOverlay;

	@Inject
	private Gson gson;

	@Inject
	private Hooks hooks;

	@Getter
	@Setter
	private int hoveredRegion = -1;

	private RegionLocker regionLocker;

	private Map<String, List<Widget>> skillOverlays;

	@Getter
	private boolean mapEnabled;

	private final Hooks.RenderableDrawListener drawListener = this::shouldDraw;

	@Provides
	ChronoConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(ChronoConfig.class);
	}

	@Override
	protected void startUp() {
		overlayManager.add(itemOverlay);
		regionLocker = new RegionLocker(client, config, configManager, this);
		regionLocker.setRegions(config.release().getRegions(), RegionTypes.UNLOCKED);
		overlayManager.add(regionLockerOverlay);
		overlayManager.add(regionBorderOverlay);
		hooks.registerRenderableDrawListener(drawListener);
		loadDefinitions();
	}

	@Override
	protected void shutDown() {
		RegionLocker.renderLockedRegions = false;
		overlayManager.remove(itemOverlay);
		overlayManager.remove(regionLockerOverlay);
		overlayManager.remove(regionBorderOverlay);
		regionLocker.setRegions(config.release().getRegions(), RegionTypes.UNLOCKED);
		hooks.unregisterRenderableDrawListener(drawListener);
	}

	private void loadDefinitions() {
		Type defMapType = new TypeToken<Map<Integer, EntityDefinition>>() {}.getType();
		EntityDefinition.itemDefinitions = loadDefinitionResource(defMapType, "items.json");
		EntityDefinition.monsterDefinition = loadDefinitionResource(defMapType, "monsters.json");
	}


	private <T> T loadDefinitionResource(Type type, String resource) {
		// Load the resource as a stream and wrap it in a reader
		InputStream resourceStream = ChronoPlugin.class.getResourceAsStream(resource);
		assert resourceStream != null;
		InputStreamReader definitionReader = new InputStreamReader(resourceStream);

		// Load the objects from the JSON file
		return gson.fromJson(definitionReader, type);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged e) {
		if(!e.getGroup().equals(CONFIG_KEY)) return;

		regionLocker.readConfig();
		regionLocker.setRegions(config.release().getRegions(), RegionTypes.UNLOCKED);
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged e){
		if(e.getGameState().equals(GameState.LOGGED_IN)) {

		}
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked e) throws ParseException {

		if(MENU_BLACKLIST.contains(e.getMenuOption())) {
			// Catch a weird special case where it says the item ID is 0 when theres no item
			if(e.getItemId() == 0 && !e.getMenuTarget().equals("Dwarf Remains")) return;

			int id = e.getItemId() > -1 ? e.getItemId() : e.getId();

			if(!isItemUnlocked(id)) {
				e.consume();
				EntityDefinition def = EntityDefinition.itemDefinitions.get(id);
				String was = def.getName().endsWith("s") ? "were" : "was";
				addWarningMessage(def.getName()+" "+was+" released after "+config.release().getName()+".", true);
			}
		}
	}

	@VisibleForTesting
	boolean shouldDraw(Renderable renderable, boolean drawingUI) {
		if (renderable instanceof NPC)
		{
			NPC npc = (NPC) renderable;

			try {
				return EntityDefinition.isMonsterUnlocked(npc.getId(), config.release().getDate());
			} catch(ParseException e) {
				e.printStackTrace();
				return true;
			}
		}

		return true;
	}

	private void addWarningMessage(String message, boolean playSound) {
		final ChatMessageBuilder chatMessage = new ChatMessageBuilder()
				.append(ChatColorType.HIGHLIGHT)
				.append(message)
				.append(ChatColorType.NORMAL);

		chatMessageManager.queue(QueuedMessage.builder()
				.type(ChatMessageType.CONSOLE)
				.runeLiteFormattedMessage(chatMessage.build())
				.build());

		client.playSoundEffect(SOUND_EFFECT_FAIL);
	}

	public boolean isItemUnlocked(int itemId) throws ParseException {
		return EntityDefinition.isItemUnlocked(itemId, config.release().getDate());
	}
}
