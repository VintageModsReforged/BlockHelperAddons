//package reforged.mods.wailaaddonsic2.info.addons;
//
//import advsolar.TileEntitySolarPanel;
//import cpw.mods.fml.common.Loader;
//import de.thexxturboxx.blockhelper.api.BlockHelperBlockProvider;
//import de.thexxturboxx.blockhelper.api.BlockHelperBlockState;
//import de.thexxturboxx.blockhelper.api.InfoHolder;
//import net.minecraft.tileentity.TileEntity;
//import reforged.mods.wailaaddonsic2.Helper;
//import reforged.mods.wailaaddonsic2.TextColor;
//import reforged.mods.wailaaddonsic2.i18n.I18n;
//
//public class AdvancedSolarPanelInfoProvider implements BlockHelperBlockProvider {
//
//    @Override
//    public void addInformation(BlockHelperBlockState blockHelperBlockState, InfoHolder infoHolder) {
//        TileEntity tile = blockHelperBlockState.te;
//        if (tile instanceof TileEntitySolarPanel) {
//            TileEntitySolarPanel panel = (TileEntitySolarPanel) tile;
//            infoHolder.add(TextColor.AQUA.format(I18n.format("info.energy", panel.storage, panel.maxStorage)));
//            infoHolder.add(TextColor.WHITE.format(I18n.format("info.eu_reader.tier", Helper.getTierForDisplay(Helper.getTierFromMaxInput(panel.getMaxEnergyOutput())))));
//            infoHolder.add(TextColor.WHITE.format(I18n.format("info.generator.output", panel.gainFuel())));
//            infoHolder.add(TextColor.WHITE.format(I18n.format("info.generator.max_output", panel.getMaxEnergyOutput())));
//        }
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return Loader.isModLoaded("AdvancedSolarPanel");
//    }
//}
