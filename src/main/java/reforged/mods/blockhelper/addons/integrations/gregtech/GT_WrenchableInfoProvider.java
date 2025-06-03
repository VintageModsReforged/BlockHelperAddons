package reforged.mods.blockhelper.addons.integrations.gregtech;

import gregtechmod.api.items.GT_Wrench_Item;
import gregtechmod.api.metatileentity.BaseTileEntity;
import ic2.api.tile.IWrenchable;
import ic2.core.item.tool.ItemToolWrench;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.Helper;
import reforged.mods.blockhelper.addons.utils.interfaces.IWailaHelper;
import reforged.mods.blockhelper.addons.utils.InfoProvider;

public class GT_WrenchableInfoProvider extends InfoProvider {

    @Override
    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        int actualDrop;
        ItemStack heldStack = player.getHeldItem();
        if (blockEntity instanceof IWrenchable && ((IWrenchable) blockEntity).getWrenchDropRate() > 0) {
            IWrenchable wrenchable = (IWrenchable) blockEntity;
            if (heldStack != null) {
                if (heldStack.getItem() instanceof GT_Wrench_Item) {
                    actualDrop = 100;
                    text(helper, FormattedTranslator.WHITE.format("info.wrenchable.rate", Helper.getTextColor(actualDrop).literal(actualDrop + "")), true);
                } else if (heldStack.getItem() instanceof ItemToolWrench) {
                    float dropRate = wrenchable.getWrenchDropRate();
                    actualDrop = ((ItemToolWrench) heldStack.getItem()).overrideWrenchSuccessRate(heldStack) ? 100 : (int) (dropRate * 100);
                    text(helper, FormattedTranslator.WHITE.format("info.wrenchable.rate", Helper.getTextColor(actualDrop).literal(actualDrop + "")), true);
                } else {
                    text(helper, FormattedTranslator.GOLD.format("info.wrenchable"), true);
                }
            } else {
                text(helper, FormattedTranslator.GOLD.format("info.wrenchable"), true);
            }
        } else if (blockEntity instanceof BaseTileEntity) {
            if (heldStack != null) {
                if (heldStack.getItem() instanceof GT_Wrench_Item) {
                    actualDrop = 100;
                    text(helper, FormattedTranslator.WHITE.format("info.wrenchable.rate", Helper.getTextColor(actualDrop).literal(actualDrop + "")), true);
                } else if (heldStack.getItem() instanceof ItemToolWrench) {
                    text(helper, FormattedTranslator.RED.format("info.gt.wrenchable.warning"), true);
                } else {
                    text(helper, FormattedTranslator.GOLD.format("info.wrenchable"), true);
                }
            } else {
                text(helper, FormattedTranslator.GOLD.format("info.wrenchable"), true);
            }
        }
    }
}
