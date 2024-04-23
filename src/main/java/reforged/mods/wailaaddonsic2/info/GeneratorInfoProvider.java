package reforged.mods.wailaaddonsic2.info;

import de.thexxturboxx.blockhelper.api.BlockHelperBlockProvider;
import de.thexxturboxx.blockhelper.api.BlockHelperBlockState;
import de.thexxturboxx.blockhelper.api.InfoHolder;
import ic2.api.IReactor;
import ic2.api.IReactorChamber;
import ic2.core.block.generator.tileentity.*;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.wailaaddonsic2.BarElement;
import reforged.mods.wailaaddonsic2.Helper;
import reforged.mods.wailaaddonsic2.TextColor;
import reforged.mods.wailaaddonsic2.i18n.I18n;

public class GeneratorInfoProvider implements BlockHelperBlockProvider {

    @Override
    public void addInformation(BlockHelperBlockState blockHelperBlockState, InfoHolder infoHolder) {
        TileEntity tile = blockHelperBlockState.te;
        if (tile instanceof TileEntityBaseGenerator) {
            TileEntityBaseGenerator gen = (TileEntityBaseGenerator) tile;
            infoHolder.add(TextColor.WHITE.format(I18n.format("info.eu_reader.tier", Helper.getTierForDisplay(1))));
            double production = 0;
            if (gen instanceof TileEntityGenerator || gen instanceof TileEntityGeoGenerator) {
                if (gen.isConverting()) {
                    production = gen.production; // return production only when converting fuel
                }
            } else if (gen instanceof TileEntitySolarGenerator) { // special case for solar gen
                if (((TileEntitySolarGenerator) gen).sunIsVisible) {
                    production = 1;
                }
            } else {
                production = gen.production;
            }
            if (production > 0) { // blame windmill
                infoHolder.add(TextColor.WHITE.format(I18n.format("info.generator.output", production)));
            }
            infoHolder.add(TextColor.WHITE.format(I18n.format("info.generator.max_output", gen.getMaxEnergyOutput())));
        }
        if (tile instanceof IReactor) {
            addReactorInfo(infoHolder, tile);
        } else if (tile instanceof IReactorChamber) {
            IReactor reactor = ((IReactorChamber) tile).getReactor();
            addReactorInfo(infoHolder, (TileEntity) reactor);
        }
    }

    public static void addReactorInfo(InfoHolder infoHolder, TileEntity reactorRelated) {
        if (reactorRelated instanceof TileEntityNuclearReactor) {
            TileEntityNuclearReactor reactor = (TileEntityNuclearReactor) reactorRelated;
            int heat = reactor.heat;
            int maxHeat = reactor.maxHeat;
            int production = reactor.getOutput() * 5;
            infoHolder.add(TextColor.WHITE.format(I18n.format("info.generator.output", production)));
            infoHolder.add(TextColor.DARK_GRAY.format(I18n.format("info.heat") + " " + BarElement.bar(heat, maxHeat, getReactorColor(heat, maxHeat), String.format("%s/%s", heat, maxHeat))));
        }
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static TextColor getReactorColor(int current, int max) {
        float progress = (float) current / max;
        if ((double) progress < 0.25) {
            return TextColor.GREEN;
        } else if ((double) progress < 0.5) {
            return TextColor.YELLOW;
        } else {
            return (double) progress < 0.75 ? TextColor.GOLD : TextColor.RED;
        }
    }
}
