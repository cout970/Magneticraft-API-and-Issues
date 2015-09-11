package com.cout970.magneticraft.api.util;

import java.util.HashSet;
import java.util.LinkedList;

public abstract class PathFinder {

    public LinkedList<VectorOffset> scanPosition;
    public HashSet<VecInt> scanMap;

    public void init() {
        scanPosition = new LinkedList<VectorOffset>();
        scanMap = new HashSet<VecInt>();
    }

    public void addBlock(VectorOffset v) {
        if (!scanMap.contains(v.getCoords())) {
            scanPosition.addLast(v);
            scanMap.add(v.getCoords());
        }
    }

    public void addNeigBlocks(VecInt pos) {
        for (MgDirection d : MgDirection.values()) {
            addBlock(new VectorOffset(d.toVecInt().add(pos), d.toVecInt()));
        }
    }

    public abstract boolean step(VectorOffset coord);

    public boolean iterate() {
        if (scanPosition.size() == 0) {
            return false;
        } else {
        	VectorOffset vec = scanPosition.removeFirst();
            return step(vec);
        }
    }
}
