package cn.sgf.asset.contorller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.sgf.asset.core.RespInfo;
import cn.sgf.asset.core.RespInfo.RespCodeEnum;
import cn.sgf.asset.dao.AssetDao;
import cn.sgf.asset.domain.AssetDO;

@RestController
@RequestMapping("/api/asset")
public class AssetController {
	
	private Logger logger=LoggerFactory.getLogger(AssetController.class);
	@Autowired
	private AssetDao assetDao;

	@RequestMapping("/save")
	public RespInfo save(AssetDO assetDo) {
		logger.info("asset:{}",assetDo);
		assetDao.save(assetDo);
		return RespInfo.success();
	}
	
	@RequestMapping("/delete")
	public RespInfo del(Long id) {
		assetDao.deleteById(id);
		return RespInfo.success();
	}
}
