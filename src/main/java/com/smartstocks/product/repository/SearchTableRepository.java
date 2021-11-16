package com.smartstocks.product.repository;

import com.smartstocks.product.models.SearchTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchTableRepository extends JpaRepository<SearchTable, String> {

    @Query(value = "SELECT st.symbol, st.exchange, st.shortname, st.longname, st.prev_name, st.name_change_date " +
            "FROM search_table st, similarity(st.longname, :q) sim " +
            "WHERE sim > 0.1 AND SUBSTRING(st.symbol, LENGTH(st.symbol)-1, LENGTH(st.symbol)) = 'NS'" +
            "ORDER BY sim DESC " +
            "LIMIT :no", nativeQuery = true)
    List<SearchTable> search(@Param("q") String q, @Param("no") int limit);

    @Modifying
    @Query(value = "CREATE EXTENSION IF NOT EXISTS pg_trgm", nativeQuery = true)
    void createTrgmExtension();

    @Query("SELECT s FROM SearchTable s WHERE s.symbol = :symbol")
    SearchTable getBySymbol(@Param("symbol") String symbol);

    @Query("SELECT s FROM SearchTable s WHERE SUBSTRING(s.symbol, LENGTH(s.symbol)-1, LENGTH(s.symbol)) = 'NS' ")
    Page<SearchTable> getAll(Pageable pageable);
}
