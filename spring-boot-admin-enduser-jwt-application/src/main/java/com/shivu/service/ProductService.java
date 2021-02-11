package com.shivu.service;


import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.shivu.model.Product;
import com.shivu.repository.ProductRepository;

@Service
@Transactional
public class ProductService {

	
	
	@Autowired
	private ProductRepository productRepo;
	
	
	public Product saveProduct(Product product) {
		return productRepo.save(product);
	}
	
	public List<Product> getAllProducts(){
		return productRepo.findAll();
	}
	
	public Optional<Product> getOneProduct(String id){
		return productRepo.findById(id);
	}

	public boolean createPDF(List<Product> products, ServletContext context, HttpServletRequest request,HttpServletResponse response) {
		Document document = new Document(PageSize.A4, 15, 15, 45, 30);
		try {
			String filepath = context.getRealPath("/resources/reports");
			File file = new File(filepath);
			boolean exists = new File(filepath).exists();
			if(!exists) {
				new File(filepath).mkdirs();
			}
			
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file+"/"+"products"+".pdf"));
			document.open();
			
			Font mainFont = FontFactory.getFont("Arial", 10, BaseColor.BLACK);
			Paragraph paragraph = new Paragraph("All Products", mainFont);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setIndentationLeft(50);
			paragraph.setIndentationRight(50);
			paragraph.setSpacingAfter(10);
			document.add(paragraph);
			
			PdfPTable table = new PdfPTable(5);
			table.setWidthPercentage(100);
			table.setSpacingAfter(10f);
			table.setSpacingBefore(10);
			
			Font tableHeader = FontFactory.getFont("Arial", 10, BaseColor.BLACK);
            Font tableBody = FontFactory.getFont("Arial", 9, BaseColor.BLACK);
	
			float[] columnWidths = {2f, 2f, 2f, 2f, 2f};
			table.setWidths(columnWidths);
			
			PdfPCell productId = new PdfPCell(new Paragraph("Product ID",tableHeader));
			productId.setBorderColor(BaseColor.BLACK);
			productId.setPaddingLeft(10);
			productId.setHorizontalAlignment(Element.ALIGN_CENTER);
			productId.setVerticalAlignment(Element.ALIGN_CENTER);
			productId.setBackgroundColor(BaseColor.GRAY);
			productId.setExtraParagraphSpace(5f);
			table.addCell(productId);
			
			PdfPCell productName = new PdfPCell(new Paragraph("Product Name",tableHeader));
			productName.setBorderColor(BaseColor.BLACK);
			productName.setPaddingLeft(10);
			productName.setHorizontalAlignment(Element.ALIGN_CENTER);
			productName.setVerticalAlignment(Element.ALIGN_CENTER);
			productName.setBackgroundColor(BaseColor.GRAY);
			productName.setExtraParagraphSpace(5f);
			table.addCell(productName);
			
			PdfPCell discription = new PdfPCell(new Paragraph("Discription",tableHeader));
			discription.setBorderColor(BaseColor.BLACK);
			discription.setPaddingLeft(10);
			discription.setHorizontalAlignment(Element.ALIGN_CENTER);
			discription.setVerticalAlignment(Element.ALIGN_CENTER);
			discription.setBackgroundColor(BaseColor.GRAY);
			discription.setExtraParagraphSpace(5f);
			table.addCell(discription);
			
			PdfPCell price = new PdfPCell(new Paragraph("Price",tableHeader));
			price.setBorderColor(BaseColor.BLACK);
			price.setPaddingLeft(10);
			price.setHorizontalAlignment(Element.ALIGN_CENTER);
			price.setVerticalAlignment(Element.ALIGN_CENTER);
			price.setBackgroundColor(BaseColor.GRAY);
			price.setExtraParagraphSpace(5f);
			table.addCell(price);
			
			PdfPCell createdDate = new PdfPCell(new Paragraph("CreatedDate",tableHeader));
			createdDate.setBorderColor(BaseColor.BLACK); createdDate.setPaddingLeft(10);
		    createdDate.setHorizontalAlignment(Element.ALIGN_CENTER);
		    createdDate.setVerticalAlignment(Element.ALIGN_CENTER);
	        createdDate.setBackgroundColor(BaseColor.GRAY);
	        createdDate.setExtraParagraphSpace(5f); 
	        table.addCell(createdDate);
			 
			
			for(Product product: products) {
				PdfPCell productIdValue = new PdfPCell(new Paragraph(product.getId(),tableBody));
				productIdValue.setBorderColor(BaseColor.BLACK);
				productIdValue.setPaddingLeft(10);
				productIdValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				productIdValue.setVerticalAlignment(Element.ALIGN_CENTER);
				productIdValue.setBackgroundColor(BaseColor.WHITE);
				productIdValue.setExtraParagraphSpace(5f);
				table.addCell(productIdValue);
				
				PdfPCell productNameValue = new PdfPCell(new Paragraph(product.getProductName(),tableBody));
				productNameValue.setBorderColor(BaseColor.BLACK);
				productNameValue.setPaddingLeft(10);
				productNameValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				productNameValue.setVerticalAlignment(Element.ALIGN_CENTER);
				productNameValue.setBackgroundColor(BaseColor.WHITE);
				productNameValue.setExtraParagraphSpace(5f);
				table.addCell(productNameValue);
				
				PdfPCell discriptionValue = new PdfPCell(new Paragraph(product.getDiscription(),tableBody));
				discriptionValue.setBorderColor(BaseColor.BLACK);
				discriptionValue.setPaddingLeft(10);
				discriptionValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				discriptionValue.setVerticalAlignment(Element.ALIGN_CENTER);
				discriptionValue.setBackgroundColor(BaseColor.WHITE);
				discriptionValue.setExtraParagraphSpace(5f);
				table.addCell(discriptionValue);
				
				PdfPCell priceValue = new PdfPCell(new Paragraph(product.toString().valueOf(product.getPrice()).toString(),tableBody));
				priceValue.setBorderColor(BaseColor.BLACK);
				priceValue.setPaddingLeft(10);
				priceValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				priceValue.setVerticalAlignment(Element.ALIGN_CENTER);
				priceValue.setBackgroundColor(BaseColor.WHITE);
				priceValue.setExtraParagraphSpace(5f);
				table.addCell(priceValue);
				
				 PdfPCell createdDateValue = new PdfPCell(new Paragraph(product.getCreatedDate().toString(),tableBody));
				 createdDateValue.setBorderColor(BaseColor.BLACK);
				 createdDateValue.setPaddingLeft(10);
				 createdDateValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				 createdDateValue.setVerticalAlignment(Element.ALIGN_CENTER);
				 createdDateValue.setBackgroundColor(BaseColor.WHITE);
				 createdDateValue.setExtraParagraphSpace(5f); 
				 table.addCell(createdDateValue);
				 
			}
			
			document.add(table);
			document.close();
			writer.close();
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	public boolean createPDF(Optional<Product> product, ServletContext context, HttpServletRequest request,HttpServletResponse response) {
			Document document = new Document(PageSize.A4, 15, 15, 45, 30);
			try {
				String filepath = context.getRealPath("/resources/reports");
				File file = new File(filepath);
				boolean exists = new File(filepath).exists();
				if(!exists) {
					new File(filepath).mkdirs();
				}
				
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file+"/"+"products"+".pdf"));
				document.open();
				
				Font mainFont = FontFactory.getFont("Arial", 10, BaseColor.BLACK);
				Paragraph paragraph = new Paragraph("Product Details", mainFont);
				paragraph.setAlignment(Element.ALIGN_CENTER);
				paragraph.setIndentationLeft(50);
				paragraph.setIndentationRight(50);
				paragraph.setSpacingAfter(10);
				document.add(paragraph);
				
				PdfPTable table = new PdfPTable(5);
				table.setWidthPercentage(100);
				table.setSpacingAfter(10f);
				table.setSpacingBefore(10);
				
				Font tableHeader = FontFactory.getFont("Arial", 10, BaseColor.BLACK);
	            Font tableBody = FontFactory.getFont("Arial", 9, BaseColor.BLACK);
		
				float[] columnWidths = {2f, 2f, 2f, 2f, 2f};
				table.setWidths(columnWidths);
				
				PdfPCell productId = new PdfPCell(new Paragraph("Product ID",tableHeader));
				productId.setBorderColor(BaseColor.BLACK);
				productId.setPaddingLeft(10);
				productId.setHorizontalAlignment(Element.ALIGN_CENTER);
				productId.setVerticalAlignment(Element.ALIGN_CENTER);
				productId.setBackgroundColor(BaseColor.GRAY);
				productId.setExtraParagraphSpace(5f);
				table.addCell(productId);
				
				PdfPCell productName = new PdfPCell(new Paragraph("Product Name",tableHeader));
				productName.setBorderColor(BaseColor.BLACK);
				productName.setPaddingLeft(10);
				productName.setHorizontalAlignment(Element.ALIGN_CENTER);
				productName.setVerticalAlignment(Element.ALIGN_CENTER);
				productName.setBackgroundColor(BaseColor.GRAY);
				productName.setExtraParagraphSpace(5f);
				table.addCell(productName);
				
				PdfPCell discription = new PdfPCell(new Paragraph("Discription",tableHeader));
				discription.setBorderColor(BaseColor.BLACK);
				discription.setPaddingLeft(10);
				discription.setHorizontalAlignment(Element.ALIGN_CENTER);
				discription.setVerticalAlignment(Element.ALIGN_CENTER);
				discription.setBackgroundColor(BaseColor.GRAY);
				discription.setExtraParagraphSpace(5f);
				table.addCell(discription);
				
				PdfPCell price = new PdfPCell(new Paragraph("Price",tableHeader));
				price.setBorderColor(BaseColor.BLACK);
				price.setPaddingLeft(10);
				price.setHorizontalAlignment(Element.ALIGN_CENTER);
				price.setVerticalAlignment(Element.ALIGN_CENTER);
				price.setBackgroundColor(BaseColor.GRAY);
				price.setExtraParagraphSpace(5f);
				table.addCell(price);
				
				PdfPCell createdDate = new PdfPCell(new Paragraph("CreatedDate",tableHeader));
				createdDate.setBorderColor(BaseColor.BLACK); createdDate.setPaddingLeft(10);
			    createdDate.setHorizontalAlignment(Element.ALIGN_CENTER);
			    createdDate.setVerticalAlignment(Element.ALIGN_CENTER);
		        createdDate.setBackgroundColor(BaseColor.GRAY);
		        createdDate.setExtraParagraphSpace(5f); 
		        table.addCell(createdDate);
				
				//for Product product:products
					PdfPCell productIdValue = new PdfPCell(new Paragraph(product.get().getId(),tableBody));
					productIdValue.setBorderColor(BaseColor.BLACK);
					productIdValue.setPaddingLeft(10);
					productIdValue.setHorizontalAlignment(Element.ALIGN_CENTER);
					productIdValue.setVerticalAlignment(Element.ALIGN_CENTER);
					productIdValue.setBackgroundColor(BaseColor.WHITE);
					productIdValue.setExtraParagraphSpace(5f);
					table.addCell(productIdValue);
					
					PdfPCell productNameValue = new PdfPCell(new Paragraph(product.get().getProductName(),tableBody));
					productNameValue.setBorderColor(BaseColor.BLACK);
					productNameValue.setPaddingLeft(10);
					productNameValue.setHorizontalAlignment(Element.ALIGN_CENTER);
					productNameValue.setVerticalAlignment(Element.ALIGN_CENTER);
					productNameValue.setBackgroundColor(BaseColor.WHITE);
					productNameValue.setExtraParagraphSpace(5f);
					table.addCell(productNameValue);
					
					PdfPCell discriptionValue = new PdfPCell(new Paragraph(product.get().getDiscription(),tableBody));
					discriptionValue.setBorderColor(BaseColor.BLACK);
					discriptionValue.setPaddingLeft(10);
					discriptionValue.setHorizontalAlignment(Element.ALIGN_CENTER);
					discriptionValue.setVerticalAlignment(Element.ALIGN_CENTER);
					discriptionValue.setBackgroundColor(BaseColor.WHITE);
					discriptionValue.setExtraParagraphSpace(5f);
					table.addCell(discriptionValue);
					
					PdfPCell priceValue = new PdfPCell(new Paragraph(product.toString().valueOf(product.get().getPrice()),tableBody));
					priceValue.setBorderColor(BaseColor.BLACK);
					priceValue.setPaddingLeft(10);
					priceValue.setHorizontalAlignment(Element.ALIGN_CENTER);
					priceValue.setVerticalAlignment(Element.ALIGN_CENTER);
					priceValue.setBackgroundColor(BaseColor.WHITE);
					priceValue.setExtraParagraphSpace(5f);
					table.addCell(priceValue);
					
					  PdfPCell createdDateValue = new PdfPCell(new Paragraph(product.get().getCreatedDate().toString(),tableBody));
					  createdDateValue.setBorderColor(BaseColor.BLACK);
					  createdDateValue.setPaddingLeft(10);
					  createdDateValue.setHorizontalAlignment(Element.ALIGN_CENTER);
					  createdDateValue.setVerticalAlignment(Element.ALIGN_CENTER);
					  createdDateValue.setBackgroundColor(BaseColor.WHITE);
					  createdDateValue.setExtraParagraphSpace(5f); 
					  table.addCell(createdDateValue);
			
				
				document.add(table);
				document.close();
				writer.close();
				return true;
			}catch(Exception e) {
				return false;
			}
		}

	public boolean createExcel(List<Product> products, ServletContext context, HttpServletRequest request,HttpServletResponse response) {
          String filepath = context.getRealPath("/resources/reports");
          File file = new File(filepath);
         boolean exists = new File(filepath).exists();
         if(!exists) {
        	 new File(filepath).mkdirs();
         }
         try {
			FileOutputStream fos = new FileOutputStream(file+"/"+"products"+".xls");
			HSSFWorkbook workBook = new HSSFWorkbook();
			HSSFSheet workSheet = workBook.createSheet("products");
			workSheet.setDefaultColumnWidth(30);
			
			HSSFCellStyle headerCellStyle = workBook.createCellStyle();
			headerCellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
			headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			
			HSSFRow headerRow = workSheet.createRow(0);
			
			HSSFCell productId = headerRow.createCell(0);
			productId.setCellValue("Product Id");
			productId.setCellStyle(headerCellStyle);
			
			HSSFCell productName = headerRow.createCell(1);
			productName.setCellValue("Product Name");
			productName.setCellStyle(headerCellStyle);
			
			HSSFCell discription = headerRow.createCell(2);
			discription.setCellValue("Discription");
			discription.setCellStyle(headerCellStyle);
			
			HSSFCell price = headerRow.createCell(3);
			price.setCellValue("Price");
			price.setCellStyle(headerCellStyle);
			
			HSSFCell createdDate = headerRow.createCell(4);
			createdDate.setCellValue("CreatedDate");
			createdDate.setCellStyle(headerCellStyle);
			
			int i =1;
			for(Product product:products) {
				HSSFRow bodyRow = workSheet.createRow(i);
				HSSFCellStyle bodyCellStyle = workBook.createCellStyle();
				bodyCellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
				
				HSSFCell firstNameValue = bodyRow.createCell(0);
				firstNameValue.setCellValue(product.getId());
				firstNameValue.setCellStyle(bodyCellStyle);
				
				HSSFCell productNameValue = bodyRow.createCell(1);
				productNameValue.setCellValue(product.getProductName());
				productNameValue.setCellStyle(bodyCellStyle);
				
				HSSFCell discriptionValue = bodyRow.createCell(2);
				discriptionValue.setCellValue(product.getDiscription());
				discriptionValue.setCellStyle(bodyCellStyle);
				
				HSSFCell priceValue = bodyRow.createCell(3);
				priceValue.setCellValue(product.toString().valueOf(product.getPrice()));
				priceValue.setCellStyle(bodyCellStyle);
				
				HSSFCell createdDateValue = bodyRow.createCell(4);
				createdDateValue.setCellValue(product.getCreatedDate().toString());
				createdDateValue.setCellStyle(bodyCellStyle);
				i++;
				}
			workBook.write(fos);
			fos.flush();
			fos.close();
			return true;
		} catch (Exception e) {
            return false;
      }
   }

	public boolean createExcel(Optional<Product> product, ServletContext context, HttpServletRequest request,HttpServletResponse response) {
		String filepath = context.getRealPath("/resources/reports");
        File file = new File(filepath);
       boolean exists = new File(filepath).exists();
       if(!exists) {
      	 new File(filepath).mkdirs();
       }
       try {
			FileOutputStream fos = new FileOutputStream(file+"/"+"products"+".xls");
			HSSFWorkbook workBook = new HSSFWorkbook();
			HSSFSheet workSheet = workBook.createSheet("products");
			workSheet.setDefaultColumnWidth(30);
			
			HSSFCellStyle headerCellStyle = workBook.createCellStyle();
			headerCellStyle.setFillForegroundColor(HSSFColor.RED.index);
			headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			
			HSSFRow headerRow = workSheet.createRow(0);
			
			HSSFCell productId = headerRow.createCell(0);
			productId.setCellValue("Product Id");
			productId.setCellStyle(headerCellStyle);
			
			HSSFCell productName = headerRow.createCell(1);
			productName.setCellValue("Product Name");
			productName.setCellStyle(headerCellStyle);
			
			HSSFCell discription = headerRow.createCell(2);
			discription.setCellValue("Discription");
			discription.setCellStyle(headerCellStyle);
			
			HSSFCell price = headerRow.createCell(3);
			price.setCellValue("Price");
			price.setCellStyle(headerCellStyle);
			
			HSSFCell createdDate = headerRow.createCell(4);
			createdDate.setCellValue("CreatedDate");
			createdDate.setCellStyle(headerCellStyle);
		
		    int i =1;
			//for(Product product:products) {
				HSSFRow bodyRow = workSheet.createRow(i);
				HSSFCellStyle bodyCellStyle = workBook.createCellStyle();
				bodyCellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
				
				HSSFCell firstNameValue = bodyRow.createCell(0);
				firstNameValue.setCellValue(product.get().getId());
				firstNameValue.setCellStyle(bodyCellStyle);
				
				HSSFCell productNameValue = bodyRow.createCell(1);
				productNameValue.setCellValue(product.get().getProductName());
				productNameValue.setCellStyle(bodyCellStyle);
				
				HSSFCell discriptionValue = bodyRow.createCell(2);
				discriptionValue.setCellValue(product.get().getDiscription());
				discriptionValue.setCellStyle(bodyCellStyle);
				
				HSSFCell priceValue = bodyRow.createCell(3);
				priceValue.setCellValue(product.toString().valueOf(product.get().getPrice()));
				priceValue.setCellStyle(bodyCellStyle);
				
				HSSFCell createdDateValue = bodyRow.createCell(4);
				createdDateValue.setCellValue(product.get().getCreatedDate().toString());
				createdDateValue.setCellStyle(bodyCellStyle);
				//i++;
				//}
			workBook.write(fos);
			fos.flush();
			fos.close();
			return true;
		} catch (Exception e) {
            return false;
      }
	}
}
	
