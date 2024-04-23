package reforged.mods.wailaaddonsic2.info;

import de.thexxturboxx.blockhelper.api.BlockHelperBlockProvider;
import de.thexxturboxx.blockhelper.api.BlockHelperBlockState;
import de.thexxturboxx.blockhelper.api.InfoHolder;
import ic2.core.block.wiring.TileEntityTransformer;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.wailaaddonsic2.TextColor;
import reforged.mods.wailaaddonsic2.i18n.I18n;

public class TransformerInfoProvider implements BlockHelperBlockProvider {

    @Override
    public void addInformation(BlockHelperBlockState blockHelperBlockState, InfoHolder infoHolder) {
        TileEntity tile = blockHelperBlockState.te;
        if (tile instanceof TileEntityTransformer) {
            TileEntityTransformer transformer = (TileEntityTransformer) tile;
            boolean inverted = transformer.redstone;

            infoHolder.add(TextColor.GOLD.format(I18n.format("info.transformer.inverted", inverted ? TextColor.GREEN.format(String.valueOf(true)) : TextColor.RED.format(String.valueOf(false)))));
            infoHolder.add(TextColor.WHITE.format(I18n.format("info.eu_reader.max_in", inverted ? transformer.lowOutput : transformer.highOutput)));
            infoHolder.add(TextColor.WHITE.format(I18n.format("info.storage.output", inverted ? transformer.highOutput : transformer.lowOutput)));
        }
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
