package co.soft.technical.test.service;

import co.soft.technical.test.dto.MediaItemDto;

import java.util.List;

// ~ Method signature
// ====================================================================
public interface IMediaItemService {
    MediaItemDto getRandomMediaItem();
    List<MediaItemDto> getAllMediaItemsSortedBy(String sortBy);
    List<MediaItemDto> filterMediaItems(String name, String type, String genre);
    void markMediaItemAsViewed(Long mediaItemId);
    void rateMediaItem(Long mediaItemId, int score);
}
