package reforged.mods.blockhelper.addons.proxy;

import reforged.mods.blockhelper.addons.ModConfig;
import reforged.mods.blockhelper.addons.i18n.I18n;

public class ClientProxy extends CommonProxy {

    @Override
    public void loadPre() {
        super.loadPre();
        ModConfig.initConfig();
        I18n.init();
    }

    @Override
    public void loadPost() {
        super.loadPost();
    }
}
