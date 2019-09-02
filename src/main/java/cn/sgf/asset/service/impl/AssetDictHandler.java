package cn.sgf.asset.service.impl;

import org.springframework.stereotype.Service;

import cn.afterturn.easypoi.handler.inter.IExcelDictHandler;

@Service
public class AssetDictHandler implements IExcelDictHandler {

	@Override
	public String toName(String dict, Object obj, String name, Object value) {
		// TODO Auto-generated method stub
		if("classDict".equals(dict)) {
			
		}
		return null;
	}

	@Override
	public String toValue(String dict, Object obj, String name, Object value) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
