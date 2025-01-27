package jp.co.aico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jp.co.aico.entity.CaZoomEntity;

public interface CaZoomRepository extends JpaRepository<CaZoomEntity,Integer>{
	
	//削除フラグで検索
	@Query("SELECT cz FROM CaZoomEntity cz WHERE cz.deleteFlag = :deleteFlag ORDER BY cz.zoomId ASC")
	List<CaZoomEntity> findByDeleteFlag(@Param("deleteFlag") Integer deleteFlag);
	
	
	

}
