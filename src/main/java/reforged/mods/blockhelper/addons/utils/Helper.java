package reforged.mods.blockhelper.addons.utils;

import mods.vintage.core.platform.lang.FormattedTranslator;
import mods.vintage.core.platform.lang.Translator;

public class Helper {

    public static String getTierForDisplay(int tier) {
        switch (tier) {
            case 0:
                return Translator.format("info.tier.ulv");
            case 1:
                return Translator.format("info.tier.lv");
            case 2:
                return Translator.format("info.tier.mv");
            case 3:
                return Translator.format("info.tier.hv");
            case 4:
                return Translator.format("info.tier.ev");
            case 5:
                return Translator.format("info.tier.iv");
            case 6:
                return Translator.format("info.tier.luv");
            case 7:
                return Translator.format("info.tier.zpm");
            case 8:
                return Translator.format("info.tier.uv");
            case 9:
                return Translator.format("info.tier.uhv");
            case 10:
                return Translator.format("info.tier.uev");
            case 11:
                return Translator.format("info.tier.uiv");
            case 12:
                return Translator.format("info.tier.umv");
            case 13:
                return Translator.format("info.tier.uxv");
            case 14:
                return Translator.format("info.tier.max");
            default:
                return FormattedTranslator.RED.literal("ERROR, please report!");
        }
    }

    public static int getTierFromEU(int value) {
        return value <= 8 ? 0 : (int) Math.ceil(Math.log((double) value * 0.125) * (1.0 / Math.log(4.0)));
    }

    public static int getMaxInputFromTier(int tier) {
        switch (tier) {
            case 1:
                return 32;
            case 2:
                return 128;
            case 3:
                return 3;
            case 4:
                return 2048;
            case 5:
                return 8192;
            case 6:
                return 32768;
            default:
                return 0;
        }
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
