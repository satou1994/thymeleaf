package com.example.spring.account;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccountController {

	@Autowired
	AccountRepository repository;

	//Account
	@RequestMapping(value="/account",method=RequestMethod.GET)
	public ModelAndView account(@AuthenticationPrincipal User user, ModelAndView mav) {//@AuthenticationPrincipal でセッションユーザーを取得
		mav.setViewName("account");
		mav.addObject("msg","this is sample content.");
		mav.addObject("account",repository.findByUsername(user.getUsername()));
		return mav;
	}

	//Account All
	@RequestMapping(value="/accountAll",method=RequestMethod.GET)
	public ModelAndView accountAll(@ModelAttribute("formModel")Account account, ModelAndView mav) {
		mav.setViewName("accountAll");
		mav.addObject("formModel",account);
		Iterable<Account> list = repository.findAll();
		mav.addObject("datalist",list);
		return mav;
	}

	//Create Account
	@RequestMapping(value="/accountCreate",method=RequestMethod.GET)
	public ModelAndView createAccount(@ModelAttribute("formModel")Account account, ModelAndView mav) {
		mav.setViewName("accountAll");
		return mav;
	}
	@RequestMapping(value="/accountCreate",method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView createAccount(@ModelAttribute("formModel") @Validated ValidateAccount validateAccount,BindingResult result,ModelAndView mav) {
		ModelAndView res =null;
		if(!result.hasErrors()) {
			//保存する前にパスワードをハッシュ化,バリデーションクラスから保存用Accountクラスにデータ移動
			Account account=new Account();
			PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
			account.setPassword(encoder.encode(validateAccount.getPassword()));
			account.setAccount(validateAccount);
			repository.saveAndFlush(account);//保存
			res=new ModelAndView("redirect:/");
		}else {
			mav.setViewName("accountCreate");
			mav.addObject("msg","sorry, error is occured...");
			Iterable<Account> list = repository.findAll();
			mav.addObject("datalist",list);
			res=mav;
		}
			return res;
	}

	//Update Account
	@RequestMapping(value="/accountUpdate",method=RequestMethod.GET)
	public ModelAndView accountUpdate(@AuthenticationPrincipal User user, ModelAndView mav) {
		mav.setViewName("accountUpdate");
		mav.addObject("account",repository.findByUsername(user.getUsername()));
		return mav;
	}
	@RequestMapping(value="/accountUpdate",method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView accountUpdate(@ModelAttribute("formModel") @Validated ValidateAccount validateAccount,BindingResult result,ModelAndView mav) {
		ModelAndView res =null;
		if(!result.hasErrors()) {
			Account account=new Account();
			account.setAccount(validateAccount);
			repository.saveAndFlush(account);//保存
			res=new ModelAndView("redirect:/account");
		}else {
			mav.setViewName("accountUpdate");
			mav.addObject("username",validateAccount.getUsername());
			res=mav;
		}
			return res;
	}

	//adminユーザー作成
	@PostConstruct
	public void init() {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		Account admin=new Account();
		admin.setUsername("admin");
		admin.setPassword(encoder.encode("password"));
		admin.setEmail("admin@example.jp");
		admin.setProfile("this is sample profile. from Akita. My favorite is programing.");
		admin.setRole("ROLE_ADMIN");
		repository.saveAndFlush(admin);
	}
}
