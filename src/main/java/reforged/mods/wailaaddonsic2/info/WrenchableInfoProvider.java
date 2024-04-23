package reforged.mods.wailaaddonsic2.info;

import de.thexxturboxx.blockhelper.api.BlockHelperBlockProvider;
import de.thexxturboxx.blockhelper.api.BlockHelperBlockState;
import de.thexxturboxx.blockhelper.api.InfoHolder;
import ic2.api.IWrenchable;
import ic2.core.item.tool.ItemToolWrench;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.wailaaddonsic2.Helper;
import reforged.mods.wailaaddonsic2.TextColor;
import reforged.mods.wailaaddonsic2.i18n.I18n;

public class WrenchableInfoProvider implements BlockHelperBlockProvider {

    @Override
    public void addInformation(BlockHelperBlockState blockHelperBlockState, InfoHolder infoHolder) {
        TileEntity machine = blockHelperBlockState.te;
        ItemStack heldStack = Minecraft.getMinecraft().thePlayer.getHeldItem();
        boolean showInfo = false;
        float dropRate = 0;
        if (machine instanceof IWrenchable) {
            IWrenchable wrenchable = (IWrenchable) machine;
            dropRate = wrenchable.getWrenchDropRate();
            if (dropRate > 0) {
                showInfo = true;
            }
        }
        if (showInfo) {
            if (heldStack != null) {
                if (heldStack.getItem() instanceof ItemToolWrench) {
                    int actualDrop = ((ItemToolWrench) heldStack.getItem()).overrideWrenchSuccessRate(heldStack) ? 100 : (int) (dropRate * 100);
                    infoHolder.add(TextColor.GOLD.format(Helper.getTextColor(actualDrop) + actualDrop + "% " + "\2476" + I18n.format("info.wrenchable.rate")));
                } else {
                    infoHolder.add(TextColor.GOLD.format(I18n.format("info.wrenchable")));
                }
            } else {
                infoHolder.add(TextColor.GOLD.format(I18n.format("info.wrenchable")));
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
