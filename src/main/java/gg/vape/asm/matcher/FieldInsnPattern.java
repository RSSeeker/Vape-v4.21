package gg.vape.asm.matcher;

import gg.vape.asm.matcher.InstructionPattern;
import gg.vape.mapping.MappingField;
import org.objectweb.asm.tree.FieldInsnNode;

public class FieldInsnPattern
extends InstructionPattern {
    public FieldInsnPattern(int opcode, String owner, String name, String descriptor) {
        super(opcode, owner, name, descriptor);
    }

    public static FieldInsnPattern fromNode(FieldInsnNode fieldInstruction) {
        return new FieldInsnPattern(fieldInstruction.getOpcode(), fieldInstruction.owner, fieldInstruction.name, fieldInstruction.desc);
    }

    public static FieldInsnPattern fromMapping(int opcode, MappingField mappingField) {
        return new FieldInsnPattern(opcode, mappingField.getOwnerClass().getName(), mappingField.getResolvedName(), mappingField.getDescriptor());
    }
}
