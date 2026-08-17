package gg.vape.mapping;

import gg.vape.asm.transform.ClassTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class Badlion189InputQueueMappingTask extends ClassTransformer {
    private static final String INPUT_METHOD = "runInputTick";
    private static final String CALLBACK_OWNER =
            "gg/vape/input/BadlionKeyBindingEventQueue";

    public Badlion189InputQueueMappingTask() {
        super(MappedClasses.uP);
    }

    @Override
    public void transform() {
        int callbackCount = installCallback(this.classNode);
        if (callbackCount != 1) {
            throw new IllegalStateException(
                    "Expected one Badlion input queue callback, found "
                            + callbackCount);
        }
    }

    static int installCallback(ClassNode minecraftClass) {
        MethodNode inputMethod = null;
        for (MethodNode method : minecraftClass.methods) {
            if (INPUT_METHOD.equals(method.name) && "()V".equals(method.desc)) {
                if (inputMethod != null) {
                    throw new IllegalStateException(
                            "Multiple Badlion runInputTick methods are present");
                }
                inputMethod = method;
            }
        }
        if (inputMethod == null || inputMethod.instructions.getFirst() == null) {
            throw new IllegalStateException(
                    "Badlion Minecraft.runInputTick() is unavailable");
        }

        int callbackCount = countCallbacks(inputMethod);
        if (callbackCount == 0) {
            inputMethod.instructions.insertBefore(
                    inputMethod.instructions.getFirst(),
                    new MethodInsnNode(Opcodes.INVOKESTATIC, CALLBACK_OWNER,
                            "drain", "()V", false));
            callbackCount = countCallbacks(inputMethod);
        }
        return callbackCount;
    }

    private static int countCallbacks(MethodNode method) {
        int count = 0;
        for (AbstractInsnNode instruction = method.instructions.getFirst();
             instruction != null; instruction = instruction.getNext()) {
            if (!(instruction instanceof MethodInsnNode)) {
                continue;
            }
            MethodInsnNode call = (MethodInsnNode)instruction;
            if (call.getOpcode() == Opcodes.INVOKESTATIC
                    && CALLBACK_OWNER.equals(call.owner)
                    && "drain".equals(call.name)
                    && "()V".equals(call.desc)) {
                ++count;
            }
        }
        return count;
    }
}
