package gg.vape.asm.helper;

import gg.vape.asm.helper.EventBuilder;
import gg.vape.asm.helper.Local;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class IndexedLocal
extends Local {
    int localOrdinal;

    public IndexedLocal(int localOrdinal) {
        super("");
        this.localOrdinal = localOrdinal;
    }

    @Override
    public void prepare(ClassNode classNode, MethodNode methodNode) {
        int ordinal = 0;
        for (LocalVariableNode candidate : methodNode.localVariables) {
            if (this.localOrdinal == ordinal) {
                this.localVariable = candidate;
                this.loadInstruction = new VarInsnNode(EventBuilder.getLoadOpcode(candidate.desc), candidate.index);
                this.loadInstructions.add(this.loadInstruction);
                this.storeInstruction = new VarInsnNode(EventBuilder.getStoreOpcode(candidate.desc), candidate.index);
                this.storeInstructions.add(this.storeInstruction);
                this.resolvedDescriptor = this.localVariable.desc;
            }
            ++ordinal;
        }
    }

}
