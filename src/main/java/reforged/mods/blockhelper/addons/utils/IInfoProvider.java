package reforged.mods.blockhelper.addons.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public interface IInfoProvider {

    boolean canHandle(EntityPlayer player);
    void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player);

    interface IFilter {
        boolean matches(ItemStack stack);
    }
}
