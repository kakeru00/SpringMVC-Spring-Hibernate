package com.zh.controller;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.zh.entity.User;
import com.zh.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
	@Resource
	private UserService userService;

	@RequestMapping(value = {"/users","/"}, method = RequestMethod.GET)
	public String find(Model model) {
		userService.find();
		return "user/users";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {

		model.addAttribute(new User());
		return "user/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(User user) {
		
		userService.save(user);
		return "redirect:/user/users";
	}

	@RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
	public String update(Model model, @PathVariable int id) {
		model.addAttribute(userService.get(id));
		return "user/update";
	}

	@RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
	public String update(User user, @PathVariable int id) {
		user.setId(id);
		userService.update(user);
		return "redirect:/user/users";
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public String delete(@PathVariable int id) {
		User user = userService.get(id);
		if(user!=null)
			userService.delete(user);;
		return "redirect:/user/users";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
		String realpath = request.getSession().getServletContext().getRealPath("/upload");
		File f = new File(realpath + "/" + file.getOriginalFilename());
		System.out.println(f.getPath());
		FileUtils.copyInputStreamToFile(file.getInputStream(), f);

		return "redirect:/user/users";
	}

	@RequestMapping("/download")
	public ResponseEntity<byte[]> download(String fileName, HttpServletRequest request) throws IOException {
		String path = request.getSession().getServletContext().getRealPath("/upload");
		File file = new File(path+"/"+fileName);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		
		System.out.println(file.getName());
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
	}
	

}
