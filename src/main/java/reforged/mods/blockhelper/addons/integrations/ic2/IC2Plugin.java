package reforged.mods.blockhelper.addons.integrations.ic2;

import cpw.mods.fml.common.Loader;
import de.thexxturboxx.blockhelper.BlockHelperCommonProxy;
import de.thexxturboxx.blockhelper.api.BlockHelperBlockProvider;
import de.thexxturboxx.blockhelper.api.BlockHelperModSupport;
import reforged.mods.blockhelper.addons.ModConfig;
import reforged.mods.blockhelper.addons.integrations.AdvancedMachinesInfoProvider;
import reforged.mods.blockhelper.addons.integrations.AdvancedPowerManagementInfoProvider;
import reforged.mods.blockhelper.addons.integrations.AdvancedSolarPanelInfoProvider;
import reforged.mods.blockhelper.addons.integrations.gregtech.GregTechPlugin;

public class IC2Plugin {

    public static final String MODID = "IC2";

    public static void init() {
        if (Loader.isModLoaded(MODID)) {
            BlockHelperCommonProxy.ic2Integration = false;
            add(new EUStorageInfoProvider());
            add(new CableInfoProvider());
            add(new BaseMachineInfoProvider());
            add(new IndividualInfoProvider());
            add(new GeneratorInfoProvider());
            add(new TransformerInfoProvider());
            add(new TeleporterInfoProvider());
            BlockHelperModSupport.registerItemStackFixer(new CropInfoProvider());
            add(new CropInfoProvider());
            if (Loader.isModLoaded("AdvancedSolarPanel")) {
                add(new AdvancedSolarPanelInfoProvider());
            }
            if (Loader.isModLoaded("AdvancedMachines") && ModConfig.advanced_machines) {
                add(new AdvancedMachinesInfoProvider());
            }
            if (Loader.isModLoaded("AdvancedPowerManagement")) {
                add(new AdvancedPowerManagementInfoProvider());
            }
            add(new WrenchableInfoProvider());
            GregTechPlugin.init();
        }
    }

    public static void add(BlockHelperBlockProvider provider) {
        BlockHelperModSupport.registerBlockProvider(provider);
    }
}
