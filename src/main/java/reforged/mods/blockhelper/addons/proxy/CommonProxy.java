package reforged.mods.blockhelper.addons.proxy;

import reforged.mods.blockhelper.addons.ModPlugins;

public class CommonProxy {

    public void loadPre() {}

    public void loadPost() {
        ModPlugins.init();
    }
}
