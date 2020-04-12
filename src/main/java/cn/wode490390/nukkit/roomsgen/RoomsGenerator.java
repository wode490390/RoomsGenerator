package cn.wode490390.nukkit.roomsgen;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockDoor;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.generic.BaseFullChunk;
import cn.nukkit.level.generator.Normal;

import java.util.Collections;
import java.util.Map;

public class RoomsGenerator extends Normal {

    protected RoomsGeneratorSettings settings;

    public RoomsGenerator() {
        this(Collections.emptyMap());
    }

    public RoomsGenerator(Map<String, Object> options) {
        super(options);
        this.settings = RoomsGeneratorPlugin.getInstance().getSettings();
    }

    @Override
    public void populateChunk(int chunkX, int chunkZ) {
        super.populateChunk(chunkX, chunkZ);
        ChunkManager level = this.getChunkManager();
        BaseFullChunk chunk = level.getChunk(chunkX, chunkZ);

        int baseX = chunkX << 4;
        int baseZ = chunkZ << 4;
        int height = chunk.getHighestBlockAt(0, 0) + 1;
        boolean zCenter = Math.floorMod(chunkX, 4) == 2;
        boolean xCenter = Math.floorMod(chunkZ, 4) == 2;

        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                if (x != 0 || z != 0 || !zCenter || !xCenter) {
                    chunk.setBlock(x, 255, z, this.settings.getWallId(), this.settings.getWallMeta());
                }
            }
        }

        if (chunkX % 4 == 0) {
            for (int z = 0; z < 16; ++z) {
                for (int y = 0; y < 256; ++y) {
                    chunk.setBlock(0, y, z, this.settings.getWallId(), this.settings.getWallMeta());
                }
            }

            if (xCenter) {
                int y = height;
                if (y < 255) {
                    chunk.setBlock(0, y, 0, Block.WOODEN_DOOR_BLOCK, 0);
                    chunk.setBlock(0, ++y, 0, Block.WOODEN_DOOR_BLOCK, BlockDoor.DOOR_TOP_BIT);
                    if (y < 255) {
                        chunk.setBlock(1, ++y, 0, Block.TORCH, 1);
                        level.setBlockAt(baseX - 1, y, baseZ, Block.TORCH, 2);
                    }
                }
            }
        }

        if (chunkZ % 4 == 0) {
            for (int x = 0; x < 16; ++x) {
                for (int y = 0; y < 256; ++y) {
                    chunk.setBlock(x, y, 0, this.settings.getWallId(), this.settings.getWallMeta());
                }
            }

            if (zCenter) {
                int y = height;
                if (y < 255) {
                    chunk.setBlock(0, y, 0, Block.WOODEN_DOOR_BLOCK, 1);
                    chunk.setBlock(0, ++y, 0, Block.WOODEN_DOOR_BLOCK, BlockDoor.DOOR_TOP_BIT);
                    if (y < 255) {
                        chunk.setBlock(0, ++y, 1, Block.TORCH, 3);
                        level.setBlockAt(baseX, y, baseZ - 1, Block.TORCH, 4);
                    }
                }
            }
        }
    }
}
