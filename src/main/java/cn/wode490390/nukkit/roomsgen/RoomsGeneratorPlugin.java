package cn.wode490390.nukkit.roomsgen;

import cn.nukkit.block.Block;
import cn.nukkit.level.GlobalBlockPalette;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.wode490390.nukkit.roomsgen.util.MetricsLite;

import java.util.NoSuchElementException;

public class RoomsGeneratorPlugin extends PluginBase {

    private static RoomsGeneratorPlugin INSTANCE;

    private RoomsGeneratorSettings settings;

    @Override
    public void onLoad() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        try {
            new MetricsLite(this, 7106);
        } catch (Throwable ignore) {

        }

        this.saveDefaultConfig();
        Config config = this.getConfig();

        String node = "material";
        int material = Block.BRICKS;
        try {
            material = config.getInt(node, material);
        } catch (Exception e) {
            this.logConfigException(node, e);
        }

        node = "meta";
        int meta = 0;
        try {
            meta = config.getInt(node, meta);
        } catch (Exception e) {
            this.logConfigException(node, e);
        }

        try {
            GlobalBlockPalette.getOrCreateRuntimeId(material, 0);
            try {
                GlobalBlockPalette.getOrCreateRuntimeId(material, meta);
            } catch (NoSuchElementException e) {
                meta = 0;
                this.getLogger().warning("Invalid block meta. Use the default value.");
            }
        } catch (NoSuchElementException e) {
            material = Block.BRICKS;
            meta = 0;
            this.getLogger().warning("Invalid block ID. Use the default value.");
        }

        this.settings = new RoomsGeneratorSettings(material, meta);

        Generator.addGenerator(RoomsGenerator.class, "default", Generator.TYPE_INFINITE);
        Generator.addGenerator(RoomsGenerator.class, "normal", Generator.TYPE_INFINITE);
    }

    public RoomsGeneratorSettings getSettings() {
        return this.settings;
    }

    private void logConfigException(String node, Throwable t) {
        this.getLogger().alert("An error occurred while reading the configuration '" + node + "'. Use the default value.", t);
    }

    public static RoomsGeneratorPlugin getInstance() {
        return INSTANCE;
    }
}
