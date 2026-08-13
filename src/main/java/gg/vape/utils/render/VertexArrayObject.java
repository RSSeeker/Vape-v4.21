package gg.vape.utils.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class VertexArrayObject {
    private int vertexArrayId = GL30.glGenVertexArrays();
    private int previousVertexArrayId;
    private static final long VERTEX_ARRAY_BINDING_QUERY_SEED;

    public int getVertexArrayId() {
        return this.vertexArrayId;
    }

    public void delete() {
        GL30.glDeleteVertexArrays((int)this.vertexArrayId);
    }

    public void bindAndRememberPrevious() {
        this.previousVertexArrayId = GL11.glGetInteger((int)((int)VERTEX_ARRAY_BINDING_QUERY_SEED));
        GL30.glBindVertexArray((int)this.vertexArrayId);
    }

    public void restorePreviousBinding() {
        GL30.glBindVertexArray((int)this.previousVertexArrayId);
    }

    static {
        VERTEX_ARRAY_BINDING_QUERY_SEED = -614517057353906763L;
    }
}
