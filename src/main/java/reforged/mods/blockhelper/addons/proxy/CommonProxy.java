package reforged.mods.blockhelper.addons.proxy;

import reforged.mods.blockhelper.addons.ModConfig;
import reforged.mods.blockhelper.addons.ModPlugins;
import reforged.mods.blockhelper.addons.i18n.I18n;

public class CommonProxy {

    public void loadPre() {
        ModConfig.initConfig();
        I18n.init();
    }

    public void loadPost() {
        ModPlugins.init();
    }
}
