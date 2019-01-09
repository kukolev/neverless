package neverless.game;

import neverless.domain.Game;
import neverless.domain.Location;
import neverless.domain.entity.inventory.Bag;
import neverless.domain.entity.inventory.Equipment;
import neverless.domain.entity.inventory.Inventory;
import neverless.domain.entity.item.weapon.Sword;
import neverless.domain.entity.mapobject.AbstractMapObject;
import neverless.domain.entity.mapobject.Coordinate;
import neverless.domain.entity.mapobject.Player;
import neverless.domain.entity.mapobject.building.AbstractBuilding;
import neverless.domain.entity.mapobject.portal.AbstractPortal;
import neverless.domain.entity.mapobject.portal.LocationsPortal;
import neverless.domain.entity.mapobject.respawn.GoblinRespawnPoint;
import neverless.domain.entity.mapobject.wall.AbstractWall;
import neverless.domain.entity.mapobject.wall.StoneWall;
import neverless.domain.quest.QuestContainer;
import neverless.game.npc.OldMan;
import neverless.domain.entity.mapobject.building.LargeVillageHouse;
import neverless.game.npc.OldManQuestKillGoblins;

import neverless.repository.persistence.CoordinateRepository;
import neverless.repository.persistence.GameRepository;
import neverless.repository.persistence.ItemRepository;
import neverless.repository.persistence.LocationRepository;
import neverless.repository.persistence.MapObjectsRepository;
import neverless.repository.persistence.PlayerRepository;
import neverless.repository.persistence.RespawnPointRepository;
import neverless.repository.persistence.BagRepository;
import neverless.repository.persistence.EquipmentRepository;
import neverless.repository.persistence.InventoryRepository;
import neverless.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static neverless.Resources.IMG_VILLAGE_BACKGROUND;

@Component
@Transactional
public class GameLoader {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private MapObjectsRepository mapObjRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private BagRepository bagRepository;
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private RespawnPointRepository respawnPointRepository;
    @Autowired
    private SessionUtil sessionUtil;
    @Autowired
    private QuestContainer questContainer;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private CoordinateRepository coordinateRepository;

    public void createNewGame() {
        createGame();
        createQuests();
    }

    private void createGame() {
        Game game = gameRepository.save(new Game()
                .setId(sessionUtil.getGameId()));

        Location village = createLocationVillage();
        Location dungeon = createLocationDungeon();

        village.getObjects().add(createPortalVillage2Dungeon(dungeon));
        dungeon.getObjects().add(createPortalDungeon2Village(village));

        Player player = createPlayer();
        player.setLocation(village);
        playerRepository.save(player);

        game.getLocations().add(village);
        game.getLocations().add(dungeon);
        game.setPlayer(player);
        gameRepository.save(game);
    }

    private Location createLocationVillage() {

        Location village = new Location()
                .setTitle("Village")
                .setSignature(IMG_VILLAGE_BACKGROUND);
        village = locationRepository.save(village);

        createNpc(village);
        createRespawnPoints(village);
        createHouse(village);

        return village;
    }

    private Location createLocationDungeon() {
        Location dungeon = new Location()
                .setTitle("Dungeon")
                .setSignature(IMG_VILLAGE_BACKGROUND);

        dungeon.getObjects().addAll(createDungeonObjects());

        return locationRepository.save(dungeon);
    }


    private Player createPlayer() {
        Bag bag = new Bag();
        bagRepository.save(bag);

        Sword sword = new Sword();
        sword
                .setPower(100)
                .setTitle("Mega Sword of Ultra Power");
        sword = itemRepository.save(sword);

        Equipment equipment = new Equipment();
        equipment
                .setRightHand(sword);
        equipmentRepository.save(equipment);

        Inventory inventory = new Inventory();
        inventory
                .setBag(bag)
                .setEquipment(equipment);
        inventoryRepository.save(inventory);

        Player player = new Player();
        player
                .setInventory(inventory)
                .setX(1600)
                .setY(1600);
        return playerRepository.save(player);
    }

    private void createHouse(Location location) {
        AbstractBuilding building = new LargeVillageHouse();
        building
                .setX(1900)
                .setY(1400)
                .setLocation(location);
        List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(new Coordinate().setX(68).setY(156));
        coordinates.add(new Coordinate().setX(92).setY(156));
        coordinates.add(new Coordinate().setX(159).setY(118));
        coordinates.add(new Coordinate().setX(92).setY(86));
        coordinates.add(new Coordinate().setX(6).setY(116));
        building.setPlatformCoordinates(coordinates);

        coordinates.forEach(c -> coordinateRepository.save(c));
        mapObjRepository.save(building);
    }

    private List<AbstractWall> drawStoneWallVertical(int x, int y1, int y2) {
        List<AbstractWall> walls = new ArrayList<>();
        for (int j = y1; j <= y2; j+=32 ) {
            walls.add(createStoneWall(x, j));
        }
        return walls;
    }

    private List<AbstractWall> drawStoneWallHorizontal(int y, int x1, int x2) {
        List<AbstractWall> walls = new ArrayList<>();
        for (int i = x1; i <= x2; i += 32 ) {
            walls.add(createStoneWall(i, y));
        }
        return walls;
    }

    private AbstractWall createStoneWall(int x, int y) {
        StoneWall wall = new StoneWall();
        wall
                .setX(x)
                .setY(y);
        return mapObjRepository.save(wall);
    }

    private void createNpc(Location location) {
        OldMan oldMan = new OldMan();
        oldMan
            .setX(1632)
            .setY(1632)
            .setLocation(location);
        mapObjRepository.save(oldMan);
    }

    private AbstractPortal createPortalVillage2Dungeon(Location destination) {
        LocationsPortal portal = new LocationsPortal();
        portal
                .setDestination(destination)
                .setDestX(64)
                .setDestY(160)
                .setX(1632)
                .setY(1696);
        return mapObjRepository.save(portal);
    }

    private AbstractPortal createPortalDungeon2Village(Location destination) {
        LocationsPortal portal = new LocationsPortal();
        portal
                .setDestination(destination)
                .setDestX(1632)
                .setDestY(1664)
                .setX(32)
                .setY(160);
        return mapObjRepository.save(portal);
    }

    private List<AbstractMapObject> createDungeonObjects() {
        List<AbstractMapObject> walls = new ArrayList<>();
        walls.addAll(drawStoneWallHorizontal(32, 32, 320));
        walls.addAll(drawStoneWallHorizontal(320,  32, 320));

        walls.addAll(drawStoneWallVertical(32, 64, 128));
        walls.addAll(drawStoneWallVertical(32, 192, 288));

        walls.addAll(drawStoneWallVertical(320, 64, 288));
        return walls;
    }

    private void createRespawnPoints(Location location) {
        GoblinRespawnPoint respawnPoint1 = new GoblinRespawnPoint();
        respawnPoint1
                .setX(1900)
                .setY(1300)
                .setLocation(location);
        respawnPointRepository.save(respawnPoint1);

        GoblinRespawnPoint respawnPoint2 = new GoblinRespawnPoint();
        respawnPoint2
                .setX(1280)
                .setY(1728);
        respawnPointRepository.save(respawnPoint2);
    }

    private void createQuests() {
        questContainer.add(context.getBean(OldManQuestKillGoblins.class));
    }
}