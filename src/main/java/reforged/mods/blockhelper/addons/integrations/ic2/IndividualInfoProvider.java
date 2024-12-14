package reforged.mods.blockhelper.addons.integrations.ic2;

import de.thexxturboxx.blockhelper.api.InfoHolder;
import ic2.api.Items;
import ic2.core.block.machine.tileentity.*;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.Helper;
import reforged.mods.blockhelper.addons.utils.InfoProvider;

public class IndividualInfoProvider extends InfoProvider {

    @Override
    public void addInfo(InfoHolder helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TileEntityCanner) {
            TileEntityCanner canner = (TileEntityCanner) blockEntity;
            helper.add(usage(canner.energyconsume));
            int progress = canner.gaugeProgressScaled(100);
            if (progress > 0) {
                helper.add(FormattedTranslator.DARK_GREEN.format("info.progress", progress));
            }
        } else if (blockEntity instanceof TileEntityInduction) {
            TileEntityInduction induction = (TileEntityInduction) blockEntity;
            helper.add(usage(15));
            int heat = induction.heat;
            int maxHeat = 10000;
            helper.add(FormattedTranslator.YELLOW.format("info.speed.heat", heat * 100 / maxHeat));
            int progress = induction.gaugeProgressScaled(100);
            if (progress > 0) {
                helper.add(FormattedTranslator.DARK_GREEN.format("info.progress", progress));
            }
        } else if (blockEntity instanceof TileEntityPump) {
            TileEntityPump pump = (TileEntityPump) blockEntity;
            helper.add(usage(1));
            int progress = (pump.energy * 100) / 200;
            if (progress > 0) {
                helper.add(FormattedTranslator.DARK_GREEN.format("info.progress", progress));
            }
        } else if (blockEntity instanceof TileEntityMatter) {
            TileEntityMatter massFab = (TileEntityMatter) blockEntity;
            String progress = massFab.getProgressAsString();
            if (!progress.isEmpty()) {
                helper.add(FormattedTranslator.LIGHT_PURPLE.format("info.progress", progress));
            }
            int scrap = massFab.scrap;
            if (scrap > 0) {
                helper.add(FormattedTranslator.DARK_AQUA.format("info.matter.amplifier", massFab.scrap));
            }
        } else if (blockEntity instanceof TileEntityMiner) {
            TileEntityMiner miner = (TileEntityMiner) blockEntity;
            int progress = 20;
            int usage = 2;
            try {
                ItemStack drill = miner.inventory[3];
                if (drill.itemID == Items.getItem("miningDrill").itemID) {
                    usage = 1;
                    progress = 200;
                } else if (drill.itemID == Items.getItem("diamondDrill").itemID) {
                    usage = 14;
                    progress = 200;
                }
            } catch (Throwable ignored) {}

            helper.add(usage(usage));
            helper.add(translate(getMiningMode(miner)));
            helper.add(FormattedTranslator.GOLD.format("info.miner.level", getOperationHeight(miner)));
            int displayProgress = miner.miningTicker * 100 / progress;
            if (displayProgress > 0) {
                helper.add(FormattedTranslator.DARK_GREEN.format("info.progress", displayProgress));
            }
        } else if (blockEntity instanceof TileEntityCropmatron) {
            helper.add(tier(Helper.getTierFromEU(TileEntityCropmatron.maxInput)));
            helper.add(maxIn(TileEntityCropmatron.maxInput));
        } else if (blockEntity instanceof TileEntityTesla) {
            TileEntityTesla tesla = (TileEntityTesla) blockEntity;
            helper.add(tier(Helper.getTierFromEU(tesla.maxInput)));
            helper.add(maxIn(tesla.maxInput));
        }
    }

    private String getMiningMode(TileEntityMiner miner) {
        int operationHeight = getOperationHeight(miner);
        if (miner.inventory[3] == null) {
            return "info.miner.retracting";
        } else if (operationHeight >= 0) {
            int blockId = miner.worldObj.getBlockId(miner.xCoord, operationHeight, miner.zCoord);
            if (blockId != Items.getItem("miningPipeTip").itemID) {
                return "info.miner.stuck";
            } else {
                return "info.miner.mining";
            }
        }
        return FormattedTranslator.RED.literal("ERROR, please report!");
    }

    private int getOperationHeight(TileEntityMiner miner) {
        for (int y = miner.yCoord - 1; y >= 0; --y) {
            int blockId = miner.worldObj.getBlockId(miner.xCoord, y, miner.zCoord);
            if (blockId != Items.getItem("miningPipe").itemID) {
                return y;
            }
        }
        return -1;
    }
}
