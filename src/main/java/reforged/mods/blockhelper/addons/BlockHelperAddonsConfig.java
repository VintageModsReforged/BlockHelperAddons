package reforged.mods.blockhelper.addons;

import cpw.mods.fml.relauncher.FMLInjectionData;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

import java.io.File;
import java.util.Arrays;

public class BlockHelperAddonsConfig {

    public static Configuration MAIN_CONFIG;

    public static String[] LANGS;

    public static void init() {
        MAIN_CONFIG = new Configuration(new File((File) FMLInjectionData.data()[6], "config/BlockHelperAddons.cfg"));
        MAIN_CONFIG.load();

        LANGS = getStrings("localization", "langs", new String[] {"en_US"}, "Supported localizations.");

        if (MAIN_CONFIG != null) {
            MAIN_CONFIG.save();
        }
    }

    private static String[] getStrings(String cat, String tag, String[] defaultValue, String comment) {
        comment = comment.replace("{t}", tag) + "\n";
        Property prop = MAIN_CONFIG.get(cat, tag, defaultValue);
        prop.comment = comment + "Default: " + Arrays.toString(defaultValue);
        return prop.getStringList();
    }
}
