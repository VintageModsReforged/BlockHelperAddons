package reforged.mods.blockhelper.addons.integrations.gregtech;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import de.thexxturboxx.blockhelper.api.BlockHelperBlockProvider;
import de.thexxturboxx.blockhelper.api.BlockHelperBlockState;
import de.thexxturboxx.blockhelper.api.InfoHolder;
import gregtechmod.api.items.GT_Wrench_Item;
import gregtechmod.api.metatileentity.BaseTileEntity;
import ic2.api.tile.IWrenchable;
import ic2.core.IC2;
import ic2.core.item.tool.ItemToolWrench;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.Helper;
import reforged.mods.blockhelper.addons.TextColor;

public class GT_WrenchableInfoProvider implements BlockHelperBlockProvider {

    @Override
    public void addInformation(BlockHelperBlockState blockHelperBlockState, InfoHolder infoHolder) {
        ItemStack heldStack = IC2.platform.getPlayerInstance().getHeldItem();
        TileEntity machine = blockHelperBlockState.te;
        int actualDrop;
        if (machine instanceof IWrenchable && ((IWrenchable) machine).getWrenchDropRate() > 0) {
            IWrenchable wrenchable = (IWrenchable) machine;
            if (heldStack != null) {
                if (heldStack.getItem() instanceof GT_Wrench_Item) {
                    actualDrop = 100;
                    infoHolder.add(TextColor.GOLD.format(Helper.getTextColor(actualDrop) + actualDrop) + "% " + TextColor.YELLOW.format("probe.info.wrenchable.rate"));
                } else if (heldStack.getItem() instanceof ItemToolWrench) {
                    float dropRate = wrenchable.getWrenchDropRate();
                    actualDrop = ((ItemToolWrench) heldStack.getItem()).overrideWrenchSuccessRate(heldStack) ? 100 : (int) (dropRate * 100);
                    infoHolder.add(TextColor.GOLD.format(Helper.getTextColor(actualDrop) + actualDrop) + "% " + TextColor.YELLOW.format("probe.info.wrenchable.rate"));
                } else {
                    infoHolder.add(TextColor.GOLD.format("probe.probe.info.wrenchable"));
                }
            } else {
                infoHolder.add(TextColor.GOLD.format("probe.probe.info.wrenchable"));
            }
        } else if (machine instanceof BaseTileEntity) {
            if (heldStack != null) {
                if (heldStack.getItem() instanceof GT_Wrench_Item) {
                    actualDrop = 100;
                    infoHolder.add(TextColor.GOLD.format(Helper.getTextColor(actualDrop) + actualDrop) + "% " + TextColor.YELLOW.format("probe.info.wrenchable.rate"));
                } else if (heldStack.getItem() instanceof ItemToolWrench) {
                    infoHolder.add(TextColor.RED.format("probe.info.gt.wrenchable.warning"));
                } else {
                    infoHolder.add(TextColor.GOLD.format("probe.info.wrenchable"));
                }
            } else {
                infoHolder.add(TextColor.GOLD.format("probe.info.wrenchable"));
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return Loader.isModLoaded("GregTech_Addon");
    }
}
