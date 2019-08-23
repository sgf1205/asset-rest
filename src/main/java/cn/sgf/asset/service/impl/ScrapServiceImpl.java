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
import cn.sgf.asset.dao.ScrapDao;
import cn.sgf.asset.domain.ApplyDO;
import cn.sgf.asset.domain.ApplyItemDO;
import cn.sgf.asset.domain.AssetDO;
import cn.sgf.asset.domain.ScrapDO;
import cn.sgf.asset.domain.SysOrganDO;
import cn.sgf.asset.domain.UserDO;
import cn.sgf.asset.dto.ApplyDTO;
import cn.sgf.asset.dto.ScrapDTO;
import cn.sgf.asset.dto.UserDTO;
import cn.sgf.asset.service.ApplyService;
import cn.sgf.asset.service.ScrapService;

@Service
public class ScrapServiceImpl implements ScrapService{
	@Autowired
	private AssetDao assetDao;
	
	@Autowired
	private ScrapDao scrapDao;

	
	@Override
	@Transactional
	public void createScrap(ScrapDTO scrapDto,UserDTO currentUser) {
		// TODO Auto-generated method stub
		ScrapDO scrapDo = new ScrapDO();
		BeanUtils.copyProperties(scrapDto, scrapDo);
		UserDO userDo = new UserDO();
		userDo.setId(currentUser.getId());
		scrapDo.setCreateUesr(userDo);
		scrapDo.setCreateTime(new Date());
		scrapDo.setStatus(StatusEnum.SCRAPPED.getCode());
		List<ApplyItemDO> items=new ArrayList<ApplyItemDO>();
		for(Long assetId:scrapDto.getAssetIds()) {
			ApplyItemDO applyItem=new ApplyItemDO();
			AssetDO asset=new AssetDO();
			asset.setId(assetId);
			applyItem.setAsset(asset);
			applyItem.setScrap(scrapDo);
			applyItem.setCreateTime(scrapDo.getCreateTime());
			applyItem.setStatus(StatusEnum.SCRAPPED.getCode());
			assetDao.editStatus(StatusEnum.SCRAPPED.getCode(), assetId);
			items.add(applyItem);
		}
		scrapDo.setItems(items);
		scrapDao.save(scrapDo);
	}


	@Override
	public PageResult<ScrapDO> list(Date startCreateDate,Date endCreateDate, Pageable pageable) {
		  Specification<ScrapDO> querySpecifi = new Specification<ScrapDO>() {
	            @Override
	            public Predicate toPredicate(Root<ScrapDO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
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
		
		Page<ScrapDO> pageList = scrapDao.findAll(querySpecifi, pageable);
		PageResult<ScrapDO> pageResult=new PageResult<ScrapDO>();
		pageResult.setResult(pageList.getContent());
		pageResult.setTotalPage(pageList.getTotalPages());
		pageResult.setTotalSize(pageList.getTotalElements());
		pageResult.setCurrentPage(pageList.getNumber());
		return pageResult;
	}
	
	@Override
	public void recovery(Long[] ids, UserDTO currentUser) {
		// TODO Auto-generated method stub
		Date now=new Date();
		for(Long id:ids) {
			ScrapDO scrapDo=scrapDao.getOne(id);
			scrapDo.setStatus(StatusEnum.SCRAPPED_RECOVERY.getCode());
			scrapDo.setRecoveryTime(now);
			scrapDo.getItems().forEach(item->{
				item.setStatus(StatusEnum.SCRAPPED_RECOVERY.getCode());
				item.setRetreatTime(now);
				item.getAsset().setStatus(StatusEnum.FREE.getCode());
			});
			UserDO userDo = new UserDO();
			userDo.setId(currentUser.getId());
			scrapDo.setRecoveryUesr(userDo);
			scrapDao.save(scrapDo);
		}
	}


}
