package gg.vape.utils.render.glu;

import gg.vape.utils.render.glu.GluQuadric;
import org.lwjgl.opengl.GL11;

public class GluSphere
extends GluQuadric {
    public void draw(float radius, int slices, int stacks) {
        boolean normalsEnabled = this.normalMode != NORMAL_NONE;
        if (normalsEnabled) {
            float orientationSign = this.orientation == ORIENTATION_INSIDE ? -1.0f : 1.0f;
            float stackAngleStep = (float)Math.PI / (float)stacks;
            float sliceAngleStep = (float)Math.PI * 2 / (float)slices;
            if (this.drawStyle == DRAW_FILL) {
                float textureU;
                float latitudeAngle;
                int endStack;
                int startStack;
                float zNormal;
                float yNormal;
                float xNormal;
                float longitudeAngle;
                int sliceIndex;
                if (!this.textureCoordinatesEnabled) {
                    GL11.glBegin((int)6);
                    GL11.glNormal3f((float)0.0f, (float)0.0f, (float)1.0f);
                    GL11.glVertex3f((float)0.0f, (float)0.0f, (float)(orientationSign * radius));
                    for (sliceIndex = 0; sliceIndex <= slices; ++sliceIndex) {
                        longitudeAngle = sliceIndex == slices ? 0.0f : (float)sliceIndex * sliceAngleStep;
                        xNormal = -this.sin(longitudeAngle) * this.sin(stackAngleStep);
                        yNormal = this.cos(longitudeAngle) * this.sin(stackAngleStep);
                        zNormal = orientationSign * this.cos(stackAngleStep);
                        GL11.glNormal3f((float)(xNormal * orientationSign), (float)(yNormal * orientationSign), (float)(zNormal * orientationSign));
                        GL11.glVertex3f((float)(xNormal * radius), (float)(yNormal * radius), (float)(zNormal * radius));
                    }
                    GL11.glEnd();
                }
                float textureUStep = 1.0f / (float)slices;
                float textureVStep = 1.0f / (float)stacks;
                float textureV = 1.0f;
                if (this.textureCoordinatesEnabled) {
                    startStack = 0;
                    endStack = stacks;
                } else {
                    startStack = 1;
                    endStack = stacks - 1;
                }
                for (int stackIndex = startStack; stackIndex < endStack; ++stackIndex) {
                    latitudeAngle = (float)stackIndex * stackAngleStep;
                    GL11.glBegin((int)8);
                    textureU = 0.0f;
                    for (sliceIndex = 0; sliceIndex <= slices; ++sliceIndex) {
                        longitudeAngle = sliceIndex == slices ? 0.0f : (float)sliceIndex * sliceAngleStep;
                        xNormal = -this.sin(longitudeAngle) * this.sin(latitudeAngle);
                        yNormal = this.cos(longitudeAngle) * this.sin(latitudeAngle);
                        zNormal = orientationSign * this.cos(latitudeAngle);
                        GL11.glNormal3f((float)(xNormal * orientationSign), (float)(yNormal * orientationSign), (float)(zNormal * orientationSign));
                        this.emitTextureCoordinate(textureU, textureV);
                        GL11.glVertex3f((float)(xNormal * radius), (float)(yNormal * radius), (float)(zNormal * radius));
                        xNormal = -this.sin(longitudeAngle) * this.sin(latitudeAngle + stackAngleStep);
                        yNormal = this.cos(longitudeAngle) * this.sin(latitudeAngle + stackAngleStep);
                        zNormal = orientationSign * this.cos(latitudeAngle + stackAngleStep);
                        GL11.glNormal3f((float)(xNormal * orientationSign), (float)(yNormal * orientationSign), (float)(zNormal * orientationSign));
                        this.emitTextureCoordinate(textureU, textureV - textureVStep);
                        textureU += textureUStep;
                        GL11.glVertex3f((float)(xNormal * radius), (float)(yNormal * radius), (float)(zNormal * radius));
                    }
                    GL11.glEnd();
                    textureV -= textureVStep;
                }
                if (!this.textureCoordinatesEnabled) {
                    GL11.glBegin((int)6);
                    GL11.glNormal3f((float)0.0f, (float)0.0f, (float)-1.0f);
                    GL11.glVertex3f((float)0.0f, (float)0.0f, (float)(-radius * orientationSign));
                    latitudeAngle = (float)Math.PI - stackAngleStep;
                    textureU = 1.0f;
                    for (sliceIndex = slices; sliceIndex >= 0; --sliceIndex) {
                        longitudeAngle = sliceIndex == slices ? 0.0f : (float)sliceIndex * sliceAngleStep;
                        xNormal = -this.sin(longitudeAngle) * this.sin(latitudeAngle);
                        yNormal = this.cos(longitudeAngle) * this.sin(latitudeAngle);
                        zNormal = orientationSign * this.cos(latitudeAngle);
                        GL11.glNormal3f((float)(xNormal * orientationSign), (float)(yNormal * orientationSign), (float)(zNormal * orientationSign));
                        textureU -= textureUStep;
                        GL11.glVertex3f((float)(xNormal * radius), (float)(yNormal * radius), (float)(zNormal * radius));
                    }
                    GL11.glEnd();
                }
            } else if (this.drawStyle != DRAW_LINE && this.drawStyle != DRAW_SILHOUETTE) {
                if (this.drawStyle == DRAW_POINT) {
                    GL11.glBegin((int)0);
                    GL11.glNormal3f((float)0.0f, (float)0.0f, (float)orientationSign);
                    GL11.glVertex3f((float)0.0f, (float)0.0f, (float)radius);
                    GL11.glNormal3f((float)0.0f, (float)0.0f, (float)(-orientationSign));
                    GL11.glVertex3f((float)0.0f, (float)0.0f, (float)(-radius));
                    for (int stackIndex = 1; stackIndex < stacks - 1; ++stackIndex) {
                        float latitudeAngle = (float)stackIndex * stackAngleStep;
                        for (int sliceIndex = 0; sliceIndex < slices; ++sliceIndex) {
                            float longitudeAngle = (float)sliceIndex * sliceAngleStep;
                            float xNormal = this.cos(longitudeAngle) * this.sin(latitudeAngle);
                            float yNormal = this.sin(longitudeAngle) * this.sin(latitudeAngle);
                            float zNormal = this.cos(latitudeAngle);
                            GL11.glNormal3f((float)(xNormal * orientationSign), (float)(yNormal * orientationSign), (float)(zNormal * orientationSign));
                            GL11.glVertex3f((float)(xNormal * radius), (float)(yNormal * radius), (float)(zNormal * radius));
                        }
                    }
                    GL11.glEnd();
                }
            } else {
                float zNormal;
                float yNormal;
                float xNormal;
                float longitudeAngle;
                int sliceIndex;
                float latitudeAngle;
                int stackIndex;
                for (stackIndex = 1; stackIndex < stacks; ++stackIndex) {
                    latitudeAngle = (float)stackIndex * stackAngleStep;
                    GL11.glBegin((int)2);
                    for (sliceIndex = 0; sliceIndex < slices; ++sliceIndex) {
                        longitudeAngle = (float)sliceIndex * sliceAngleStep;
                        xNormal = this.cos(longitudeAngle) * this.sin(latitudeAngle);
                        yNormal = this.sin(longitudeAngle) * this.sin(latitudeAngle);
                        zNormal = this.cos(latitudeAngle);
                        GL11.glNormal3f((float)(xNormal * orientationSign), (float)(yNormal * orientationSign), (float)(zNormal * orientationSign));
                        GL11.glVertex3f((float)(xNormal * radius), (float)(yNormal * radius), (float)(zNormal * radius));
                    }
                    GL11.glEnd();
                }
                for (sliceIndex = 0; sliceIndex < slices; ++sliceIndex) {
                    longitudeAngle = (float)sliceIndex * sliceAngleStep;
                    GL11.glBegin((int)3);
                    for (stackIndex = 0; stackIndex <= stacks; ++stackIndex) {
                        latitudeAngle = (float)stackIndex * stackAngleStep;
                        xNormal = this.cos(longitudeAngle) * this.sin(latitudeAngle);
                        yNormal = this.sin(longitudeAngle) * this.sin(latitudeAngle);
                        zNormal = this.cos(latitudeAngle);
                        GL11.glNormal3f((float)(xNormal * orientationSign), (float)(yNormal * orientationSign), (float)(zNormal * orientationSign));
                        GL11.glVertex3f((float)(xNormal * radius), (float)(yNormal * radius), (float)(zNormal * radius));
                    }
                    GL11.glEnd();
                }
            }
            return;
        }
        float orientationSign = this.orientation == ORIENTATION_INSIDE ? -1.0f : 1.0f;
        float stackAngleStep = (float)Math.PI / (float)stacks;
        float sliceAngleStep = (float)Math.PI * 2 / (float)slices;
        if (this.drawStyle == DRAW_FILL) {
            float textureU;
            float latitudeAngle;
            int endStack;
            int startStack;
            float zNormal;
            float yNormal;
            float xNormal;
            float longitudeAngle;
            int sliceIndex;
            if (!this.textureCoordinatesEnabled) {
                GL11.glBegin((int)6);
                GL11.glNormal3f((float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glVertex3f((float)0.0f, (float)0.0f, (float)(orientationSign * radius));
                for (sliceIndex = 0; sliceIndex <= slices; ++sliceIndex) {
                    longitudeAngle = sliceIndex == slices ? 0.0f : (float)sliceIndex * sliceAngleStep;
                    xNormal = -this.sin(longitudeAngle) * this.sin(stackAngleStep);
                    yNormal = this.cos(longitudeAngle) * this.sin(stackAngleStep);
                    zNormal = orientationSign * this.cos(stackAngleStep);
                    GL11.glVertex3f((float)(xNormal * radius), (float)(yNormal * radius), (float)(zNormal * radius));
                }
                GL11.glEnd();
            }
            float textureUStep = 1.0f / (float)slices;
            float textureVStep = 1.0f / (float)stacks;
            float textureV = 1.0f;
            if (this.textureCoordinatesEnabled) {
                startStack = 0;
                endStack = stacks;
            } else {
                startStack = 1;
                endStack = stacks - 1;
            }
            for (int stackIndex = startStack; stackIndex < endStack; ++stackIndex) {
                latitudeAngle = (float)stackIndex * stackAngleStep;
                GL11.glBegin((int)8);
                textureU = 0.0f;
                for (sliceIndex = 0; sliceIndex <= slices; ++sliceIndex) {
                    longitudeAngle = sliceIndex == slices ? 0.0f : (float)sliceIndex * sliceAngleStep;
                    xNormal = -this.sin(longitudeAngle) * this.sin(latitudeAngle);
                    yNormal = this.cos(longitudeAngle) * this.sin(latitudeAngle);
                    zNormal = orientationSign * this.cos(latitudeAngle);
                    this.emitTextureCoordinate(textureU, textureV);
                    GL11.glVertex3f((float)(xNormal * radius), (float)(yNormal * radius), (float)(zNormal * radius));
                    xNormal = -this.sin(longitudeAngle) * this.sin(latitudeAngle + stackAngleStep);
                    yNormal = this.cos(longitudeAngle) * this.sin(latitudeAngle + stackAngleStep);
                    zNormal = orientationSign * this.cos(latitudeAngle + stackAngleStep);
                    this.emitTextureCoordinate(textureU, textureV - textureVStep);
                    textureU += textureUStep;
                    GL11.glVertex3f((float)(xNormal * radius), (float)(yNormal * radius), (float)(zNormal * radius));
                }
                GL11.glEnd();
                textureV -= textureVStep;
            }
            if (!this.textureCoordinatesEnabled) {
                GL11.glBegin((int)6);
                GL11.glNormal3f((float)0.0f, (float)0.0f, (float)-1.0f);
                GL11.glVertex3f((float)0.0f, (float)0.0f, (float)(-radius * orientationSign));
                latitudeAngle = (float)Math.PI - stackAngleStep;
                textureU = 1.0f;
                for (sliceIndex = slices; sliceIndex >= 0; --sliceIndex) {
                    longitudeAngle = sliceIndex == slices ? 0.0f : (float)sliceIndex * sliceAngleStep;
                    xNormal = -this.sin(longitudeAngle) * this.sin(latitudeAngle);
                    yNormal = this.cos(longitudeAngle) * this.sin(latitudeAngle);
                    zNormal = orientationSign * this.cos(latitudeAngle);
                    textureU -= textureUStep;
                    GL11.glVertex3f((float)(xNormal * radius), (float)(yNormal * radius), (float)(zNormal * radius));
                }
                GL11.glEnd();
            }
        } else if (this.drawStyle != DRAW_LINE && this.drawStyle != DRAW_SILHOUETTE) {
            if (this.drawStyle == DRAW_POINT) {
                GL11.glBegin((int)0);
                GL11.glVertex3f((float)0.0f, (float)0.0f, (float)radius);
                GL11.glVertex3f((float)0.0f, (float)0.0f, (float)(-radius));
                for (int stackIndex = 1; stackIndex < stacks - 1; ++stackIndex) {
                    float latitudeAngle = (float)stackIndex * stackAngleStep;
                    for (int sliceIndex = 0; sliceIndex < slices; ++sliceIndex) {
                        float longitudeAngle = (float)sliceIndex * sliceAngleStep;
                        float xNormal = this.cos(longitudeAngle) * this.sin(latitudeAngle);
                        float yNormal = this.sin(longitudeAngle) * this.sin(latitudeAngle);
                        float zNormal = this.cos(latitudeAngle);
                        GL11.glVertex3f((float)(xNormal * radius), (float)(yNormal * radius), (float)(zNormal * radius));
                    }
                }
                GL11.glEnd();
            }
        } else {
            float zNormal;
            float yNormal;
            float xNormal;
            float longitudeAngle;
            int sliceIndex;
            float latitudeAngle;
            int stackIndex;
            for (stackIndex = 1; stackIndex < stacks; ++stackIndex) {
                latitudeAngle = (float)stackIndex * stackAngleStep;
                GL11.glBegin((int)2);
                for (sliceIndex = 0; sliceIndex < slices; ++sliceIndex) {
                    longitudeAngle = (float)sliceIndex * sliceAngleStep;
                    xNormal = this.cos(longitudeAngle) * this.sin(latitudeAngle);
                    yNormal = this.sin(longitudeAngle) * this.sin(latitudeAngle);
                    zNormal = this.cos(latitudeAngle);
                    GL11.glVertex3f((float)(xNormal * radius), (float)(yNormal * radius), (float)(zNormal * radius));
                }
                GL11.glEnd();
            }
            for (sliceIndex = 0; sliceIndex < slices; ++sliceIndex) {
                longitudeAngle = (float)sliceIndex * sliceAngleStep;
                GL11.glBegin((int)3);
                for (stackIndex = 0; stackIndex <= stacks; ++stackIndex) {
                    latitudeAngle = (float)stackIndex * stackAngleStep;
                    xNormal = this.cos(longitudeAngle) * this.sin(latitudeAngle);
                    yNormal = this.sin(longitudeAngle) * this.sin(latitudeAngle);
                    zNormal = this.cos(latitudeAngle);
                    GL11.glVertex3f((float)(xNormal * radius), (float)(yNormal * radius), (float)(zNormal * radius));
                }
                GL11.glEnd();
            }
        }
    }

}

