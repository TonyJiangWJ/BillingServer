package com.tony.annotation;

import java.lang.annotation.*;

/**
 * Author by TonyJiang on 2017/6/10.
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {

}
