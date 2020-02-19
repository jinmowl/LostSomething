package com.mumulx.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mumulx.entity.Area;
import com.mumulx.mapper.AreaMapper;
import com.mumulx.service.AreaService;
import com.mumulx.util.HelpDev;
@Service("areaService")
public class AreaServiceImpl implements AreaService{
	
	@Autowired
	AreaMapper areaMapper;
	@Autowired
	HelpDev helpDev;
	
	public AreaMapper getAreaMapper() {
		return areaMapper;
	}

	public void setAreaMapper(AreaMapper areaMapper) {
		this.areaMapper = areaMapper;
	}

	public void setHelpDev(HelpDev helpDev) {
		this.helpDev = helpDev;
	}

	@Override
	public HelpDev getHelpDev() {
		// TODO Auto-generated method stub
		return this.helpDev;
	}

	@Override
    /*添加学院记录*/
    public Boolean addArea(Area area) throws Exception {
		//判断是否增加过该学院，如果增加过则报错
		Area areaByAreaName = areaMapper.getAreaByAreaName(area.getAreaName());
		if(areaByAreaName==null) {
			areaMapper.addArea(area);
			return true;
		}
		return false;
    	
    }

	/*按照查询条件分页查询学院记录*/
    public ArrayList<Area> queryArea(int currentPage) throws Exception { 
     	String where = "where 1=1";
    	int startIndex = (currentPage-1) * this.helpDev.getRows();
    	return areaMapper.queryArea(where, startIndex, this.helpDev.getRows());
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Area> queryArea() throws Exception  { 
     	String where = "where 1=1";
    	return areaMapper.queryAreaList(where);
    }

	@Override
    /*查询所有学院记录*/
    public ArrayList<Area> queryAllArea()  throws Exception {
        return areaMapper.queryAreaList("where 1=1");
    }
	
	
	/*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber() throws Exception {
     	String where = "where 1=1";
     	this.helpDev.computeTotalPag(areaMapper.queryAreaCount(where));
    }
    /*根据主键获取学院记录*/
    public Area getArea(int areaId) throws Exception  {
        Area area = areaMapper.getArea(areaId);
        return area;
    }
    /*更新学院记录*/
    public Boolean updateArea(Area area) throws Exception {
    	//判断是否增加过该学院，如果增加过则报错
		Area areaByAreaName = areaMapper.getAreaByAreaName(area.getAreaName());
		if(areaByAreaName==null) {
			areaMapper.updateArea(area);
			return true;
		}
		return false;
        //areaMapper.updateArea(area);
    }

    /*删除一条学院记录*/
    public void deleteArea (int areaId) throws Exception {
        areaMapper.deleteArea(areaId);
    }

    /*删除多条学院信息*/
    public int deleteAreas (String areaIds) throws Exception {
    	String _areaIds[] = areaIds.split(",");
    	for(String _areaId: _areaIds) {
    		areaMapper.deleteArea(Integer.parseInt(_areaId));
    	}
    	return _areaIds.length;
    }

}
