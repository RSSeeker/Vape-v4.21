package gg.vape.utils.concurrent;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockHelper
extends ReentrantReadWriteLock {
    public void lockWrite() {
        this.writeLock().lock();
    }

    public void lockRead() {
        this.readLock().lock();
    }

    public void unlockRead() {
        this.readLock().unlock();
    }

    public void unlockWrite() {
        this.writeLock().unlock();
    }
}

