package gg.vape.runtime.model;

import gg.vape.runtime.model.DetachedStringTree;
import gg.vape.runtime.model.DetachedStringTreeEntry;
import java.util.List;

class DetachedStringTreeNode {
    List<DetachedStringTreeEntry> entries;
    final DetachedStringTree ownerTree;
    String primaryValue;
    String secondaryValue;

    DetachedStringTreeNode(DetachedStringTree ownerTree) {
        this.ownerTree = ownerTree;
    }
}
