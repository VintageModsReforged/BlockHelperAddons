package reforged.mods.blockhelper.addons.integrations.gregtech;

import gregtechmod.api.BaseMetaTileEntity;
import gregtechmod.api.MetaTileEntity;
import gregtechmod.common.tileentities.GT_MetaTileEntity_BasicMachine;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.ColorUtils;
import reforged.mods.blockhelper.addons.utils.Helper;
import reforged.mods.blockhelper.addons.utils.InfoProvider;
import reforged.mods.blockhelper.addons.utils.interfaces.IWailaHelper;

public class GT_BaseMetaMachineInfoProvider extends InfoProvider {

    @Override
    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof BaseMetaTileEntity) {
            BaseMetaTileEntity baseMetaTileEntity = (BaseMetaTileEntity) blockEntity;
            MetaTileEntity metaTileEntity = baseMetaTileEntity.mMetaTileEntity;
            if (metaTileEntity != null) {
                if (metaTileEntity.maxEUStore() > 0) {
                    int storage = Math.min(baseMetaTileEntity.getStoredEnergy(), baseMetaTileEntity.getCapacity());
                    bar(helper, storage, baseMetaTileEntity.getCapacity(), translate("info.energy", storage, baseMetaTileEntity.getCapacity()), ColorUtils.RED);
                }
                int maxInput = baseMetaTileEntity.getMaxSafeInput();
                if (maxInput > 0) {
                    text(helper, tier(Helper.getTierFromEU(maxInput)));
                    text(helper, maxIn(maxInput));
                }
                int maxOut = baseMetaTileEntity.getMaxEnergyOutput();
                if (maxOut > 0) {
                    text(helper, translate("info.generator.max_output", maxOut));
                }
                if (metaTileEntity instanceof GT_MetaTileEntity_BasicMachine) {
                    GT_MetaTileEntity_BasicMachine machine = (GT_MetaTileEntity_BasicMachine) metaTileEntity;
                    int progress = machine.mProgresstime;
                    int maxProgress = machine.mMaxProgresstime;
                    int scaled = (int) (((float) progress / maxProgress) * 100);

                    if (progress > 0)
                        bar(helper, progress, maxProgress, FormattedTranslator.WHITE.format("info.progress", scaled), ColorUtils.PROGRESS);
                }

                String possibleUpgrades = getPossibleUpgrades(metaTileEntity);
                if (!possibleUpgrades.isEmpty()) {
                    text(helper, FormattedTranslator.GREEN.format("info.gt.possible_upgrades") + " " + literal(possibleUpgrades));
                }
            }


            addUpgradesInfo(helper, baseMetaTileEntity);
        }
    }

    public String getPossibleUpgrades(MetaTileEntity metaTileEntity) {
        return (metaTileEntity.isOverclockerUpgradable() ? "O " : "") +
                (metaTileEntity.isTransformerUpgradable() ? "T " : "") +
                (metaTileEntity.isBatteryUpgradable() ? "B " : "") +
                (metaTileEntity.maxMJStore() > 0 ? "M " : "");
    }

    public void addUpgradesInfo(IWailaHelper helper, BaseMetaTileEntity metatileEntity) {
        if (metatileEntity.mOverclockers > 0) {
            text(helper, translate("info.gt.overclockers", metatileEntity.mOverclockers));
        }
        if (metatileEntity.mTransformers > 0) {
            text(helper, translate("info.gt.transformers", metatileEntity.mTransformers));
        }
        if (metatileEntity.mBatteries > 0) {
            text(helper, translate("info.gt.batteries", metatileEntity.mBatteries));
        }
        if (metatileEntity.mMJConverter) {
            text(helper, translate("info.gt.mj", 1));
        }
    }
}
