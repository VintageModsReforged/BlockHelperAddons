package reforged.mods.blockhelper.addons.integrations.ic2;

import de.thexxturboxx.blockhelper.api.InfoHolder;
import ic2.core.block.machine.tileentity.TileEntityTeleporter;
import mods.vintage.core.platform.lang.FormattedTranslator;
import mods.vintage.core.platform.lang.Translator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.InfoProvider;

public class TeleporterInfoProvider extends InfoProvider {

    @Override
    public void addInfo(InfoHolder helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TileEntityTeleporter) {
            TileEntityTeleporter tp = (TileEntityTeleporter) blockEntity;
            boolean hasTarget = tp.targetSet;
            helper.add(FormattedTranslator.GOLD.format("info.teleporter.target.set", Translator.formattedBoolean(hasTarget)));
            helper.add(translate("info.teleporter.target", tp.targetX, tp.targetY, tp.targetZ));
        }
    }
}
