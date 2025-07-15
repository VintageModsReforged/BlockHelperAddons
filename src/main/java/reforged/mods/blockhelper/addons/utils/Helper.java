package reforged.mods.blockhelper.addons.utils;

import mods.vintage.core.platform.lang.FormattedTranslator;

public class Helper {

    public static String getTierForDisplay(int tier) {
        String suffix;
        FormattedTranslator formatter;

        switch (tier) {
            case 0:  suffix = "ulv"; formatter = FormattedTranslator.DARK_GRAY; break;
            case 1:  suffix = "lv";  formatter = FormattedTranslator.GRAY; break;
            case 2:  suffix = "mv";  formatter = FormattedTranslator.AQUA; break;
            case 3:  suffix = "hv";  formatter = FormattedTranslator.GOLD; break;
            case 4:  suffix = "ev";  formatter = FormattedTranslator.DARK_PURPLE; break;
            case 5:  suffix = "iv";  formatter = FormattedTranslator.BLUE; break;
            case 6:  suffix = "luv"; formatter = FormattedTranslator.LIGHT_PURPLE; break;
            case 7:  suffix = "zpm"; formatter = FormattedTranslator.RED; break;
            case 8:  suffix = "uv";  formatter = FormattedTranslator.DARK_AQUA; break;
            case 9:  suffix = "uhv"; formatter = FormattedTranslator.WHITE; break;
            case 10: suffix = "uev"; formatter = FormattedTranslator.WHITE; break;
            case 11: suffix = "uiv"; formatter = FormattedTranslator.WHITE; break;
            case 12: suffix = "umv"; formatter = FormattedTranslator.WHITE; break;
            case 13: suffix = "uxv"; formatter = FormattedTranslator.WHITE; break;
            case 14: suffix = "max"; formatter = FormattedTranslator.WHITE; break;
            default:
                return FormattedTranslator.RED.literal("ERROR, please report!");
        }
        return formatter.format("info.tier." + suffix);
    }

    public static int getTierFromEU(int value) {
        return value <= 8 ? 0 : (int) Math.ceil(Math.log((double) value * 0.125) * (1.0 / Math.log(4.0)));
    }

    public static int getMaxInputFromTier(int tier) {
        return 8 << Math.min(tier, 13) * 2;
    }

    public static FormattedTranslator getTextColor(int value) {
        FormattedTranslator colorCode = FormattedTranslator.WHITE; // white
        if (value >= 90) {
            colorCode = FormattedTranslator.GREEN; // green
        }
        if ((value <= 90) && (value > 75)) {
            colorCode = FormattedTranslator.YELLOW; // yellow
        }
        if ((value <= 75) && (value > 50)) {
            colorCode = FormattedTranslator.GOLD; // gold
        }
        if ((value <= 50) && (value > 35)) {
            colorCode = FormattedTranslator.RED; // red
        }
        if (value <= 35) {
            colorCode = FormattedTranslator.DARK_RED; // dark_red
        }

        return colorCode;
    }
}
