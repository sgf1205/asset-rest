package cn.sgf.asset.service;

import cn.sgf.asset.core.model.PageResult;
import cn.sgf.asset.dto.AssetDTO;
import cn.sgf.asset.dto.AssetSearchDTO;
import cn.sgf.asset.dto.UserDTO;


public interface AssetService {

	void save(AssetDTO assetDto,UserDTO currentUserDto);

	PageResult<AssetDTO> list(AssetSearchDTO searchDto);

	void delete(Long[] ids);

}
