package gg.vape.asm.matcher;

import gg.vape.asm.matcher.ClassMethodReferenceIndex;
import gg.vape.asm.matcher.ClassNodeCache;
import gg.vape.asm.matcher.MethodInstructionIndex;
import java.util.Iterator;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public abstract class MethodNodeMatcher {
    private static String opaqueMarker;
    private final Class targetClass;
    private final ClassMethodReferenceIndex referenceIndex;
    private final ClassNode classNode;

    public String findMethodName(String descriptor) {
        Iterator<MethodNode> iterator = this.classNode.methods.iterator();
        while (iterator.hasNext()) {
            MethodNode method = iterator.next();
            if (!descriptor.equals(method.desc) || !this.matchesMethod(method)) continue;
            return method.name;
        }
        return null;
    }

    public abstract boolean matchesMethod(MethodNode methodNode);

    public MethodNodeMatcher(Class targetClass) {
        this.targetClass = targetClass;
        this.classNode = ClassNodeCache.getClassNode(targetClass);
        this.referenceIndex = ClassNodeCache.getClassReferences(targetClass);
    }

    public MethodInstructionIndex getMethodInstructionIndex(MethodNode methodNode) {
        return this.referenceIndex.getMethodIndexes().get(methodNode);
    }


    public static String getOpaqueMarker() {
        return opaqueMarker;
    }

    public static void setOpaqueMarker(String marker) {
        opaqueMarker = marker;
    }

    static {
        if (MethodNodeMatcher.getOpaqueMarker() == null) {
            MethodNodeMatcher.setOpaqueMarker("h95Sqb");
        }
    }
}
