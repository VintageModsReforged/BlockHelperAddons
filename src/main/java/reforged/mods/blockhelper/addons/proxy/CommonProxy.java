package reforged.mods.blockhelper.addons.proxy;

import net.minecraft.entity.player.EntityPlayer;
import reforged.mods.blockhelper.addons.BlockHelperAddonsConfig;
import reforged.mods.blockhelper.addons.base.WailaCommonHandler;

public class CommonProxy {

    public void loadPre() {
        BlockHelperAddonsConfig.init();
        WailaCommonHandler.init();
    }

    public void loadPost() {}

    public EntityPlayer getPlayer() {
        return null;
    }
}
