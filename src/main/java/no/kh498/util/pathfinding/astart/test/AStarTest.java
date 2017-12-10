package no.kh498.util.pathfinding.astart.test;

import no.kh498.util.pathfinding.astart.AStar;
import no.kh498.util.pathfinding.astart.Tile;

import java.util.List;

/**
 * @author karl henrik
 */
public class AStarTest {

    public static void main(final String[] args) {
        final Tile start = new Tile(0, 0, 0, null);
        final Tile goal = new Tile(1, 20, 1, null);
        final AStar aStar = new AStar(start, goal);
        final List<Tile> loc = aStar.pathfindTile();
        System.out.println(loc);
    }
}
