package de.vonmusil.swingapp.action;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Actionmethod
{
    String name();
    String caption();
}
