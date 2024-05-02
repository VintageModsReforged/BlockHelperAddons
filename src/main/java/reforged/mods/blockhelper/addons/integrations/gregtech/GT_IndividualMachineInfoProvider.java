package reforged.mods.blockhelper.addons.integrations.gregtech;

import de.thexxturboxx.blockhelper.api.BlockHelperBlockProvider;
import de.thexxturboxx.blockhelper.api.BlockHelperBlockState;
import de.thexxturboxx.blockhelper.api.InfoHolder;
import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.metatileentity.MetaTileEntity;
import gregtechmod.common.tileentities.*;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.TextColor;

public class GT_IndividualMachineInfoProvider implements BlockHelperBlockProvider {

    @Override
    public void addInformation(BlockHelperBlockState blockHelperBlockState, InfoHolder infoHolder) {
        TileEntity tile = blockHelperBlockState.te;
        if (tile instanceof IGregTechTileEntity) {
            IGregTechTileEntity baseTile = (IGregTechTileEntity) tile;
            MetaTileEntity metaTile = baseTile.getMetaTileEntity();
            if (metaTile != null) {
                if (metaTile instanceof GT_MetaTileEntity_BlastFurnace) {
                    GT_MetaTileEntity_BlastFurnace blast = (GT_MetaTileEntity_BlastFurnace) metaTile;
                    if (blast.mMachine) {
                        infoHolder.add(TextColor.DARK_GREEN.format("info.heat") + " " + blast.mHeatCapacity + " K");
                    } else {
                        infoHolder.add(TextColor.RED.format("info.gt.invalid"));
                    }
                } else if (metaTile instanceof GT_MetaTileEntity_Grinder) {
                    GT_MetaTileEntity_Grinder grinder = (GT_MetaTileEntity_Grinder) metaTile;
                    if (!grinder.mMachine) {
                        infoHolder.add(TextColor.RED.format("info.gt.invalid"));
                    }
                } else if (metaTile instanceof GT_MetaTileEntity_Sawmill) {
                    GT_MetaTileEntity_Sawmill sawmill = (GT_MetaTileEntity_Sawmill) metaTile;
                    if (!sawmill.mMachine) {
                        infoHolder.add(TextColor.RED.format("info.gt.invalid"));
                    }
                } else if (metaTile instanceof GT_MetaTileEntity_ImplosionCompressor) {
                    GT_MetaTileEntity_ImplosionCompressor compressor = (GT_MetaTileEntity_ImplosionCompressor) metaTile;
                    if (!compressor.mMachine) {
                        infoHolder.add(TextColor.RED.format("info.gt.invalid"));
                    }
                } else if (metaTile instanceof GT_MetaTileEntity_DistillationTower) {
                    GT_MetaTileEntity_DistillationTower tower = (GT_MetaTileEntity_DistillationTower) metaTile;
                    if (!tower.mMachine) {
                        infoHolder.add(TextColor.RED.format("info.gt.invalid"));
                    }
                }
            }
            if (baseTile instanceof GT_TileEntity_Matterfabricator) {
                GT_TileEntity_Matterfabricator matter = (GT_TileEntity_Matterfabricator) baseTile;
                int progress = (int) Math.max(0L, (long) matter.getProgresstime() / Math.max(1L, (long) matter.maxProgresstime() / 100L));
                infoHolder.add(TextColor.LIGHT_PURPLE.format("info.progress", progress) + "%");
            } else if (baseTile instanceof GT_TileEntity_PlayerDetector) {
                GT_TileEntity_PlayerDetector detector = (GT_TileEntity_PlayerDetector) baseTile;
                byte mode = detector.mMode;
                if (mode == 0) {
                    infoHolder.add(TextColor.GREEN.format("Detects all Players"));
                } else if (mode == 1) {
                    infoHolder.add(TextColor.YELLOW.format("Detects only you"));
                } else if (mode == 2) {
                    infoHolder.add(TextColor.AQUA.format("Detects only other Players"));
                }
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
