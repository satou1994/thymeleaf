package com.example.spring;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring.repositories.MyDataRepository;

@Controller
public class YoutubeController {

	@Autowired
	MyDataRepository repository;

	//index
	@RequestMapping(value="/",method=RequestMethod.GET)
	public ModelAndView index(@ModelAttribute("formModel") MyData mydata, ModelAndView mav) {
		mav.setViewName("index");
		mav.addObject("msg","this is sample content.");
		mav.addObject("formModel",mydata);
		Iterable<MyData> list = repository.findAll();
		mav.addObject("datalist",list);
		return mav;
	}

	@RequestMapping(value="/",method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView form(@ModelAttribute("formModel") @Validated MyData mydata,BindingResult result,ModelAndView mav) {
		ModelAndView res =null;
		if(!result.hasErrors()) {
			repository.saveAndFlush(mydata);
			res=new ModelAndView("redirect:/");
		}else {
			mav.setViewName("/index");
			mav.addObject("msg","sorry, error is occured...");
			Iterable<MyData> list = repository.findAll();
			mav.addObject("datalist",list);
			res=mav;
		}
			return res;
	}

//udate
	@RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
	public ModelAndView edit(@ModelAttribute MyData mydata,@PathVariable int id,ModelAndView mav) {
		mav.setViewName("edit");
		mav.addObject("title","edit mydata");
		Optional<MyData> data = repository.findById((long)id);
		mav.addObject("formModel",data.get());
		return mav;
	}

	@RequestMapping(value="/edit" , method=RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView update(@ModelAttribute MyData mydata,ModelAndView mav) {
		repository.saveAndFlush(mydata);
		return new ModelAndView("redirect:/");
	}

//	delete
	@RequestMapping(value="/delete/{id}",method=RequestMethod.GET)
	public ModelAndView delete(@PathVariable int id,ModelAndView mav) {
		mav.setViewName("delete");
		mav.addObject("title","delete mydata");
		Optional<MyData> data = repository.findById((long)id);
		mav.addObject("formModel",data.get());
		return mav;
	}

	@RequestMapping(value="/delete" , method=RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView remove(@RequestParam long id,ModelAndView mav) {
		repository.deleteById(id);
		return new ModelAndView("redirect:/");
	}

	//create defoult data
	@PostConstruct
	public void init() {
		//data1
//		MyData d1=new MyData();
//		d1.setName("sato");
//		d1.setAge(123);
//		d1.setMail("example@sample.jp");
//		d1.setMemo("this is my data!");
//		repository.saveAndFlush(d1);
//		//data2
//		MyData d2=new MyData();
//		d2.setName("sato");
//		d2.setAge(123);
//		d2.setMail("example@sample.jp");
//		d2.setMemo("this is my data!");
//		repository.saveAndFlush(d2);
//		//data3
//		MyData d3=new MyData();
//		d3.setName("sato");
//		d3.setAge(123);
//		d3.setMail("example@sample.jp");
//		d3.setMemo("this is my data!");
//		repository.saveAndFlush(d3);
	}

}
