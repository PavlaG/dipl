package cz.grossmannova.pointcloudvisualiser.models;

import cz.grossmannova.pointcloudvisualiser.pathfinding.Edge;
import cz.grossmannova.pointcloudvisualiser.pointcloud.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import org.lwjgl.util.vector.Vector3f;

/**
 * Represents a block.
 * @author Pavla Grossmannová
 * @version 1.0
 * @since 1.0
 */
public class Block {

    private List<Point> innerPoints;
    private Vector3f position;
    private Vector3f size;
    private Vector3f color = new Vector3f();
    private Point center;
    private Set<Block> neighbours = new HashSet<Block>();
    private Set<Edge> edges = new HashSet<Edge>();

    private boolean visited = false;
    float finishDistance = -1;
    float startDistance = Integer.MAX_VALUE;
    Block previousBlock;

    public Block() {
        innerPoints = new ArrayList<>();
        position = new Vector3f();
        size = new Vector3f();
        setColor();
    }

    public Block(List<Point> innerPoints, int sizeX, int sizeY, int sizeZ, Vector3f position) {
        this.innerPoints = innerPoints;
        this.size = new Vector3f(sizeX, sizeY, sizeZ);
        this.position = position;
        setColor();
    }

    public Block(List<Point> innerPoints, Vector3f size, Vector3f position) {
        this.innerPoints = innerPoints;
        this.size = size;
        this.position = position;
        setColor();
    }

    public Block(Vector3f size, Vector3f position) {
        this.innerPoints = new ArrayList<>();
        this.size = size;
        this.position = position;
        setColor();
    }

    public List<Point> getInnerPoints() {
        return innerPoints;
    }

    public void setInnerPoints(List<Point> innerPoints) {
        this.innerPoints = innerPoints;
        for (Point point : this.innerPoints) {
            point.setCorrespondingBlock(this);
        }
    }

    public void addInnerPoints(List<Point> innerPoints) {
        this.innerPoints.addAll(innerPoints);
        for (Point point : this.innerPoints) {
            point.setCorrespondingBlock(this);
        }
    }

    public void expand(Vector3f direction) {
        if (direction.x < 0) {
            position.x -= direction.x;
        } else if (direction.x > 0) {
            size.x += direction.x;
        }

        if (direction.y < 0) {
            position.y -= direction.y;
        } else if (direction.y > 0) {
            size.y += direction.y;
        }

        if (direction.z < 0) {
            position.z -= direction.z;
        } else if (direction.z > 0) {
            size.z += direction.z;
        }
    }

    public void merge(Vector3f target) {
        if (target.x < position.x) {
            position.x = target.x;
        } else if (target.x > position.x + size.x) {
            size.x = target.x - position.x;
        }

        if (target.y < position.y) {
            position.y = target.y;
        } else if (target.y > position.y + size.y) {
            size.y = target.y - position.y;
        }

        if (target.z < position.z) {
            position.z = target.z;
        } else if (target.z > position.z + size.z) {
            size.z = target.z - position.z;
        }
    }

    public void merge(Block target) {
        if (target.getPosition().x < position.x) {
            position.x = target.getPosition().x;
        }
        if (target.getPosition().x + target.getSize().x > position.x + size.x) {
            size.x = (target.getPosition().x + target.getSize().x) - position.x;
        }

        if (target.getPosition().y < position.y) {
            position.y = target.getPosition().y;
        }
        if (target.getPosition().y + target.getSize().y > position.y + size.y) {
            size.y = (target.getPosition().y + target.getSize().y) - position.y;
        }

        if (target.getPosition().z < position.z) {
            position.z = target.getPosition().z;
        }
        if (target.getPosition().z + target.getSize().z > position.z + size.z) {
            size.z = (target.getPosition().z + target.getSize().z) - position.z;
        }
    }

    public void resetSomeValues() {
        visited = false;
        finishDistance = -1;
        startDistance = Integer.MAX_VALUE;
        previousBlock = null;
    }

    public Vector3f getSize() {
        return size;
    }

    public void setSize(Vector3f size) {
        this.size = size;
    }

    public void setSize(float x, float y, float z) {
        this.size.set(x, y, z);
    }

    public void setSize(float size) {
        this.size.set(size, size, size);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    private void setColor() {
        Random r = new Random();
        color.set(r.nextFloat(), r.nextFloat(), r.nextFloat());
    }

    public Vector3f getColor() {
        return color;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {

        this.center = center;
    }

    public void createCenter() {
        center = new Point(new Vector3f(this.position.x + (this.size.x - 1) / 2, this.position.y + (this.size.y - 1) / 2, this.position.z + (this.size.z - 1) / 2), null);
        center.setCorrespondingBlock(this);
    }

    public Set<Block> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(Set<Block> neighbours) {
        this.neighbours = neighbours;
    }

    public void addNeighbour(Block neighbour) {
        neighbours.add(neighbour);
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public void setEdges(Set<Edge> edges) {
        this.edges = edges;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public float getFinishDistance() {
        return finishDistance;
    }

    public void setFinishDistance(float finishDistance) {
        this.finishDistance = finishDistance;
    }

    public void countFinishDistance(Block finish) {
        if (finishDistance == -1) {
            this.finishDistance = (float) Math.sqrt(Math.pow(center.getCoords().x - finish.getCenter().getCoords().x, 2)
                    + Math.pow(center.getCoords().y - finish.getCenter().getCoords().y, 2)
                    + Math.pow(center.getCoords().z - finish.getCenter().getCoords().z, 2));
        }
    }

    public float getStartDistance() {
        return startDistance;
    }

    public void setStartDistance(float startDistance) {
        this.startDistance = startDistance;
    }

    public Block getPreviousBlock() {
        return previousBlock;
    }

    public void setPreviousBlock(Block previoudBlock) {
        this.previousBlock = previoudBlock;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.position);
        hash = 29 * hash + Objects.hashCode(this.size);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Block other = (Block) obj;
        if (!Objects.equals(this.position, other.position)) {
            return false;
        }
        if (!Objects.equals(this.size, other.size)) {
            return false;
        }
        return true;
    }

    public void myString() { //nakonec odstranit
        System.out.println("///////////////");
        System.out.println("position: " + position.toString());
        System.out.println("size: " + size.toString());
        System.out.println("edges:" + edges.size());
        for (Edge edge : edges) {
            System.out.println("   edge: " + edge.getBlockFrom().center + " to: " + edge.getBlockTo().center + " weight: " + edge.getWeight());
        }
        System.out.println("neighbours: " + neighbours.size());
        for (Block neighbour : neighbours) {
            System.out.println("neigh: " + neighbour.getCenter());
        }
        System.out.println("////////////////////");
    }

}
