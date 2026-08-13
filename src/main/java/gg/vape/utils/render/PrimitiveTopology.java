package gg.vape.utils.render;


public enum PrimitiveTopology {
    LINES(2, 2, "line"),
    LINES_LOOP(2, 2, "line"),
    QUADS(6, 4, "quad"),
    TRIANGLES(3, 3, "triangle");

    public final String name;
    public final int verticesCount;
    private static final PrimitiveTopology[] DECLARED_VALUES;
    public final int indicesCount;

    private PrimitiveTopology(int indicesCount, int verticesCount, String name) {
        this.indicesCount = indicesCount;
        this.verticesCount = verticesCount;
        this.name = name;
    }

    static {
        String[] legacyNames = new String[]{"quad", "line", "line", "QUADS", "TRIANGLES", "LINES", "triangle", "LINES_LOOP"};




        DECLARED_VALUES = new PrimitiveTopology[]{LINES, LINES_LOOP, QUADS, TRIANGLES};
    }


    public int getOpenGlMode() {
        switch (this) {
            case LINES: {
                return 1;
            }
            case QUADS: 
            case TRIANGLES: {
                return 4;
            }
            case LINES_LOOP: {
                return 2;
            }
        }
        return -1;
    }
}

