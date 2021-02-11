package com.shivu.storageService;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.shivu.controller.EndUserController;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;

@Service
public class FileSystemStorageService implements StorageService {
	
    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.rootLocation);
        } catch (Exception ex) {
            throw new StorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Autowired
    private EndUserController enduserService;
    
    @Override
    public String store(MultipartFile file)
    {        
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try
        {
            if (file.isEmpty())
            {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            Path targetLocation = this.rootLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            
            return fileName;
        }
        catch (IOException e)
        {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }
    
    @Override
    public void init()
    {
        try
        {
            Files.createDirectory(rootLocation);
        }
        catch (IOException e)
        {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    public Object liveRecording() {
    	
    final String Path = "src\\main\\resources\\video\\saved.mp4";
        File file = new File(Path);
       
		IMediaWriter writer = ToolFactory.makeWriter(Path);
		Dimension size = WebcamResolution.VGA.getSize();

		writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, size.width, size.height);
		Webcam webcam = Webcam.getDefault();
		webcam.setViewSize(size);
		webcam.open(true);

        System.out.println("Start capturing...");
		long start = System.currentTimeMillis();

		for (int i = 0; i < 500; i++) {

			System.out.println("Capture frame " + i);

			BufferedImage image = ConverterFactory.convertToType(webcam.getImage(), BufferedImage.TYPE_3BYTE_BGR);
			IConverter converter = ConverterFactory.createConverter(image, IPixelFormat.Type.YUV420P);

			IVideoPicture frame = converter.toPicture(image, (System.currentTimeMillis() - start) * 1000);
			frame.setKeyFrame(i == 0);
			frame.setQuality(100);
			writer.encodeVideo(0, frame);
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		writer.close();
		webcam.close();
		System.out.println("Video recorded in file: " + file.getAbsolutePath());
		
		return null;
		}

	public Object liveRecording1() {
		File file = new File("live.mp4");
		IMediaWriter writer = ToolFactory.makeWriter(file.getName());
		Dimension size = WebcamResolution.VGA.getSize();

		writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, size.width, size.height);
		Webcam webcam = Webcam.getDefault();
		webcam.setViewSize(size);
		webcam.open(true);
		long start = System.currentTimeMillis();

		for (int i = 0; i < 500; i++) {

			System.out.println("Capture frame " + i);

			BufferedImage image = ConverterFactory.convertToType(webcam.getImage(), BufferedImage.TYPE_3BYTE_BGR);
			IConverter converter = ConverterFactory.createConverter(image, IPixelFormat.Type.YUV420P);

			IVideoPicture frame = converter.toPicture(image, (System.currentTimeMillis() - start) * 1000);
			frame.setKeyFrame(i == 0);
			frame.setQuality(100);
			writer.encodeVideo(0, frame);
			enduserService.liveStreaming1(file);
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			//videoStreamService.liveStreaming1(file);
		}
		writer.close();
		webcam.close();
		//System.out.println("Video recorded in file: " + file.getAbsolutePath());
		
		return file;
		
	}
}
/*
 * int addAudioStream(int inputIndex, int streamId, ICodec codec, int
 * channelCount, int sampleRate)
 */