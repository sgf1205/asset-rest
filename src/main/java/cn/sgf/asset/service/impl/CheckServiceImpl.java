package cn.sgf.asset.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cn.sgf.asset.core.enu.StatusEnum;
import cn.sgf.asset.core.model.PageResult;
import cn.sgf.asset.dao.CheckInfoDao;
import cn.sgf.asset.domain.AssetDO;
import cn.sgf.asset.domain.CheckInfoDO;
import cn.sgf.asset.domain.SysOrganDO;
import cn.sgf.asset.domain.UserDO;
import cn.sgf.asset.dto.AssetDTO;
import cn.sgf.asset.dto.AssetSearchDTO;
import cn.sgf.asset.dto.CheckInfoDTO;
import cn.sgf.asset.dto.CheckSearchDTO;
import cn.sgf.asset.dto.UserDTO;
import cn.sgf.asset.service.CheckService;

@Service
public class CheckServiceImpl implements CheckService {

	
	@Autowired
	private CheckInfoDao checkInfoDao;
	
	
	@Override
	public void save(CheckInfoDTO checkInfoDto, UserDTO currentUserDto) {
		// TODO Auto-generated method stub
		CheckInfoDO checkInfoDo=new CheckInfoDO();
		BeanUtils.copyProperties(checkInfoDto, checkInfoDo);
		checkInfoDo.setUser(checkInfoDto.getUser());
		checkInfoDo.setCreateTime(new Date());
		checkInfoDo.setCheckTime(checkInfoDo.getCreateTime());
		UserDO userDo = new UserDO();
		userDo.setId(currentUserDto.getId());
		SysOrganDO organ=new SysOrganDO();
		organ.setId(checkInfoDto.getOrganId());
		checkInfoDo.setCreateUesr(userDo);
		checkInfoDo.setOrgan(organ);
		checkInfoDao.save(checkInfoDo);
	}
	

	@Override
	public PageResult<CheckInfoDTO> list(CheckSearchDTO searchDto) {
		// TODO Auto-generated method stub
		CheckInfoDO searchCheckInfo=new CheckInfoDO();
		SysOrganDO organ=new SysOrganDO();
		organ.setId(searchDto.getOrganId());
		searchCheckInfo.setOrgan(organ);
		ExampleMatcher matcher =ExampleMatcher.matching()
				.withMatcher("organ", ExampleMatcher.GenericPropertyMatchers.exact());
		Example<CheckInfoDO> example=Example.of(searchCheckInfo, matcher);
		Pageable pageable = PageRequest.of(searchDto.getCurrentPage() - 1, searchDto.getPageSize());

		Page<CheckInfoDO> page=checkInfoDao.findAll(example,pageable);
		List<CheckInfoDTO> dtoList = page.getContent().stream().map(checkInfoDo -> {
			CheckInfoDTO dto = new CheckInfoDTO();
			BeanUtils.copyProperties(checkInfoDo, dto);
			dto.setOrganId(checkInfoDo.getOrgan().getId());
			dto.setOrganName(checkInfoDo.getOrgan().getName());
			dto.setNeedCheckSize(checkInfoDo.getNeedCheckAssets().split(",").length);
			dto.setAlreadyCheckSize(checkInfoDo.getAlreadyCheckAssets().split(",").length);
			
			return dto;
		}).collect(Collectors.toList());
		
		PageResult<CheckInfoDTO> pageResult = new PageResult<CheckInfoDTO>();
		pageResult.setResult(dtoList);
		pageResult.setTotalPage(page.getTotalPages());
		pageResult.setTotalSize(page.getTotalElements());
		pageResult.setCurrentPage(page.getNumber());
		return pageResult;
	}


}
