package reforged.mods.blockhelper.addons.i18n;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringTranslate;
import reforged.mods.blockhelper.addons.ModConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public final class I18n {

    private static final String PREFIX = "probe.";

    private I18n() {
        throw new UnsupportedOperationException();
    }

    public static void init() {
        if (!ModConfig.additional_languages.isEmpty()) {
            String[] LANGS = ModConfig.additional_languages.split(",");
            if (LANGS.length == 1) {
                loadLanguage(ModConfig.additional_languages);
            } else {
                for (String lang : LANGS) {
                    loadLanguage(lang);
                }
            }
        } else {
            loadLanguage(ModConfig.default_language);
        }
        LanguageRegistry.reloadLanguageTable();
    }

    public static void loadLanguage(String lang) {
        InputStream stream = null;
        InputStreamReader reader = null;
        try {
            if (ModConfig.additional_languages.isEmpty()) {
                stream = I18n.class.getResourceAsStream("/mods/blockhelper/addons/lang/" + lang + ".properties"); // use the default .lang file from modJar
            } else {
                stream = new FileInputStream(Minecraft.getMinecraftDir() + "/config/langs/blockhelperaddons/" + lang + ".properties"); // use the lang files from config/langs/blockhelperaddons folder
            }
            if (stream == null) {
                throw new IOException("Couldn't load language file.");
            }

            reader = new InputStreamReader(stream, "UTF-8");
            Properties props = new Properties();
            props.load(reader);
            for (String key : props.stringPropertyNames()) {
                LanguageRegistry.instance().addStringLocalization(key, lang, props.getProperty(key));
            }

        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (Throwable ignored) {
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (Throwable ignored) {
                }
            }
        }
    }

    public static String format(StringTranslate translator, boolean b) {
        return format(translator, b ? "true" : "false");
    }

    public static String format(String key, Object... args) {
        return format(null, key, args);
    }

    public static String format(StringTranslate translator, String key, Object... args) {
        if (translator == null) return StatCollector.translateToLocalFormatted(PREFIX + key, args);
        return translator.translateKeyFormat(PREFIX + key, args);
    }

}
