/*
 * package com.shivu.videocontroller;
 * 
 * import java.util.Optional;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.web.bind.annotation.PathVariable; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestMethod; import
 * org.springframework.web.bind.annotation.RequestParam; import
 * org.springframework.web.bind.annotation.RestController; import
 * org.springframework.web.multipart.MultipartFile;
 * 
 * import com.shivu.model.Product; import
 * com.shivu.repository.ProductRepository; import
 * com.shivu.storageService.FileSystemStorageService;
 * 
 * @RestController
 * 
 * @RequestMapping("/v1") public class VideoUploadController {
 * 
 * @Autowired private ProductRepository productRepository;
 * 
 * @Autowired private FileSystemStorageService storageService;
 * 
 * @RequestMapping(value = "/videoupload/{id}",method = RequestMethod.POST)
 * public Object videoUpload(@RequestParam("videofile") MultipartFile
 * videofile,@PathVariable("id")String id) { Optional<Product> pd =
 * productRepository.findById(id); if(pd.isPresent()) { Product pt = pd.get();
 * pt.setVideoName(videofile.getOriginalFilename());
 * productRepository.save(pt);}
 * 
 * storageService.store(videofile); return "Video uploaded successfully."; }
 * 
 * }
 */