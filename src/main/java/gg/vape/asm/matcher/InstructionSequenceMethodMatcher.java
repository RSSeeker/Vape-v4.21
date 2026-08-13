package gg.vape.asm.matcher;

import gg.vape.asm.matcher.InstructionPattern;
import gg.vape.asm.matcher.MethodInstructionIndex;
import gg.vape.asm.matcher.MethodNodeMatcher;
import java.util.List;
import org.objectweb.asm.tree.MethodNode;

public class InstructionSequenceMethodMatcher
extends MethodNodeMatcher {
    private static int opaqueState;
    private final InstructionPattern[] expectedPatterns;

    public static int getOpaqueState() {
        return opaqueState;
    }

    static {
        if (InstructionSequenceMethodMatcher.opaquePredicate() != 0) {
            InstructionSequenceMethodMatcher.setOpaqueState(89);
        }
    }

    public static void setOpaqueState(int state) {
        opaqueState = state;
    }

    public static int opaquePredicate() {
        int state = InstructionSequenceMethodMatcher.getOpaqueState();
        if (state == 0) {
            return 77;
        }
        return 0;
    }

    public InstructionSequenceMethodMatcher(Class targetClass, InstructionPattern ... expectedPatterns) {
        super(targetClass);
        this.expectedPatterns = expectedPatterns;
    }


    @Override
    public boolean matchesMethod(MethodNode methodNode) {
        MethodInstructionIndex methodInstructionIndex = this.getMethodInstructionIndex(methodNode);
        int matchedCount = 0;
        int searchStartIndex = 0;
        List<InstructionPattern> actualPatterns = methodInstructionIndex.getPatterns();
        block0: for (InstructionPattern expectedPattern : this.expectedPatterns) {
            for (int index = searchStartIndex; index < actualPatterns.size(); ++index) {
                searchStartIndex = index;
                InstructionPattern actualPattern = actualPatterns.get(index);
                if (!expectedPattern.matches(actualPattern)) continue;
                ++matchedCount;
                continue block0;
            }
        }
        return matchedCount == this.expectedPatterns.length;
    }
}

