package co.soft.technical.test.rest;

import co.soft.technical.test.dto.MediaItemDto;
import co.soft.technical.test.service.IMediaItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/media-item")
public class MediaItemController {

    // ~ Dependencies
    // ====================================================================
    private final IMediaItemService mediaItemService;

    // ~ Dependency Injection
    // ====================================================================
    public MediaItemController(IMediaItemService mediaItemService) {
        this.mediaItemService = mediaItemService;
    }

    // ~ Controllers
    // ====================================================================

    @GetMapping("/random")
    public ResponseEntity<MediaItemDto> getRandomMediaItem(){
        return new ResponseEntity<>(this.mediaItemService.getRandomMediaItem(), HttpStatus.OK);
    }

    @GetMapping("/sort")
    public ResponseEntity<List<MediaItemDto>> getAllMediaItemsSortedBy(@RequestParam(name = "sort") String sortBy){
        return new ResponseEntity<>(this.mediaItemService.getAllMediaItemsSortedBy(sortBy), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<MediaItemDto>> filterMediaItems(@RequestParam(name = "name", required = false) String name, @RequestParam(name = "type", required = false) String type, @RequestParam(name = "genre", required = false) String genre){
        return new ResponseEntity<>(this.mediaItemService.filterMediaItems(name, type, genre), HttpStatus.OK);
    }

    @PutMapping("/{id}/mark-as-viewed")
    public ResponseEntity<Void> markAsViewed(@PathVariable("id") Long id) {
        this.mediaItemService.markMediaItemAsViewed(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{mediaItemId}/rate")
    public ResponseEntity<Void> rateMediaItem(@PathVariable("mediaItemId") Long mediaItemId, @RequestParam("score") int score) {
        this.mediaItemService.rateMediaItem(mediaItemId, score);
        return ResponseEntity.noContent().build();
    }
}
