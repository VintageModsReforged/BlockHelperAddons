package reforged.mods.blockhelper.addons.info;

import de.thexxturboxx.blockhelper.api.BlockHelperBlockProvider;
import de.thexxturboxx.blockhelper.api.BlockHelperBlockState;
import de.thexxturboxx.blockhelper.api.InfoHolder;
import ic2.core.Ic2Items;
import ic2.core.block.machine.tileentity.*;
import ic2.core.util.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.Helper;
import reforged.mods.blockhelper.addons.TextColor;
import reforged.mods.blockhelper.addons.i18n.I18n;

public class IndividualInfoProvider implements BlockHelperBlockProvider {

    @Override
    public void addInformation(BlockHelperBlockState blockHelperBlockState, InfoHolder infoHolder) {
        TileEntity tile = blockHelperBlockState.te;
        if (tile instanceof TileEntityCanner) {
            TileEntityCanner canner = (TileEntityCanner) tile;
            infoHolder.add(TextColor.WHITE.format(I18n.format("info.eu_reader.usage", canner.energyconsume)));

            int progress = canner.gaugeProgressScaled(100);
            if (progress > 0) {
                infoHolder.add(TextColor.DARK_GREEN.format(I18n.format("info.progress", progress) + "%"));
            }
        } else if (tile instanceof TileEntityInduction) {
            TileEntityInduction induction = (TileEntityInduction) tile;
            infoHolder.add(TextColor.WHITE.format(I18n.format("info.eu_reader.usage", 15)));
            int heat = induction.heat;
            int maxHeat = 10000;
            infoHolder.add(TextColor.YELLOW.format(I18n.format("info.induction.heat", heat * 100 / maxHeat) + "%"));
            int progress = induction.gaugeProgressScaled(100);
            if (progress > 0) {
                infoHolder.add(TextColor.DARK_GREEN.format(I18n.format("info.progress", progress) + "%"));
            }
        } else if (tile instanceof TileEntityPump) {
            TileEntityPump pump = (TileEntityPump) tile;
            infoHolder.add(TextColor.WHITE.format(I18n.format("info.eu_reader.usage", 1)));
            int progress = (pump.energy * 100) / 200;
            if (progress > 0) {
                infoHolder.add(TextColor.DARK_GREEN.format(I18n.format("info.progress", progress) + "%"));
            }
        } else if (tile instanceof TileEntityMatter) {
            TileEntityMatter massFab = (TileEntityMatter) tile;
            String progress = massFab.getProgressAsString();
            if (!progress.isEmpty()) {
                infoHolder.add(TextColor.LIGHT_PURPLE.format(I18n.format("info.progress", progress)));
            }
            int scrap = massFab.scrap;
            if (scrap > 0) {
                infoHolder.add(TextColor.DARK_AQUA.format(I18n.format("info.matter.amplifier", massFab.scrap)));
            }
        } else if (tile instanceof TileEntityMiner) {
            TileEntityMiner miner = (TileEntityMiner) tile;
            ItemStack drill = miner.inventory[3];
            int progress;
            int usage;
            if (StackUtil.isStackEqual(drill, Ic2Items.miningDrill)) {
                usage = 1;
                progress = 200;
            } else if (StackUtil.isStackEqual(drill, Ic2Items.diamondDrill)) {
                usage = 14;
                progress = 200;
            } else {
                usage = 2;
                progress = 20;
            }
            infoHolder.add(TextColor.WHITE.format(I18n.format("info.eu_reader.usage", usage)));
            infoHolder.add(TextColor.WHITE.format(getMiningMode(miner)));
            infoHolder.add(TextColor.GOLD.format(I18n.format("info.miner.level", getOperationHeight(miner))));
            int displayProgress = miner.miningTicker * 100 / progress;
            if (displayProgress > 0) {
                infoHolder.add(TextColor.DARK_GREEN.format(I18n.format("info.progress", displayProgress)  + "%"));
            }
        } else if (tile instanceof TileEntityCropmatron) {
            infoHolder.add(TextColor.WHITE.format(I18n.format("info.eu_reader.tier", Helper.getTierForDisplay(Helper.getTierFromMaxInput(TileEntityCropmatron.maxInput)))));
            infoHolder.add(TextColor.WHITE.format(I18n.format("info.eu_reader.max_in", TileEntityCropmatron.maxInput)));
        } else if (tile instanceof TileEntityTesla) {
            TileEntityTesla tesla = (TileEntityTesla) tile;
            infoHolder.add(TextColor.WHITE.format(I18n.format("info.eu_reader.tier", Helper.getTierForDisplay(Helper.getTierFromMaxInput(tesla.maxInput)))));
            infoHolder.add(TextColor.WHITE.format(I18n.format("info.eu_reader.max_in", tesla.maxInput)));
        }
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static String getMiningMode(TileEntityMiner miner) {
        int operationHeight = getOperationHeight(miner);
        if (miner.inventory[3] == null) {
            return I18n.format("info.miner.retracting");
        } else if (operationHeight >= 0) {
            int blockId = miner.worldObj.getBlockId(miner.xCoord, operationHeight, miner.zCoord);
            if (blockId != Ic2Items.miningPipeTip.itemID) {
                return I18n.format("info.miner.stuck");
            } else {
                return I18n.format("info.miner.mining");
            }
        }
        return TextColor.RED.format("ERROR, please report!");
    }

    private static int getOperationHeight(TileEntityMiner miner) {
        for(int y = miner.yCoord - 1; y >= 0; --y) {
            int blockId = miner.worldObj.getBlockId(miner.xCoord, y, miner.zCoord);
            if (blockId != Ic2Items.miningPipe.itemID) {
                return y;
            }
        }
        return -1;
    }
}
