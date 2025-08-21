package reforged.mods.blockhelper.addons.integrations.gregtech;

import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.metatileentity.MetaTileEntity;
import gregtechmod.common.tileentities.GT_MetaTileEntity_TesseractGenerator;
import gregtechmod.common.tileentities.GT_MetaTileEntity_TesseractTerminal;
import mods.vintage.core.helpers.ElectricHelper;
import mods.vintage.core.platform.lang.Translator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.ColorUtils;
import reforged.mods.blockhelper.addons.utils.InfoProvider;
import reforged.mods.blockhelper.addons.utils.interfaces.IWailaHelper;

public class GT_BaseMetaMachineInfoProvider extends InfoProvider {

    public static final GT_BaseMetaMachineInfoProvider THIS = new GT_BaseMetaMachineInfoProvider();

    @Override
    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof IGregTechTileEntity) {
            IGregTechTileEntity baseMetaTileEntity = (IGregTechTileEntity) blockEntity;
            MetaTileEntity metaTileEntity = baseMetaTileEntity.getMetaTileEntity();
            if (metaTileEntity != null) {
                if (baseMetaTileEntity.getEnergyCapacity() > 0) {
                    int storage = Math.min(baseMetaTileEntity.getStoredEU(), baseMetaTileEntity.getEnergyCapacity());
                    bar(helper, storage, baseMetaTileEntity.getEnergyCapacity(), translate("info.energy", storage, baseMetaTileEntity.getEnergyCapacity()), ColorUtils.RED);
                }
                int maxInput = baseMetaTileEntity.getInputVoltage();
                if (maxInput > 0) {
                    text(helper, tier(ElectricHelper.getTierFromEU(maxInput)));
                    text(helper, maxIn(maxInput));
                }
                int maxOut = baseMetaTileEntity.getOutputVoltage();
                if (maxOut > 0) {
                    text(helper, translate("info.generator.max_output", maxOut));
                }
                if (metaTileEntity instanceof GT_MetaTileEntity_TesseractGenerator) {
                    GT_MetaTileEntity_TesseractGenerator tesseract = (GT_MetaTileEntity_TesseractGenerator) metaTileEntity;
                    text(helper, translate(tesseract.getSecondaryInfo()));
                    text(helper, translate(tesseract.getTertiaryInfo()));
                }
                if (metaTileEntity instanceof GT_MetaTileEntity_TesseractTerminal) {
                    GT_MetaTileEntity_TesseractTerminal tTerminal = (GT_MetaTileEntity_TesseractTerminal) metaTileEntity;
                    text(helper, translate(tTerminal.getSecondaryInfo()));
                    text(helper, translate(tTerminal.getTertiaryInfo()));
                }
                int progress = metaTileEntity.getProgresstime();
                int maxProgress = metaTileEntity.maxProgresstime();
                int scaled = (int) (((float) progress / maxProgress) * 100);

                if (progress > 0)
                    bar(helper, progress, maxProgress, Translator.WHITE.format("info.progress", scaled), ColorUtils.PROGRESS);
            }

            String possibleUpgrades = getPossibleUpgrades(baseMetaTileEntity);
            if (!possibleUpgrades.isEmpty()) {
                text(helper, Translator.GREEN.format("info.gt.possible_upgrades") + " " + literal(possibleUpgrades));
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

    public void addUpgradesInfo(IWailaHelper helper, IGregTechTileEntity metatileEntity) {
        byte overclockers = metatileEntity.getOverclockerUpgradeCount();
        if (overclockers > 0) {
            text(helper, translate("info.gt.overclockers", overclockers));
        }
        byte transformers = metatileEntity.getTransformerUpgradeCount();
        if (transformers > 0) {
            text(helper, translate("info.gt.transformers", transformers));
        }
        int storage = metatileEntity.getUpgradeStorageVolume();
        if (storage > 0) {
            text(helper, translate("info.gt.batteries", storage));
        }
        if (metatileEntity.hasMJConverterUpgrade()) {
            text(helper, translate("info.gt.mj", 1));
        }
        if (metatileEntity.hasSteamEngineUpgrade()) {
            text(helper, translate("info.gt.steam", 1));
        }
    }
}
