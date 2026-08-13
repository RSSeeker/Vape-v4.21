package gg.vape.utils.render;

import java.nio.FloatBuffer;
import org.lwjgl.opengl.GL15;

public class FloatBufferObject {
    private int bufferId = GL15.glGenBuffers();

    public void delete() {
        GL15.glDeleteBuffers((int)this.bufferId);
    }

    public void allocate(long sizeBytes) {
        GL15.glBufferData((int)34962, (long)sizeBytes, (int)35048);
    }


    public int getBufferId() {
        return this.bufferId;
    }

    public void bind() {
        GL15.glBindBuffer((int)34962, (int)this.bufferId);
    }

    public void upload(FloatBuffer floatBuffer) {
        GL15.glBufferSubData((int)34962, (long)0L, (FloatBuffer)floatBuffer);
    }

    public void unbind() {
        GL15.glBindBuffer((int)34962, (int)0);
    }
}
