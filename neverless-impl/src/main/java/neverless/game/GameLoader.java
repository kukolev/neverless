package neverless.game;

import neverless.domain.entity.Game;
import neverless.domain.entity.Location;
import neverless.domain.entity.inventory.Bag;
import neverless.domain.entity.inventory.Equipment;
import neverless.domain.entity.inventory.Inventory;
import neverless.domain.entity.item.weapon.Sword;
import neverless.domain.entity.mapobject.Coordinate;
import neverless.domain.entity.mapobject.Player;
import neverless.domain.entity.mapobject.building.TavernSmall;
import neverless.domain.entity.mapobject.portal.LocationPortal;
import neverless.domain.entity.mapobject.respawn.GoblinRespawnPoint;
import neverless.domain.quest.QuestContainer;
import neverless.game.npc.OldMan;
import neverless.game.npc.OldManQuestKillGoblins;
import neverless.context.GameContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static neverless.Signatures.IMG_DUNGEON_BACKGROUND;
import static neverless.Signatures.IMG_VILLAGE_BACKGROUND;

@Component
public class GameLoader {

    @Autowired
    private GameContext cache;
    @Autowired
    private QuestContainer questContainer;
    @Autowired
    private ApplicationContext context;


    public void createNewGame() {
        createGame();
        createQuests();
    }

    private void createGame() {
        Game game = new Game();
        cache.save(game);

        Location village = createLocationVillage();
        Location dungeon = createLocationDungeon();

        village.getAreas().add(createPortalVillage2Dungeon(dungeon));
        dungeon.getAreas().add(createPortalDungeon2Village(village));

        Player player = createPlayer();
        player.setLocation(village);
        village.getObjects().add(player);

        game.getLocations().add(village);
        game.getLocations().add(dungeon);
        game.setPlayer(player);
    }

    private Location createLocationVillage() {

        Location village = new Location()
                .setTitle("Village")
                .setSignature(IMG_VILLAGE_BACKGROUND);

        createNpc(village);
        createRespawnPoints(village);
        createTavern(village);

        return village;
    }

    private Location createLocationDungeon() {
        Location dungeon = new Location()
                .setTitle("Dungeon")
                .setSignature(IMG_DUNGEON_BACKGROUND);

        return dungeon;
    }


    private Player createPlayer() {
        Bag bag = new Bag();

        Sword sword = new Sword();
        sword
                .setPower(5)
                .setTitle("Mega Sword of Ultra Power");

        Equipment equipment = new Equipment();
        equipment
                .setRightHand(sword);

        Inventory inventory = new Inventory();
        inventory
                .setBag(bag)
                .setEquipment(equipment);

        Player player = new Player();
        player
                .setInventory(inventory)
                .setWidth(64)
                .setHeight(96)
                .setX(200)
                .setY(1200);
        return player;
    }

    private void createTavern(Location location) {
        TavernSmall tavern = new TavernSmall();
        tavern.setX(330);
        tavern.setY(1100);
        location.getObjects().add(tavern);
    }

    private void createNpc(Location location) {
        OldMan oldMan = new OldMan();
        oldMan
            .setX(1632)
            .setY(1632)
            .setLocation(location);
    }

    private LocationPortal createPortalVillage2Dungeon(Location destination) {
        LocationPortal portal = new LocationPortal();
        portal
                .setDestination(destination)
                .setDestX(64)
                .setDestY(160);
        portal.getCoordinates().add(new Coordinate().setX(1530).setY(380));
        portal.getCoordinates().add(new Coordinate().setX(1546).setY(296));
        portal.getCoordinates().add(new Coordinate().setX(1600).setY(266));
        portal.getCoordinates().add(new Coordinate().setX(1632).setY(318));
        portal.getCoordinates().add(new Coordinate().setX(1633).setY(380));

        return portal;
    }

    private LocationPortal createPortalDungeon2Village(Location destination) {
        LocationPortal portal = new LocationPortal();
        portal
                .setDestination(destination)
                .setDestX(1632)
                .setDestY(1664);
        return portal;
    }

    private void createRespawnPoints(Location location) {
        GoblinRespawnPoint respawnPoint1 = new GoblinRespawnPoint();
        respawnPoint1
                .setX(1900)
                .setY(1300)
                .setLocation(location);

        GoblinRespawnPoint respawnPoint2 = new GoblinRespawnPoint();
        respawnPoint2
                .setX(1280)
                .setY(1728)
                .setLocation(location);
        location.getObjects().add(respawnPoint1);
        location.getObjects().add(respawnPoint2);
    }

    private void createQuests() {
        questContainer.add(context.getBean(OldManQuestKillGoblins.class));
    }
}