package gg.vape.asm.helper;

import gg.vape.asm.ITramsformNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class FieldTransformNode
implements ITramsformNode {
    private FieldNode matchedField;
    private InsnList storeInstructions;
    private String fieldName;
    private InsnList loadInstructions = new InsnList();

    @Override
    public boolean hasOwner() {
        return false;
    }

    @Override
    public InsnList getLoadInstructions() {
        return this.loadInstructions;
    }


    public FieldTransformNode(String fieldName) {
        this.storeInstructions = new InsnList();
        this.fieldName = fieldName;
    }

    @Override
    public String getOwnerInternalName() {
        return null;
    }

    @Override
    public String getDescriptor() {
        return this.matchedField.desc;
    }

    @Override
    public void prepare(ClassNode classNode, MethodNode methodNode) {
        for (FieldNode candidate : classNode.fields) {
            if (!candidate.name.equals(this.fieldName)) continue;
            this.matchedField = candidate;
            this.loadInstructions.add(new VarInsnNode(25, 0));
            this.loadInstructions.add(new FieldInsnNode(180, classNode.name, candidate.name, candidate.desc));
            this.storeInstructions.add(new VarInsnNode(25, 0));
            this.storeInstructions.add(new FieldInsnNode(181, classNode.name, candidate.name, candidate.desc));
        }
    }

    @Override
    public ITramsformNode setOwner(Class ownerClass) {
        return this;
    }

    @Override
    public InsnList getStoreInstructions() {
        return this.storeInstructions;
    }
}
