package reforged.mods.wailaaddonsic2.proxy;

import de.thexxturboxx.blockhelper.BlockHelperCommonProxy;
import de.thexxturboxx.blockhelper.api.BlockHelperBlockProvider;
import de.thexxturboxx.blockhelper.api.BlockHelperModSupport;
import reforged.mods.wailaaddonsic2.ModConfig;
import reforged.mods.wailaaddonsic2.info.*;

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
        INFO_PROVIDERS.add(new WrenchableInfoProvider());
        INFO_PROVIDERS.add(new CropInfoProvider());

        for (BlockHelperBlockProvider infoProvider : INFO_PROVIDERS) {
            BlockHelperModSupport.registerBlockProvider(infoProvider);
        }
        BlockHelperModSupport.registerItemStackFixer(new CropInfoProvider());
    }
}
