package neverless.game;


import lombok.AllArgsConstructor;
import neverless.domain.item.weapon.Sword;
import neverless.domain.mapobject.Player;
import neverless.domain.mapobject.building.AbstractBuilding;
import neverless.domain.mapobject.monster.Goblin;
import neverless.domain.mapobject.portal.LocationsPortal;
import neverless.domain.mapobject.wall.StoneWall;
import neverless.game.npc.OldMan;
import neverless.domain.mapobject.road.Road;
import neverless.domain.mapobject.tree.FirTree;
import neverless.domain.mapobject.building.LargeVillageHouse;
import neverless.domain.mapobject.building.LittleVillageHouse;
import neverless.domain.mapobject.building.LongVillageHouse;
import neverless.repository.MapObjectsRepository;

import neverless.repository.PlayerRepository;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
@AllArgsConstructor
public class GameLoader {

    public static final String LOCATION_VILLAGE = "Village";
    public static final String LOCATION_DUNGEON = "Dungeon";


    private MapObjectsRepository mapObjRepository;
    private PlayerRepository playerRepository;

    public void createNewGame() {
        createPlayer();
        createHouse2x2();
        createHouse4x5();
        createHouse3x7();
        createRoads();
        createTreesTop();
        createTreesLeft();
        createTreesRight();
        createTreesNpc();
        createTreesMonster();
        createPortalVillage2Dungeon();
        createPortalDungeon2Village();
        createDungeon();
    }

    private void createPlayer() {
        Player player = new Player();
        player.setUniqueName("Vova");
        player.setX(50)
                .setY(50)
                .setLocation(LOCATION_VILLAGE);

        Sword sword = new Sword();
        sword.setUniqueName("MEGA_SWORD");
        sword.setPower(100);
        sword.setTitle("Mega Sword of Ultra Power");

        player.getInventory().getEquipment().setRightHand(sword);

        playerRepository.save(player);
    }

    private void createHouse2x2() {
        LittleVillageHouse building = new LittleVillageHouse();
        building
                .setX(10)
                .setY(10)
                .setLocation(LOCATION_VILLAGE)
                .setUniqueName("House2x2");
        mapObjRepository.save(building);
    }

    private void createHouse4x5() {
        AbstractBuilding building = new LargeVillageHouse();
        building
                .setX(80)
                .setY(14)
                .setLocation(LOCATION_VILLAGE)
                .setUniqueName("House4x5");
        mapObjRepository.save(building);
    }

    private void createHouse3x7() {
        AbstractBuilding building = new LongVillageHouse();
        building
                .setX(65)
                .setY(90)
                .setLocation(LOCATION_VILLAGE)
                .setUniqueName("House3x7");
        mapObjRepository.save(building);
    }

    private void createRoads() {

        // 1-st road
        drawRoadVertical(11, 12, 50);
        drawRoadHorizontal(50, 11, 60);
        drawRoadVertical(60, 40, 92);
        drawRoadHorizontal(92, 60, 64);
        drawRoadHorizontal(40, 60, 83);
        drawRoadVertical(83, 17, 40);
    }

    private void drawRoadHorizontal(int y, int x1, int x2) {
        for (int i = x1; i <= x2; i++ ) {
            Road road = new Road();
            road
                    .setX(i)
                    .setY(y)
                    .setLocation(LOCATION_VILLAGE)
                .setUniqueName(format("Road %s %s", i, y));
            mapObjRepository.save(road);
        }
    }

    private void drawRoadVertical(int x, int y1, int y2) {
        for (int j = y1; j <= y2; j++ ) {
            Road road = new Road();
            road
                    .setX(x)
                    .setY(j)
                    .setLocation(LOCATION_VILLAGE)
                    .setUniqueName(format("Road %s %s", x, j));
            mapObjRepository.save(road);
        }
    }

    private void createTreesTop() {
        createTree(20, 43, LOCATION_VILLAGE);
        createTree(35, 45, LOCATION_VILLAGE);
        createTree(43, 47, LOCATION_VILLAGE);
        createTree(63, 36, LOCATION_VILLAGE);
        createTree(74, 38, LOCATION_VILLAGE);
    }

    private void createTreesLeft() {
        createTree(50, 55, LOCATION_VILLAGE);
        createTree(53, 65, LOCATION_VILLAGE);
        createTree(57, 75, LOCATION_VILLAGE);
        createTree(52, 79, LOCATION_VILLAGE);
        createTree(58, 87, LOCATION_VILLAGE);
    }

    private void createTreesRight() {
        createTree(69, 58, LOCATION_VILLAGE);
        createTree(65, 63, LOCATION_VILLAGE);
        createTree(67, 70, LOCATION_VILLAGE);
        createTree(68, 75, LOCATION_VILLAGE);
        createTree(68, 81, LOCATION_VILLAGE);
    }

    private void createTree(int x, int y, String location) {
        FirTree tree = new FirTree();
        tree.setX(x)
                .setY(y)
                .setLocation(location)
                .setUniqueName(format("FirTree %s %s", x, y));
        mapObjRepository.save(tree);
    }

    private void drawStoneWallVertical(int x, int y1, int y2, String location) {
        for (int j = y1; j <= y2; j++ ) {
            createStoneWall(x, j, location);
        }
    }

    private void drawStoneWallHorizontal(int y, int x1, int x2, String location) {
        for (int i = x1; i <= x2; i++ ) {
            createStoneWall(i, y, location);
        }
    }

    private void createStoneWall(int x, int y, String location) {
        StoneWall wall = new StoneWall();
        wall
                .setLocation(location)
                .setX(x)
                .setY(y)
                .setUniqueName(format("StoneWall %s %s", x, y));
        mapObjRepository.save(wall);
    }

    private void createTreesNpc() {
        OldMan oldMan = new OldMan();
        oldMan
            .setX(51)
            .setY(51)
            .setLocation(LOCATION_VILLAGE);

        oldMan.register(mapObjRepository);

    }

    private void createTreesMonster() {
        Goblin monster = new Goblin();
        monster
                .setX(75)
                .setY(95)
                .setLocation(LOCATION_VILLAGE)
                .setUniqueName("Ork warrior 75 95");
        mapObjRepository.save(monster);
    }

    private void createPortalVillage2Dungeon() {
        LocationsPortal portal = new LocationsPortal();
        portal
                .setDestination(LOCATION_DUNGEON)
                .setDestX(2)
                .setDestY(5)
                .setX(51)
                .setY(53)
                .setLocation(LOCATION_VILLAGE)
                .setUniqueName("Village to Dungeon Portal");
        mapObjRepository.save(portal);

    }

    private void createPortalDungeon2Village() {
        LocationsPortal portal = new LocationsPortal();
        portal
                .setDestination(LOCATION_VILLAGE)
                .setDestX(51)
                .setDestY(52)
                .setX(1)
                .setY(5)
                .setLocation(LOCATION_DUNGEON)
                .setUniqueName("Dungeon to Vilage Portal");
        mapObjRepository.save(portal);

    }

    private void createDungeon() {
        drawStoneWallHorizontal(1, 1, 10, LOCATION_DUNGEON);
        drawStoneWallHorizontal(10, 1, 10, LOCATION_DUNGEON);

        drawStoneWallVertical(1, 2, 4, LOCATION_DUNGEON);
        drawStoneWallVertical(1, 6, 9, LOCATION_DUNGEON);

        drawStoneWallVertical(10, 2, 9, LOCATION_DUNGEON);
    }
}