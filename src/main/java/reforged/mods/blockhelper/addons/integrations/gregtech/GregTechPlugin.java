package reforged.mods.blockhelper.addons.integrations.gregtech;

import cpw.mods.fml.common.Loader;
import de.thexxturboxx.blockhelper.api.BlockHelperBlockProvider;
import de.thexxturboxx.blockhelper.api.BlockHelperModSupport;

public class GregTechPlugin {

    public static final String MODID = "GregTech_Addon";

    public static void init() {
        if (Loader.isModLoaded(MODID)) {
            add(new GT_BaseMetaMachineInfoProvider());
            add(new GT_MetaMachineInfoProvider());
            add(new GT_IndividualMachineInfoProvider());
            add(new GT_WrenchableInfoProvider());
        }
    }

    public static void add(BlockHelperBlockProvider provider) {
        BlockHelperModSupport.registerBlockProvider(provider);
    }
}
