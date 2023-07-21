package co.soft.technical.test.business;

import co.soft.technical.test.TechnicalTestApplication;
import co.soft.technical.test.config.DataSourceConfigurationTest;
import co.soft.technical.test.dto.MediaItemDto;
import co.soft.technical.test.mapper.MediaItemMapper;
import co.soft.technical.test.model.MediaItem;
import co.soft.technical.test.model.enums.MediaItemTypeEnum;
import co.soft.technical.test.repository.IMediaItemRepository;
import co.soft.technical.test.service.impl.MediaItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {TechnicalTestApplication.class, DataSourceConfigurationTest.class, MediaItemMapper.class })
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class MediaItemBusinessTest {
    @Mock
    private IMediaItemRepository mediaItemRepository;
    @InjectMocks
    private MediaItemServiceImpl mediaItemService;
    @Mock
    private MediaItemMapper  mediaItemMapperMock;

    @Autowired
    private MediaItemMapper mediaItemMapper;

    @BeforeEach
    void setup(){
        this.mediaItemService = new MediaItemServiceImpl(this.mediaItemRepository, this.mediaItemMapperMock);
    }

    @Test
    void testGetAllMediaItemsSortedBy() {
        // Datos de prueba
        List<MediaItem> mediaItemListSortedByName = Arrays.asList(
                MediaItem
                        .builder()
                        .id(1L)
                        .name("A")
                        .genre("Sciences")
                        .type(MediaItemTypeEnum.MOVIE)
                        .rating(500)
                        .ratings(new ArrayList<>())
                        .build(),
                MediaItem
                        .builder()
                        .id(2L)
                        .name("B")
                        .genre("Sciences")
                        .type(MediaItemTypeEnum.MOVIE)
                        .rating(600)
                        .ratings(new ArrayList<>())
                        .build()
        );
        List<MediaItemDto> expectMediaItemListSortedByNameDto = Arrays.asList(
                MediaItemDto
                        .builder()
                        .id(1L)
                        .name("A")
                        .genre("Sciences")
                        .type(MediaItemTypeEnum.MOVIE)
                        .rating(500)
                        .build(),
                MediaItemDto
                        .builder()
                        .id(2L)
                        .name("B")
                        .genre("Sciences")
                        .type(MediaItemTypeEnum.MOVIE)
                        .rating(600)
                        .build()
        );
        when(mediaItemRepository.findAll(Sort.by(Sort.Direction.ASC, "name"))).thenReturn(mediaItemListSortedByName);
        when(mediaItemRepository.findAll(Sort.by(Sort.Direction.ASC, "type"))).thenReturn(mediaItemListSortedByName);
        when(mediaItemRepository.findAll(Sort.by(Sort.Direction.ASC, "genre"))).thenReturn(mediaItemListSortedByName);

        // Sort by name
        Mockito.when(mediaItemMapperMock.toDto(Mockito.any(MediaItem.class))).thenAnswer(invocation -> this.mediaItemMapper.toDto(invocation.getArgument(0)));
        List<MediaItemDto> result = mediaItemService.getAllMediaItemsSortedBy("name");

        assertEquals(expectMediaItemListSortedByNameDto.size(), result.size());
        assertEquals(expectMediaItemListSortedByNameDto.get(0).getId(), result.get(0).getId());

        Mockito.when(mediaItemMapperMock.toDto(Mockito.any(MediaItem.class))).thenAnswer(invocation -> this.mediaItemMapper.toDto(invocation.getArgument(0)));
        result = mediaItemService.getAllMediaItemsSortedBy("type");

        assertEquals(expectMediaItemListSortedByNameDto.size(), result.size());
        assertEquals(expectMediaItemListSortedByNameDto.get(0).getId(), result.get(0).getId());

        Mockito.when(mediaItemMapperMock.toDto(Mockito.any(MediaItem.class))).thenAnswer(invocation -> this.mediaItemMapper.toDto(invocation.getArgument(0)));
        result = mediaItemService.getAllMediaItemsSortedBy("genre");

        assertEquals(expectMediaItemListSortedByNameDto.size(), result.size());
        assertEquals(expectMediaItemListSortedByNameDto.get(0).getId(), result.get(0).getId());

        Mockito.verify(mediaItemRepository, Mockito.times(3)).findAll(Mockito.any(Sort.class));
        Mockito.verify(mediaItemMapperMock, Mockito.times(mediaItemListSortedByName.size()*3)).toDto(Mockito.any(MediaItem.class));
    }

    @Test
    void getRating(){
        MediaItem mediaItem = MediaItem
                .builder()
                .id(1L)
                .name("A")
                .genre("Sciences")
                .type(MediaItemTypeEnum.MOVIE)
                .rating(500)
                .ratings(new ArrayList<>())
                .build();
        assertEquals(0.0, this.mediaItemService.getRating(mediaItem));
    }

}
