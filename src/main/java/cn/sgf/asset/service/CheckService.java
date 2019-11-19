package cn.sgf.asset.service;


import cn.sgf.asset.core.model.PageResult;
import cn.sgf.asset.dto.CheckInfoDTO;
import cn.sgf.asset.dto.CheckSearchDTO;
import cn.sgf.asset.dto.UserDTO;


public interface CheckService {

	PageResult<CheckInfoDTO> list(CheckSearchDTO searchDto);

	void save(CheckInfoDTO checkInfoDto, UserDTO currentUserDto);

	
}
