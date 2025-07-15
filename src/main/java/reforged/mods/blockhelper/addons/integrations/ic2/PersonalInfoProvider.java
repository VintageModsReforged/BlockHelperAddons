package reforged.mods.blockhelper.addons.integrations.ic2;

import ic2.core.block.personal.IPersonalBlock;
import mods.vintage.core.platform.lang.FormattedTranslator;
import mods.vintage.core.platform.lang.Translator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import reforged.mods.blockhelper.addons.utils.InfoProvider;
import reforged.mods.blockhelper.addons.utils.interfaces.IWailaHelper;

public class PersonalInfoProvider extends InfoProvider {

    public static final PersonalInfoProvider THIS = new PersonalInfoProvider();

    @Override
    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof IPersonalBlock) {
            IPersonalBlock personalBlock = (IPersonalBlock) blockEntity;
            boolean canAccess = personalBlock.permitsAccess(player) || personalBlock.permitsAccess(player.username);
            text(helper, translate(FormattedTranslator.GOLD, "info.personal.access", Translator.formattedBoolean(canAccess)));

            if (!"null".equals(personalBlock.getUsername())) {
                text(helper, translate(FormattedTranslator.LIGHT_PURPLE, "info.personal.owner", FormattedTranslator.AQUA.literal(personalBlock.getUsername())));
            }
        }
    }
}
