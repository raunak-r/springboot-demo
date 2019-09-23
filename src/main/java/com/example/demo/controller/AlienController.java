package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.AlienRepo;
import com.example.demo.model.Alien;

@RestController
public class AlienController {
	@Autowired
	AlienRepo repo;
	
	@RequestMapping("/")
	public String home() {
		System.out.println("Inside Alien Controller...");
		return "home";
	}	
	
	
	//***************GET One Object at a time
//	localhost:8080/getAlien/?aid=1  || as an HTML
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
	
//	localhost:8080/getAlien/1  || as an object/text
	@RequestMapping("/getAlien/{aid}")
	@ResponseBody
	public String getAliens(@PathVariable("aid") int aid){
		return repo.findById(aid).toString();
	}
	
//	localhost:8080/getAlien/optional/1  || in JSON format.
	@RequestMapping("/getAlien/optional/{aid}")
	@ResponseBody
	public Optional<Alien> getOptionalAliens(@PathVariable("aid") int aid){
		return repo.findById(aid);
	}
	//***************
	
	
	//***************GET List of Objects
//	localhost:8080/aliens  || Text List
	@RequestMapping("/aliens")
	@ResponseBody
	public String getAliens(){
		return repo.findAll().toString();
	}
	
//	localhost:8080/aliens/list  || JSON Format
	@RequestMapping("/aliens/list")
	@ResponseBody
	public List<Alien> getAlienList(){
		return (List<Alien>) repo.findAll();
	}
	//***************
	
	
	//***************POST new alien
//	from the form
	@RequestMapping("/addAlien")
	public String addAlien(Alien alien) {
		repo.save(alien);
		return "home";
	}
	
	@PostMapping("/alien")
//	from the postman, raw or form data
	public Alien addAlienPost(@RequestBody Alien alien) {
		repo.save(alien);
		return alien;
	}
	//***************
	
	
	//***************DELETE Alien
//	localhost:8080/alien/6
	@DeleteMapping("/alien/{aid}")
	public String deleteAlien(@PathVariable int aid) {
		Alien a = repo.getOne(aid);
		repo.delete(a);
		
		return "Succesfully Deleted";
	}
	
	//***************PUT Alien
//	localhost:8080/alien/6  || AID should match
	@PutMapping("/alien")
	public Alien saveOrUpdateAlien(@RequestBody Alien alien) {
		repo.save(alien);
		return alien;
	}
}
