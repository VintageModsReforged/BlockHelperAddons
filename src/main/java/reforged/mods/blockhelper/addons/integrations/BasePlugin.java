package reforged.mods.blockhelper.addons.integrations;

import de.thexxturboxx.blockhelper.api.BlockHelperBlockProvider;
import de.thexxturboxx.blockhelper.api.BlockHelperInfoProvider;
import de.thexxturboxx.blockhelper.api.BlockHelperModSupport;

public class BasePlugin {

    public static void add(BlockHelperBlockProvider provider) {
        BlockHelperModSupport.registerBlockProvider(provider);
    }

    public static void addIconProvider(BlockHelperInfoProvider provider) {
        BlockHelperModSupport.registerItemStackFixer(provider);
    }
}
