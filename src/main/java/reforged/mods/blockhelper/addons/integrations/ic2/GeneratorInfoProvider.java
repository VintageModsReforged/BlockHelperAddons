package reforged.mods.blockhelper.addons.integrations.ic2;

import de.thexxturboxx.blockhelper.api.InfoHolder;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorChamber;
import ic2.core.block.generator.tileentity.*;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.BarElement;
import reforged.mods.blockhelper.addons.utils.InfoProvider;

public class GeneratorInfoProvider extends InfoProvider {

    public static void addReactorInfo(InfoHolder infoHolder, TileEntity reactorRelated) {
        if (reactorRelated instanceof TileEntityNuclearReactor) {
            TileEntityNuclearReactor reactor = (TileEntityNuclearReactor) reactorRelated;
            int heat = reactor.heat;
            int maxHeat = reactor.maxHeat;
            int production = reactor.getOutput() * 5;
            infoHolder.add(FormattedTranslator.WHITE.format("info.generator.output", production));
            infoHolder.add(FormattedTranslator.DARK_GRAY.format("probe.reactor.heat.short", BarElement.bar(heat, maxHeat, getReactorColor(heat, maxHeat), String.format("%s/%s", heat, maxHeat))));
        }
    }

    @Override
    public void addInfo(InfoHolder helper, TileEntity blockEntity) {
        if (blockEntity instanceof TileEntityBaseGenerator) {
            TileEntityBaseGenerator gen = (TileEntityBaseGenerator) blockEntity;
            helper.add(tier(1));
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
                helper.add(translate("info.generator.output", production));
            }
            helper.add(translate("info.generator.max_output", gen.getMaxEnergyOutput()));
        }
        if (blockEntity instanceof IReactor) {
            addReactorInfo(helper, blockEntity);
        } else if (blockEntity instanceof IReactorChamber) {
            IReactor reactor = ((IReactorChamber) blockEntity).getReactor();
            addReactorInfo(helper, (TileEntity) reactor);
        }
    }

    public static FormattedTranslator getReactorColor(int current, int max) {
        float progress = (float) current / max;
        if ((double) progress < 0.25) {
            return FormattedTranslator.GREEN;
        } else if ((double) progress < 0.5) {
            return FormattedTranslator.YELLOW;
        } else {
            return (double) progress < 0.75 ? FormattedTranslator.GOLD : FormattedTranslator.RED;
        }
    }
}
