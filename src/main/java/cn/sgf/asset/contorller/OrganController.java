package cn.sgf.asset.contorller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.sgf.asset.core.RespInfo;
import cn.sgf.asset.core.RespInfo.RespCodeEnum;
import cn.sgf.asset.dao.AssetDao;
import cn.sgf.asset.dao.OrganDao;
import cn.sgf.asset.domain.AssetDO;
import cn.sgf.asset.domain.SysOrganDO;

@RestController
@RequestMapping("/api/sys/organ")
public class OrganController {
	
	private Logger logger=LoggerFactory.getLogger(OrganController.class);
	@Autowired
	private OrganDao organDao;

	@RequestMapping("/save")
	public RespInfo save(SysOrganDO organDo) {
		logger.info("asset:{}",organDo);
		organDao.save(organDo);
		return RespInfo.success();
	}
	
	@RequestMapping("/delete")
	public RespInfo delete(Long id) {
		organDao.deleteById(id);
		return RespInfo.success();
	}
	
	@RequestMapping("/list")
	public RespInfo list(@RequestParam(required=false)Long id) {
		if(id==null || id==0) {
			List<SysOrganDO> list=organDao.findAll();
			return RespInfo.success(list);
		}else {
			List<SysOrganDO> list=organDao.findByPid(id);
			return RespInfo.success(list);
		}
	}
}
