package gg.vape.mapping;

import gg.vape.mapping.EventInjectionSpec;
import gg.vape.mapping.InsertedCallbackMarker;
import gg.vape.mapping.MappingMethod;

public class InsertedCallbackEventInjectionSpec
extends EventInjectionSpec {
    private static final String h;

    @Override
    public String buildInjectionCode() {
        String callbackCode = this.getEventClass().getName() + h;
        return callbackCode;
    }

    public InsertedCallbackEventInjectionSpec(MappingMethod mappingMethod, Class<? extends InsertedCallbackMarker> clazz) {
        super(mappingMethod, clazz);
    }

    static {
        try {
            h = "#call();";
        }
        catch (Exception exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }
}
