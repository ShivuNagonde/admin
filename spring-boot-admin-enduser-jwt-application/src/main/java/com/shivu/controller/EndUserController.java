package com.shivu.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shivu.helper.QRCodeGenerator;
import com.shivu.model.Product;
import com.shivu.service.ProductService;
import com.shivu.storageService.VideoStreamService;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.xuggler.IVideoPicture;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/enduser")
public class EndUserController {

	private final VideoStreamService videoStreamService;

    public EndUserController(VideoStreamService videoStreamService) {
        this.videoStreamService = videoStreamService;
    }
    
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "/products",method = RequestMethod.GET)
	public List<Product> getAllProducts(HttpServletRequest request, HttpServletResponse response) throws IOException{
		if(request.getHeader("Authorization").endsWith((String) request.getSession().getAttribute("ENDUSER_SESSION"))) {
           return productService.getAllProducts();
           }else {
        	   response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
		}
		return null;
	}
	
	@GetMapping("/stream/{fileType}/{fileName}")
    public Mono<ResponseEntity<byte[]>> streamVideo(@RequestHeader(value = "Range", required = false) String httpRangeList,
                                                    @PathVariable("fileType") String fileType,
                                                    @PathVariable("fileName") String fileName, HttpServletRequest request, HttpServletResponse response 
                                                    ) throws IOException  {
		if(request.getHeader("Authorization").endsWith((String) request.getSession().getAttribute("ENDUSER_SESSION"))) {

        return Mono.just(videoStreamService.prepareContent(fileName, fileType, httpRangeList));
        }else {
     	   response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
		return null;
    }
	
	@RequestMapping(value = "/qrcode/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String qrcode(@PathVariable("id")String id, Product product, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getHeader("Authorization").endsWith((String) request.getSession().getAttribute("ENDUSER_SESSION"))) {
			response.setContentType("image/png");
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(QRCodeGenerator.getQRCodeImage("Product Id :"+ product.getId()+"\n"+"Product Name :"+ product.getProductName()
			+"\n"+"Discription :"+ product.getDiscription()+"\n"+"Total Price :"+ product.getPrice()+"\n"+"Product Image :"+ product.getImage()
			+"\n"+"ManufactureDate :"+ product.getCreatedDate(), 200, 200));
			outputStream.flush();
			outputStream.close();
			return "QRCode is generated successfully.";
	           }else if(!request.getHeader("Authorization").endsWith((String) request.getSession().getAttribute("ENDUSER_SESSION"))){
	        	   response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
			}
		return null;
		
	}
	@RequestMapping(value = "/barcode/{id}", method = RequestMethod.GET)
	public void barcode(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getHeader("Authorization").endsWith((String) request.getSession().getAttribute("ENDUSER_SESSION"))) {
			response.setContentType("image/png");
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(QRCodeGenerator.getBarCodeImage(id, 200, 200));
			outputStream.flush();
			outputStream.close();
	           }else if(!request.getHeader("Authorization").endsWith((String) request.getSession().getAttribute("ENDUSER_SESSION"))){
	        	   response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
			}
		
	}

	
	/*
	 * @GetMapping("/liveStreaming1") public Mono<Object> liveStreaming1(Object
	 * encodeVideo) { return
	 * Mono.just(videoStreamService.preparedContent(encodeVideo)); }
	 */

	 @GetMapping("/liveStreaming1")
	public void liveStreaming1(Object file) {
		 videoStreamService.preparedContent(file);
	}

}
