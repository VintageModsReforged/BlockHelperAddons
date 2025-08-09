package reforged.mods.blockhelper.addons.integrations.ic2;

import ic2.core.block.machine.tileentity.TileEntityTeleporter;
import mods.vintage.core.platform.lang.FormattedTranslator;
import mods.vintage.core.platform.lang.Translator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.InfoProvider;
import reforged.mods.blockhelper.addons.utils.interfaces.IWailaHelper;

public class TeleporterInfoProvider extends InfoProvider {

    public static final TeleporterInfoProvider THIS = new TeleporterInfoProvider();

    @Override
    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TileEntityTeleporter) {
            TileEntityTeleporter tp = (TileEntityTeleporter) blockEntity;
            boolean hasTarget = tp.targetSet;
            text(helper, FormattedTranslator.GOLD.format("info.teleporter.target.set", Translator.formattedBoolean(hasTarget)));
            text(helper, translate("info.teleporter.target", tp.targetX, tp.targetY, tp.targetZ));
        }
    }
}
