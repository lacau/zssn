package com.zssn.model.query;

import com.zssn.model.enumeration.Resource;

public interface ResourceCountQueryResult {

    String QUERY = "SELECT "
        + "i.resource as resource, "
        + "sum(i.quantity) as quantity "
        + "FROM Inventory i "
        + "WHERE i.survivor.infected IS FALSE "
        + "GROUP BY i.resource";

    Resource getResource();

    Long getQuantity();
}
