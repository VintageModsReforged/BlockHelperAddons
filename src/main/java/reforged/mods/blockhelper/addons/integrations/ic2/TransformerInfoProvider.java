package reforged.mods.blockhelper.addons.integrations.ic2;

import ic2.core.block.wiring.TileEntityTransformer;
import mods.vintage.core.platform.lang.FormattedTranslator;
import mods.vintage.core.platform.lang.Translator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.IWailaHelper;
import reforged.mods.blockhelper.addons.utils.InfoProvider;

public class TransformerInfoProvider extends InfoProvider {

    @Override
    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TileEntityTransformer) {
            TileEntityTransformer transformer = (TileEntityTransformer) blockEntity;
            boolean inverted = transformer.redstone;

            text(helper, FormattedTranslator.GOLD.format("info.transformer.inverted", Translator.formattedBoolean(inverted)));
            text(helper, maxIn(inverted ? transformer.lowOutput : transformer.highOutput));
            text(helper, translate("info.storage.output", inverted ? transformer.highOutput : transformer.lowOutput));
        }
    }
}
