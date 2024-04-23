package reforged.mods.wailaaddonsic2;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import reforged.mods.wailaaddonsic2.proxy.CommonProxy;

@Mod(modid = "blockhelperaddonsic2", name = "Block Helper Addons - IC2", version = "1.4.7-1.0.1", acceptedMinecraftVersions = "[1.4.7]", dependencies =
        "required-after:IC2;required-after:mod_BlockHelper;after:AdvancedMachines;after:AdvancedPowerManagement;after:ChargePads;after:GregTech_Addon;after:AdvancedSolarPanel;after:AdvancedMachines;after:AdvancedPowerManagement")
public class BlockHelperAddonsIC2 {

    @SidedProxy(clientSide = "reforged.mods.wailaaddonsic2.proxy.ClientProxy", serverSide = "reforged.mods.wailaaddonsic2.proxy.CommonProxy")
    public static CommonProxy PROXY;

    public BlockHelperAddonsIC2() {}

    @Mod.PreInit
    public void pre(FMLPreInitializationEvent e) {
        PROXY.loadPre();
    }

    @Mod.PostInit
    public void post(FMLPostInitializationEvent e) {
        PROXY.loadPost();
    }
}
