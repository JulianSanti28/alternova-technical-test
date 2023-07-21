package co.soft.technical.test.repository;

import co.soft.technical.test.model.MediaItem;
import co.soft.technical.test.model.enums.MediaItemTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface IMediaItemRepository extends JpaRepository<MediaItem, Long> , JpaSpecificationExecutor<MediaItem> {

    @Query(value = "SELECT * FROM media_item ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<MediaItem> findRandomMediaItem();
    @Query(value = "SELECT * FROM media_item mi WHERE LOWER(mi.name) LIKE %:name% OR mi.type = :type OR LOWER(mi.genre) = LOWER(:genre)", nativeQuery = true)
    List<MediaItem> searchByFilter(String name, MediaItemTypeEnum type, String genre);

}

