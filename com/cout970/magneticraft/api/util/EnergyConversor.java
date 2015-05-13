package com.cout970.magneticraft.api.util;

public class EnergyConversor {

	private static final int RF_W = 100;//conversion from Watts to RF, 1RF = 100W, 1KW = 10RF = 1MJ
	private static final double RF_KW = 0.1;//1RF = 100W = 0.1 KW
	private static final double STEAM_KW = 5;//amount of steam equivalent to 1KW
	private static final int WATER_STEAM = 5;//steam generated from 1mB of water
	private static final int CALORIE = 100;//100 calories to make 5mB steam from 1mB water 
	private static final double EU_KW = 0.4;//1EU = 400W = 0.4KW
	private static final double EU_W = 400;//1EU = 400W

	public static double RFtoW(int rf){
		return rf*RF_W;
	}
	
	public static double WtoRF(double kw){
		return kw/RF_W;
	}
	
	public static double RFtoKW(int rf){
		return rf*RF_KW;
	}
	
	public static double KWtoRF(double kw){
		return kw/RF_KW;
	}
	
	public static double CALORIEStoW(double heat){
		return (heat/CALORIE)*1000;
	}

	public static double STEAMtoKW(int steam) {
		return steam/STEAM_KW;
	}

	public static double KWtoSTEAM(int kw) {
		return kw*STEAM_KW;
	}

	public static double FUELtoCALORIES(int fuel) {
		return fuel*CALORIE;
	}

	public static int STEAMtoWATER(int steam) {
		return steam/WATER_STEAM;
	}

	public static int WATERtoSTEAM(int water) {
		return water*WATER_STEAM;
	}

	public static double WATERtoSTEAM_HEAT(int water) {
		return water*CALORIE;
	}

	public static double WtoEU(double w) {
		return w/EU_W;
	}

	public static double EUtoW(double eu) {
		return eu*EU_W;
	}

	public static double KWtoEU(double eu) {
		return eu*EU_KW;
	}

	public static double EUtoKW(double Kw) {
		return Kw/EU_KW;
	}

	public static double STEAMtoW(int steam) {
		return steam*1000/STEAM_KW;
	}

	public static double WtoCALORIES(double w) {
		return (w*CALORIE)/1000;
	}

	public static double CALORIEStoFuel(double heat) {
		return heat/CALORIE;
	}
}
