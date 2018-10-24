package cn.gov.tjp.app.constant;

import java.util.ArrayList;
import java.util.List;


/**
 * 地区码表，目前暂时只需广东省的
 * 
 */
public enum AreaCodeEnum {

	guangZhou("4401","广州"),
	
	shaoGuang("4402","韶关"),
	
	shenZhen("4403","深圳"),
	zhuHai("4404","珠海"),
	shanTou("4405","汕头"),
	foShan("4406","佛山"),
	jiangMen("4407","江门"),
	zhanJiang("4408","湛江"),
	maoMing("4409","茂名"),
	zhaoQing("4412","肇庆"),
	huiZhou("4413","惠州"),
	meiZhou("4414","梅州"),
	
	shanWei("4415","汕尾"),
	heYuan("4416","河源"),
	yangJiang("4417","阳江"),
	qingYuan("4418","清远"),
	dongGuan("4419","东莞"),
	zhongShan("4420","中山"),
	
	chaoZhou("4451","潮州"),
	
	jieYang("4452","揭阳"),
	
	yunFu("4453","云浮");
	
	
	private String name;
	
	private String code;

	private AreaCodeEnum(String code,String name){
		this.code = code;
		this.name = name;
	}

	public static List<PairVo> getAll(){
		List<PairVo> list = new ArrayList<PairVo>();
		for(AreaCodeEnum e : AreaCodeEnum.values()){
			list.add(new PairVo(e.getCode(),e.getName()));
		}
		return list;
	}
	
	public static PairVo getBy(String code){
		if(code == null || code.trim().length() == 0){
			return null;
		}
		for(AreaCodeEnum e : AreaCodeEnum.values()){
			if(e.getCode().equals(code)){
				return new PairVo(e.getCode(),e.getName());
			}
		}
		return null;
	}
	
	public static String getNameBy(String code){
		if(code == null || code.trim().length() == 0){
			return null;
		}
		for(AreaCodeEnum e : AreaCodeEnum.values()){
			if(e.getCode().equals(code)){
				return e.getName();
			}
		}
		return null;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
