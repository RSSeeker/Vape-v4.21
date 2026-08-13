package gg.vape.runtime.model;

import gg.vape.runtime.model.DetachedStringTreeNode;

class DetachedStringTreeEntry {
    String value;
    final DetachedStringTreeNode ownerNode;

    DetachedStringTreeEntry(DetachedStringTreeNode ownerNode) {
        this.ownerNode = ownerNode;
    }
}
