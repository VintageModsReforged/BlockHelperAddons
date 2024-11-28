package reforged.mods.blockhelper.addons;

import reforged.mods.blockhelper.addons.integrations.ic2.IC2Plugin;

public class ModPlugins {

    public static void init() {
        IC2Plugin.THIS.init();
    }
}
