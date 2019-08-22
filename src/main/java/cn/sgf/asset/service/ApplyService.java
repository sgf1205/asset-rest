package cn.sgf.asset.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;

import cn.sgf.asset.core.model.PageParam;
import cn.sgf.asset.core.model.PageResult;
import cn.sgf.asset.domain.ApplyDO;
import cn.sgf.asset.domain.SysOrganDO;
import cn.sgf.asset.dto.ApplyDTO;
import cn.sgf.asset.dto.UserDTO;


public interface ApplyService {

	void createApply(ApplyDTO applyDto,UserDTO currentUser);

	PageResult<ApplyDO> list(Date startCreateDate,Date endCreateDate,Pageable pageable);
}
