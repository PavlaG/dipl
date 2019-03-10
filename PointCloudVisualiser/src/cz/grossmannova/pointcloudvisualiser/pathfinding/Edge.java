/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.grossmannova.pointcloudvisualiser.pathfinding;

import cz.grossmannova.pointcloudvisualiser.models.Block;
import java.util.Objects;

/**
 *
 * @author Pavla
 */
public class Edge {

    private float weight;
    private Block blockFrom;
    private Block blockTo;

    public Edge(Block blockFrom, Block blockTo) {
        this.blockFrom = blockFrom;
        this.blockTo = blockTo;
        weight = (float) Math.sqrt(Math.pow(blockFrom.getCenter().getCoords().x - blockTo.getCenter().getCoords().x, 2)
                + Math.pow(blockFrom.getCenter().getCoords().y - blockTo.getCenter().getCoords().y, 2)
                + Math.pow(blockFrom.getCenter().getCoords().z - blockTo.getCenter().getCoords().z, 2));
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Block getBlockFrom() {
        return blockFrom;
    }

    public void setBlockFrom(Block blockFrom) {
        this.blockFrom = blockFrom;
    }

    public Block getBlockTo() {
        return blockTo;
    }

    public void setBlockTo(Block blockTo) {
        this.blockTo = blockTo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.blockFrom);
        hash = 37 * hash + Objects.hashCode(this.blockTo);
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
        final Edge other = (Edge) obj;
        if (!Objects.equals(this.blockFrom, other.blockFrom)) {
            return false;
        }
        if (!Objects.equals(this.blockTo, other.blockTo)) {
            return false;
        }
        return true;
    }

}
