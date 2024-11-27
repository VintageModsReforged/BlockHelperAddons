package reforged.mods.blockhelper.addons.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IInfoProvider {

    boolean canHandle(EntityPlayer player);

    interface IFilter {
        boolean matches(ItemStack stack);
    }
}
