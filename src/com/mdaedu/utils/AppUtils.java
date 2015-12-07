package com.mdaedu.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;

public class AppUtils {
	
	private static String uploadFile;
	static {
		Properties p=new Properties();
		try {
			p.load(AppUtils.class.getClassLoader().getResourceAsStream("/app.properties"));
			uploadFile=p.getProperty("uploadFile");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void saveFile(InputStream uploadedInputStream,
			File serverLocation) {
		
		try {
			OutputStream outpuStream = new FileOutputStream(serverLocation);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = uploadedInputStream.read(bytes)) != -1) {
				outpuStream.write(bytes, 0, read);
			}
			outpuStream.flush();
			outpuStream.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	public static File getVideoFolder(){
		URL root=AppUtils.class.getClassLoader().getResource("/");
		System.out.println("uploadFile"+uploadFile);
		File videoFolder=new File(root.getFile(),"vedioes");
		if(!videoFolder.exists()){
			videoFolder.mkdirs();
		}
		return videoFolder;
	}
	
	public static File getClientFolder(){
		File pfile=new File(uploadFile);
		File videoFolder=new File(pfile,"clientes");
		if(!videoFolder.exists()){
			videoFolder.mkdirs();
		}
		return videoFolder;
	}
	public static File getImageFolder(){
		File pfile=new File(uploadFile);
		File videoFolder=new File(pfile,"images");
		if(!videoFolder.exists()){
			videoFolder.mkdirs();
		}
		return videoFolder;
	}
	
	public static File getCourseImageFolder(){
		File pfile=new File(uploadFile);
		File videoFolder=new File(pfile,"courses/images");
		if(!videoFolder.exists()){
			videoFolder.mkdirs();
		}
		return videoFolder;
	}
}
