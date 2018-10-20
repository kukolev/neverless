package neverless.game;


import lombok.AllArgsConstructor;
import neverless.domain.item.weapon.Sword;
import neverless.domain.mapobject.Player;
import neverless.domain.mapobject.building.AbstractBuilding;
import neverless.domain.mapobject.monster.Goblin;
import neverless.game.npc.OldMan;
import neverless.domain.mapobject.road.Road;
import neverless.domain.mapobject.tree.FirTree;
import neverless.domain.mapobject.building.LargeVillageHouse;
import neverless.domain.mapobject.building.LittleVillageHouse;
import neverless.domain.mapobject.building.LongVillageHouse;
import neverless.repository.MapObjectsRepository;

import neverless.repository.PlayerRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GameLoader {

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
    }

    private void createPlayer() {
        Player player = new Player();
        player.setUniqueName("Vova");
        player.setX(50)
                .setY(50);

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
                .setUniqueName("House2x2");
        mapObjRepository.save(building);
    }

    private void createHouse4x5() {
        AbstractBuilding building = new LargeVillageHouse();
        building
                .setX(80)
                .setY(14)
                .setUniqueName("House4x5");
        mapObjRepository.save(building);
    }

    private void createHouse3x7() {
        AbstractBuilding building = new LongVillageHouse();
        building
                .setX(65)
                .setY(90)
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
            road.setX(i)
                .setY(y)
                .setUniqueName(String.format("Road %s %s", i, y));
            mapObjRepository.save(road);
        }
    }

    private void drawRoadVertical(int x, int y1, int y2) {
        for (int j = y1; j <= y2; j++ ) {
            Road road = new Road();
            road.setX(x)
                .setY(j)
                .setUniqueName(String.format("Road %s %s", x, j));
            mapObjRepository.save(road);
        }
    }

    private void createTreesTop() {
        createTree(20, 43);
        createTree(35, 45);
        createTree(43, 47);
        createTree(63, 36);
        createTree(74, 38);
    }

    private void createTreesLeft() {
        createTree(50, 55);
        createTree(53, 65);
        createTree(57, 75);
        createTree(52, 79);
        createTree(58, 87);
    }

    private void createTreesRight() {
        createTree(69, 58);
        createTree(65, 63);
        createTree(67, 70);
        createTree(68, 75);
        createTree(68, 81);
    }

    private void createTree(int x, int y) {
        FirTree tree = new FirTree();
        tree.setX(x).setY(y)
               .setUniqueName(String.format("FirTree %s %s", x, y));
        mapObjRepository.save(tree);
    }

    private void createTreesNpc() {
        OldMan oldMan = new OldMan();
        oldMan.register(mapObjRepository);

    }

    private void createTreesMonster() {
        Goblin monster = new Goblin();
        monster.setX(75).setY(95)
                .setUniqueName("Ork warrior 75 95");
        mapObjRepository.save(monster);
    }
}