package reforged.mods.blockhelper.addons.integrations.ic2;

import cpw.mods.fml.common.Loader;
import de.thexxturboxx.blockhelper.BlockHelperCommonProxy;
import reforged.mods.blockhelper.addons.integrations.AdvancedMachinesInfoProvider;
import reforged.mods.blockhelper.addons.integrations.AdvancedPowerManagementInfoProvider;
import reforged.mods.blockhelper.addons.integrations.AdvancedSolarPanelInfoProvider;
import reforged.mods.blockhelper.addons.integrations.BasePlugin;
import reforged.mods.blockhelper.addons.integrations.gregtech.GregTechPlugin;

public class IC2Plugin extends BasePlugin {

    public final String MODID = "IC2";

    public static final IC2Plugin THIS = new IC2Plugin();

    public IC2Plugin() {}

    public void init() {
        if (Loader.isModLoaded(MODID)) {
            BlockHelperCommonProxy.ic2Integration = false;
            add(new EUStorageInfoProvider());
            add(new CableInfoProvider());
            add(new BaseMachineInfoProvider());
            add(new IndividualInfoProvider());
            add(new GeneratorInfoProvider());
            add(new TransformerInfoProvider());
            add(new TeleporterInfoProvider());
            addNameProvider(new CropInfoProvider());
            addIconProvider(new CropInfoProvider());
            add(new CropInfoProvider());
            if (Loader.isModLoaded("AdvancedSolarPanel")) {
                add(new AdvancedSolarPanelInfoProvider());
            }
            if (Loader.isModLoaded("AdvancedMachines")) {
                add(new AdvancedMachinesInfoProvider());
            }
            if (Loader.isModLoaded("AdvancedPowerManagement")) {
                add(new AdvancedPowerManagementInfoProvider());
            }
            add(new WrenchableInfoProvider());
            GregTechPlugin.THIS.init();
        }
    }
}
