package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MOrdering;
import gg.vape.ui.click.component.GuiComponent;
import java.util.ArrayList;
import java.util.List;

public class MLists
extends Mapping {
    public final MappingMethod reverseMethod;
    public final MappingMethod newArrayListMethod;

    public ArrayList newArrayList(Iterable iterable) {
        return (ArrayList)this.newArrayListMethod.invokeObject(null, iterable);
    }


    public List reverse(List list) {
        return (List)this.reverseMethod.invokeObject(null, list);
    }

    public MLists() {
        this(MOrdering.getControlFlowMarker());
    }

    private MLists(String controlFlowMarker) {
        super(MappedClasses.VZ);
        this.reverseMethod = this.registerStaticMethod("reverse", false, List.class, List.class);
        this.newArrayListMethod = this.registerStaticMethod("newArrayList", false, ArrayList.class, Iterable.class);
        if (GuiComponent.getLegacyComponentState() == null) {
            MOrdering.setControlFlowMarker("Ze85D");
        }
    }
}

