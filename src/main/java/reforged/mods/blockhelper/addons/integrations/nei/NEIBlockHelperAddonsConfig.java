package reforged.mods.blockhelper.addons.integrations.nei;

import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.forge.GuiContainerManager;
import reforged.mods.blockhelper.addons.BlockHelperAddons;

public class NEIBlockHelperAddonsConfig implements IConfigureNEI {

    @Override
    public void loadConfig() {
        GuiContainerManager.addTooltipHandler(new NEITooltipHandler());
    }

    @Override
    public String getName() {
        return BlockHelperAddons.ID;
    }

    @Override
    public String getVersion() {
        return "1.4.7-1.0.0";
    }
}
