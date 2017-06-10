package com.tony.annotation;

import java.lang.annotation.*;

/**
 * Author by TonyJiang on 2017/6/10.
 */
@Inherited
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {

}
