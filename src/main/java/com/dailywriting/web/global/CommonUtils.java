package com.dailywriting.web.global;

import java.util.Optional;

public class CommonUtils {
    // safeCasting
    public static <T> Optional<T> safeCast(Object o, Class<T> targetClass) {
        return targetClass.isInstance(o)
                ? Optional.of(targetClass.cast(o))
                : Optional.empty();
    }
}
