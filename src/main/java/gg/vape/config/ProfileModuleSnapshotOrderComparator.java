package gg.vape.config;

import gg.vape.config.ProfileModuleSnapshot;
import gg.vape.config.ProfileSnapshot;
import java.util.Comparator;

public class ProfileModuleSnapshotOrderComparator
implements Comparator<ProfileModuleSnapshot> {
    @Override
    public int compare(ProfileModuleSnapshot left, ProfileModuleSnapshot right) {
        return Integer.compare(right.getSortPriority(), left.getSortPriority());
    }
}
