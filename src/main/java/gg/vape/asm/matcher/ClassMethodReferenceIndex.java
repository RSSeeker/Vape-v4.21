package gg.vape.asm.matcher;

import gg.vape.asm.matcher.MethodInstructionIndex;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class ClassMethodReferenceIndex {
    private Map<MethodNode, MethodInstructionIndex> methodIndexes = new HashMap<MethodNode, MethodInstructionIndex>();
    private ClassNode classNode;

    public void buildIndex() {
        Iterator<MethodNode> iterator = this.classNode.methods.iterator();
        while (iterator.hasNext()) {
            MethodNode method = iterator.next();
            MethodInstructionIndex methodIndex = new MethodInstructionIndex(method);
            methodIndex.buildIndex();
            this.methodIndexes.put(method, methodIndex);
        }
    }

    public Map<MethodNode, MethodInstructionIndex> getMethodIndexes() {
        return this.methodIndexes;
    }

    public ClassMethodReferenceIndex(ClassNode classNode) {
        this.classNode = classNode;
        this.buildIndex();
    }
}
