package reforged.mods.blockhelper.addons;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mcp.mobius.waila.mod_BlockHelper;
import mods.vintage.core.helpers.ConfigHelper;
import mods.vintage.core.platform.lang.ILangProvider;
import mods.vintage.core.platform.lang.LangManager;
import net.minecraftforge.common.Configuration;
import reforged.mods.blockhelper.addons.base.WailaCommonHandler;

import java.util.Arrays;
import java.util.List;

@Mod(modid = "BlockHelperAddons", name = "Block Helper Addons", version = "1.5.2-1.0.5", acceptedMinecraftVersions = "[1.5.2]", dependencies =
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
        mod_BlockHelper.proxy.registerPlugin(new WailaPluginHandler());
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
