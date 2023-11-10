package io.github.astro.mantis.common.util;

import java.util.Collection;
import java.util.function.BiPredicate;

@SuppressWarnings("unchecked")
public interface CollectionUtils {

    static <T> void addToList(Collection<T> collection, BiPredicate<T, T> predicate, T... items) {
        loop:
        for (T item : items) {
            for (T collect : collection) {
                if (predicate.test(collect, item)) {
                    continue loop;
                }
            }
            collection.add(item);
        }
    }

}
