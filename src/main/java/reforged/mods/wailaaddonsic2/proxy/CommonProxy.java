package reforged.mods.wailaaddonsic2.proxy;

import cpw.mods.fml.common.Loader;
import de.thexxturboxx.blockhelper.BlockHelperCommonProxy;
import de.thexxturboxx.blockhelper.api.BlockHelperBlockProvider;
import de.thexxturboxx.blockhelper.api.BlockHelperModSupport;
import reforged.mods.wailaaddonsic2.ModConfig;
import reforged.mods.wailaaddonsic2.info.*;
import reforged.mods.wailaaddonsic2.info.addons.AdvancedMachinesInfoProvider;
import reforged.mods.wailaaddonsic2.info.addons.AdvancedPowerManagementInfoProvider;
import reforged.mods.wailaaddonsic2.info.addons.AdvancedSolarPanelInfoProvider;

import java.util.ArrayList;
import java.util.List;

public class CommonProxy {

    public static List<BlockHelperBlockProvider> INFO_PROVIDERS = new ArrayList<BlockHelperBlockProvider>();

    public void loadPre() {
        ModConfig.initConfig();
    }

    public void loadPost() {
        BlockHelperCommonProxy.ic2Integration = false;

        INFO_PROVIDERS.add(new EUStorageInfoProvider());
        INFO_PROVIDERS.add(new CableInfoProvider());
        INFO_PROVIDERS.add(new BaseMachineInfoProvider());
        INFO_PROVIDERS.add(new IndividualInfoProvider());
        INFO_PROVIDERS.add(new GeneratorInfoProvider());
        INFO_PROVIDERS.add(new TransformerInfoProvider());
        INFO_PROVIDERS.add(new TeleporterInfoProvider());
        if (Loader.isModLoaded("AdvancedSolarPanel")) {
            INFO_PROVIDERS.add(new AdvancedSolarPanelInfoProvider());
        }
        if (Loader.isModLoaded("AdvancedMachines") && ModConfig.advanced_machines) {
            INFO_PROVIDERS.add(new AdvancedMachinesInfoProvider());
        }
        if (Loader.isModLoaded("AdvancedPowerManagement")) {
            INFO_PROVIDERS.add(new AdvancedPowerManagementInfoProvider());
        }
        INFO_PROVIDERS.add(new WrenchableInfoProvider());

        for (BlockHelperBlockProvider infoProvider : INFO_PROVIDERS) {
            BlockHelperModSupport.registerBlockProvider(infoProvider);
        }
    }
}
