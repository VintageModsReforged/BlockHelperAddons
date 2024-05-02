package reforged.mods.blockhelper.addons;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

import java.io.File;

public class ModConfig {

    public static File info;
    public static Configuration main_config;

    public static String additional_languages;
    public static String default_language;

    public static boolean advanced_machines = false;

    public static void initConfig() {

        main_config = new Configuration(new File(Minecraft.getMinecraftDir(), "/config/BlockHelperAddonsConfig.cfg"));
        main_config.load();

        default_language = getString("general", "default_language", "en_US", "Default Language. DO NOT CHANGE THIS! Use additional_languages field instead!");
        additional_languages = getString("general", "additional_languages", "", "Additional supported localizations. Place your <lang_code>.properties file in config/langs/blockhelperaddons folder and list <lang_code> here. Format: no spaces, comma separated. Ex: ko_KR,de_DE,ru_RU");

        advanced_machines = getBoolean("compat", "advanced_machines", advanced_machines, "Enable AdvancedMachines Compat. THIS WILL CRASH YOUR GAME WHEN ENABLED! KEEP IT DISABLED FOR NOW!");

        if (main_config.hasChanged()) main_config.save();
    }

    private static boolean getBoolean(String cat, String tag, boolean defaultValue, String comment) {
        comment = comment.replace("{t}", tag) + "\n";
        Property prop = main_config.get(cat, tag, defaultValue);
        prop.comment = comment + "Default: " + defaultValue;
        return prop.getBoolean(defaultValue);
    }

    public static String getString(String cat, String tag, String defaultValue, String comment) {
        comment = comment.replace("{t}", tag) + "\n";
        Property prop = main_config.get(cat, tag, defaultValue);
        prop.comment = comment + "Default: " + defaultValue;
        return prop.getString();
    }
}
