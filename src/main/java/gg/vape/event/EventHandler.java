package gg.vape.event;

import gg.vape.event.EventPriority;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface EventHandler {
    public EventPriority priority() default EventPriority.NORMAL;

    public boolean skipCanceled() default false;
}
