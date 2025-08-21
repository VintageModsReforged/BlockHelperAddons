package reforged.mods.blockhelper.addons.utils;

import mods.vintage.core.platform.lang.Translator;

public class Helper {

    public static String getTierForDisplay(int tier) {
        String suffix;
        Translator formatter;

        switch (tier) {
            case 0:  suffix = "ulv"; formatter = Translator.DARK_GRAY; break;
            case 1:  suffix = "lv";  formatter = Translator.GRAY; break;
            case 2:  suffix = "mv";  formatter = Translator.AQUA; break;
            case 3:  suffix = "hv";  formatter = Translator.GOLD; break;
            case 4:  suffix = "ev";  formatter = Translator.DARK_PURPLE; break;
            case 5:  suffix = "iv";  formatter = Translator.BLUE; break;
            case 6:  suffix = "luv"; formatter = Translator.LIGHT_PURPLE; break;
            case 7:  suffix = "zpm"; formatter = Translator.RED; break;
            case 8:  suffix = "uv";  formatter = Translator.DARK_AQUA; break;
            case 9:  suffix = "uhv"; formatter = Translator.WHITE; break;
            case 10: suffix = "uev"; formatter = Translator.WHITE; break;
            case 11: suffix = "uiv"; formatter = Translator.WHITE; break;
            case 12: suffix = "umv"; formatter = Translator.WHITE; break;
            case 13: suffix = "uxv"; formatter = Translator.WHITE; break;
            case 14: suffix = "max"; formatter = Translator.WHITE; break;
            default:
                return Translator.RED.literal("ERROR, please report!");
        }
        return formatter.format("info.tier." + suffix);
    }

    public static int getTierFromEU(int value) {
        return value <= 8 ? 0 : (int) Math.ceil(Math.log((double) value * 0.125) * (1.0 / Math.log(4.0)));
    }

    public static int getMaxInputFromTier(int tier) {
        return 8 << Math.min(tier, 13) * 2;
    }

    public static Translator getTextColor(int value) {
        Translator colorCode = Translator.WHITE; // white
        if (value >= 90) {
            colorCode = Translator.GREEN; // green
        }
        if ((value <= 90) && (value > 75)) {
            colorCode = Translator.YELLOW; // yellow
        }
        if ((value <= 75) && (value > 50)) {
            colorCode = Translator.GOLD; // gold
        }
        if ((value <= 50) && (value > 35)) {
            colorCode = Translator.RED; // red
        }
        if (value <= 35) {
            colorCode = Translator.DARK_RED; // dark_red
        }

        return colorCode;
    }
}
