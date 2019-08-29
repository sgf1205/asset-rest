package cn.sgf.asset.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.sgf.asset.core.enu.DeleteEnum;
import cn.sgf.asset.core.enu.StatusEnum;
import cn.sgf.asset.core.enu.SysOpsTypeEnum;
import cn.sgf.asset.core.model.PageResult;
import cn.sgf.asset.dao.AssetDao;
import cn.sgf.asset.domain.AssetDO;
import cn.sgf.asset.domain.ClassesDO;
import cn.sgf.asset.domain.SysOrganDO;
import cn.sgf.asset.domain.UserDO;
import cn.sgf.asset.dto.AssetDTO;
import cn.sgf.asset.dto.AssetSearchDTO;
import cn.sgf.asset.dto.AssetStatisticsDTO;
import cn.sgf.asset.dto.UserDTO;
import cn.sgf.asset.service.AssetService;
import cn.sgf.asset.service.SysLogService;

@Service
public class AssetServiceImpl implements AssetService {

	@Autowired
	private AssetDao assetDao;
	
	@Autowired
	private SysLogService sysLogService;

	@Override
	@Transactional
	public void save(AssetDTO assetDto,UserDTO currentUserDto) {
		// TODO Auto-generated method stub
		AssetDO assetDo = new AssetDO();
		BeanUtils.copyProperties(assetDto, assetDo);
		ClassesDO classesDo = new ClassesDO();
		classesDo.setId(assetDto.getClassesId());
		assetDo.setClasses(classesDo);
		
		UserDO userDo = new UserDO();
		userDo.setId(currentUserDto.getId());
		SysOrganDO organ=new SysOrganDO();
		organ.setId(assetDto.getOrganId());
		assetDo.setRegisterOrgan(organ);
		if (assetDo.getId() == null) {//新增
			assetDo.setUsingOrgan(organ);
			assetDo.setRegisterUser(userDo);
			assetDo.setRegisterTime(new Date());
			assetDo.setStatus(StatusEnum.FREE.getCode());
			assetDo.setDeleteFlag(DeleteEnum.NO_DELETED.getCode());
			assetDo.setCode(UUID.randomUUID().toString().replaceAll("-", ""));
		}else {//修改
			assetDo.setEditUser(userDo);
			assetDo.setEditTime(new Date());
		}
		assetDao.save(assetDo);
		sysLogService.save(userDo, SysOpsTypeEnum.REGISTER,"登记资产"+assetDo.getName());
	}

	@Override
	public PageResult<AssetDTO> list(AssetSearchDTO searchDto) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(searchDto.getCurrentPage() - 1, searchDto.getPageSize());

		Specification<AssetDO> querySpecifi = new Specification<AssetDO>() {
			@Override
			public Predicate toPredicate(Root<AssetDO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (StringUtils.isNotEmpty(searchDto.getName())) {
					predicates.add(cb.like(root.get("name").as(String.class), "%"+searchDto.getName()+"%"));
				}
				if (searchDto.getStatus() != null) {
					predicates.add(cb.equal(root.get("status").as(Integer.class), searchDto.getStatus()));
				}
				if (searchDto.getRegisterOrganId() != null) {
					SysOrganDO registerOrgan = new SysOrganDO();
					registerOrgan.setId(searchDto.getRegisterOrganId());
					predicates.add(cb.equal(root.get("registerOrgan").as(SysOrganDO.class), registerOrgan));
				}
				if (searchDto.getUsingOrganId() != null) {
					SysOrganDO usingOrgan = new SysOrganDO();
					usingOrgan.setId(searchDto.getUsingOrganId());
					predicates.add(cb.equal(root.get("usingOrgan").as(SysOrganDO.class), usingOrgan));
				}
				if (searchDto.getLowMoney() != null) {
					predicates.add(cb.ge(root.get("money").as(Double.class), searchDto.getLowMoney()));
				}
				if (searchDto.getMaxMoney() != null) {
					predicates.add(cb.le(root.get("money").as(Double.class), searchDto.getMaxMoney()));
				}
				// and到一起的话所有条件就是且关系，or就是或关系
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		Page<AssetDO> pageList = assetDao.findAll(querySpecifi, pageable);

		List<AssetDTO> dtoList = pageList.getContent().stream().map(assetDo -> {
			AssetDTO dto = new AssetDTO();
			BeanUtils.copyProperties(assetDo, dto);
			dto.setClassesName(assetDo.getClasses().getName());
			dto.setClassesId(assetDo.getClasses().getId());
			dto.setRegisterUserName(assetDo.getRegisterUser().getName());
			dto.setOrganId(assetDo.getRegisterOrgan().getId());
			dto.setOrganName(assetDo.getRegisterOrgan().getName());
			if(assetDo.getUsingOrgan()!=null) {
				dto.setUsingOrganId(assetDo.getUsingOrgan().getId());
				dto.setUsingOrganName(assetDo.getUsingOrgan().getName());
			}
			
			return dto;
		}).collect(Collectors.toList());
		PageResult<AssetDTO> pageResult = new PageResult<AssetDTO>();
		pageResult.setResult(dtoList);
		pageResult.setTotalPage(pageList.getTotalPages());
		pageResult.setTotalSize(pageList.getTotalElements());
		pageResult.setCurrentPage(pageList.getNumber());
		return pageResult;
	}
	
	@Override
	@Transactional
	public void delete(Long[] ids,UserDTO currentUserDto) {
		// TODO Auto-generated method stub
		for (Long id : ids) {
			AssetDO assetDo = assetDao.getOne(id);
			assetDo.setDeleteFlag(DeleteEnum.DELETED.getCode());
			assetDao.save(assetDo);
			sysLogService.save(currentUserDto.getId(), SysOpsTypeEnum.DELETE,"删除资产:"+assetDo.getName());
		}
	}
	
	@Override
	public List<AssetStatisticsDTO> statistics(String type) {
		// TODO Auto-generated method stub
		
		if(type.equals("classes")) {
			return assetDao.statisticsByClasses();
		}else if(type.equals("organ")) {
			return assetDao.statisticsByClasses();
		}
		return null;
	}

}
