package com.cout970.magneticraft.api.util;

public class VecIntUtil {

	public static final VecInt[] FORGE_DIRECTIONS = {
		VecInt.getConnexion(MgDirection.DOWN),
		VecInt.getConnexion(MgDirection.UP),
		VecInt.getConnexion(MgDirection.NORTH),
		VecInt.getConnexion(MgDirection.SOUTH),
		VecInt.getConnexion(MgDirection.WEST),
		VecInt.getConnexion(MgDirection.EAST) };
	
	public static final VecInt[] EXTENDED_DIRECTIONS = {// a cube 3x3x3 around
		// the position
		VecInt.getConnexion(MgDirection.DOWN),
		VecInt.getConnexion(MgDirection.UP),
		VecInt.getConnexion(MgDirection.NORTH),
		VecInt.getConnexion(MgDirection.SOUTH),
		VecInt.getConnexion(MgDirection.WEST),
		VecInt.getConnexion(MgDirection.EAST), 
		
		new VecInt(-1, -1, 0),	// -y -x
		new VecInt(1, -1, 0),	// -y +x

		new VecInt(0, -1, 1),   // -y +z
		new VecInt(0, -1, -1), 	// -y -z

		new VecInt(-1, 1, 0), 	// +y -x 10
		new VecInt(1, 1, 0), 	// +y +x

		new VecInt(0, 1, 1), 	// +y +z
		new VecInt(0, 1, -1), 	// +y -z

		new VecInt(-1, 0, 1),	// +z -x
		new VecInt(1, 0, 1),	// +z +x 15

		new VecInt(1, 0, -1), 	// -z +x
		new VecInt(-1, 0, -1),	// -z -x
	};
	public static final VecInt[] WIRE_DOWN = { VecInt.NULL_VECTOR,
		VecInt.getConnexion(MgDirection.DOWN),
		VecInt.getConnexion(MgDirection.NORTH),
		VecInt.getConnexion(MgDirection.SOUTH),
		VecInt.getConnexion(MgDirection.WEST),
		VecInt.getConnexion(MgDirection.EAST), 
		new VecInt(1, -1, 0), // -y	+x
		new VecInt(-1, -1, 0),// -y -x
		new VecInt(0, -1, 1), // -y +z
		new VecInt(0, -1, -1) // -y -z;
	};
	public static final VecInt[] WIRE_UP = { VecInt.NULL_VECTOR,
		VecInt.getConnexion(MgDirection.UP),
		VecInt.getConnexion(MgDirection.NORTH),
		VecInt.getConnexion(MgDirection.SOUTH),
		VecInt.getConnexion(MgDirection.WEST),
		VecInt.getConnexion(MgDirection.EAST), 
		new VecInt(1, 1, 0), // +y +x
		new VecInt(-1, 1, 0),// +y -x
		new VecInt(0, 1, 1), // +y +z
		new VecInt(0, 1, -1) // +y -z
	};
	public static final VecInt[] WIRE_NORTH = { VecInt.NULL_VECTOR,
		VecInt.getConnexion(MgDirection.DOWN),
		VecInt.getConnexion(MgDirection.UP),
		VecInt.getConnexion(MgDirection.NORTH),
		VecInt.getConnexion(MgDirection.WEST),
		VecInt.getConnexion(MgDirection.EAST), 
		new VecInt(0, 1, -1), // +y	-z
		new VecInt(0, -1, -1),// -y -z
		new VecInt(1, 0, -1), // +x -z
		new VecInt(-1, 0, -1),// -x -z
	};
	public static final VecInt[] WIRE_WEST = { VecInt.NULL_VECTOR,
		VecInt.getConnexion(MgDirection.DOWN),
		VecInt.getConnexion(MgDirection.UP),
		VecInt.getConnexion(MgDirection.NORTH),
		VecInt.getConnexion(MgDirection.SOUTH),
		VecInt.getConnexion(MgDirection.WEST), 
		new VecInt(-1, 1, 0), // +y -x
		new VecInt(-1, -1, 0),// -y -x
		new VecInt(-1, 0, 1), // +z -x
		new VecInt(-1, 0, -1) // -z -x
	};
	public static final VecInt[] WIRE_SOUTH = { VecInt.NULL_VECTOR,
		VecInt.getConnexion(MgDirection.DOWN),
		VecInt.getConnexion(MgDirection.UP),
		VecInt.getConnexion(MgDirection.SOUTH),
		VecInt.getConnexion(MgDirection.WEST),
		VecInt.getConnexion(MgDirection.EAST), 
		new VecInt(0, 1, 1), 	// +y +z
		new VecInt(0, -1, 1),	// -y +z
		new VecInt(1, 0, 1),	// +z +z
		new VecInt(-1, 0, 1) 	// -z +z
	};
	public static final VecInt[] WIRE_EAST = { VecInt.NULL_VECTOR,
		VecInt.getConnexion(MgDirection.DOWN),
		VecInt.getConnexion(MgDirection.UP),
		VecInt.getConnexion(MgDirection.NORTH),
		VecInt.getConnexion(MgDirection.SOUTH),
		VecInt.getConnexion(MgDirection.EAST), 
		new VecInt(-1, 1, 0), // +y -x
		new VecInt(-1, -1, 0),// -y -x
		new VecInt(-1, 0, 1), // +z -x
		new VecInt(-1, 0, -1) // -z -x
	};

}
