package reforged.mods.blockhelper.addons.integrations.ic2;

import de.thexxturboxx.blockhelper.api.InfoHolder;
import ic2.core.block.wiring.TileEntityTransformer;
import mods.vintage.core.platform.lang.FormattedTranslator;
import mods.vintage.core.platform.lang.Translator;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.InfoProvider;

public class TransformerInfoProvider extends InfoProvider {

    @Override
    public void addInfo(InfoHolder helper, TileEntity blockEntity) {
        if (blockEntity instanceof TileEntityTransformer) {
            TileEntityTransformer transformer = (TileEntityTransformer) blockEntity;
            boolean inverted = transformer.redstone;

            helper.add(FormattedTranslator.GOLD.format("info.transformer.inverted", Translator.formattedBoolean(inverted)));
            helper.add(maxIn(inverted ? transformer.lowOutput : transformer.highOutput));
            helper.add(translate("info.storage.output", inverted ? transformer.highOutput : transformer.lowOutput));
        }
    }
}
