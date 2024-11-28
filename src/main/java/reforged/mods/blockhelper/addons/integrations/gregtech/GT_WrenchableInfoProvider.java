package reforged.mods.blockhelper.addons.integrations.gregtech;

import de.thexxturboxx.blockhelper.api.InfoHolder;
import gregtechmod.api.items.GT_Wrench_Item;
import gregtechmod.api.metatileentity.BaseTileEntity;
import ic2.api.tile.IWrenchable;
import ic2.core.item.tool.ItemToolWrench;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.BlockHelperAddons;
import reforged.mods.blockhelper.addons.Helper;
import reforged.mods.blockhelper.addons.utils.InfoProvider;

public class GT_WrenchableInfoProvider extends InfoProvider {

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void addInfo(InfoHolder helper, TileEntity blockEntity) {
        int actualDrop;
        ItemStack heldStack = BlockHelperAddons.PROXY.getPlayer().getHeldItem();
        if (blockEntity instanceof IWrenchable && ((IWrenchable) blockEntity).getWrenchDropRate() > 0) {
            IWrenchable wrenchable = (IWrenchable) blockEntity;
            if (heldStack != null) {
                if (heldStack.getItem() instanceof GT_Wrench_Item) {
                    actualDrop = 100;
                    helper.add(FormattedTranslator.GOLD.format("info.wrenchable.rate", Helper.getTextColor(actualDrop).literal(actualDrop + "")));
                } else if (heldStack.getItem() instanceof ItemToolWrench) {
                    float dropRate = wrenchable.getWrenchDropRate();
                    actualDrop = ((ItemToolWrench) heldStack.getItem()).overrideWrenchSuccessRate(heldStack) ? 100 : (int) (dropRate * 100);
                    helper.add(FormattedTranslator.GOLD.format("info.wrenchable.rate", Helper.getTextColor(actualDrop).literal(actualDrop + "")));
                } else {
                    helper.add(FormattedTranslator.GOLD.format("info.wrenchable"));
                }
            } else {
                helper.add(FormattedTranslator.GOLD.format("info.wrenchable"));
            }
        } else if (blockEntity instanceof BaseTileEntity) {
            if (heldStack != null) {
                if (heldStack.getItem() instanceof GT_Wrench_Item) {
                    actualDrop = 100;
                    helper.add(FormattedTranslator.GOLD.format("info.wrenchable.rate", Helper.getTextColor(actualDrop).literal(actualDrop + "")));
                } else if (heldStack.getItem() instanceof ItemToolWrench) {
                    helper.add(FormattedTranslator.RED.format("info.gt.wrenchable.warning"));
                } else {
                    helper.add(FormattedTranslator.GOLD.format("info.wrenchable"));
                }
            } else {
                helper.add(FormattedTranslator.GOLD.format("info.wrenchable"));
            }
        }
    }
}
