package cn.sgf.asset.contorller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import cn.sgf.asset.core.model.RespInfo;
import cn.sgf.asset.core.utils.AuthUtil;
import cn.sgf.asset.dto.AssetSearchDTO;
import cn.sgf.asset.dto.CheckInfoDTO;
import cn.sgf.asset.dto.CheckSearchDTO;
import cn.sgf.asset.dto.UserDTO;
import cn.sgf.asset.service.AssetService;
import cn.sgf.asset.service.CheckService;

@RestController
@RequestMapping("/api/check")
public class CheckController {

	private Logger logger = LoggerFactory.getLogger(CheckController.class);
	@Autowired
	private CheckService checkService;
	
	
	
	@PostMapping("/save")
	public RespInfo save(@RequestHeader("token") String token, CheckInfoDTO checkInfoDto) {
		logger.info("asset:{}", checkInfoDto);
		UserDTO currentUserDto = AuthUtil.getUserByToken(token);
		checkService.save(checkInfoDto, currentUserDto);
		return RespInfo.success();
	}

	@RequestMapping("/list")
	public RespInfo list(CheckSearchDTO searchDto) {
		return RespInfo.success(checkService.list(searchDto));
	}

}
