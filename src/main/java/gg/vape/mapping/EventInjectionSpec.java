package gg.vape.mapping;

import gg.vape.event.EventBus;
import gg.vape.event.IEvent;
import gg.vape.mapping.InsertedCallbackEventMarker;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import java.lang.reflect.Method;

public class EventInjectionSpec {
    private static GuiComponent[] obfuscationComponents;
    private String constructorArguments = null;
    private boolean insertBefore = true;
    private Class instanceType;
    private boolean useCancelableReturnGuard = true;
    static int generatedEventCounter;
    private String condition;
    private final MappingMethod mappingMethod;
    private String returnExpression = "";
    private final Class eventClass;
    private String afterCode;

    public void setConstructorArguments(String constructorArguments) {
        this.constructorArguments = constructorArguments;
    }

    public MappingMethod getMappingMethod() {
        return this.mappingMethod;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setInstanceType(Class instanceType) {
        this.instanceType = instanceType;
    }

    public boolean isInsertBefore() {
        return this.insertBefore;
    }

    public String buildInjectionCode() {
        boolean hasInsertedCallbackMarker;
        Object listenerAccessor;
        Object eventStatement = "";
        String injectionStatement = "";
        String generatedEventName = "";
        if (this.useCancelableReturnGuard) {
            generatedEventName = "event" + ++generatedEventCounter;
            String returnGuardName = "res" + generatedEventName;
            injectionStatement = this.eventClass.getName() + " " + generatedEventName + " = new " + this.eventClass.getName() + "(" + (this.constructorArguments == null ? "" : this.constructorArguments) + ");\n";
            eventStatement = "boolean " + returnGuardName + " = " + generatedEventName + "." + EventBus.getFireMethod(this.eventClass).getName() + "();";
            eventStatement = (String)eventStatement + "if(" + returnGuardName + ") { return " + this.returnExpression.replace("$event", generatedEventName) + "; } ";
        } else {
            eventStatement = "new " + this.eventClass.getName() + "(" + (this.constructorArguments == null ? "" : this.constructorArguments) + ")." + EventBus.getFireMethod(this.eventClass).getName() + "();";
        }
        if (this.condition != null) {
            eventStatement = "if (" + this.condition + ") { " + (String)eventStatement + " }";
        }
        injectionStatement = injectionStatement + (String)eventStatement;
        if (this.afterCode != null) {
            injectionStatement = injectionStatement + this.afterCode.replace("$event", generatedEventName);
        }
        if (this.instanceType != null) {
            injectionStatement = "if($0 instanceof " + this.instanceType.getName() + ") { " + injectionStatement + " }";
        }
        hasInsertedCallbackMarker = IEvent.class.isAssignableFrom(this.eventClass) && InsertedCallbackEventMarker.class.isAssignableFrom(this.eventClass);
        if (hasInsertedCallbackMarker) {
            listenerAccessor = EventBus.findEventListenersAccessor(this.eventClass);
            injectionStatement = "if(" + this.eventClass.getName() + "#" + ((Method)listenerAccessor).getName() + "()." + EventBus.getHasListenersMethod().getName() + "()) { " + injectionStatement + "}";
        }
        injectionStatement = "{ " + injectionStatement + " }";
        return injectionStatement;
    }

    public EventInjectionSpec(MappingMethod mappingMethod, Class clazz) {
        this.mappingMethod = mappingMethod;
        this.eventClass = clazz;
    }

    static {
        EventInjectionSpec.setObfuscationComponents(null);
        generatedEventCounter = 0;
    }


    public void setAfterCode(String afterCode) {
        this.afterCode = afterCode;
    }

    public void setCancelableReturnGuard(boolean useCancelableReturnGuard) {
        this.useCancelableReturnGuard = useCancelableReturnGuard;
    }

    public static void setObfuscationComponents(GuiComponent[] components) {
        obfuscationComponents = components;
    }

    public Class getEventClass() {
        return this.eventClass;
    }

    public void setReturnExpression(String returnExpression) {
        this.returnExpression = returnExpression;
    }

    public void setInsertBefore(boolean insertBefore) {
        this.insertBefore = insertBefore;
    }

    public static GuiComponent[] getObfuscationComponents() {
        return obfuscationComponents;
    }
}

