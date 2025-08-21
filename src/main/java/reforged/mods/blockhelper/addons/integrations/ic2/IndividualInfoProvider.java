package reforged.mods.blockhelper.addons.integrations.ic2;

import ic2.api.item.Items;
import ic2.core.block.machine.tileentity.*;
import mods.vintage.core.platform.lang.Translator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.ColorUtils;
import reforged.mods.blockhelper.addons.utils.Formatter;
import reforged.mods.blockhelper.addons.utils.Helper;
import reforged.mods.blockhelper.addons.utils.InfoProvider;
import reforged.mods.blockhelper.addons.utils.interfaces.IWailaHelper;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class IndividualInfoProvider extends InfoProvider {

    public static final IndividualInfoProvider THIS = new IndividualInfoProvider();

    @Override
    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TileEntityCanner) {
            TileEntityCanner canner = (TileEntityCanner) blockEntity;
            text(helper, usage(canner.energyconsume));

            int progress = canner.gaugeProgressScaled(100);
            if (progress > 0) {
                bar(helper, progress, 100, translate("info.progress", progress), ColorUtils.PROGRESS);
            }
        } else if (blockEntity instanceof TileEntityInduction) {
            TileEntityInduction induction = (TileEntityInduction) blockEntity;
            text(helper, usage(15));
            int heat = induction.heat;
            int maxHeat = 10000;
            if (heat > 0)
                bar(helper, heat, maxHeat, translate("info.speed.heat", heat * 100 / maxHeat), -295680);
            int progress = induction.progress;
            if (progress > 0) {
                int operationsPerTick = induction.heat / 30;
                int scaledOp = (int) Math.min(6.0E7F, (float) progress / operationsPerTick);
                int scaledMaxOp = (int) Math.min(6.0E7F, (float) 4000 / operationsPerTick);
                bar(helper, scaledOp, scaledMaxOp, Translator.WHITE.format("info.progress.full", scaledOp, scaledMaxOp).concat("t"), ColorUtils.PROGRESS);
            }
        } else if (blockEntity instanceof TileEntityPump) {
            TileEntityPump pump = (TileEntityPump) blockEntity;
            text(helper, usage(1));
            int progress = (pump.energy * 100) / 200;
            if (progress > 0) {
                bar(helper, progress, 120, translate("info.progress.full", progress, 120), -16733185);
            }
        } else if (blockEntity instanceof TileEntityMatter) {
            TileEntityMatter massFab = (TileEntityMatter) blockEntity;
            int progress = massFab.energy;
            int maxProgress = massFab.maxEnergy;
            if (progress > 0) {
                double finalProgress = Math.min((double) progress / maxProgress * 100.0, 100);
                bar(helper, progress, maxProgress, translate("info.progress",
                        Formatter.THERMAL_GEN.format(finalProgress)), -4441721);
            }
            int scrap = massFab.scrap;
            if (scrap > 0) {
                bar(helper, scrap, 45000, translate("info.matter.amplifier",
                        massFab.scrap), -10996205);
            }
        } else if (blockEntity instanceof TileEntityMiner) {
            TileEntityMiner miner = (TileEntityMiner) blockEntity;
            int progress = 20;
            int usage = 3;
            try {
                ItemStack drill = miner.drillSlot.get();
                if (drill.itemID == Items.getItem("miningDrill").itemID) {
                    usage = 6;
                } else if (drill.itemID == Items.getItem("diamondDrill").itemID) {
                    usage = 200;
                    progress = 50;
                }
            } catch (Throwable ignored) {}
            text(helper, usage(usage));
            text(helper, translate(getMiningMode(miner)));
            text(helper, Translator.GOLD.format("info.miner.level", getOperationHeight(miner)));
            double displayProgress = ((double) miner.progress / progress) * 100.0;
            DecimalFormat format = new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.ROOT));
            if (displayProgress > 0) {
                text(helper, Translator.DARK_GREEN.format("info.progress", format.format(displayProgress)));
            }
        } else if (blockEntity instanceof TileEntityCropmatron) {
            TileEntityCropmatron cropmatron = (TileEntityCropmatron) blockEntity;
            bar(helper, cropmatron.energy, cropmatron.maxEnergy, Translator.WHITE.format("info.energy", Formatter.formatNumber(cropmatron.energy, 2), Formatter.formatNumber(cropmatron.maxEnergy, 2)), ColorUtils.RED);
            text(helper, tier(Helper.getTierFromEU(TileEntityCropmatron.maxInput)));
            text(helper, maxIn(TileEntityCropmatron.maxInput));
        } else if (blockEntity instanceof TileEntityTesla) {
            TileEntityTesla tesla = (TileEntityTesla) blockEntity;
            text(helper, tier(Helper.getTierFromEU(tesla.maxInput)));
            text(helper, maxIn(tesla.maxInput));
        }
        if (blockEntity instanceof TileEntityElectrolyzer) {
            TileEntityElectrolyzer electrolyzer = (TileEntityElectrolyzer) blockEntity;
            if (electrolyzer.energy > 0)
                bar(helper, electrolyzer.energy, 20000, Translator.WHITE.format("info.progress.full", electrolyzer.energy, 20000).concat(" EU"), ColorUtils.RED);
        }
    }

    private String getMiningMode(TileEntityMiner miner) {
        int operationHeight = getOperationHeight(miner);
        if (miner.drillSlot.isEmpty()) {
            return "info.miner.retracting";
        } else if (operationHeight >= 0) {
            int blockId = miner.worldObj.getBlockId(miner.xCoord, operationHeight, miner.zCoord);
            if (blockId != Items.getItem("miningPipeTip").itemID) {
                return "info.miner.stuck";
            } else {
                return "info.miner.mining";
            }
        }
        return Translator.RED.literal("ERROR, please report!");
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
