package reforged.mods.blockhelper.addons.integrations;

import de.thexxturboxx.blockhelper.api.BlockHelperBlockProvider;
import de.thexxturboxx.blockhelper.api.BlockHelperItemStackFixer;
import de.thexxturboxx.blockhelper.api.BlockHelperModSupport;
import de.thexxturboxx.blockhelper.api.BlockHelperNameFixer;

public class BasePlugin {

    public static void add(BlockHelperBlockProvider provider) {
        BlockHelperModSupport.registerBlockProvider(provider);
    }

    public static void addIconProvider(BlockHelperItemStackFixer provider) {
        BlockHelperModSupport.registerItemStackFixer(provider);
    }

    public static void addNameProvider(BlockHelperNameFixer provider) {
        BlockHelperModSupport.registerNameFixer(provider);
    }
}
