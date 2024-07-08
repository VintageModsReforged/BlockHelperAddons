package reforged.mods.blockhelper.addons.integrations.gregtech;

import de.thexxturboxx.blockhelper.api.BlockHelperBlockProvider;
import de.thexxturboxx.blockhelper.api.BlockHelperBlockState;
import de.thexxturboxx.blockhelper.api.InfoHolder;
import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.metatileentity.MetaTileEntity;
import gregtechmod.common.tileentities.GT_MetaTileEntity_TesseractGenerator;
import gregtechmod.common.tileentities.GT_MetaTileEntity_TesseractTerminal;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.Helper;
import reforged.mods.blockhelper.addons.TextColor;

public class GT_BaseMetaMachineInfoProvider implements BlockHelperBlockProvider {

    @Override
    public void addInformation(BlockHelperBlockState blockHelperBlockState, InfoHolder infoHolder) {
        TileEntity tile = blockHelperBlockState.te;
        if (tile instanceof IGregTechTileEntity) {
            IGregTechTileEntity baseMetaTileEntity = (IGregTechTileEntity) tile;
            MetaTileEntity metaTileEntity = baseMetaTileEntity.getMetaTileEntity();
            if (metaTileEntity != null) {
                if (metaTileEntity.maxEUStore() > 0) {
                    int storage = metaTileEntity.getEUVar();
                    if (storage > metaTileEntity.maxEUStore()) {
                        storage = metaTileEntity.maxEUStore();
                    }
                    infoHolder.add(TextColor.AQUA.format("probe.info.energy", storage, metaTileEntity.maxEUStore()));
                }
                int maxInput = metaTileEntity.maxEUInput();
                if (maxInput > 0) {
                    infoHolder.add(TextColor.WHITE.format("probe.info.eu_reader.tier", Helper.getTierForDisplay(Helper.getTierFromEU(metaTileEntity.maxEUInput()))));
                    infoHolder.add(TextColor.WHITE.format("probe.info.eu_reader.max_in", metaTileEntity.maxEUInput()));
                }
                int maxOut = metaTileEntity.maxEUOutput();
                if (maxOut > 0) {
                    infoHolder.add(TextColor.WHITE.format("probe.info.generator.max_output", maxOut));
                }
                if (metaTileEntity instanceof GT_MetaTileEntity_TesseractGenerator) {
                    GT_MetaTileEntity_TesseractGenerator tesseract = (GT_MetaTileEntity_TesseractGenerator) metaTileEntity;
                    infoHolder.add(TextColor.WHITE.format(tesseract.getSecondaryInfo()));
                    infoHolder.add(TextColor.WHITE.format(tesseract.getTertiaryInfo()));
                }
                if (metaTileEntity instanceof GT_MetaTileEntity_TesseractTerminal) {
                    GT_MetaTileEntity_TesseractTerminal tTerminal = (GT_MetaTileEntity_TesseractTerminal) metaTileEntity;
                    infoHolder.add(TextColor.WHITE.format(tTerminal.getSecondaryInfo()));
                    infoHolder.add(TextColor.WHITE.format(tTerminal.getTertiaryInfo()));
                }
            }

//            float progress = metaTileEntity.getProgresstime();
//            if (progress > 0) {
//                infoHolder.add(TextColor.DARK_GREEN.format("probe.info.progress", (int) (progress * 100)) + "%");
//            }

            String possibleUpgrades = getPossibleUpgrades(baseMetaTileEntity);
            if (!possibleUpgrades.isEmpty()) {
                infoHolder.add(TextColor.GREEN.format("probe.info.gt.possible_upgrades") + " " + TextColor.WHITE.literal(possibleUpgrades));
            }
            addUpgradesInfo(infoHolder, baseMetaTileEntity);
        }
    }

    public static String getPossibleUpgrades(IGregTechTileEntity metaTileEntity) {
        return (metaTileEntity.isOverclockerUpgradable() ? "O " : "") +
                (metaTileEntity.isTransformerUpgradable() ? "T " : "") +
                (metaTileEntity.isBatteryUpgradable(0, (byte) 0) ? "B " : "") +
                (metaTileEntity.isMJConverterUpgradable() ? "M " : "") +
                (metaTileEntity.isSteamEngineUpgradable() ? "S " : "");
    }

    public static void addUpgradesInfo(InfoHolder info, IGregTechTileEntity metatileEntity) {
        byte overclockers = metatileEntity.getOverclockerUpgradeCount();
        if (overclockers > 0) {
            info.add(TextColor.WHITE.format("probe.info.gt.overclockers", overclockers));
        }
        byte transformers = metatileEntity.getTransformerUpgradeCount();
        if (transformers > 0) {
            info.add(TextColor.WHITE.format("probe.info.gt.transformers", transformers));
        }
        int storage = metatileEntity.getUpgradeStorageVolume();
        if (storage > 0) {
            info.add(TextColor.WHITE.format("probe.info.gt.batteries", storage));
        }
        if (metatileEntity.hasMJConverterUpgrade()) {
            info.add(TextColor.WHITE.format("probe.info.gt.mj", 1));
        }
        if (metatileEntity.hasSteamEngineUpgrade()) {
            info.add(TextColor.WHITE.format("probe.info.gt.steam", 1));
        }
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
