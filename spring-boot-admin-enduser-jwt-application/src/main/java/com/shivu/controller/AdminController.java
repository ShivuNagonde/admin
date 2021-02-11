package com.shivu.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Hashtable;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.shivu.model.Product;
import com.shivu.repository.ProductRepository;
import com.shivu.service.ProductService;
import com.shivu.storageService.FileSystemStorageService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private ProductService productService;
	@Autowired
    private ProductRepository productRepository;
	@Autowired
    private FileSystemStorageService storageService;
	
	@RequestMapping(value = "/products",method = RequestMethod.POST)
	public Object saveProducts(@RequestParam("Product Image") MultipartFile file,Product product, HttpServletRequest request, HttpServletResponse response) throws IOException, WriterException {
		if(request.getHeader("Authorization").endsWith((String) request.getSession().getAttribute("ADMIN_SESSION"))) {
			String encodstring = encodeFileToBase64Binary(file.getBytes());
	        product.setImage(encodstring);
			
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode("Product Id :"+ product.getId()+"\n"+"Product Name :"+ product.getProductName()
			+"\n"+"Discription :"+ product.getDiscription()+"\n"+"Total Price :"+ product.getPrice()
			+"\n"+"ManufactureDate :"+ product.getCreatedDate(), BarcodeFormat.QR_CODE, 200, 200);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			MatrixToImageWriter.writeToStream(bitMatrix, "png", byteArrayOutputStream);
			byte[] qrcode=byteArrayOutputStream.toByteArray();
			product.setQrcodeImage(qrcode);
		
			Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			Writer writer = new Code128Writer();
			BitMatrix bitMatrix1 = writer.encode("Product Id :"+ product.getId(), BarcodeFormat.CODE_128, 200, 200);
			ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
			MatrixToImageWriter.writeToStream(bitMatrix1, "png", byteArrayOutputStream1);
			byte[] barcode=byteArrayOutputStream1.toByteArray();
			product.setBarcodeImage(barcode);
	    	return productService.saveProduct(product);
		}else {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
		}
		return null;
	}
	
	@RequestMapping(value = "/videoupload/{id}",method = RequestMethod.POST)
	public Object videoUpload(@RequestParam("videofile") MultipartFile videofile,@PathVariable("id")String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(request.getHeader("Authorization").endsWith((String) request.getSession().getAttribute("ADMIN_SESSION"))) {
		Optional<Product> pd =  productRepository.findById(id);
        if(pd.isPresent()) {
         Product pt = pd.get();
         pt.setVideoName(videofile.getOriginalFilename());
         productRepository.save(pt);}
      
    	storageService.store(videofile);
		return "Video uploaded successfully.";
		}else {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
		}
		return null;
	}
	
	@GetMapping(value = "/liveRecord")
	public Object liveVideoRecord(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(request.getHeader("Authorization").endsWith((String) request.getSession().getAttribute("ADMIN_SESSION"))) {
		
			return storageService.liveRecording();
		
		}else {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");	
		}
		return null;
	}
	
	@GetMapping(value = "/liveRecording")
	public Object liveVideoRecording()  {
		//if(request.getHeader("Authorization").endsWith((String) request.getSession().getAttribute("ADMIN_SESSION"))) {
		return storageService.liveRecording1();
		//}else {
			//response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");	
		//}
		//return null;
	}
	//@SuppressWarnings("resource")
	private String encodeFileToBase64Binary(byte[] image) {
    	String encodedfile = null;
        try {
            encodedfile = Base64.getEncoder().encodeToString(image);
        }catch (Exception e) {
            e.printStackTrace();
        }
       
        return encodedfile;
	}
}
