package neverless.service.view;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.Coordinate;
import neverless.domain.view.Sprite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class DrawerUtils {

    /**
     * Utility class for relation between sprites.
     */
    @Data
    @Accessors(chain = true)
    private static class RelationGraphNode {
        private int level = Integer.MIN_VALUE;
        private Sprite sprite;
        private List<RelationGraphNode> children = new ArrayList<>();
        private List<RelationGraphNode> parents = new ArrayList<>();
    }

    /**
     * Returns list of sprites, sorted by draw priority.
     * First element of the list should be drawn first.
     *
     * @param sprites   list of sprites for sorting.
     */
    public static List<Sprite> calcRenderOrder(List<Sprite> sprites) {

        Map<Integer, Map<Integer, Sprite>> xySpriteMap = new HashMap<>();
        Map<Sprite, RelationGraphNode> graph = new HashMap<>();

        // fill graph
        sprites.forEach(sprite -> graph.put(sprite, new RelationGraphNode().setSprite(sprite)));

        // fill map (x -> (y -> sprite))
        sprites.forEach(sprite -> {
            List<Coordinate> coordinates = calcAllPerimeterCoordinates(sprite);
            if (coordinates != null) {
                coordinates.forEach(c -> {
                    Map<Integer, Sprite> locMap = xySpriteMap.computeIfAbsent(c.getX(), k -> new TreeMap<>());
                    locMap.put(c.getY(), sprite);
                });
            }
        });

        // calculates relations for graph nodes.
        // relation graph represents relation "parent -> child" for sprites.
        // every node may has several parents and several children.
        xySpriteMap.values().forEach(lm -> {
            ArrayList<Sprite> vals = new ArrayList<>(lm.values());
            for (int i = 1; i < vals.size(); i++) {
                Sprite curSprite = vals.get(i);
                Sprite parentSprite = vals.get(i - 1);

                if (!curSprite.equals(parentSprite)) {
                    RelationGraphNode curNode = graph.get(curSprite);
                    RelationGraphNode parentNode = graph.get(parentSprite);
                    addSpriteToParent(curNode, parentNode);
                }
            }
        });

        // start calculating weights for every node.
        // starts recursive algorithm for every root node.
        graph.values().forEach(node -> {
            if (node.getParents().size() == 0) {
                calcLevels(node, 0);
            }
        });

        // as result we should return sprites list, sorted by level.
        // first element in list should be displayed first.
        sprites.sort((s1, s2) -> {
            RelationGraphNode node1 = graph.get(s1);
            RelationGraphNode node2 = graph.get(s2);
            return node1.getLevel() > node2.level ? 1 : -1;
        });
        return sprites;
    }

    /**
     * Recursively visits every node of relation graph and fills level property.
     *
     * @param root  root node in graph.
     * @param level level for root node.
     */
    private static void calcLevels(RelationGraphNode root, int level) {
        int curLevel = level;

        // level should be equal or higher than root level (if already set)
        if (root.getLevel() < curLevel) {
            root.setLevel(curLevel);
        } else {
            curLevel = root.getLevel();
        }
        final int nextLevel = curLevel;

        root.getChildren()
                .forEach(child -> calcLevels(child, nextLevel + 1));
    }

    /**
     * Links parent and child nodes on relation graph.
     *
     * @param child     child node.
     * @param parent    parent node.
     */
    private static void addSpriteToParent(RelationGraphNode child, RelationGraphNode parent) {
        if (!parent.getChildren().contains(child)) {
            parent.getChildren().add(child);
            child.getParents().add(parent);
        }
    }

    /**
     * Calculates and returns list of points for sprite's platform.
     *
     * @param sprite    sprite for calculation
     */
    private static List<Coordinate> calcAllPerimeterCoordinates(Sprite sprite) {
        switch (sprite.getPlatformShape()) {
            case ELLIPSE: {
                return calcAllPerimeterCoordinatesEllipse(sprite.getPlatformShapeWidth(), sprite.getPlatformShapeHeight(), sprite.getX(), sprite.getY());
            }
            case RECTANGLE: {
                return calcAllPerimeterCoordinatesRectangle(sprite.getPlatformShapeWidth(), sprite.getPlatformShapeHeight());
            }
            case CUSTOM: {
                return calcAllPerimeterCoordinatesCustom(sprite.getPlatformCoordinates(), sprite.getX(), sprite.getY());
            }
        }
        return null;
    }

    private static List<Coordinate> calcAllPerimeterCoordinatesEllipse(int width, int height, int objectX, int objectY) {
        List<Coordinate> coordinates = new ArrayList<>();

        double radX = (double) (width / 2);
        double radY = (double) (height / 2);

        for(int x = 0; x < radX; x++) {
            int y = (int) sqrt ((radY * radY * (1 - (x * x) / (radX * radX))));
            coordinates.add(new Coordinate().setX(objectX + x).setY(objectY + y));
            coordinates.add(new Coordinate().setX(objectX - x).setY(objectY + y));
        }
        return coordinates;
    }

    private static List<Coordinate> calcAllPerimeterCoordinatesRectangle(int width, int height) {
        return new ArrayList<>();
    }

    private static List<Coordinate> calcAllPerimeterCoordinatesCustom(List<Coordinate> customCoordinates, int objectX, int objectY) {
        List<Coordinate> coordinates = new ArrayList<>();

        for (int i = 0; i < customCoordinates.size(); i++) {
            Coordinate c1 = customCoordinates.get(i);
            Coordinate c2;
            if (i < customCoordinates.size() - 1) {
                c2 = customCoordinates.get(i + 1);
            } else {
                c2 = customCoordinates.get(0);
            }

            int x1 = c1.getX() + objectX;
            int y1 = c1.getY() + objectY;

            int x2 = c2.getX() + objectX;
            int y2 = c2.getY() + objectY;

            int xCount = abs(x2 - x1);

            int dx = xCount != 0 ? (x2 - x1) / xCount : 0;
            double dy = xCount != 0 ? ((double) (y2 - y1)) / xCount : 0;

            for (int j = 0; j < xCount; j++) {
                int curX = x1 + dx * j;
                int curY = (int) (y1 + dy * j);
                coordinates.add(new Coordinate().setX(curX).setY(curY));
            }
        }
        return coordinates;
    }
}