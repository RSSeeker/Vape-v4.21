package gg.vape.asm.matcher;

import gg.vape.asm.matcher.ClassMethodReferenceIndex;
import gg.vape.runtime.ClassBytecodeCache;
import java.util.HashMap;
import java.util.Map;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

public class ClassNodeCache {
    private static Map<Class, ClassNode> classNodes = new HashMap<Class, ClassNode>();
    private static boolean opaqueState;
    private static Map<Class, ClassMethodReferenceIndex> referenceIndexes;

    private static ClassMethodReferenceIndex cacheClassReferences(Class targetClass, Class ignoredKey) {
        ClassMethodReferenceIndex referenceIndex = new ClassMethodReferenceIndex(ClassNodeCache.getClassNode(targetClass));
        return referenceIndexes.put(targetClass, referenceIndex);
    }

    public static void setOpaqueState(boolean state) {
        opaqueState = state;
    }

    private static ClassNode cacheClassNode(Class targetClass, Class ignoredKey) {
        byte[] bytecode = ClassBytecodeCache.getClassBytecode(targetClass);
        ClassReader classReader = new ClassReader(bytecode);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);
        return classNodes.put(targetClass, classNode);
    }


    public static boolean getOpaqueState() {
        return opaqueState;
    }

    public static boolean opaquePredicate() {
        boolean state = ClassNodeCache.getOpaqueState();
        return false;
    }

    static {
        referenceIndexes = new HashMap<Class, ClassMethodReferenceIndex>();
        ClassNodeCache.setOpaqueState(true);
    }

    public static ClassNode getClassNode(Class targetClass) {
        classNodes.computeIfAbsent(targetClass, ignoredKey -> ClassNodeCache.cacheClassNode(targetClass, ignoredKey));
        return classNodes.get(targetClass);
    }

    public static ClassMethodReferenceIndex getClassReferences(Class targetClass) {
        referenceIndexes.computeIfAbsent(targetClass,
                ignoredKey -> ClassNodeCache.cacheClassReferences(targetClass, ignoredKey));
        return referenceIndexes.get(targetClass);
    }
}

