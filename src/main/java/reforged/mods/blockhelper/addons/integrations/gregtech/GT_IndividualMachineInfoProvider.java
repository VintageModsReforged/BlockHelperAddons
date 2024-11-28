package reforged.mods.blockhelper.addons.integrations.gregtech;

import de.thexxturboxx.blockhelper.api.InfoHolder;
import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.metatileentity.MetaTileEntity;
import gregtechmod.common.tileentities.*;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.InfoProvider;

public class GT_IndividualMachineInfoProvider extends InfoProvider {

    @Override
    public void addInfo(InfoHolder helper, TileEntity blockEntity) {
        if (blockEntity instanceof IGregTechTileEntity) {
            IGregTechTileEntity baseTile = (IGregTechTileEntity) blockEntity;
            MetaTileEntity metaTile = baseTile.getMetaTileEntity();
            if (metaTile != null) {
                if (metaTile instanceof GT_MetaTileEntity_BlastFurnace) {
                    GT_MetaTileEntity_BlastFurnace blast = (GT_MetaTileEntity_BlastFurnace) metaTile;
                    if (blast.mMachine) {
                        helper.add(FormattedTranslator.DARK_GREEN.format("info.heat") + " " + blast.mHeatCapacity + " K");
                    } else {
                        helper.add(FormattedTranslator.RED.format("info.gt.invalid"));
                    }
                } else if (metaTile instanceof GT_MetaTileEntity_Grinder) {
                    GT_MetaTileEntity_Grinder grinder = (GT_MetaTileEntity_Grinder) metaTile;
                    if (!grinder.mMachine) {
                        helper.add(FormattedTranslator.RED.format("info.gt.invalid"));
                    }
                } else if (metaTile instanceof GT_MetaTileEntity_Sawmill) {
                    GT_MetaTileEntity_Sawmill sawmill = (GT_MetaTileEntity_Sawmill) metaTile;
                    if (!sawmill.mMachine) {
                        helper.add(FormattedTranslator.RED.format("info.gt.invalid"));
                    }
                } else if (metaTile instanceof GT_MetaTileEntity_ImplosionCompressor) {
                    GT_MetaTileEntity_ImplosionCompressor compressor = (GT_MetaTileEntity_ImplosionCompressor) metaTile;
                    if (!compressor.mMachine) {
                        helper.add(FormattedTranslator.RED.format("info.gt.invalid"));
                    }
                } else if (metaTile instanceof GT_MetaTileEntity_DistillationTower) {
                    GT_MetaTileEntity_DistillationTower tower = (GT_MetaTileEntity_DistillationTower) metaTile;
                    if (!tower.mMachine) {
                        helper.add(FormattedTranslator.RED.format("info.gt.invalid"));
                    }
                }
            }
            if (baseTile instanceof GT_TileEntity_Matterfabricator) {
                GT_TileEntity_Matterfabricator matter = (GT_TileEntity_Matterfabricator) baseTile;
                int progress = (int) Math.max(0L, (long) matter.getProgresstime() / Math.max(1L, (long) matter.maxProgresstime() / 100L));
                helper.add(FormattedTranslator.LIGHT_PURPLE.format("info.progress", progress) + "%");
            } else if (baseTile instanceof GT_TileEntity_PlayerDetector) {
                GT_TileEntity_PlayerDetector detector = (GT_TileEntity_PlayerDetector) baseTile;
                byte mode = detector.mMode;
                if (mode == 0) {
                    helper.add(FormattedTranslator.GREEN.format("info.gt.detector.players.all"));
                } else if (mode == 1) {
                    helper.add(FormattedTranslator.YELLOW.format("info.gt.detector.players.you"));
                } else if (mode == 2) {
                    helper.add(FormattedTranslator.AQUA.format("info.gt.detector.players.other"));
                }
            }
        }
    }
}
