package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MLaunchClassLoader;
import gg.vape.wrapper.Wrapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LaunchClassLoader
extends Wrapper {
    public static LaunchClassLoader getLaunchClassLoader() {
        return new LaunchClassLoader(LaunchClassLoader.vapeInstance.getMappingsMapperCompat().C4.getInstance());
    }

    public LaunchClassLoader(Object object) {
        super(object);
    }

    public Set getClassLoaderExceptions() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return new HashSet();
        }
        return MLaunchClassLoader.l(LaunchClassLoader.vapeInstance.getMappingsMapperCompat().C4, this.I);
    }

    public Map cachedClasses() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return new HashMap();
        }
        return LaunchClassLoader.vapeInstance.getMappingsMapperCompat().C4.cachedClasses(this.I);
    }

    public boolean supportsLegacyClassCache() {
        return LaunchClassLoader.vapeInstance.getMappingsMapperCompat().C4.supportsLegacyClassCache();
    }

}

