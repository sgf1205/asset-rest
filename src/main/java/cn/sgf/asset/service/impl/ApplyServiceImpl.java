package cn.sgf.asset.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import cn.sgf.asset.core.enu.ApplyTypeEnum;
import cn.sgf.asset.core.enu.StatusEnum;
import cn.sgf.asset.core.model.PageResult;
import cn.sgf.asset.dao.ApplyDao;
import cn.sgf.asset.dao.AssetDao;
import cn.sgf.asset.domain.ApplyDO;
import cn.sgf.asset.domain.ApplyItemDO;
import cn.sgf.asset.domain.AssetDO;
import cn.sgf.asset.domain.SysOrganDO;
import cn.sgf.asset.domain.UserDO;
import cn.sgf.asset.dto.ApplyDTO;
import cn.sgf.asset.dto.UserDTO;
import cn.sgf.asset.service.ApplyService;

@Service
public class ApplyServiceImpl implements ApplyService{
	@Autowired
	private AssetDao assetDao;
	
	@Autowired
	private ApplyDao applyDao;

	
	@Override
	@Transactional
	public void createApply(ApplyDTO applyDto,UserDTO currentUser) {
		// TODO Auto-generated method stub
		ApplyDO applyDo = new ApplyDO();
		BeanUtils.copyProperties(applyDto, applyDo);
		UserDO userDo = new UserDO();
		userDo.setId(currentUser.getId());
		applyDo.setCreateUesr(userDo);
		SysOrganDO organ=new SysOrganDO();
		organ.setId(applyDto.getOrganId());
		applyDo.setOrgan(organ);
		applyDo.setCreateTime(new Date());
		List<ApplyItemDO> items=new ArrayList<ApplyItemDO>();
		for(Long assetId:applyDto.getAssetIds()) {
			ApplyItemDO applyItem=new ApplyItemDO();
			AssetDO asset=new AssetDO();
			asset.setId(assetId);
			applyItem.setAsset(asset);
			applyItem.setApply(applyDo);
			applyItem.setCreateTime(applyDo.getCreateTime());
			if(applyDto.getType()==ApplyTypeEnum.BORROW.getCode()) {
				applyItem.setStatus(StatusEnum.USED_BORROW.getCode());
				assetDao.editStatus(StatusEnum.USED_BORROW.getCode(), assetId);
			}else if(applyDto.getType()==ApplyTypeEnum.RECEIVE.getCode()) {
				applyItem.setStatus(StatusEnum.USED_RECEIVE.getCode());
				assetDao.editStatus(StatusEnum.USED_RECEIVE.getCode(), assetId);
			}
			items.add(applyItem);
		}
		applyDo.setItems(items);
		applyDao.save(applyDo);
	}


	@Override
	public PageResult<ApplyDO> list(Date startCreateDate,Date endCreateDate, Pageable pageable) {
		ApplyDO searchDo = new ApplyDO();
		  Specification<ApplyDO> querySpecifi = new Specification<ApplyDO>() {
	            @Override
	            public Predicate toPredicate(Root<ApplyDO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
	                List<Predicate> predicates = new ArrayList<>();
	                if (startCreateDate!=null) {
	                    //大于或等于传入时间
	                    predicates.add(cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class), startCreateDate));
	                }
	                if (endCreateDate!=null) {
	                    //小于或等于传入时间
	                    predicates.add(cb.lessThanOrEqualTo(root.get("createTime").as(Date.class), endCreateDate));
	                }
	                // and到一起的话所有条件就是且关系，or就是或关系
	                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	            }
		  };
		
		Page<ApplyDO> pageList = applyDao.findAll(querySpecifi, pageable);
		PageResult<ApplyDO> pageResult=new PageResult<ApplyDO>();
		pageResult.setResult(pageList.getContent());
		pageResult.setTotalPage(pageList.getTotalPages());
		pageResult.setTotalSize(pageList.getTotalElements());
		pageResult.setCurrentPage(pageList.getNumber());
		return pageResult;
	}


}
