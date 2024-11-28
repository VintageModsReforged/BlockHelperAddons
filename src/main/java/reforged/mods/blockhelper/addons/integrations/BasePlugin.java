package reforged.mods.blockhelper.addons.integrations;

import de.thexxturboxx.blockhelper.api.BlockHelperBlockProvider;
import de.thexxturboxx.blockhelper.api.BlockHelperItemStackFixer;
import de.thexxturboxx.blockhelper.api.BlockHelperModSupport;
import mods.vintage.core.VintageCore;

public class BasePlugin {

    public void add(BlockHelperBlockProvider provider) {
        BlockHelperModSupport.registerBlockProvider(provider);
        VintageCore.LOGGER.info("Loaded: " + provider.toString());
    }

    public void addIconProvider(BlockHelperItemStackFixer provider) {
        BlockHelperModSupport.registerItemStackFixer(provider);
    }
}
