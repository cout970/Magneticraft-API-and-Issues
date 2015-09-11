package com.cout970.magneticraft.api.util;

/**
 * Some Basic Utility to exchange between equivalent forms of energy
 *
 * @author Cout970
 */
public class EnergyConversor {

    //1W = 1J/t
    public static final double RF_J = 10;            // 1RF = 10J | 1J = 0.1RF
    public static final double EU_J = 40;            // 1EU = 40J | 1J = 0,025EU
    public static final double STEAM_J = 20;        // 1mB = 20J | 1J = 0.05mB | 1 Burn tick = 5mB Steam = 100J
    public static final double CALORIE_J = 0.2;    // 1cal = 0.2J | 1J = 5cal
    public static final double FUEL_J = 100;        // 1 Burning tick = 100J
    public static final double WATER_STEAM = 5;    // 1mB of Water = 5mB of Steam | 1mB Steam = 0.2 mB Water
    public static final double FUEL_CALORIE = 500; // 1 Burning tick = 500 cal | 1cal = 0.002 Burning ticks => 1mB Water == 5mB Steam | Calories needed to boil 1mB of water into 5mB of Steam
    public static final double MOL_MB = 8192;
    public static final double AVOGADROS_CONSTANT = 6.022E23;
    public static final double BAR_PA = 100000;

    //RF
    public static double RFtoW(double rf) {
        return rf * RF_J;
    }

    public static double WtoRF(double w) {
        return w / RF_J;
    }

    public static double RFtoCALORIES(double rf) {
        return WtoCALORIES(RFtoW(rf));
    }

    public static double CALORIEStoRF(double cal) {
        return WtoRF(CALORIEStoW(cal));
    }

    //STEAM
    public static double WtoSTEAM(double w) {
        return w / STEAM_J;
    }

    public static double STEAMtoW(double steam) {
        return steam * STEAM_J;
    }

    public static int STEAMtoWATER(int steam) {
        return (int) (steam / WATER_STEAM);
    }

    public static int WATERtoSTEAM(int water) {
        return (int) (water * WATER_STEAM);
    }

    //CALORIES
    public static double CALORIEStoW(double cal) {
        return cal * CALORIE_J;
    }

    public static double WtoCALORIES(double w) {
        return w / CALORIE_J;
    }

    public static double FUELtoCALORIES(double fuel) {
        return fuel * FUEL_CALORIE;
    }

    public static double CALORIEStoFUEL(double cal) {
        return cal / FUEL_CALORIE;
    }

    //FUEL
    public static double FUELtoW(double cal) {
        return cal * FUEL_J;
    }

    public static double WtoFUEL(double w) {
        return w / FUEL_J;
    }

    //heat need to boil an amount of water
    public static double WATERtoSTEAM_HEAT(int water) {
        return water * FUEL_CALORIE;
    }

    public static double CALORIEStoSTEAM(double cal) {
        return CALORIEStoWATER(cal) * WATER_STEAM;
    }

    //aount of water boiled with this amount of calories
    public static double CALORIEStoWATER(double cal) {
        return cal / FUEL_CALORIE;
    }

    //EU
    public static double EUtoW(double eu) {
        return eu * EU_J;
    }

    public static double WtoEU(double w) {
        return w / EU_J;
    }

    //Railcraft Charge (RC)
    public static double RCtoW(double rc) {
        return EUtoW(rc);
    }

    public static double WtoRC(double rc) {
        return WtoEU(rc);
    }

    public static double MBtoMOL(double amount) {
        return (amount / MOL_MB);
    }

    public static double MOLtoMB(double d) {
        return d * MOL_MB;
    }

    public static double PAtoBAR(double amount) {
        return amount / BAR_PA;
    }

    public static double BARtoPA(double amount) {
        return amount * BAR_PA;
    }

}
