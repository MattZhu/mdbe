package com.mdaedu.ws;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.springframework.beans.factory.annotation.Autowired;

import com.mdaedu.domainobject.Course;
import com.mdaedu.domainobject.Grade;
import com.mdaedu.domainobject.Publisher;
import com.mdaedu.domainobject.Questions;
import com.mdaedu.domainobject.Subject;
import com.mdaedu.services.CourseService;
import com.mdaedu.services.GradeService;
import com.mdaedu.services.SubjectService;
import com.mdaedu.utils.AppUtils;
import com.mdaedu.ws.message.CourseResponse;

@RequestScoped
@Path("/courses")
public class CourseResource {
	@Autowired
	private CourseService service;

	@Autowired
	private GradeService gradeService;

	@Autowired
	private SubjectService subjectService;

	public CourseResource() {

	}

	@GET
	@Produces({ "application/json;charset=UTF-8" })
	public MyResponse<Course> getCourese(@Context UriInfo info) {
		String subjectId = info.getQueryParameters().getFirst("subId");
		String to = info.getQueryParameters().getFirst("to");
		String page = info.getQueryParameters().getFirst("page");
		String rows = info.getQueryParameters().getFirst("rows");
		MyResponse<Course> result = new MyResponse<Course>();
		result.setPage(Integer.valueOf(page));
		result.setTotal(2);
		result.setRecords(1);
		result.setRows(this.service.getCourse(Integer.valueOf(subjectId)));
		return result;
	}

	@GET
	@Path("/{id}")
	@Produces({ "application/json;charset=UTF-8" })
	public MyResponse<Course> getCoureseBySubject(
			@PathParam("id") Integer subjectId,
			@QueryParam("page") Integer pager,
			@QueryParam("rows") Integer rows, @QueryParam("sidx") Integer sidx,
			@QueryParam("sord") Integer sord) {

		System.out.println("subject id:" + subjectId);
		MyResponse<Course> result = new MyResponse<Course>();
		result.setRows(this.service.getCourse(subjectId));
		return result;
	}

	// @GET
	// @Path("/{id}/clients")
	// @Produces({ "application/json;charset=UTF-8" })
	// public CourseResponse getCoureseBySubjectForMobile(@PathParam("id")
	// Integer subjectId,
	// @QueryParam("page")Integer pager, @QueryParam("rows")Integer rows,
	// @QueryParam("sidx")Integer sidx, @QueryParam("sord")Integer sord
	// )
	// {
	//
	// System.out.println("subject id:"+subjectId);
	// CourseResponse result=new CourseResponse();
	// result.setCourses(this.service.getCourse(subjectId).toArray(new
	// Course[0]));
	// return result;
	// }

	@GET
	@Path("/clients")
	@Produces({ "application/json;charset=UTF-8" })
	public CourseResponse getCoureseBySubjectForMobile() {
		List<Grade> grades = gradeService.getAll();
		List<Subject> subjects = subjectService.getAll();

		CourseResponse result = new CourseResponse();
		result.setGrades(grades.toArray(new Grade[0]));
		result.setSubjects(subjects.toArray(new Subject[0]));
		List<com.mdaedu.ws.message.Course> courses = new ArrayList<com.mdaedu.ws.message.Course>();
		for (Subject s : subjects) {
			for (Course cs : service.getCourse(s.getId())) {
				com.mdaedu.ws.message.Course c = new com.mdaedu.ws.message.Course();
				c.setGradeId(cs.getGrade().getId());
				c.setName(cs.getName());
				c.setSubjectId(cs.getSubject().getId());
				c.setId(cs.getId());
				if(cs.getPublisher()!=null){
					c.setPublisher(cs.getPublisher().getName());
				}
				c.setImagePath(cs.getImagePath());
				courses.add(c);
			}
		}
		result.setCourses(courses.toArray(new com.mdaedu.ws.message.Course[0]));
		return result;
	}

	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces({ "application/json;charset=UTF-8" })
	public Course saveOrUpdate(@FormParam("oper") String oper,
			@FormParam("id") String id,
			@FormParam("grade.name") Integer gradeId,
			@FormParam("subject.name") Integer subjectId,@FormParam("publisher.name") Integer publisherId) {
		System.out.println("id:" + id + ",gradeId:" + gradeId + ",subjectId:"
				+ subjectId + "");

		Course course = new Course();
		if (oper.equals("edit")) {
			Grade grade = new Grade();
			grade.setId(gradeId);
			course.setGrade(grade);
			// course.setName(name);
			Subject subject = new Subject();
			subject.setId(subjectId);
			course.setSubject(subject);
			course.setId(Integer.valueOf(id));
			
			Publisher p=new Publisher();
			p.setId(publisherId);
			course.setPublisher(p);
			service.saveOrUpdate(course);
		} else if (oper.equals("add")) {
			Grade grade = new Grade();
			grade.setId(gradeId);
			course.setGrade(grade);
			// course.setName(name);
			Subject subject = new Subject();
			subject.setId(subjectId);
			course.setSubject(subject);

			Publisher p=new Publisher();
			p.setId(publisherId);
			course.setPublisher(p);
			service.saveOrUpdate(course);
		} else if (oper.equals("del")) {
			course.setId(Integer.valueOf(id));
			service.delete(course);
		}

		return course;
	}
	@POST
	@Path("/image")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadImage(
			@FormDataParam("cId")Integer cId,
			@FormDataParam("courseImageFile") InputStream fileInputStream,
			@FormDataParam("courseImageFile") FormDataContentDisposition contentDispositionHeader) {
		File folder=AppUtils.getCourseImageFolder();
		String output = "File saved to server location : " ;
		Course q=service.getCourseById(cId);
		String savedName= q.getId()+contentDispositionHeader.getFileName().substring(contentDispositionHeader.getFileName().lastIndexOf("."));;
		q.setImagePath(savedName);
		service.saveOrUpdate(q);
		File filePath = new File(folder,savedName);
		AppUtils.saveFile(fileInputStream, filePath);
		return Response.status(200).entity(output).build();
	}
}
