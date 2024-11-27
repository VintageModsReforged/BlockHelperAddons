package reforged.mods.blockhelper.addons.integrations.gregtech;

import de.thexxturboxx.blockhelper.api.InfoHolder;
import gregtechmod.api.BaseMetaTileEntity;
import gregtechmod.api.MetaTileEntity;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.Helper;
import reforged.mods.blockhelper.addons.utils.InfoProvider;

public class GT_BaseMetaMachineInfoProvider extends InfoProvider {

    @Override
    public void addInfo(InfoHolder helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof BaseMetaTileEntity) {
            BaseMetaTileEntity baseMetaTileEntity = (BaseMetaTileEntity) blockEntity;
            MetaTileEntity metaTileEntity = baseMetaTileEntity.mMetaTileEntity;
            if (metaTileEntity != null) {
                if (metaTileEntity.maxEUStore() > 0) {
                    helper.add(FormattedTranslator.AQUA.format("info.energy", baseMetaTileEntity.getStoredEnergy(), baseMetaTileEntity.getCapacity()));
                }
                int maxInput = baseMetaTileEntity.getMaxSafeInput();
                if (maxInput > 0) {
                    helper.add(tier(Helper.getTierFromEU(maxInput)));
                    helper.add(maxIn(maxInput));
                }
                int maxOut = baseMetaTileEntity.getMaxEnergyOutput();
                if (maxOut > 0) {
                    helper.add(translate("info.generator.max_output", maxOut));
                }
            }
            String possibleUpgrades = getPossibleUpgrades(metaTileEntity);
            if (!possibleUpgrades.isEmpty()) {
                helper.add(FormattedTranslator.GREEN.format("info.gt.possible_upgrades") + " " + literal(possibleUpgrades));
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

    public void addUpgradesInfo(InfoHolder info, BaseMetaTileEntity metatileEntity) {
        if (metatileEntity.mOverclockers > 0) {
            info.add(translate("info.gt.overclockers", metatileEntity.mOverclockers));
        }
        if (metatileEntity.mTransformers > 0) {
            info.add(translate("info.gt.transformers", metatileEntity.mTransformers));
        }
        if (metatileEntity.mBatteries > 0) {
            info.add(translate("info.gt.batteries", metatileEntity.mBatteries));
        }
        if (metatileEntity.mMJConverter) {
            info.add(translate("info.gt.mj", 1));
        }
    }
}
