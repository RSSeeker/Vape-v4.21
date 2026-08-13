package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEffectRenderer;
import gg.vape.wrapper.Wrapper;

import java.util.ArrayList;
import java.util.List;

public class EffectRenderer
extends Wrapper {
    public EffectRenderer(Object object) {
        super(object);
    }

    public List<SoundAwareEntityFX> getParticleEmitters() {
        List list = EffectRenderer.vapeInstance.getMappings().Ru.A(this.I);
        ArrayList<SoundAwareEntityFX> arrayList = new ArrayList<SoundAwareEntityFX>();
        for (Object e : list) {
            arrayList.add(new SoundAwareEntityFX(e));
        }
        return arrayList;
    }

    public List<EntityFX>[][] getFxLayers() {
        if (ForgeVersion.MC_1_7_10.Y()) {
            List[][] listArray = MEffectRenderer.N(EffectRenderer.vapeInstance.getMappings().Ru, this.I);
            ArrayList[][] arrayListArrayArray = new ArrayList[4][];
            int n = 0;
            for (List[] listArray2 : listArray) {
                ArrayList[] arrayListArray = new ArrayList[listArray2.length];
                for (int i = 0; i < listArray2.length; ++i) {
                    List list = listArray2[i];
                    ArrayList<EntityFX> arrayList = new ArrayList<EntityFX>();
                    for (Object e : list) {
                        arrayList.add(new EntityFX(e));
                    }
                    arrayListArray[i] = arrayList;
                }
                arrayListArrayArray[n] = arrayListArray;
                ++n;
            }
            return arrayListArrayArray;
        }
        ArrayList[][] arrayListArrayArray = new ArrayList[1][];
        List[] listArray = MEffectRenderer.z(EffectRenderer.vapeInstance.getMappings().Ru, this.I);
        int n = 0;
        ArrayList[] arrayListArray = new ArrayList[4];
        for (int i = 0; i < listArray.length; ++i) {
            List list = listArray[i];
            ArrayList<EntityFX> arrayList = new ArrayList<EntityFX>();
            for (Object e : list) {
                arrayList.add(new EntityFX(e));
            }
            arrayListArray[i] = arrayList;
        }
        arrayListArrayArray[n] = arrayListArray;
        return arrayListArrayArray;
    }
}

