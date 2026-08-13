package gg.vape.asm;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;

public interface ITramsformNode {
    String getOwnerInternalName();

    InsnList getLoadInstructions();

    public boolean hasOwner();

    String getDescriptor();

    ITramsformNode setOwner(Class ownerClass);

    InsnList getStoreInstructions();

    void prepare(ClassNode classNode, MethodNode methodNode);
}
