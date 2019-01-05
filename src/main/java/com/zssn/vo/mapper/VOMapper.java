package com.zssn.vo.mapper;

public abstract class VOMapper<E, V> {

    public abstract E fromVO(V source);
}
