package reforged.mods.blockhelper.addons.proxy;

import net.minecraft.entity.player.EntityPlayer;
import reforged.mods.blockhelper.addons.BlockHelperAddonsConfig;
import reforged.mods.blockhelper.addons.ModPlugins;

public class CommonProxy {

    public void loadPre() {
        BlockHelperAddonsConfig.init();
    }

    public void loadPost() {
        ModPlugins.init();
    }

    public EntityPlayer getPlayer() {
        return null;
    }
}
