package reforged.mods.blockhelper.addons.integrations.gregtech;

import de.thexxturboxx.blockhelper.api.InfoHolder;
import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.metatileentity.MetaTileEntity;
import gregtechmod.common.tileentities.GT_MetaTileEntity_TesseractGenerator;
import gregtechmod.common.tileentities.GT_MetaTileEntity_TesseractTerminal;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.Helper;
import reforged.mods.blockhelper.addons.utils.InfoProvider;

public class GT_BaseMetaMachineInfoProvider extends InfoProvider {

    @Override
    public void addInfo(InfoHolder helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof IGregTechTileEntity) {
            IGregTechTileEntity baseMetaTileEntity = (IGregTechTileEntity) blockEntity;
            MetaTileEntity metaTileEntity = baseMetaTileEntity.getMetaTileEntity();
            if (metaTileEntity != null) {
                if (baseMetaTileEntity.getEnergyCapacity() > 0) {
                    int storage = Math.min(baseMetaTileEntity.getStoredEU(), baseMetaTileEntity.getEnergyCapacity());
                    helper.add(FormattedTranslator.AQUA.format("info.energy", storage, baseMetaTileEntity.getEnergyCapacity()));
                }
                int maxInput = baseMetaTileEntity.getInputVoltage();
                if (maxInput > 0) {
                    helper.add(tier(Helper.getTierFromEU(maxInput)));
                    helper.add(maxIn(maxInput));
                }
                int maxOut = baseMetaTileEntity.getOutputVoltage();
                if (maxOut > 0) {
                    helper.add(translate("info.generator.max_output", maxOut));
                }
                if (metaTileEntity instanceof GT_MetaTileEntity_TesseractGenerator) {
                    GT_MetaTileEntity_TesseractGenerator tesseract = (GT_MetaTileEntity_TesseractGenerator) metaTileEntity;
                    helper.add(translate(tesseract.getSecondaryInfo()));
                    helper.add(translate(tesseract.getTertiaryInfo()));
                }
                if (metaTileEntity instanceof GT_MetaTileEntity_TesseractTerminal) {
                    GT_MetaTileEntity_TesseractTerminal tTerminal = (GT_MetaTileEntity_TesseractTerminal) metaTileEntity;
                    helper.add(translate(tTerminal.getSecondaryInfo()));
                    helper.add(translate(tTerminal.getTertiaryInfo()));
                }
            }

            String possibleUpgrades = getPossibleUpgrades(baseMetaTileEntity);
            if (!possibleUpgrades.isEmpty()) {
                helper.add(FormattedTranslator.GREEN.format("info.gt.possible_upgrades") + " " + literal(possibleUpgrades));
            }
            addUpgradesInfo(helper, baseMetaTileEntity);
        }
    }

    public String getPossibleUpgrades(IGregTechTileEntity metaTileEntity) {
        return (metaTileEntity.isOverclockerUpgradable() ? "O " : "") +
                (metaTileEntity.isTransformerUpgradable() ? "T " : "") +
                (metaTileEntity.isBatteryUpgradable(0, (byte) 0) ? "B " : "") +
                (metaTileEntity.isMJConverterUpgradable() ? "M " : "") +
                (metaTileEntity.isSteamEngineUpgradable() ? "S " : "");
    }

    public void addUpgradesInfo(InfoHolder info, IGregTechTileEntity metatileEntity) {
        byte overclockers = metatileEntity.getOverclockerUpgradeCount();
        if (overclockers > 0) {
            info.add(translate("info.gt.overclockers", overclockers));
        }
        byte transformers = metatileEntity.getTransformerUpgradeCount();
        if (transformers > 0) {
            info.add(translate("info.gt.transformers", transformers));
        }
        int storage = metatileEntity.getUpgradeStorageVolume();
        if (storage > 0) {
            info.add(translate("info.gt.batteries", storage));
        }
        if (metatileEntity.hasMJConverterUpgrade()) {
            info.add(translate("info.gt.mj", 1));
        }
        if (metatileEntity.hasSteamEngineUpgrade()) {
            info.add(translate("info.gt.steam", 1));
        }
    }
}
