package co.soft.technical.test.service.impl;

import co.soft.technical.test.dto.MediaItemDto;
import co.soft.technical.test.exception.BusinessRuleException;
import co.soft.technical.test.exception.ObjectNotFoundException;
import co.soft.technical.test.mapper.MediaItemMapper;
import co.soft.technical.test.model.MediaItem;
import co.soft.technical.test.model.Rating;
import co.soft.technical.test.model.enums.MediaItemTypeEnum;
import co.soft.technical.test.repository.IMediaItemRepository;
import co.soft.technical.test.service.IMediaItemService;
import co.soft.technical.test.util.MediaItemSpecifications;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.EnumUtils;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MediaItemServiceImpl implements IMediaItemService {

    // ~ Dependencies
    // ====================================================================
    private final IMediaItemRepository mediaItemRepository;


    // ~ Dependency Injection
    // ====================================================================
    private final MediaItemMapper mediaItemMapper;

    // ~ Business Logic
    // ====================================================================

    public MediaItemServiceImpl(IMediaItemRepository mediaItemRepository, MediaItemMapper mediaItemMapper) {
        this.mediaItemRepository = mediaItemRepository;
        this.mediaItemMapper = mediaItemMapper;
    }

    @Override
    public MediaItemDto getRandomMediaItem() {
        Optional<MediaItem> mediaItemOptional = this.mediaItemRepository.findRandomMediaItem();
        if(mediaItemOptional.isEmpty()) throw new ObjectNotFoundException("media.item.object.not.found");
        MediaItem mediaItem = mediaItemOptional.get();
        mediaItem.setRating(getRating(mediaItem));
        return this.mediaItemMapper.toDto(mediaItemOptional.get());
    }

    @Override
    public List<MediaItemDto> getAllMediaItemsSortedBy(final String sortBy) {
        Sort sort;
        if ("NAME".equalsIgnoreCase(sortBy)) {
            sort = Sort.by(Sort.Direction.ASC, "name");
        } else if ("TYPE".equalsIgnoreCase(sortBy)) {
            sort = Sort.by(Sort.Direction.ASC, "type");
        } else if ("GENRE".equalsIgnoreCase(sortBy)) {
            sort = Sort.by(Sort.Direction.ASC, "genre");
        } else if ("RATING".equalsIgnoreCase(sortBy)) {
            sort = Sort.by(Sort.Direction.DESC, "rating");
        } else {
            sort = Sort.unsorted();
        }
        List<MediaItem> mediaItemList = this.mediaItemRepository.findAll(sort);
        return mediaItemList.stream()
                .map(mediaItem -> {
                    MediaItemDto mediaItemDto = mediaItemMapper.toDto(mediaItem);
                    Double averageRating = getRating(mediaItem);
                    mediaItemDto.setRating(averageRating);
                    return mediaItemDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<MediaItemDto> filterMediaItems(final String name, final String type, final String genre) {
        MediaItemTypeEnum itemType = convertToMediaType(type);
        if (itemType == null && type != null) throw new BusinessRuleException("media.item.type.invalid");
        List<MediaItem> mediaItemList = mediaItemRepository.findAll(getSpecifications(name, itemType, genre));
        return mediaItemList.stream()
                .map(mediaItem -> {
                    mediaItem.setRating(getRating(mediaItem));
                    return mediaItemMapper.toDto(mediaItem);
                })
                .collect(Collectors.toList());
    }

    private Specification<MediaItem> getSpecifications(final String name, final MediaItemTypeEnum type, final String genre) {
        Specification<MediaItem> spec = Specification.where(null);
        if (name != null) {
            spec = spec.and(MediaItemSpecifications.containsName(name));
        }
        if (type != null) {
            spec = spec.and(MediaItemSpecifications.hasType(type));
        }
        if (genre != null) {
            spec = spec.and(MediaItemSpecifications.hasGenre(genre));
        }
        return spec;
    }
    private MediaItemTypeEnum convertToMediaType(final String type) {
        if (type == null) return null;
        try{
            return EnumUtils.findEnumInsensitiveCase(MediaItemTypeEnum.class, type);
        }catch (IllegalArgumentException e){
            return null;
        }
    }
    @Override
    public void markMediaItemAsViewed(final Long mediaItemId) {
        Optional<MediaItem> mediaItemOptional = this.mediaItemRepository.findById(mediaItemId);
        if(mediaItemOptional.isEmpty()) throw new ObjectNotFoundException("media.item.object.not.found");
        MediaItem mediaItem = mediaItemOptional.get();
        mediaItem.setViews(mediaItem.getViews() + 1);
        this.mediaItemRepository.save(mediaItem);
    }

    @Override
    public void rateMediaItem(final Long mediaItemId, final int score) {
        Optional<MediaItem> mediaItemOptional = this.mediaItemRepository.findById(mediaItemId);
        if(mediaItemOptional.isEmpty()) throw new ObjectNotFoundException("media.item.object.not.found");
        if(score < 1 || score > 5) throw new BusinessRuleException("media.item.score.not.valid");
        MediaItem mediaItem = mediaItemOptional.get();
        Rating rating = Rating.builder()
                .score(score)
                .mediaItem(mediaItem)
                .build();
        mediaItem.getRatings().add(rating);
        this.mediaItemRepository.save(mediaItem);
    }

    private Double getRating(final MediaItem mediaItem) {
        List<Rating> ratings = mediaItem.getRatings();
        return ratings.stream()
                .mapToDouble(Rating::getScore)
                .average()
                .orElse(0.0);
    }
}
