package reforged.mods.blockhelper.addons.integrations.gregtech;

import de.thexxturboxx.blockhelper.api.BlockHelperBlockProvider;
import de.thexxturboxx.blockhelper.api.BlockHelperBlockState;
import de.thexxturboxx.blockhelper.api.InfoHolder;
import gregtechmod.api.BaseMetaTileEntity;
import gregtechmod.api.MetaTileEntity;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.Helper;
import reforged.mods.blockhelper.addons.TextColor;
import reforged.mods.blockhelper.addons.i18n.I18n;

public class GT_BaseMetaMachineInfoProvider implements BlockHelperBlockProvider {

    @Override
    public void addInformation(BlockHelperBlockState blockHelperBlockState, InfoHolder infoHolder) {
        TileEntity tile = blockHelperBlockState.te;
        if (tile instanceof BaseMetaTileEntity) {
            BaseMetaTileEntity baseMetaTileEntity = (BaseMetaTileEntity) tile;
            MetaTileEntity metaTileEntity = baseMetaTileEntity.mMetaTileEntity;
            if (metaTileEntity != null) {
                if (metaTileEntity.maxEUStore() > 0) {
                    infoHolder.add(TextColor.AQUA.format(I18n.format("info.energy", metaTileEntity.getEUVar(), metaTileEntity.maxEUStore())));
                }
                int maxInput = metaTileEntity.maxEUInput();
                if (maxInput > 0) {
                    infoHolder.add(TextColor.WHITE.format(I18n.format("info.eu_reader.tier", Helper.getTierForDisplay(Helper.getTierFromEU(metaTileEntity.maxEUInput())))));
                    infoHolder.add(TextColor.WHITE.format(I18n.format("info.eu_reader.max_in", metaTileEntity.maxEUInput())));
                }
                int maxOut = metaTileEntity.maxEUOutput();
                if (maxOut > 0) {
                    infoHolder.add(TextColor.WHITE.format(I18n.format("info.generator.max_output", maxOut)));
                }
            }
            String possibleUpgrades = getPossibleUpgrades(metaTileEntity);
            if (!possibleUpgrades.isEmpty()) {
                infoHolder.add(TextColor.GREEN.format(I18n.format("info.gt.possible_upgrades") + " " + TextColor.WHITE.format(possibleUpgrades)));
            }
            addUpgradesInfo(infoHolder, baseMetaTileEntity);
        }
    }

    public static String getPossibleUpgrades(MetaTileEntity metaTileEntity) {
        return (metaTileEntity.isOverclockerUpgradable() ? "O " : "") +
                (metaTileEntity.isTransformerUpgradable() ? "T " : "") +
                (metaTileEntity.isBatteryUpgradable() ? "B " : "") +
                (metaTileEntity.maxMJStore() > 0 ? "M " : "");
    }

    public static void addUpgradesInfo(InfoHolder info, BaseMetaTileEntity metatileEntity) {
        if (metatileEntity.mOverclockers > 0) {
            info.add(TextColor.WHITE.format(I18n.format("info.gt.overclockers", metatileEntity.mOverclockers)));
        }
        if (metatileEntity.mTransformers > 0) {
            info.add(TextColor.WHITE.format(I18n.format("info.gt.transformers", metatileEntity.mTransformers)));
        }
        if (metatileEntity.mBatteries > 0) {
            info.add(TextColor.WHITE.format(I18n.format("info.gt.batteries", metatileEntity.mBatteries)));
        }
        if (metatileEntity.mMJConverter) {
            info.add(TextColor.WHITE.format(I18n.format("info.gt.mj", 1)));
        }
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
