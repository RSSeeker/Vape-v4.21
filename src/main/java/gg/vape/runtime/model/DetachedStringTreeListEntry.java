package gg.vape.runtime.model;

import gg.vape.runtime.model.DetachedStringTreeEntry;
import gg.vape.runtime.model.DetachedStringTreeNode;
import java.util.List;

class DetachedStringTreeListEntry
extends DetachedStringTreeEntry {
    final DetachedStringTreeNode listOwnerNode;
    List<String> values;

    DetachedStringTreeListEntry(DetachedStringTreeNode ownerNode) {
        super(ownerNode);
        this.listOwnerNode = ownerNode;
    }
}
