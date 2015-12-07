package com.mdaedu.ws;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;

import com.mdaedu.domainobject.Charpater;
import com.mdaedu.domainobject.Questions;
import com.mdaedu.domainobject.Video;
import com.mdaedu.services.ChapterService;
import com.mdaedu.services.VideoService;
import com.mdaedu.utils.AppUtils;

@Path("/videos")
public class VideoUploadResource {

	@Autowired
	private VideoService service;

	@Autowired
	private ChapterService chservice;

	/**
	 * Upload a File
	 */

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@FormDataParam("chid") Integer chid,
			@FormDataParam("vid") Integer vid,
			@FormDataParam("videoName") String videoName,
			@FormDataParam("vedioFile") InputStream fileInputStream,
			@FormDataParam("vedioFile") FormDataContentDisposition contentDispositionHeader) {

		Video v = new Video();
		if (vid != null) {
			v.setId(vid);
			v=service.getVideo(v);
		}
		if (contentDispositionHeader != null
				&& contentDispositionHeader.getFileName() != null&&!contentDispositionHeader.getFileName().isEmpty()) {
			v.setName(contentDispositionHeader.getFileName());
		}
		v.setSavedName(videoName);
		Charpater ch = chservice.getById(chid);
		v.setCharpater(ch);
		service.saveOrUpdate(v);
		String output = "video updated";
		if (null != fileInputStream && contentDispositionHeader != null
				&& contentDispositionHeader.getFileName() != null&&!contentDispositionHeader.getFileName().isEmpty()) {
			String savedName = v.getId()
					+ contentDispositionHeader.getFileName().substring(
							contentDispositionHeader.getFileName().lastIndexOf(
									"."));
			File folder = AppUtils.getVideoFolder();
			File filePath = new File(folder, savedName);

			// save the file to the server
			AppUtils.saveFile(fileInputStream, filePath);
			output = "File saved to server location : " + filePath;

		}

		return Response.status(200).entity(output).build();

	}

	@GET
	@Produces({ "application/json;charset=UTF-8" })
	public MyResponse<Video> getCourese(@Context UriInfo info) {
		String courseId = info.getQueryParameters().getFirst("chId");
		String to = info.getQueryParameters().getFirst("to");
		String page = info.getQueryParameters().getFirst("page");
		String rows = info.getQueryParameters().getFirst("rows");
		MyResponse<Video> result = new MyResponse<Video>();
		result.setPage(Integer.valueOf(page));
		result.setTotal(2);
		result.setRecords(1);
		result.setRows(this.service.getVideoByCh(Integer.valueOf(courseId)));

		return result;
	}

	// save uploaded file to a defined location on the server
	@GET
	@Path("/del")
	@Produces({ "application/json;charset=UTF-8" })
	public Video delete(@QueryParam("vid") Integer vid) {

		Video v = new Video();
		v.setId(vid);
		service.deleteVideo(v);
		return v;
	}
}
