package cn.sgf.asset.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;

import cn.sgf.asset.core.model.PageParam;
import cn.sgf.asset.core.model.PageResult;
import cn.sgf.asset.domain.ApplyDO;
import cn.sgf.asset.domain.ScrapDO;
import cn.sgf.asset.domain.SysOrganDO;
import cn.sgf.asset.dto.ApplyDTO;
import cn.sgf.asset.dto.ScrapDTO;
import cn.sgf.asset.dto.UserDTO;


public interface ScrapService {

	void createScrap(ScrapDTO scrapDto,UserDTO currentUser);

	PageResult<ScrapDO> list(Date startCreateDate,Date endCreateDate,Pageable pageable);

	void recovery(Long[] ids, UserDTO currentUser);
}
