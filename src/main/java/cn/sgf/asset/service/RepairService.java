package cn.sgf.asset.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;

import cn.sgf.asset.core.model.PageParam;
import cn.sgf.asset.core.model.PageResult;
import cn.sgf.asset.domain.ApplyDO;
import cn.sgf.asset.domain.RepairDO;
import cn.sgf.asset.domain.ScrapDO;
import cn.sgf.asset.domain.SysOrganDO;
import cn.sgf.asset.dto.ApplyDTO;
import cn.sgf.asset.dto.RepairDTO;
import cn.sgf.asset.dto.ScrapDTO;
import cn.sgf.asset.dto.UserDTO;


public interface RepairService {

	void createRepair(RepairDTO dto,UserDTO currentUser);

	PageResult<RepairDO> list(Date startCreateDate,Date endCreateDate,Pageable pageable);

	void finishRepair(Long[] ids, UserDTO currentUser);
}
