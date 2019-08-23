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
import cn.sgf.asset.dao.RepairDao;
import cn.sgf.asset.dao.ScrapDao;
import cn.sgf.asset.domain.ApplyDO;
import cn.sgf.asset.domain.ApplyItemDO;
import cn.sgf.asset.domain.AssetDO;
import cn.sgf.asset.domain.RepairDO;
import cn.sgf.asset.domain.ScrapDO;
import cn.sgf.asset.domain.SysOrganDO;
import cn.sgf.asset.domain.UserDO;
import cn.sgf.asset.dto.ApplyDTO;
import cn.sgf.asset.dto.RepairDTO;
import cn.sgf.asset.dto.ScrapDTO;
import cn.sgf.asset.dto.UserDTO;
import cn.sgf.asset.service.ApplyService;
import cn.sgf.asset.service.RepairService;
import cn.sgf.asset.service.ScrapService;

@Service
public class RepairServiceImpl implements RepairService{
	@Autowired
	private AssetDao assetDao;
	
	@Autowired
	private RepairDao repairDao;

	
	@Override
	@Transactional
	public void createRepair(RepairDTO dto,UserDTO currentUser) {
		// TODO Auto-generated method stub
		RepairDO repairDo = new RepairDO();
		BeanUtils.copyProperties(dto, repairDo);
		UserDO userDo = new UserDO();
		userDo.setId(currentUser.getId());
		repairDo.setCreateUesr(userDo);
		repairDo.setCreateTime(new Date());
		repairDo.setStatus(StatusEnum.MAINTAIN.getCode());
		List<ApplyItemDO> items=new ArrayList<ApplyItemDO>();
		for(Long assetId:dto.getAssetIds()) {
			ApplyItemDO applyItem=new ApplyItemDO();
			AssetDO asset=new AssetDO();
			asset.setId(assetId);
			applyItem.setAsset(asset);
			applyItem.setRepair(repairDo);
			applyItem.setCreateTime(repairDo.getCreateTime());
			applyItem.setStatus(StatusEnum.MAINTAIN.getCode());
			assetDao.editStatus(StatusEnum.MAINTAIN.getCode(), assetId);
			items.add(applyItem);
		}
		repairDo.setItems(items);
		repairDao.save(repairDo);
	}


	@Override
	public PageResult<RepairDO> list(Date startCreateDate,Date endCreateDate, Pageable pageable) {
		  Specification<RepairDO> querySpecifi = new Specification<RepairDO>() {
	            @Override
	            public Predicate toPredicate(Root<RepairDO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
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
		
		Page<RepairDO> pageList = repairDao.findAll(querySpecifi, pageable);
		PageResult<RepairDO> pageResult=new PageResult<RepairDO>();
		pageResult.setResult(pageList.getContent());
		pageResult.setTotalPage(pageList.getTotalPages());
		pageResult.setTotalSize(pageList.getTotalElements());
		pageResult.setCurrentPage(pageList.getNumber());
		return pageResult;
	}
	
	@Override
	public void finishRepair(Long[] ids, UserDTO currentUser) {
		// TODO Auto-generated method stub
		Date now=new Date();
		for(Long id:ids) {
			RepairDO repairDo=repairDao.getOne(id);
			repairDo.setStatus(StatusEnum.SCRAPPED_RECOVERY.getCode());
			repairDo.setFinishTime(now);
			repairDo.getItems().forEach(item->{
				item.setStatus(StatusEnum.SCRAPPED_RECOVERY.getCode());
				item.setRetreatTime(now);
				item.getAsset().setStatus(StatusEnum.FREE.getCode());
			});
			UserDO userDo = new UserDO();
			userDo.setId(currentUser.getId());
			repairDo.setFinishUesr(userDo);
			repairDao.save(repairDo);
		}
	}


}
