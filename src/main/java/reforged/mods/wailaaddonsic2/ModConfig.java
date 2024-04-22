package reforged.mods.wailaaddonsic2;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

import java.io.File;

public class ModConfig {

    public static File info;
    public static Configuration main_config;

    public static boolean advanced_machines = false;

    public static void initConfig() {

        main_config = new Configuration(new File(Minecraft.getMinecraftDir(), "/config/BlockHelperAddonsConfig.cfg"));
        main_config.load();

        advanced_machines = getBoolean("compat", "advanced_machines", advanced_machines, "Enable AdvancedMachines Compat. THIS WILL CRASH YOUR GAME WHEN ENABLED! KEEP IT DISABLED FOR NOW!");

        if (main_config.hasChanged()) main_config.save();

        info = new File(Minecraft.getMinecraftDir(), "/config/gravisuite/langs.md");
    }

    private static boolean getBoolean(String cat, String tag, boolean defaultValue, String comment) {
        comment = comment.replace("{t}", tag) + "\n";
        Property prop = main_config.get(cat, tag, defaultValue);
        prop.comment = comment + "Default: " + defaultValue;
        return prop.getBoolean(defaultValue);
    }
}
