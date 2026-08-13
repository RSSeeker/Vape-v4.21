package gg.vape.utils.render;

public class PoseMatrix {
    public int component0;
    public int component3;
    public int component2;
    public int component1;

    public PoseMatrix(int component0, int component1, int component2, int component3) {
        this.component0 = component0;
        this.component1 = component1;
        this.component2 = component2;
        this.component3 = component3;
    }

    public void set(int component0, int component1, int component2, int component3) {
        this.component0 = component0;
        this.component1 = component1;
        this.component2 = component2;
        this.component3 = component3;
    }

    public boolean equals(Object object) {
        if (!(object instanceof PoseMatrix)) {
            return false;
        }
        PoseMatrix poseMatrix = (PoseMatrix)object;
        return this.component0 == poseMatrix.component0 && this.component1 == poseMatrix.component1 && this.component2 == poseMatrix.component2 && this.component3 == poseMatrix.component3;
    }

    public int hashCode() {
        int middlePairSum = this.component1 + this.component2;
        int outerPairSum = this.component3 + this.component0;
        int middlePairHash = middlePairSum * (middlePairSum + 1) / 2 + this.component1;
        int outerPairHash = outerPairSum * (outerPairSum + 1) / 2 + this.component0;
        int combinedPairSum = middlePairHash + outerPairHash;
        return combinedPairSum * (combinedPairSum + 1) / 2 + outerPairHash;
    }
}
