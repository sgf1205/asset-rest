package cn.sgf.asset.contorller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {

	private Logger logger = LoggerFactory.getLogger(LoginController.class);
	@RequestMapping
	public String login(String username,String password) {
		logger.info("username:{},password:{}",username,password);
		return "success";
	}
}
