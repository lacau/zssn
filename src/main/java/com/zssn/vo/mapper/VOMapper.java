package com.zssn.vo.mapper;

import java.util.ArrayList;
import java.util.List;

public abstract class VOMapper<E, V> {

    public abstract E fromVO(V source);

    public List<E> fromVO(Iterable<V> sources) {
        final List<E> destination = new ArrayList<>();
        if (sources != null) {
            sources.forEach((s) -> destination.add(fromVO(s)));
        }
        return destination;
    }
}
