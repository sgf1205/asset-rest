package cn.sgf.asset.contorller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.sgf.asset.core.model.PageParam;
import cn.sgf.asset.core.model.PageResult;
import cn.sgf.asset.core.model.RespInfo;
import cn.sgf.asset.core.utils.AuthUtil;
import cn.sgf.asset.domain.ScrapDO;
import cn.sgf.asset.dto.ScrapDTO;
import cn.sgf.asset.dto.UserDTO;
import cn.sgf.asset.service.ScrapService;

@RestController
@RequestMapping("/api/asset/scrap")
public class ScrapController {

	private Logger logger = LoggerFactory.getLogger(ScrapController.class);
	
	@Autowired
	private ScrapService scrapService;

	@RequestMapping("/save")
	public RespInfo save(@RequestHeader("token") String token, ScrapDTO  dto) {
		logger.info("scrap:{}", dto);
		UserDTO currentUser=AuthUtil.getUserByToken(token);
		scrapService.createScrap(dto, currentUser);
		return RespInfo.success();
	}


	@RequestMapping("/list")
	public RespInfo list(@RequestParam(required = false) Date startCreateDate,@RequestParam(required = false) Date endCreateDate, PageParam pageParam) {
		Pageable pageable = PageRequest.of(pageParam.getCurrentPage() - 1, pageParam.getPageSize());
		PageResult<ScrapDO> pageResult=scrapService.list(startCreateDate,endCreateDate,pageable);
		return RespInfo.success(pageResult);
	}
	
	@RequestMapping("/recovery")
	public RespInfo recovery(@RequestHeader("token") String token, Long[] ids) {
		UserDTO currentUser=AuthUtil.getUserByToken(token);
		scrapService.recovery(ids, currentUser);
		return RespInfo.success();
	}
	
}
