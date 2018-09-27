package com.drm.action;

import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.drm.domain.Addrbook;
import com.drm.service.AddrbookService;
import com.drm.utils.Page;
import com.drm.utils.ReadConfigation;

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class AddrbookAction  extends BaseAction<Addrbook>{ 
	@Resource
	private AddrbookService  addrbookService; 
	
	public String queryAddrbookPage(){
		dataMap = new HashMap<String, Object>();
		Integer currentPage=Integer.valueOf(getParameterByKey("page"));
		Integer pageRecorders=Integer.valueOf(getParameterByKey("limit")); 
 
	
		try {
			Page page =addrbookService.queryAddrbookPage(currentPage,pageRecorders); 
			  if(page!=null){
				  dataMap.put("code", 0);
				  dataMap.put("msg", "");
				  dataMap.put("count",page.getTotalRows());
				  dataMap.put("data",page.getDataList() );
			  } 

		} catch (Exception e) { 
			e.printStackTrace();
			  dataMap.put("code", 0);
			  dataMap.put("msg", "");
			  dataMap.put("count",0);
			  dataMap.put("data",0);
		} 
	
		return SUCCESS;
	}
	
	
	public String validateIP(){ 
		dataMap = new HashMap<String, Object>();
		String ip=getParameterByKey("ip");  
		
		Addrbook addrbook=addrbookService.getAddrbookByIP(ip);
		
		if(addrbook!=null){
			  dataMap.put("result", true);
		}else{
			 dataMap.put("result", false);
		} 
		return SUCCESS;
	}
	
	public String saveAddrbook(){
		dataMap = new HashMap<String, Object>();
		try {
			Integer count=addrbookService.getAddrbookCount();
			String addrbook_count=ReadConfigation.getConfigItem("addrbook_count");
			if(count<Integer.valueOf(addrbook_count)){
				String ip=getParameterByKey("ip"); 
				String name=getParameterByKey("name"); 
				String usr_id=getParameterByKey("usr_id"); 
				if(StringUtils.isNotEmpty(ip)&&StringUtils.isNotEmpty(name)&&StringUtils.isNotEmpty(usr_id)){
					
					Addrbook addrbook=new Addrbook();
					addrbook.setIp(ip);
					addrbook.setName(name.trim());
					addrbook.setUsr_id(usr_id.trim());
					
					addrbookService.saveAddrbook(addrbook);
					
					 dataMap.put("result", true);
					 dataMap.put("msg", "新增成功");
				}else{
					 dataMap.put("result",false );
					 dataMap.put("msg", "参数错误,编辑失败");
				} 	
			}else{
				 dataMap.put("result", false);
				 dataMap.put("msg", "通讯录成员数量已超出"+addrbook_count+",请联系管理员!");
			} 
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("result", false);
			 dataMap.put("msg", "新增失败");
		} 
		
		return SUCCESS;
	}
	
	public String toEditAddrbook(){ 
 		try {   
 			String id=getParameterByKey("id"); 
 			Addrbook addrbook=addrbookService.queryAddrbook(Integer.valueOf(id)); 
			request.setAttribute("addrbook", addrbook); 
		} catch (Exception e) {
			e.printStackTrace();  
		} 
		return SUCCESS;
	} 
	
	public String updateAddrbook(){
		
		dataMap = new HashMap<String, Object>();
		try {
			String id=getParameterByKey("id"); 
			String ip=getParameterByKey("ip"); 
			String name=getParameterByKey("name"); 
			String usr_id=getParameterByKey("usr_id"); 
			
			if(StringUtils.isNotEmpty(id)&&StringUtils.isNotEmpty(ip)&&StringUtils.isNotEmpty(name)&&StringUtils.isNotEmpty(usr_id)){
				Addrbook addrbook=addrbookService.getAddrbookByIP(ip);

				if(addrbook==null){
					Addrbook newAddrbook=new Addrbook();
					newAddrbook.setId(Integer.valueOf(id));
					newAddrbook.setIp(ip);
					newAddrbook.setName(name); 
					newAddrbook.setUsr_id(usr_id);
					
					addrbookService.updateAddrbook(newAddrbook);
					
					dataMap.put("result", true);
					dataMap.put("msg", "编辑成功");
				}else{
					if(Integer.valueOf(id)==addrbook.getId()){
						Addrbook newAddrbook=new Addrbook();
						newAddrbook.setId(Integer.valueOf(id));
						newAddrbook.setIp(ip);
						newAddrbook.setName(name.trim()); 
						newAddrbook.setUsr_id(usr_id.trim());
						
						addrbookService.updateAddrbook(newAddrbook);
						
						dataMap.put("result", true);
						dataMap.put("msg", "编辑成功");
					}else{
						dataMap.put("result",false );
						dataMap.put("msg", "该IP已存在,编辑失败");
					} 
				}	
			}else{
				 dataMap.put("result",false );
				 dataMap.put("msg", "参数错误,编辑失败");
			} 
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("result", false);
			dataMap.put("msg", "编辑失败");
		}  
		return SUCCESS;
	}
	
	
	
	 public String delAddrbook(){ 
		 dataMap = new HashMap<String, Object>();
			try {
				String ids=getParameterByKey("ids");  
				addrbookService.delAddrbook(ids); 
				 dataMap.put("result", true);
				 dataMap.put("msg", "删除成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("result", false);
				dataMap.put("msg", "删除失败");
			} 
		 return SUCCESS;
	 }
}
