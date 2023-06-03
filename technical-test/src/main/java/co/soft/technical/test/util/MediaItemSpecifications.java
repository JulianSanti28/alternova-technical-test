package co.soft.technical.test.util;

import co.soft.technical.test.model.MediaItem;
import co.soft.technical.test.model.enums.MediaItemTypeEnum;
import org.springframework.data.jpa.domain.Specification;

public class MediaItemSpecifications {

    public static Specification<MediaItem> containsName(String name) {
        return (root, query, criteriaBuilder) -> {
            String lowercaseName = name.toLowerCase();
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name"))
                    , "%" + lowercaseName + "%");
        };
    }

    public static Specification<MediaItem> hasType(MediaItemTypeEnum type) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("type"), type);
    }
    public static Specification<MediaItem> hasGenre(String genre) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("genre"), genre);
    }
}
