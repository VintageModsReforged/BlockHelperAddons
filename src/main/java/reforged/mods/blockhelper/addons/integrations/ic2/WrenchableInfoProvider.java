package reforged.mods.blockhelper.addons.integrations.ic2;

import cpw.mods.fml.common.Loader;
import de.thexxturboxx.blockhelper.api.BlockHelperBlockProvider;
import de.thexxturboxx.blockhelper.api.BlockHelperBlockState;
import de.thexxturboxx.blockhelper.api.InfoHolder;
import ic2.api.tile.IWrenchable;
import ic2.core.IC2;
import ic2.core.item.tool.ItemToolWrench;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.Helper;
import reforged.mods.blockhelper.addons.TextColor;

public class WrenchableInfoProvider implements BlockHelperBlockProvider {

    @Override
    public void addInformation(BlockHelperBlockState blockHelperBlockState, InfoHolder infoHolder) {
        TileEntity machine = blockHelperBlockState.te;
        ItemStack heldStack = IC2.platform.getPlayerInstance().getHeldItem();
        float dropRate;
        if (machine instanceof IWrenchable) {
            IWrenchable wrenchable = (IWrenchable) machine;
            dropRate = wrenchable.getWrenchDropRate();
            if (dropRate > 0) {
                if (heldStack != null) {
                    if (heldStack.getItem() instanceof ItemToolWrench) {
                        int actualDrop = ((ItemToolWrench) heldStack.getItem()).overrideWrenchSuccessRate(heldStack) ? 100 : (int) (dropRate * 100);
                        infoHolder.add(TextColor.GOLD.format(Helper.getTextColor(actualDrop) + actualDrop) + "% " + TextColor.YELLOW.format("probe.info.wrenchable.rate"));
                    } else {
                        infoHolder.add(TextColor.GOLD.format("probe.info.wrenchable"));
                    }
                } else {
                    infoHolder.add(TextColor.GOLD.format("probe.info.wrenchable"));
                }
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return !Loader.isModLoaded("GregTech_Addon"); // if GT is loaded, use GT InfoProvider instead
    }
}
