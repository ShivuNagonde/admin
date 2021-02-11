package com.shivu.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Product {

	@Id
	private String id;
	private String productName;
	private String discription;
	private float price;
	private String videoName;
	@Lob
	private String image;
	@Lob
	private byte[] qrcodeImage;
	@Lob
	private byte[] barcodeImage;
	private Date createdDate=new Date();

}
