package reforged.mods.blockhelper.addons;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mods.vintage.core.Refs;
import mods.vintage.core.platform.lang.ILangProvider;
import mods.vintage.core.platform.lang.LangManager;
import reforged.mods.blockhelper.addons.proxy.CommonProxy;

import java.util.Arrays;
import java.util.List;

@Mod(modid = "BlockHelperAddons", name = "Block Helper Addons", version = "1.4.7-1.0.2", acceptedMinecraftVersions = "[1.4.7]", dependencies =
        "required-after:IC2;required-after:mod_BlockHelper;after:AdvancedMachines;after:AdvancedPowerManagement;after:ChargePads;after:GregTech_Addon;after:AdvancedSolarPanel;after:AdvancedMachines;after:AdvancedPowerManagement")
public class BlockHelperAddons implements ILangProvider {

    @SidedProxy(clientSide = "reforged.mods.blockhelper.addons.proxy.ClientProxy", serverSide = "reforged.mods.blockhelper.addons.proxy.CommonProxy")
    public static CommonProxy PROXY;

    public BlockHelperAddons() {}

    @Mod.PreInit
    public void pre(FMLPreInitializationEvent e) {
        PROXY.loadPre();
        LangManager.THIS.registerLangProvider(this);
    }

    @Mod.PostInit
    public void post(FMLPostInitializationEvent e) {
        PROXY.loadPost();
    }

    @Override
    public String getModid() {
        return "BlockHelperAddons";
    }

    @Override
    public List<String> getLocalizationList() {
        return Arrays.asList(BlockHelperAddonsConfig.LANGS);
    }
}
