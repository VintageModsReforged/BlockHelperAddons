package reforged.mods.blockhelper.addons.integrations.ic2;

import cpw.mods.fml.common.Loader;
import de.thexxturboxx.blockhelper.BlockHelperCommonProxy;
import reforged.mods.blockhelper.addons.integrations.BasePlugin;
import reforged.mods.blockhelper.addons.integrations.gregtech.GregTechPlugin;

public class IC2Plugin extends BasePlugin {

    public static final String MODID = "IC2";

    public static void init() {
        if (Loader.isModLoaded(MODID)) {
            BlockHelperCommonProxy.ic2Integration = false;
            GregTechPlugin.init();
            add(new EUStorageInfoProvider());
            add(new CableInfoProvider());
            add(new BaseMachineInfoProvider());
            add(new IndividualInfoProvider());
            add(new GeneratorInfoProvider());
            add(new TransformerInfoProvider());
            add(new TeleporterInfoProvider());
            addIconProvider(new CropInfoProvider());
            add(new CropInfoProvider());
            add(new WrenchableInfoProvider());
        }
    }
}
