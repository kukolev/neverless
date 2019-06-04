package neverless.game;

import neverless.domain.model.entity.Game;
import neverless.domain.model.entity.Location;
import neverless.domain.model.entity.inventory.Bag;
import neverless.domain.model.entity.inventory.Equipment;
import neverless.domain.model.entity.inventory.Inventory;
import neverless.domain.model.entity.item.weapon.Sword;
import neverless.domain.model.entity.mapobject.loot.LootContainer;
import neverless.domain.Coordinate;
import neverless.domain.model.entity.mapobject.Player;
import neverless.domain.model.entity.mapobject.building.TavernSmall;
import neverless.domain.model.entity.mapobject.portal.LocationPortal;
import neverless.domain.model.entity.mapobject.respawn.GoblinRespawnPoint;
import neverless.domain.model.quest.QuestContainer;
import neverless.game.npc.OldMan;
import neverless.game.npc.OldManQuestKillGoblins;
import neverless.service.model.GameRepository;

import neverless.service.model.util.LocalMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static neverless.game.Signatures.IMG_DUNGEON_BACKGROUND;
import static neverless.game.Signatures.IMG_VILLAGE_BACKGROUND;

@Component
public class GameBuilder {

    @Autowired
    private QuestContainer questContainer;
    @Autowired
    private ApplicationContext context;

    public Game createNewGame() {
        Game game = new Game();

        // Create locations
        Location village = createLocationVillage();
        Location dungeon = createLocationDungeon();
        game.getLocations().add(village);
        game.getLocations().add(dungeon);

        // Create Portals
        LocationPortal vilageDungeonPortal = new LocationPortal();
        vilageDungeonPortal
                .setDestination(dungeon)
                .setDestX(64)
                .setDestY(500);
        vilageDungeonPortal.getCoordinates().add(new Coordinate().setX(1530).setY(380));
        vilageDungeonPortal.getCoordinates().add(new Coordinate().setX(1546).setY(296));
        vilageDungeonPortal.getCoordinates().add(new Coordinate().setX(1600).setY(266));
        vilageDungeonPortal.getCoordinates().add(new Coordinate().setX(1632).setY(318));
        vilageDungeonPortal.getCoordinates().add(new Coordinate().setX(1633).setY(380));
        vilageDungeonPortal.setEnterPoint(new Coordinate().setX(1580).setY(380));

        LocationPortal dungeonVillagePortal = new LocationPortal();
        dungeonVillagePortal
                .setDestination(village)
                .setDestX(1600)
                .setDestY(400);
        dungeonVillagePortal.getCoordinates().add(new Coordinate().setX(0).setY(400));
        dungeonVillagePortal.getCoordinates().add(new Coordinate().setX(50).setY(400));
        dungeonVillagePortal.getCoordinates().add(new Coordinate().setX(50).setY(600));
        dungeonVillagePortal.getCoordinates().add(new Coordinate().setX(0).setY(600));
        dungeonVillagePortal.setEnterPoint(new Coordinate().setX(50).setY(500));

        LocationPortal housePortal = new LocationPortal();
        housePortal.getCoordinates().add(new Coordinate().setX(420).setY(1040));
        housePortal.getCoordinates().add(new Coordinate().setX(420).setY(940));
        housePortal.getCoordinates().add(new Coordinate().setX(470).setY(900));
        housePortal.getCoordinates().add(new Coordinate().setX(470).setY(1000));

        housePortal.setEnterPoint(new Coordinate().setX(400).setY(100));


        // Bind portals
        village.getAreas().add(vilageDungeonPortal);
        village.getAreas().add(housePortal);
        dungeon.getAreas().add(dungeonVillagePortal);


        // Create player
        Player player = createPlayer(village);
        game.setPlayer(player);

        // Create quests
        createQuests();
        return game;
    }

    private Location createLocationVillage() {

        Location village = new Location()
                .setTitle("Village")
                .setSignature(IMG_VILLAGE_BACKGROUND);

        // NPC (Old Man)
        OldMan oldMan = new OldMan();
        oldMan.setX(1632);
        oldMan.setY(1632);
        oldMan.setLocation(village);

        // Tavern
        TavernSmall tavern = new TavernSmall();
        tavern.setX(330);
        tavern.setY(1100);
        village.getObjects().add(tavern);

        // Loot
        LootContainer lootContainer = new LootContainer();
        lootContainer.setX(300);
        lootContainer.setY(1200);
        village.getObjects().add(lootContainer);

        lootContainer.getItems().add(new Sword().setTitle("Rust sword"));
        lootContainer.getItems().add(new Sword().setTitle("Silver sword"));

        return village;
    }

    private Location createLocationDungeon() {
        Location dungeon = new Location()
                .setTitle("Dungeon")
                .setSignature(IMG_DUNGEON_BACKGROUND);

        // Respawn points in dungeon
        GoblinRespawnPoint respawnPoint1 = new GoblinRespawnPoint();
        respawnPoint1.setX(1900);
        respawnPoint1.setY(1300);
        respawnPoint1.setLocation(dungeon);

        GoblinRespawnPoint respawnPoint2 = new GoblinRespawnPoint();
        respawnPoint2.setX(1280);
        respawnPoint2.setY(1728);
        respawnPoint2.setLocation(dungeon);

        dungeon.getObjects().add(respawnPoint1);
        dungeon.getObjects().add(respawnPoint2);

        return dungeon;
    }

    private Player createPlayer(Location location) {
        Sword sword = new Sword();
        sword.setPower(5);
        sword.setTitle("Mega Sword of Ultra Power");

        Sword flameSword = new Sword();
        flameSword.setPower(50);
        flameSword.setTitle("Flame Sword");

        Sword iceSword = new Sword();
        iceSword.setPower(70);
        iceSword.setTitle("Ice Sword");


        Equipment equipment = new Equipment();
        equipment.setWeapon(sword);

        Bag bag = new Bag();
        bag.addLast(flameSword);
        bag.addLast(iceSword);

        Inventory inventory = new Inventory();
        inventory.setBag(bag);
        inventory.setEquipment(equipment);

        Player player = new Player();
        player.setInventory(inventory);
        player.setHitPoints(1000);
        player.setWidth(64);
        player.setHeight(96);
        player.setX(200);
        player.setY(1200);
        player.setLocation(location);
        location.getObjects().add(player);
        return player;
    }

    private void createQuests() {
        questContainer.add(context.getBean(OldManQuestKillGoblins.class));
    }
}