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

import cn.sgf.asset.core.enu.DeleteEnum;
import cn.sgf.asset.core.enu.StatusEnum;
import cn.sgf.asset.core.model.PageParam;
import cn.sgf.asset.core.model.PageResult;
import cn.sgf.asset.core.model.RespInfo;
import cn.sgf.asset.core.utils.AuthUtil;
import cn.sgf.asset.dao.AssetDao;
import cn.sgf.asset.domain.AssetDO;
import cn.sgf.asset.domain.ClassesDO;
import cn.sgf.asset.domain.SysOrganDO;
import cn.sgf.asset.domain.UserDO;
import cn.sgf.asset.dto.AssetDTO;
import cn.sgf.asset.dto.AssetSearchDTO;
import cn.sgf.asset.dto.UserDTO;
import cn.sgf.asset.service.AssetService;

@RestController
@RequestMapping("/api/asset")
public class AssetController {

	private Logger logger = LoggerFactory.getLogger(AssetController.class);
	@Autowired
	private AssetService assetService;

	@RequestMapping("/save")
	public RespInfo save(@RequestHeader("token") String token, AssetDTO assetDto) {
		logger.info("asset:{}", assetDto);
		UserDTO currentUserDto = AuthUtil.getUserByToken(token);
		assetService.save(assetDto,currentUserDto);
		return RespInfo.success();
	}

	@RequestMapping("/delete")
	public RespInfo del(Long[] ids) {
		assetService.delete(ids);
		return RespInfo.success();
	}

	@RequestMapping("/list")
	public RespInfo list(AssetSearchDTO searchDto) {
		return RespInfo.success(assetService.list(searchDto));
	}
	
	@RequestMapping("/statistics")
	public RespInfo statistics(String type) {
		return RespInfo.success(assetService.statistics(type));
	}
	
}
