package reforged.mods.blockhelper.addons;

import cpw.mods.fml.relauncher.FMLInjectionData;
import mods.vintage.core.VintageCore;
import mods.vintage.core.helpers.ConfigHelper;
import net.minecraftforge.common.Configuration;

import java.io.File;

public class BlockHelperAddonsConfig {

    public static Configuration MAIN_CONFIG;

    public static String[] LANGS;
    public static int DEFAULT_BAR_SIZE = 25;

    public static void init() {
        MAIN_CONFIG = new Configuration(new File((File) FMLInjectionData.data()[6], "config/BlockHelperAddons.cfg"));
        MAIN_CONFIG.load();

        LANGS = ConfigHelper.getStrings(MAIN_CONFIG, "localization", "langs", new String[] {"en_US", "ru_RU"}, "Supported localizations.");
        DEFAULT_BAR_SIZE = ConfigHelper.getInt(MAIN_CONFIG, "general", "defaultBarSize", 25, Integer.MAX_VALUE, DEFAULT_BAR_SIZE, "Default Bar Element Size.\nSome localizations might require extra space (ru_RU for instance requires at least extra 10 size (35) to display the info).\nFeel free to change this to fit your language.");
        if (DEFAULT_BAR_SIZE < 25) {
            VintageCore.LOGGER.warning("defaultBarSize needs to be higher or equal to 25 for it to display info properly! Reverting to default (25)!");
            DEFAULT_BAR_SIZE = ConfigHelper.getInt(MAIN_CONFIG, "general", "defaultBarSize", 25, Integer.MAX_VALUE, DEFAULT_BAR_SIZE, "Default Bar Element Size.\nSome localizations might require extra space (ru_RU for instance requires at least extra 10 size (35) to display the info).\nFeel free to change this to fit your language.");
        }

        if (MAIN_CONFIG != null) {
            MAIN_CONFIG.save();
        }
    }
}
