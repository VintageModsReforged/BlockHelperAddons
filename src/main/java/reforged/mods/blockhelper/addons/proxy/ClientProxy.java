package reforged.mods.blockhelper.addons.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ClientProxy extends CommonProxy {

    @Override
    public void loadPre() {
        super.loadPre();
    }

    @Override
    public void loadPost() {
        super.loadPost();
    }

    @Override
    public EntityPlayer getPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }
}
