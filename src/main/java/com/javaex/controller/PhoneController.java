package com.javaex.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.dao.PhoneDao;
import com.javaex.vo.PersonVo;

@Controller  //첫자 대문자 주의
@RequestMapping(value="/phone")
public class PhoneController {

	// 필드
	// 생성자 - 디폴트 사용
	// 메소드 g/s
	// 메소드 일반

	/** 메소드 일반 **/
	// 메소드 단위로 기능을 정의한다. - 기존의 doGet()은 메소드 1개에 파라미터로 구분 action이 필요없음.
	// 메소드마다 기능 1개씩 --> 기능마다 url을 부여한다.   '주소'가 반드시 필요
	
	// 리스트
	@RequestMapping(value ="/list", method = {RequestMethod.GET, RequestMethod.POST})
	public String list(Model model) {                                                                  //model 내가 지어준 이름. Model: import까지, 만들어 달라고 요청해야 한다. Model model = new Model(); 하면 안된다.
		System.out.println("list");
		
		PhoneDao phoneDao = new PhoneDao();
		List<PersonVo> personList = phoneDao.getPersonList();
		System.out.println(personList.toString());
		
		// model --> data를 보내는 방법 -> 담아 놓으면 된다.
		model.addAttribute("pList", personList);   //setAttribute 비슷하게 담는다. ("만들어 준 이름",파일명)
		
		return "/WEB-INF/views/list.jsp";
	}
	
	// 등록폼
	@RequestMapping(value ="/writeForm", method = {RequestMethod.GET, RequestMethod.POST})
	public String writeForm() {
		System.out.println("writeForm");
		
		
		return "/WEB-INF/views/writeForm.jsp";
	}
	//http://localhost:8088/phonebook3/phone/write?name=김경아&hp=010-7777-7777&company=02-8888-8888&action=insert
	// 등록
	@RequestMapping(value="write", method= {RequestMethod.GET, RequestMethod.POST})
	public String write(@RequestParam("name") String name,
						@RequestParam("hp") String hp,
						@RequestParam("company") String company) {
		
		System.out.println("write");
		
		System.out.println(name + "," + hp + "," + company);
		
		PersonVo personVo = new PersonVo(name, hp, company);
		System.out.println(personVo.toString());
		
		PhoneDao phoneDao = new PhoneDao();
		phoneDao.personInsert(personVo);
		
		return "redirect:/phone/list";
	}

	// 수정폼 --> modifyForm
	@RequestMapping(value="/modifyForm", method= {RequestMethod.GET,RequestMethod.POST})
	public String modifyForm(Model model, @RequestParam("id") int personId) {
		System.out.println("modifyForm");
		
		PhoneDao phoneDao = new PhoneDao();
		PersonVo personVo = phoneDao.getPerson(personId);
		
		//model에 담아주기
		model.addAttribute("pvo", personVo);
		
		return "/WEB-INF/views/modifyForm.jsp";
	}
	
	// 수정
	@RequestMapping(value="/modify", method= {RequestMethod.GET, RequestMethod.POST})
	public String modify(@RequestParam("id") int personId, @RequestParam("name") String name, @RequestParam("hp") String hp, @RequestParam("company") String company) {
		System.out.println("수정");
		
		PersonVo personVo = new PersonVo(personId, name, hp, company);
			
		/* modify */
		PhoneDao phoneDao = new PhoneDao();
		phoneDao.personUpdate(personVo);
		
		return "redirect:/phone/list";
	}
	
	// 삭제
	@RequestMapping(value="/delete", method= {RequestMethod.GET, RequestMethod.POST})
	public String delete(@RequestParam("id") int personId) {
		System.out.println("삭제");
		
		/* delete */
   		PhoneDao phoneDao = new PhoneDao();
   		phoneDao.personDelete(personId);
		  		
		return "redirect:/phone/list";
	}
}
