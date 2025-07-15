package reforged.mods.blockhelper.addons;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mods.vintage.core.helpers.ConfigHelper;
import mods.vintage.core.platform.lang.ILangProvider;
import mods.vintage.core.platform.lang.LangManager;
import net.minecraftforge.common.Configuration;
import reforged.mods.blockhelper.addons.base.WailaCommonHandler;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Mod(modid = "BlockHelperAddons", name = "Block Helper Addons", useMetadata = true, dependencies =
        "required-after:VintageCore;" +
                "required-after:mod_BlockHelper;" +
                "required-after:IC2;" +
                "after:AdvancedPowerManagement;" +
                "after:ChargePads;" +
                "after:GregTech_Addon;"
)
public class BlockHelperAddons implements ILangProvider {

    public Configuration CONFIG;
    public static String[] LANGS;
    public static int barMaxWidth;

    public BlockHelperAddons() {
        CONFIG = ConfigHelper.getConfigFor("BlockHelperAddons");
        CONFIG.load();
        LANGS = ConfigHelper.getLocalizations(CONFIG, new String[] {"en_US", "ru_RU"}, "BlockHelperAddons");
        barMaxWidth = ConfigHelper.getInt(CONFIG, "general", "barMaxWidth", 1, Integer.MAX_VALUE, 120, "Set Max Width for bar element (useful when using localizations that are longer than English)");
        CONFIG.save();
    }

    @Mod.PreInit
    public void pre(FMLPreInitializationEvent e) {
        WailaCommonHandler.INSTANCE.init();
        LangManager.THIS.registerLangProvider(this);
        try {
            Method register = Class.forName("mod_BlockHelper").getMethod("registerPlugin",
                    Class.forName("mcp.mobius.waila.api.IWailaPlugin"));
            register.invoke(null, new WailaPluginHandler());
        } catch (Throwable t) {
            t.printStackTrace(); // Proper logging or ignoring
        }
    }

    @Override
    public String getModid() {
        return "BlockHelperAddons";
    }

    @Override
    public List<String> getLocalizationList() {
        return Arrays.asList(LANGS);
    }
}
