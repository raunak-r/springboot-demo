package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.AlienRepo;
import com.example.demo.model.Alien;

@Controller
public class AlienController {
	@Autowired
	AlienRepo repo;
	
	@RequestMapping("/")
	public String home() {
		System.out.println("Inside Alien Controller...");
		return "home";
	}
	
	@RequestMapping("/addAlien")
	public String addAlien(Alien alien) {
		repo.save(alien);
		return "home";
	}
	
//	localhost:8080/getAlien/?aid=1
	@RequestMapping("/getAlien")
	public ModelAndView getAlien(@RequestParam int aid) {
		ModelAndView mv = new ModelAndView("showAlien");
		Alien alien = repo.findById(aid).orElse(new Alien());
		
		System.out.println(repo.findByTech("java"));
		System.out.println(repo.findByAidGreaterThan(3));
		System.out.println(repo.findByTechSorted("java"));
		
		mv.addObject(alien);
		return mv;
	}
	
//	localhost:8080/getAlien/1
	@RequestMapping("/getAlien/{aid}")
	@ResponseBody
	public String getAliens(@PathVariable("aid") int aid){
		return repo.findById(aid).toString();
	}
	
//	localhost:8080/aliens
	@RequestMapping("/aliens")
	@ResponseBody
	public String getAliens(){
		return repo.findAll().toString();
	}
	
//	localhost:8080/aliens
	@RequestMapping("/aliens/list")
	@ResponseBody
	public List<Alien> getAlienList(){
		return (List<Alien>) repo.findAll();
	}	
}
