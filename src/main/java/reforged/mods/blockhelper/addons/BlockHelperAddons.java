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

    public BlockHelperAddons() {
        CONFIG = ConfigHelper.getConfigFor("BlockHelperAddons");
        CONFIG.load();
        LANGS = ConfigHelper.getLocalizations(CONFIG, new String[] {"en_US", "ru_RU"}, "BlockHelperAddons");
        if (CONFIG != null) {
            CONFIG.save();
        }
    }

    @Mod.PreInit
    public void pre(FMLPreInitializationEvent e) {
        WailaCommonHandler.INSTANCE.init();
        LangManager.THIS.registerLangProvider(this);
        try {
            Method register = Class.forName("mcp.mobius.waila.mod_BlockHelper").getMethod("registerPlugin",
                    Class.forName("mcp.mobius.waila.api.IWailaPlugin"));
            register.invoke(null, new WailaPluginHandler());
        } catch (Throwable t) {
            t.printStackTrace();
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
