package reforged.mods.blockhelper.addons.integrations.ic2;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorChamber;
import ic2.core.block.generator.tileentity.*;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.ColorUtils;
import reforged.mods.blockhelper.addons.utils.interfaces.IWailaHelper;
import reforged.mods.blockhelper.addons.utils.InfoProvider;

public class GeneratorInfoProvider extends InfoProvider {

    @Override
    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TileEntityBaseGenerator) {
            TileEntityBaseGenerator gen = (TileEntityBaseGenerator) blockEntity;
            text(helper, tier(1));
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
                text(helper, translate("info.generator.output", production));
            }
            text(helper, translate("info.generator.max_output", gen.getMaxEnergyOutput()));
        }
        if (blockEntity instanceof IReactor) {
            addReactorInfo(helper, blockEntity);
        } else if (blockEntity instanceof IReactorChamber) {
            IReactor reactor = ((IReactorChamber) blockEntity).getReactor();
            addReactorInfo(helper, (TileEntity) reactor);
        }
    }

    public void addReactorInfo(IWailaHelper helper, TileEntity reactorRelated) {
        if (reactorRelated instanceof TileEntityNuclearReactor) {
            TileEntityNuclearReactor reactor = (TileEntityNuclearReactor) reactorRelated;
            int heat = reactor.heat;
            int maxHeat = reactor.maxHeat;
            int production = reactor.getOutput() * 5;
            text(helper, FormattedTranslator.WHITE.format("info.generator.output", production));
            bar(helper, heat, maxHeat, translate("info.reactor.heat.long", heat, maxHeat), getReactorColor(heat, maxHeat));

        }
    }

    public static int getReactorColor(int current, int max) {
        float progress = (float) current / max;
        if ((double) progress < 0.25) {
            return ColorUtils.GREEN;
        } else if ((double) progress < 0.5) {
            return -1189115;
        } else {
            return (double) progress < 0.75 ? -1203707 : ColorUtils.RED;
        }
    }
}
