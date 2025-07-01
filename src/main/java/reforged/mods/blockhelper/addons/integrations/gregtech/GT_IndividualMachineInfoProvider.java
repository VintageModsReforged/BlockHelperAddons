package reforged.mods.blockhelper.addons.integrations.gregtech;

import gregtechmod.api.BaseMetaTileEntity;
import gregtechmod.api.MetaTileEntity;
import gregtechmod.common.tileentities.*;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.ColorUtils;
import reforged.mods.blockhelper.addons.utils.InfoProvider;
import reforged.mods.blockhelper.addons.utils.interfaces.IWailaHelper;

public class GT_IndividualMachineInfoProvider extends InfoProvider {

    @Override
    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof BaseMetaTileEntity) {
            BaseMetaTileEntity baseTile = (BaseMetaTileEntity) blockEntity;
            MetaTileEntity metaTile = baseTile.mMetaTileEntity;
            if (metaTile != null) {
                if (metaTile instanceof GT_MetaTileEntity_BlastFurnace) {
                    GT_MetaTileEntity_BlastFurnace blast = (GT_MetaTileEntity_BlastFurnace) metaTile;
                    if (blast.mMachine) {
                        text(helper, FormattedTranslator.DARK_GREEN.format("info.heat") + " " + blast.mHeatCapacity + " K");
                    } else {
                        addStructureStatus(helper);
                    }
                } else {
                    boolean needsStructureStatus =
                            (metaTile instanceof GT_MetaTileEntity_Grinder && !((GT_MetaTileEntity_Grinder) metaTile).mMachine)
                                    || (metaTile instanceof GT_MetaTileEntity_Sawmill && !((GT_MetaTileEntity_Sawmill) metaTile).mMachine)
                                    || (metaTile instanceof GT_MetaTileEntity_ImplosionCompressor && !((GT_MetaTileEntity_ImplosionCompressor) metaTile).mMachine)
                                    || (metaTile instanceof GT_MetaTileEntity_DistillationTower && !((GT_MetaTileEntity_DistillationTower) metaTile).mMachine);

                    if (needsStructureStatus) {
                        addStructureStatus(helper);
                    }
                }
                if (metaTile instanceof GT_MetaTileEntity_BasicMachine) {
                    GT_MetaTileEntity_BasicMachine machine = (GT_MetaTileEntity_BasicMachine) metaTile;
                    int progress = machine.mProgresstime;
                    int maxProgress = machine.mMaxProgresstime;
                    int scaled = (int) (((float) progress / maxProgress) * 100);

                    if (progress > 0)
                        bar(helper, progress, maxProgress, FormattedTranslator.WHITE.format("info.progress", scaled), ColorUtils.PROGRESS);
                }
            }
        }
        if (blockEntity instanceof GT_TileEntity_Matterfabricator) {
            GT_TileEntity_Matterfabricator matter = (GT_TileEntity_Matterfabricator) blockEntity;
            int progress = (int) Math.max(0L, (long) matter.getProgresstime() / Math.max(1L, (long) matter.maxProgresstime() / 100L));
            text(helper, FormattedTranslator.LIGHT_PURPLE.format("info.progress", progress));
        } else if (blockEntity instanceof GT_TileEntity_PlayerDetector) {
            GT_TileEntity_PlayerDetector detector = (GT_TileEntity_PlayerDetector) blockEntity;
            int mode = detector.mMode;
            if (mode == 0) {
                text(helper, FormattedTranslator.GREEN.format("info.gt.detector.players.all"));
            } else if (mode == 1) {
                text(helper, FormattedTranslator.YELLOW.format("info.gt.detector.players.you"));
            } else if (mode == 2) {
                text(helper, FormattedTranslator.AQUA.format("info.gt.detector.players.other"));
            }
        }
    }

    public void addStructureStatus(IWailaHelper helper) {
        text(helper, FormattedTranslator.RED.format("info.gt.invalid"));
    }
}
