package reforged.mods.blockhelper.addons.integrations.ic2;

import de.thexxturboxx.blockhelper.api.InfoHolder;
import ic2.core.Ic2Items;
import ic2.core.block.machine.tileentity.*;
import ic2.core.util.StackUtil;
import mods.vintage.core.platform.lang.FormattedTranslator;
import mods.vintage.core.platform.lang.Translator;
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
            helper.add(FormattedTranslator.YELLOW.format("info.induction.heat", heat * 100 / maxHeat));
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
                helper.add(FormattedTranslator.DARK_GREEN.format("info.progress", progress));
            }
            int scrap = massFab.scrap;
            if (scrap > 0) {
                helper.add(FormattedTranslator.DARK_AQUA.format("info.matter.amplifier", massFab.scrap));
            }
        } else if (blockEntity instanceof TileEntityMiner) {
            TileEntityMiner miner = (TileEntityMiner) blockEntity;
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
            helper.add(usage(usage));
            helper.add(translate(getMiningMode(miner)));
            helper.add(FormattedTranslator.GOLD.format("info.miner.level", getOperationHeight(miner)));
            int displayProgress = miner.miningTicker * 100 / progress;
            if (displayProgress > 0) {
                helper.add(FormattedTranslator.DARK_GREEN.format("info.progress", progress));
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

    public static String getMiningMode(TileEntityMiner miner) {
        int operationHeight = getOperationHeight(miner);
        if (miner.inventory[3] == null) {
            return Translator.format("info.miner.retracting");
        } else if (operationHeight >= 0) {
            int blockId = miner.worldObj.getBlockId(miner.xCoord, operationHeight, miner.zCoord);
            if (blockId != Ic2Items.miningPipeTip.itemID) {
                return Translator.format("info.miner.stuck");
            } else {
                return Translator.format("info.miner.mining");
            }
        }
        return FormattedTranslator.RED.literal("ERROR, please report!");
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
