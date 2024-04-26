package reforged.mods.blockhelper.addons;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import reforged.mods.blockhelper.addons.proxy.CommonProxy;

@Mod(modid = "BlockHelperAddons", name = "Block Helper Addons", version = "1.5.2-1.0.2", acceptedMinecraftVersions = "[1.5.2]", dependencies =
        "required-after:IC2;required-after:mod_BlockHelper;after:AdvancedMachines;after:AdvancedPowerManagement;after:ChargePads;after:GregTech_Addon;after:AdvancedSolarPanel;after:AdvancedMachines;after:AdvancedPowerManagement")
public class BlockHelperAddons {

    @SidedProxy(clientSide = "reforged.mods.blockhelper.addons.proxy.ClientProxy", serverSide = "reforged.mods.blockhelper.addons.proxy.CommonProxy")
    public static CommonProxy PROXY;

    public BlockHelperAddons() {}

    @Mod.PreInit
    public void pre(FMLPreInitializationEvent e) {
        PROXY.loadPre();
    }

    @Mod.PostInit
    public void post(FMLPostInitializationEvent e) {
        PROXY.loadPost();
    }
}
