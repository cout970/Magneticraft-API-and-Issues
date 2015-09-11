package com.cout970.magneticraft.api.electricity.prefab;

import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IEnergyInterface;
import com.cout970.magneticraft.api.electricity.IIndexedConnection;
import com.cout970.magneticraft.api.util.VecInt;

/**
 * @author Cout970
 */
public class IndexedConnection implements IIndexedConnection {

    protected VecInt vecDir;                //direction from the source to the cond or interface
    protected IElectricConductor source;    //source conductor
    protected IElectricConductor cond;
    protected IEnergyInterface inter;
    protected int index;                    //used by the conductor for current flow

    public IndexedConnection(IElectricConductor s, VecInt c, IEnergyInterface e, int side) {
        vecDir = c;
        inter = e;
        source = s;
        this.index = side;
    }

    public IndexedConnection(IElectricConductor s, VecInt c, IElectricConductor e, int side) {
        vecDir = c;
        cond = e;
        source = s;
        this.index = side;
    }

    public VecInt getOffset() {
        return vecDir;
    }

    public IElectricConductor getSource() {
        return source;
    }

    public IElectricConductor getConductor() {
        return cond;
    }

    public IEnergyInterface getEnergyInterface() {
        return inter;
    }

    public int getIndex() {
        return index;
    }
}
