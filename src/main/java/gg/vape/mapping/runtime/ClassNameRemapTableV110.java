package gg.vape.mapping.runtime;

import gg.vape.mapping.runtime.ClassNameRemapTableV100;

public class ClassNameRemapTableV110
extends ClassNameRemapTableV100 {
    public ClassNameRemapTableV110() {
        this.Q("net/minecraft/entity/monster/EntitySlime", "net/minecraft/world/entity/monster/cubemob/Slime");
        this.Q("com/mojang/blaze3d/vertex/VertexFormat$Mode", "com/mojang/blaze3d/PrimitiveTopology");
        this.Q("com/mojang/blaze3d/vertex/VertexFormat$IndexType", "com/mojang/blaze3d/IndexType");
        this.Q("net/minecraft/client/gui/Hud", "net/minecraft/client/gui/Hud");
        this.Q("net/minecraft/client/renderer/LevelExtractor", "net/minecraft/client/renderer/extract/LevelExtractor");
    }
}
