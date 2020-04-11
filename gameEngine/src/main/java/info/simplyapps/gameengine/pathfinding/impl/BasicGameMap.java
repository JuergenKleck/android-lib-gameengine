package info.simplyapps.gameengine.pathfinding.impl;

import info.simplyapps.gameengine.EngineConstants;
import info.simplyapps.gameengine.pathfinding.interfaces.Mover;
import info.simplyapps.gameengine.pathfinding.interfaces.TileBasedMap;
import info.simplyapps.gameengine.pathfinding.map.MapTile;

import java.util.Random;

public abstract class BasicGameMap implements TileBasedMap {

    /**
     * The terrain settings for each tile in the map
     */
    public MapTile[][] tiles = null;
    /**
     * Indicator if a given tile has been visited during the search
     */
    public boolean[][] visited = null;
    public boolean[][] path = null;

    public abstract int getGameTilesX();

    public abstract int getGameTilesY();

    public abstract int[] getInmoveables();

    public abstract int[] getMoveables();

    public abstract int getInitTerrain();

    public abstract int getInitBorder();

    /**
     * Create a new test map with some default configuration
     */
    public BasicGameMap() {
        tiles = new MapTile[getGameTilesX()][getGameTilesY()];
        initTiles();
        visited = new boolean[getGameTilesX()][getGameTilesY()];
        path = new boolean[getGameTilesX()][getGameTilesY()];
    }

    public void fillTerrain(boolean border) {
        fillTerrain(EngineConstants.PathFinding.TERRAIN_NONE, border, getInitBorder());
    }

    public void fillTerrain(boolean border, int borderTerrain) {
        fillTerrain(EngineConstants.PathFinding.TERRAIN_NONE, border, borderTerrain);
    }

    public void fillTerrain(int optionalTerrain, boolean border) {
        fillTerrain(optionalTerrain, border, getInitBorder());
    }

    public void fillTerrain(int optionalTerrain, boolean border, int borderTerrain) {
        // fill terrain
        fillArea(0, 0, getGameTilesX() - 1, getGameTilesY() - 1, optionalTerrain > -1 ? optionalTerrain : getInitTerrain(), -1, -1);
        // draw border
        if (border) {
            fillArea(0, 0, getGameTilesX() - 1, 0, -1, borderTerrain > -1 ? borderTerrain : getInitBorder(), -1);
            fillArea(0, getGameTilesY() - 1, getGameTilesX() - 1, getGameTilesY() - 1, -1, borderTerrain > -1 ? borderTerrain : getInitBorder(), -1);
            fillArea(0, 0, 0, getGameTilesY() - 1, -1, borderTerrain > -1 ? borderTerrain : getInitBorder(), -1);
            fillArea(getGameTilesX() - 1, 0, getGameTilesX() - 1, getGameTilesY() - 1, -1, borderTerrain > -1 ? borderTerrain : getInitBorder(), -1);
        }
    }

    /**
     * Fill an area with a given terrain type
     *
     * @param sX   The x coordinate to start filling at
     * @param sY   The y coordinate to start filling at
     * @param tX   The width of the area to fill
     * @param tY   The height of the area to fill
     * @param type The terrain type to fill with
     */
    private void initTiles() {
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[x].length; y++) {
                tiles[x][y] = new MapTile();
            }
        }
    }

    /**
     * Fill an area with a given terrain type
     *
     * @param sX   The x coordinate to start filling at
     * @param sY   The y coordinate to start filling at
     * @param tX   The width of the area to fill
     * @param tY   The height of the area to fill
     * @param type The terrain type to fill with
     */
    private void fillArea(int sX, int sY, int tX, int tY, int terrain, int statics, int semistatics) {
        for (int xp = sX; xp <= tX; xp++) {
            for (int yp = sY; yp <= tY; yp++) {
                if (terrain > -1) {
                    tiles[xp][yp].terrain = terrain;
                }
                if (statics > -1) {
                    tiles[xp][yp].statics = statics;
                }
                if (semistatics > -1) {
                    tiles[xp][yp].semistatic = semistatics;
                }
            }
        }
    }

    /**
     * Clear the array marking which tiles have been visted by the path
     * finder.
     */
    public void clearVisited() {
        for (int x = 0; x < getWidthInTiles(); x++) {
            for (int y = 0; y < getHeightInTiles(); y++) {
                visited[x][y] = false;
            }
        }
    }

    /**
     * @see TileBasedMap#visited(int, int)
     */
    public boolean visited(int x, int y) {
        return visited[x][y];
    }

    public boolean isPath(int x, int y) {
        return path[x][y];
    }

    public void setPath(int x, int y) {
        path[x][y] = true;
    }

    /**
     * Get the terrain at a given location
     *
     * @param x The x coordinate of the terrain tile to retrieve
     * @param y The y coordinate of the terrain tile to retrieve
     * @return The terrain tile at the given location
     */
    public int getTerrain(int x, int y) {
        return tiles[x][y].terrain;
    }

    public float getRotation(int x, int y) {
        return tiles[x][y].rotation;
    }

    public void setTerrain(int x, int y, int type) {
        tiles[x][y].terrain = type;
    }

    public void setTerrain(int x, int y, int type, float angle) {
        tiles[x][y].terrain = type;
        tiles[x][y].rotation = angle;
    }

    public int getStatics(int x, int y) {
        return tiles[x][y].statics;
    }

    public void setStatics(int x, int y, int type) {
        tiles[x][y].statics = type;
    }

    public void setStatics(int x, int y, int type, float angle) {
        tiles[x][y].statics = type;
        tiles[x][y].rotationstatics = angle;
    }

    public float getRotationStatics(int x, int y) {
        return tiles[x][y].rotationstatics;
    }

    public int getSemiStatics(int x, int y) {
        return tiles[x][y].semistatic;
    }

    public void setSemiStatics(int x, int y, int type) {
        tiles[x][y].semistatic = type;
    }

    /**
     * Get the unit at a given location
     *
     * @param x The x coordinate of the tile to check for a unit
     * @param y The y coordinate of the tile to check for a unit
     * @return The ID of the unit at the given location or 0 if there is no unit
     */
    public int getUnit(int x, int y) {
        return tiles[x][y].unit;
    }

    /**
     * Set the unit at the given location
     *
     * @param x    The x coordinate of the location where the unit should be set
     * @param y    The y coordinate of the location where the unit should be set
     * @param unit The ID of the unit to be placed on the map, or 0 to clear the unit at the
     *             given location
     */
    public void setUnit(int x, int y, int unit) {
        tiles[x][y].unit = unit;
    }

    public boolean blocked(int unit, int x, int y) {
        // if theres a unit at the location, then it's blocked
        int targetUnit = getUnit(x, y);

        // hedgehog can only move across grass
        if (unit == EngineConstants.PathFinding.UNIT_HERO) {
            return (isInmoveable(tiles[x][y].terrain) || isInmoveable(tiles[x][y].statics))
                    && targetUnit != EngineConstants.PathFinding.OBJECT_FOOD && targetUnit != EngineConstants.PathFinding.OBJECT_TUNNEL;
        }

        // hunter can move on serveral terrains
        if (unit == EngineConstants.PathFinding.UNIT_HUNTER) {
            return (isInmoveable(tiles[x][y].terrain) || isInmoveable(tiles[x][y].statics))
                    && targetUnit != EngineConstants.PathFinding.OBJECT_FOOD;
        }

        // unknown unit so everything blocks
        return true;
    }

    private boolean isInmoveable(int type) {
        for (int obj : getInmoveables()) {
            if (obj == type) {
                return true;
            }
        }
        return false;
    }

    private boolean isMoveable(int type) {
        for (int obj : getMoveables()) {
            if (obj == type) {
                return true;
            }
        }
        return false;
    }

    /**
     * @see TileBasedMap#blocked(Mover, int, int)
     */
    public boolean blocked(Mover mover, int x, int y) {
        int unit = ((UnitMover) mover).type;
        return blocked(unit, x, y);
    }

    public int[] getUnitPosition(int unit) {
        int[] pos = new int[2];
        for (int x = 0; x < getWidthInTiles(); x++) {
            for (int y = 0; y < getHeightInTiles(); y++) {
                if (getUnit(x, y) == unit) {
                    pos[0] = x;
                    pos[1] = y;
                    return pos;
                }
            }
        }
        return pos;
    }

    public int[] getHeroUnitPosition() {
        return getUnitPosition(EngineConstants.PathFinding.UNIT_HERO);
    }

    public void moveUnitPosition(int unit, int sX, int sY, int tX, int tY) {
        if (getUnit(sX, sY) == unit) {
            setUnit(sY, sY, 0);
            tiles[tX][tY].unit = unit;
        }
    }

    public void moveUnitPosition(int unit, int tX, int tY) {
        for (int x = 0; x < getWidthInTiles(); x++) {
            for (int y = 0; y < getHeightInTiles(); y++) {
                if (getUnit(x, y) == unit) {
                    setUnit(x, y, 0);
                    tiles[tX][tY].unit = unit;
                    return;
                }
            }
        }
    }

    public void moveHeroUnitPosition(int tX, int tY) {
        moveUnitPosition(EngineConstants.PathFinding.UNIT_HERO, tX, tY);
    }

    /**
     * Get a free tile which is not blocked by statics
     *
     * @param rnd
     * @return
     */
    public int[] getFreeTile(Random rnd) {
        int x = -1;
        int y = -1;

        int iter = 0;
        boolean free = false;
        while (!free) {
            x = rnd.nextInt(getGameTilesX());
            y = rnd.nextInt(getGameTilesY());
            // in safe frame, no unit, no path, only grass
            free = x > 0 && y > 0 && x < getGameTilesX() && y < getGameTilesY() && isMoveable(tiles[x][y].terrain) && isMoveable(tiles[x][y].statics)
                    && tiles[x][y].unit <= 0 && !path[x][y] && iter < 50;
            iter++;
        }

        return new int[]{x, y};
    }

    /**
     * Get the next free tile
     *
     * @param tmpX
     * @param tmpY
     * @return
     */
    public int[] getNextFreeTile(int tmpX, int tmpY) {
        int x = -1;
        int y = -1;

        if (tmpX > 0 && tmpX < getGameTilesX() && tmpY > 0 && tmpY < getGameTilesY() && isMoveable(tiles[tmpX][tmpY].terrain) && isMoveable(tiles[tmpX][tmpY].statics)
                && tiles[tmpX][tmpY].unit <= 0) {
            x = tmpX;
            y = tmpY;
        }

        return new int[]{x, y};
    }

    /**
     * @see TileBasedMap#getCost(Mover, int, int, int, int)
     */
    public float getCost(Mover mover, int sx, int sy, int tx, int ty) {
        return 1;
    }

    /**
     * @see TileBasedMap#getHeightInTiles()
     */
    public int getHeightInTiles() {
        return getGameTilesY();
    }

    /**
     * @see TileBasedMap#getWidthInTiles()
     */
    public int getWidthInTiles() {
        return getGameTilesX();
    }

    /**
     * @see TileBasedMap#pathFinderVisited(int, int)
     */
    public void pathFinderVisited(int x, int y) {
        visited[x][y] = true;
    }


}