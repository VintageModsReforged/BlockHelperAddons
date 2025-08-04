package reforged.mods.blockhelper.addons.integrations.ic2;

import ic2.core.block.TileEntityBarrel;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.ColorUtils;
import reforged.mods.blockhelper.addons.utils.InfoProvider;
import reforged.mods.blockhelper.addons.utils.interfaces.IWailaHelper;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class BarrelInfoProvider extends InfoProvider {

    public static BarrelInfoProvider THIS = new BarrelInfoProvider();

    @Override
    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TileEntityBarrel) {
            TileEntityBarrel barrel = (TileEntityBarrel) blockEntity;

            int brewQuality = barrel.timeRatio;
            int alcoholLevel = barrel.hopsRatio;
            int solidRatio = barrel.solidRatio;

            // beer
            int wheatAmount = barrel.wheatCount;
            int hopsAmount = barrel.hopsCount;
            int fluidAmount = barrel.boozeAmount;

            // rum
            int age;

            double maxValue;
            double current;
            int brewType = barrel.type;
            DecimalFormat format = new DecimalFormat("#0.00", new DecimalFormatSymbols(Locale.ROOT));

            switch (brewType) {
                case 0:
                    text(helper, translate(getBrewType(brewType)));
                    break;
                case 1:
                    age = barrel.age;
                    maxValue = 24000.0 * Math.pow(3.0, brewQuality == 4 ? 6.0 : (double) brewQuality);
                    current = age / maxValue * 100.0;

                    text(helper, translate(getBrewType(brewType)));
                    textCentered(helper, FormattedTranslator.YELLOW.format("info.barrel.storage"));
                    bar(helper, wheatAmount, 64, translate("info.barrel.storage.wheat", wheatAmount), ColorUtils.YELLOW);
                    bar(helper, hopsAmount, 64, translate("info.barrel.storage.hops", hopsAmount), ColorUtils.GREEN);
                    bar(helper, fluidAmount, 32, translate("info.crop.info.water", fluidAmount, 32), ColorUtils.rgb(93, 105, 255));

                    textCentered(helper, FormattedTranslator.YELLOW.format("info.barrel.status.brew"));
                    text(helper, translate("info.barrel.beer.quality." + brewQuality));
                    text(helper, translate("info.barrel.beer.alc." + alcoholLevel));
                    text(helper, translate("info.barrel.beer.solid." + solidRatio));
                    bar(helper, age, (int) maxValue, literal(format.format(current) + "%"), ColorUtils.PROGRESS);
                    break;
                case 2:
                    maxValue = barrel.timeNedForRum(fluidAmount);
                    age = (int) Math.min(barrel.age, maxValue);
                    text(helper, translate(getBrewType(brewType)));
                    textCentered(helper, FormattedTranslator.YELLOW.format("info.barrel.status.brew"));
                    bar(helper, fluidAmount, 32, translate("info.barrel.beer.sugar_cane", fluidAmount), ColorUtils.GREEN);
                    bar(helper, age, (int) maxValue, literal(format.format(Math.min(age, maxValue) * 100.0 / maxValue) + "%"), ColorUtils.PROGRESS);
                    break;
            }
        }
    }

    @Override
    public IFilter getFilter() {
        return TREETAP;
    }

    public String getBrewType(int type) {
        switch (type) {
            case 0: return "info.barrel.empty";
            case 1: return "info.barrel.beer";
            case 2: return "info.barrel.rum";
            default: return "ERROR, PLEASE REPORT!";
        }
    }
}
