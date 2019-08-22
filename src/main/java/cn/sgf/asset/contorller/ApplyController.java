package cn.sgf.asset.contorller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
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
import cn.sgf.asset.domain.ApplyDO;
import cn.sgf.asset.dto.ApplyDTO;
import cn.sgf.asset.dto.UserDTO;
import cn.sgf.asset.service.ApplyService;

@RestController
@RequestMapping("/api/asset/apply")
public class ApplyController {

	private Logger logger = LoggerFactory.getLogger(ApplyController.class);
	
	@Autowired
	private ApplyService applyService;

	@RequestMapping("/save")
	public RespInfo save(@RequestHeader("token") String token, ApplyDTO  applyDto) {
		logger.info("collar:{}", applyDto);
		UserDTO currentUser=AuthUtil.getUserByToken(token);
		applyService.createApply(applyDto, currentUser);
		return RespInfo.success();
	}


	@RequestMapping("/list")
	public RespInfo list(Integer type,@RequestParam(required = false) Date startCreateDate,@RequestParam(required = false) Date endCreateDate, PageParam pageParam) {
		Pageable pageable = PageRequest.of(pageParam.getCurrentPage() - 1, pageParam.getPageSize());
		PageResult<ApplyDO> pageResult=applyService.list(type,startCreateDate,endCreateDate,pageable);
		return RespInfo.success(pageResult);
	}
	
	@RequestMapping("/return")
	public RespInfo borrowReturn(@RequestHeader("token") String token, Long[] ids) {
		UserDTO currentUser=AuthUtil.getUserByToken(token);
		applyService.borrowReturn(ids, currentUser);
		return RespInfo.success();
	}
	
}
