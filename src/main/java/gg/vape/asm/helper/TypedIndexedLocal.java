package gg.vape.asm.helper;

import gg.vape.asm.helper.EventBuilder;
import gg.vape.asm.helper.IndexedLocal;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class TypedIndexedLocal
extends IndexedLocal {
    public TypedIndexedLocal(int localIndex, String descriptor) {
        super(localIndex);
        this.resolvedDescriptor = descriptor;
    }

    @Override
    public void prepare(ClassNode classNode, MethodNode methodNode) {
        this.loadInstruction = new VarInsnNode(EventBuilder.getLoadOpcode(this.resolvedDescriptor), this.localOrdinal);
        this.loadInstructions.add(this.loadInstruction);
        this.storeInstruction = new VarInsnNode(EventBuilder.getStoreOpcode(this.resolvedDescriptor), this.localOrdinal);
        this.storeInstructions.add(this.storeInstruction);
    }
}
