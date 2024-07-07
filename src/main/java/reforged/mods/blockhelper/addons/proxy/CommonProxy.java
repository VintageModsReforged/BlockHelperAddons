package reforged.mods.blockhelper.addons.proxy;

import reforged.mods.blockhelper.addons.ModConfig;
import reforged.mods.blockhelper.addons.ModPlugins;

public class CommonProxy {

    public void loadPre() {
        ModConfig.initConfig();
    }

    public void loadPost() {
        ModPlugins.init();
    }
}
