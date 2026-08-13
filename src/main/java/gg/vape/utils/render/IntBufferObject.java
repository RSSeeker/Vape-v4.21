package gg.vape.utils.render;

import java.nio.IntBuffer;
import org.lwjgl.opengl.GL15;

public class IntBufferObject {
    private int bufferId = GL15.glGenBuffers();

    public void upload(IntBuffer intBuffer) {
        GL15.glBufferSubData((int)34963, (long)0L, (IntBuffer)intBuffer);
    }


    public void bind() {
        GL15.glBindBuffer((int)34963, (int)this.bufferId);
    }

    public void delete() {
        GL15.glDeleteBuffers((int)this.bufferId);
    }

    public void unbind() {
        GL15.glBindBuffer((int)34963, (int)0);
    }
}
