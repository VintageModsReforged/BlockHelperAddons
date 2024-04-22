package reforged.mods.wailaaddonsic2.proxy;

import reforged.mods.wailaaddonsic2.i18n.I18n;

public class ClientProxy extends CommonProxy {

    @Override
    public void loadPre() {
        super.loadPre();
        I18n.init();
    }

    @Override
    public void loadPost() {
        super.loadPost();
    }
}
