package com.zssn.model.repository;

import com.zssn.model.entity.InfectionLog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InfectionLogRepository extends CrudRepository<InfectionLog, Long> {

    @Query("SELECT COUNT(il) > 0 FROM InfectionLog il WHERE il.reporter.id =:reporterId AND il.reported.id =:reportedId")
    boolean isAlteradyReported(@Param("reporterId") Long reporterId, @Param("reportedId") Long reportedId);

    int countByReportedId(Long reportedId);
}
