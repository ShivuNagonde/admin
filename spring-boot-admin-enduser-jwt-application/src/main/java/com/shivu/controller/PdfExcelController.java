package com.shivu.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shivu.model.Product;
import com.shivu.service.ProductService;

@RestController
@RequestMapping("/pdf")
public class PdfExcelController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ServletContext context;
	
	@GetMapping("/createPDF")
	public void createPDF(HttpServletRequest request,HttpServletResponse response) throws IOException {
		if(request.getHeader("Authorization").endsWith((String) request.getSession().getAttribute("ENDUSER_SESSION"))) {
			List<Product> products = productService.getAllProducts();
			boolean isFlag = productService.createPDF(products,context,request,response);
			if(isFlag) {
				String fulpath = request.getServletContext().getRealPath("/resources/reports/"+"products"+".pdf");
			    filedownload(fulpath, response, "products.pdf");
			}
	           }else if(!request.getHeader("Authorization").endsWith((String) request.getSession().getAttribute("ENDUSER_SESSION"))){
	        	   response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
			}
	}
	
	@GetMapping("/createExcel")
	public void createExcel(HttpServletRequest request,HttpServletResponse response) throws IOException {
		if(request.getHeader("Authorization").endsWith((String) request.getSession().getAttribute("ENDUSER_SESSION"))) {
		List<Product> products = productService.getAllProducts();
		boolean isFlag = productService.createExcel(products,context,request,response);
		if(isFlag) {
			String fulpath = request.getServletContext().getRealPath("/resources/reports/"+"products"+".xls");
		    filedownload(fulpath, response, "products.xls");
		}}
		else if(!request.getHeader("Authorization").endsWith((String) request.getSession().getAttribute("ENDUSER_SESSION"))){
     	   response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
		}	
	}
	@GetMapping("/createPDF/{id}")
	public void createPdfByProductId(@PathVariable("id") String id,HttpServletRequest request,HttpServletResponse response) throws IOException {
		if(request.getHeader("Authorization").endsWith((String) request.getSession().getAttribute("ENDUSER_SESSION"))) {
	    Optional<Product> product = productService.getOneProduct(id);
		boolean isFlag = productService.createPDF(product,context,request,response);
		if(isFlag) {
			String fulpath = request.getServletContext().getRealPath("/resources/reports/"+"products"+".pdf");
		    filedownload(fulpath, response, "products.pdf");
		}}
		else if(!request.getHeader("Authorization").endsWith((String) request.getSession().getAttribute("ENDUSER_SESSION"))){
     	   response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
		}
	}
	@GetMapping("/createExcel/{id}")
	public void createExcelByProductId(@PathVariable("id") String id,HttpServletRequest request,HttpServletResponse response) throws IOException {
		if(request.getHeader("Authorization").endsWith((String) request.getSession().getAttribute("ENDUSER_SESSION"))) {
		Optional<Product> product = productService.getOneProduct(id);
		boolean isFlag = productService.createExcel(product, context, request, response);
	    if(isFlag) {
	    	String fulpath = request.getServletContext().getRealPath("/resources/reports/"+"products"+".xls");
	        filedownload(fulpath,response,"products.xls");
	    }}
        else if(!request.getHeader("Authorization").endsWith((String) request.getSession().getAttribute("ENDUSER_SESSION"))){
     	   response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
		}
	}
	private void filedownload(String fulpath, HttpServletResponse response, String filename) {
		File file = new File(fulpath);
		final int BUFFER_SIZE = 4096;
		if(file.exists()) {
			try {
				FileInputStream fis = new FileInputStream(file);
				String mimetype = context.getMimeType(fulpath);
				response.setContentType(mimetype);
				response.setHeader("content-disposition", "attachment; filename="+filename);
				OutputStream outputStream = response.getOutputStream();
				byte[] buffer = new byte[BUFFER_SIZE];
				int bytesRead = -1;
				while((bytesRead = fis.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
				fis.close();
				outputStream.close();
				file.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
