package co.soft.technical.test.mapper;

import co.soft.technical.test.dto.MediaItemDto;
import co.soft.technical.test.model.MediaItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {MediaItem.class})
public interface MediaItemMapper extends IEntityMapper<MediaItemDto, MediaItem> {
    MediaItem toDomain(MediaItemDto dto);
    MediaItemDto toDto(MediaItem entity);
}
