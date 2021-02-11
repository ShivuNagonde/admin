/*
 * package com.shivu.videocontroller;
 * 
 * import org.springframework.http.ResponseEntity; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.PathVariable; import
 * org.springframework.web.bind.annotation.RequestHeader; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RestController;
 * 
 * import com.shivu.storageService.VideoStreamService;
 * 
 * import reactor.core.publisher.Mono;
 * 
 * @RestController
 * 
 * @RequestMapping("/video") public class VideoStreamController {
 * 
 * private final VideoStreamService videoStreamService;
 * 
 * public VideoStreamController(VideoStreamService videoStreamService) {
 * this.videoStreamService = videoStreamService; }
 * 
 * @GetMapping("/stream/{fileType}/{fileName}") public
 * Mono<ResponseEntity<byte[]>> streamVideo(
 * 
 * @RequestHeader(value = "Range", required = false) String httpRangeList,
 * 
 * @PathVariable("fileType") String fileType,
 * 
 * @PathVariable("fileName") String fileName) { return
 * Mono.just(videoStreamService.prepareContent(fileName, fileType,
 * httpRangeList)); } //@GetMapping("/live") // public Mono<Object>
 * streamVideo(IVideoPicture frame) { //return
 * Mono.just(videoStreamService.preparedContent(frame)); //} }
 */