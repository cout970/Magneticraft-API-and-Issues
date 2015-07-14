package com.cout970.magneticraft.api.electricity;

/**
 * 
 * @author Cout970
 *
 */
public enum EnumAcces {

	NOTHING(false,false),
	INPUT(true,false),
	OUTPUT(false,true),
	BOTH(true,true);
	
	private boolean canAccept;
	private boolean canExtract;
	
	private EnumAcces(boolean a,boolean b){
		canAccept = a;
		canExtract = b;
	}
	
	public boolean canAccept(){return canAccept;}
	
	public boolean canExtract(){return canExtract;}

	public static EnumAcces getAcces(boolean b, boolean c) {
		if(b && c)return BOTH;
		if(b && !c)return INPUT;
		if(!b && c)return OUTPUT;
		return NOTHING;
	}
}
