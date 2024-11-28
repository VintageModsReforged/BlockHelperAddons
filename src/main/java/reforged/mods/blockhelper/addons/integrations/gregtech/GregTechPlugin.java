package reforged.mods.blockhelper.addons.integrations.gregtech;

import cpw.mods.fml.common.Loader;
import reforged.mods.blockhelper.addons.integrations.BasePlugin;

public class GregTechPlugin extends BasePlugin {

    public static final String MODID = "GregTech_Addon";

    public static final GregTechPlugin THIS = new GregTechPlugin();

    public void init() {
        if (Loader.isModLoaded(MODID)) {
            add(new GT_BaseMetaMachineInfoProvider());
            add(new GT_MetaMachineInfoProvider());
            add(new GT_IndividualMachineInfoProvider());
            add(new GT_WrenchableInfoProvider());
        }
    }
}
