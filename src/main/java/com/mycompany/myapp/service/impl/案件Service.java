package com.mycompany.myapp.service.impl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.util.ObjectUtils;

import com.mycompany.myapp.bean.案件Bean;
import com.mycompany.myapp.bean.案件検索Bean;
import com.mycompany.myapp.service.文件db;
import com.mycompany.myapp.service.親Service;

public class 案件Service extends 親Service{
	// "名称" 必须放在0号位，否则全件检索时会出问题
	String[] fileName = { "案件名称", "案件概要", "案件場所", "職種", "工程", "案件開始日", "予定終了日", "実際終了日", "人数" };

	文件db file_db = new 文件db("案件");

	public List<案件Bean> 検索案件_by検索Bean(案件検索Bean bean) {

		file_db.情報読み込み(fileName);

		Map<String,List<String>> 中間結果IDList = get中間結果_by案件検索Bean(bean);

		List<String> 最終結果IDList = get最終結果_by中間結果(中間結果IDList);

		return 取得検索結果_by最終結果(最終結果IDList);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<案件Bean> 取得検索結果_by最終結果(List<String> 最終結果idList) {

		List<案件Bean> 案件BeanList = new ArrayList();

		for (String 案件id : 最終結果idList) {

			案件BeanList.add(取得案件情報_by案件id(案件id));
		}

		return 案件BeanList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private 案件Bean 取得案件情報_by案件id(String 案件id) {

		Map<String, Map> 大Map = file_db.getMap_data();

		案件Bean 案件bean = new 案件Bean();

		案件bean.setS_ID(案件id);

		for (Map.Entry<String, Map> entry : 大Map.entrySet()) {

			if (entry == null) {

				continue;

			}

			Map<String, String> 小Map;

			String 小Map名 = entry.getKey();

			switch (小Map名) {

			case "案件名称":
				小Map = entry.getValue();
				案件bean.set案件名称(小Map.get(案件id));
				break;
			case "案件概要":
				小Map = entry.getValue();
				案件bean.set案件概要(小Map.get(案件id));
				break;
			case "時期":
				小Map = entry.getValue();
				案件bean.set案件開始日(小Map.get(案件id));
				break;
			case "場所":
				小Map = entry.getValue();
				案件bean.set案件場所(小Map.get(案件id));
				break;
			case "人数":
				小Map = entry.getValue();
				案件bean.set人数(小Map.get(案件id));
				break;
			}
		}
		return 案件bean;
	}



	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<String,List<String>> get中間結果_by案件検索Bean(案件検索Bean bean) {

		Map<String,List<String>> 中間結果list = new LinkedHashMap();
		if (StringUtils.isNotEmpty(bean.getS_ID())) {

			/*IDList_name = getIDList_byNameandValue("姓名", "Aさん");*/
			中間結果list.put("ID",Arrays.asList(bean.getS_ID()));
		}

		if (StringUtils.isNotEmpty(bean.get案件名称())) {

			//IDList_name = getIDList_byNameandValue("案件名称", "Aさん");
			中間結果list.put("案件名称",getIDList_by小Map名andValue("案件名称", bean.get案件名称(), "=="));

		}
		Object getIDList_byNameandValue;
		if (StringUtils.isNotEmpty(bean.get案件概要())) {

			//IDList_name = getIDList_byNameandValue("案件概要", "Aさん");
			中間結果list.put("案件概要",getIDList_by小Map名andValue("案件概要", bean.get案件概要(), "like"));

		}
		if (StringUtils.isNotEmpty(bean.get案件概要())) {

			//IDList_name = getIDList_byNameandValue("案件場所", "Aさん");
			中間結果list.put("案件場所",getIDList_by小Map名andValue("案件場所", bean.get案件場所(), "like"));

		}
		if (StringUtils.isNotEmpty(bean.get職種())) {

			//IDList_name = getIDList_byNameandValue("職種", "Aさん");
			中間結果list.put("職種",getIDList_by小Map名andValue("職種", bean.get職種(), "like"));

		}
		if (StringUtils.isNotEmpty(bean.get工程())) {

			//IDList_name = getIDList_byNameandValue("職種", "Aさん");
			中間結果list.put("工程",getIDList_by小Map名andValue("工程", bean.get工程(), "like"));

		}

		if (StringUtils.isNotEmpty(bean.get案件開始日_開始())) {

			中間結果list.put("案件開始日",getIDList_by小Map名andValue("案件開始日", bean.get案件開始日_開始(), ">="));

		}

		if (StringUtils.isNotEmpty(bean.get案件開始日_終了())) {

			中間結果list.put("案件開始日",getIDList_by小Map名andValue("案件開始日", bean.get案件開始日_終了(), "<="));

		}

		if (StringUtils.isNotEmpty(bean.get案件場所())) {

			中間結果list.put("場所",getIDList_by小Map名andValue("場所", bean.get案件場所(), ""));
		}


		if (StringUtils.isNotEmpty(bean.get人数_開始())) {

			中間結果list.put("人数",getIDList_by小Map名andValue("人数", bean.get人数_開始(), ">="));
		}

		if (StringUtils.isNotEmpty(bean.get人数_終了())) {

			中間結果list.put("人数",getIDList_by小Map名andValue("人数", bean.get人数_終了(), "<="));
		}

		if (StringUtils.isNotEmpty(bean.get予定終了日_開始())) {

			中間結果list.put("予定終了日",getIDList_by小Map名andValue("予定終了日", bean.get予定終了日_開始(), ">="));
		}

		if (StringUtils.isNotEmpty(bean.get予定終了日_終了())) {

			中間結果list.put("予定終了日",getIDList_by小Map名andValue("予定終了日", bean.get予定終了日_終了(), "<="));
		}

		if (StringUtils.isNotEmpty(bean.get実際終了日_開始())) {

			中間結果list.put("実際終了日",getIDList_by小Map名andValue("実際終了日", bean.get実際終了日_開始(), ">="));
		}

		if (StringUtils.isNotEmpty(bean.get予定終了日_終了())) {

			中間結果list.put("実際終了日",getIDList_by小Map名andValue("実際終了日", bean.get実際終了日_終了(), "<="));
		}
		/*
		if (StringUtils.isNotEmpty(bean.get契約種類())) {

			中間結果list.put("契約種類",getIDList_by小Map名andValue("契約種類", bean.get契約種類(), "=="));
		}
		if (StringUtils.isNotEmpty(bean.get電話名称())) {

			中間結果list.put("電話名称",getIDList_by小Map名andValue("電話名称", bean.get電話名称(), "=="));
		}
*/

		// 无条件检索时
		if(allfieldIsNUll(bean)) {

			//中間結果list.put("名称",file_db.取得全ID_by項目名("名称"));
			中間結果list.put("ID",file_db.取得全部ID(fileName[0]));
		}

		// 削除時の対応
		中間結果list.put("削除年月日",getIDList_by小Map名andValue("削除年月日", "", "!="));

		return 中間結果list;

	}

	private List<String> getIDList_by小Map名andValue(String 小Map名, String value, String s計算方法) {

		List<String> IDList = new ArrayList();

		Map<String, Map> 大Map = file_db.getMap_data();

		Map<String, String> 小map = 大Map.get(小Map名);

		if (小map != null && !小map.isEmpty()) {
		} else {
			return null;
		}

		for (Map.Entry<String, String> entry : 小map.entrySet()) {

			String str1= entry.getValue().replace("\\", "");
			String str2= value.replace("\\", "");

			switch(s計算方法) {

			case ">=":
				if (str1.equals(str2)) {

					String id = entry.getKey();

					IDList.add(id);
				}

			case ">":

				if (NumberUtils.toInt(str1) > NumberUtils.toInt(str2)) {

					String id = entry.getKey();

					IDList.add(id);
				}

				break;

			case "<=":
				if (str1.equals(str2)) {

					String id = entry.getKey();

					IDList.add(id);
				}

			case "<":

				if (NumberUtils.toInt(str1) < NumberUtils.toInt(str2)) {

					String id = entry.getKey();

					IDList.add(id);
				}
				break;

			case "==":

				if (str1.equals(str2)) {

					String id = entry.getKey();

					IDList.add(id);
				}

				break;

			case "!=":

				if ( !str1.equals(str2)) {

					String id = entry.getKey();

					IDList.add(id);
				}

				break;

			case "like":

				if (value_like(entry.getValue(), value)) {

					String id = entry.getKey();

					IDList.add(id);
				}
			}


		}

		return IDList;
	}

	private boolean value_like(String str1, String str2) {
		// java 两个字符串取交集
        HashSet<String> result = new HashSet<String>();
        int length1 = str1.length();
        int length2 = str2.length();
        for (int i = 0; i < length1; i++) {
            for (int j = 0; j < length2; j++) {
                String char1 = str1.charAt(i) + "";
                String char2 = str2.charAt(j) + "";
                if (char1.equals(char2))
                {
                    result.add(char1);
                }
            }
        }

        return result.isEmpty()? false:true;
    }

	public String 追加案件_by案件Bean(案件Bean bean) {

		文件db file_db = new 文件db("案件");

		// ①チェック入力
		file_db.情報読み込み(fileName);

		// ②追加処理
		追加案件_byFile_db_案件Bean(file_db, bean);


		return "";
	}

	private void 追加案件_byFile_db_案件Bean(文件db file_db2, 案件Bean bean) {

		String path;
		String ID = null;
		try {
			// ①採番
			ID = file_db.採番(file_db.getSPath() + "案件名称" + ".txt")+1 +"";

		} catch (IOException e) {

			e.printStackTrace();
		}

		for(String s文件名 : fileName) {

			path = file_db.getSPath() +  s文件名 + ".txt";

			switch(s文件名) {

			case "案件名称":

				if(!StringUtils.isEmpty(bean.get案件名称())) {
					file_db.文件書込(path, ID + "," + bean.get案件名称());
				}
				break;

			case "案件概要":

				if(!StringUtils.isEmpty(bean.get案件概要())) {
					file_db.文件書込(path, ID + "," + bean.get案件概要());
				}
				break;

			case "人数":

				if(!StringUtils.isEmpty(bean.get人数())) {
					file_db.文件書込(path, ID + "," + bean.get人数());
				}
				break;

			case "案件開始日":

				if(!StringUtils.isEmpty(bean.get案件開始日())) {
					file_db.文件書込(path, ID + "," + bean.get案件開始日());
				}
				break;

			case "職種":

				if(!StringUtils.isEmpty(bean.get職種())) {
					file_db.文件書込(path, ID + "," + bean.get職種());
				}
				break;



		case "工程":

			if(!StringUtils.isEmpty(bean.get工程())) {
				file_db.文件書込(path, ID + "," + bean.get工程());
			}
			break;



		case "案件場所":

			if(!StringUtils.isEmpty(bean.get案件場所())) {
				file_db.文件書込(path, ID + "," + bean.get案件場所());
			}
			break;

		case "予定終了日":

			if(!StringUtils.isEmpty(bean.get予定終了日())) {
				file_db.文件書込(path, ID + "," + bean.get案件概要());
			}
			break;

		case "実際終了日":

			if(!StringUtils.isEmpty(bean.get実際終了日())) {
				file_db.文件書込(path, ID + "," + bean.get実際終了日());
			}
			break;
		}
		}
	}

	public void 更新案件_by案件Bean(案件Bean bean) {
		// 更新时
		// 将最新信息追加到指定文件
		文件db file_db = new 文件db("案件");
		//String ID = bean.getOld_名称();
		file_db.情報読み込み(fileName);
		String ID = bean.getS_ID();

		if(!StringUtils.equals(bean.getOld_人数(), bean.get人数())) {
			String path = file_db.getSPath() +  "人数.txt";
			file_db.文件書込(path, ID + "," + bean.get人数());
		}

		if(!StringUtils.equals(bean.getOld_案件名称(), bean.get案件名称())) {
			String path = file_db.getSPath() +  "案件名称.txt";
			file_db.文件書込(path, ID + "," + bean.get案件名称());

		}
		if(!StringUtils.equals(bean.getOld_案件概要(), bean.get案件概要())) {
			String path = file_db.getSPath() +  "案件概要.txt";
			file_db.文件書込(path, ID + "," + bean.get案件概要());

		}

		if(!StringUtils.equals(bean.getOld_案件開始日(), bean.get案件開始日())) {
			String path = file_db.getSPath() +  "案件開始日.txt";
			file_db.文件書込(path, ID + "," + bean.get案件開始日());

		}
		if(!StringUtils.equals(bean.getOld_案件場所(), bean.get案件場所())) {
			String path = file_db.getSPath() +  "案件場所.txt";
			file_db.文件書込(path, ID + "," + bean.get案件場所());
		}


		if(!StringUtils.equals(bean.getOld_職種(), bean.get職種())) {
			String path = file_db.getSPath() +  "職種.txt";
			file_db.文件書込(path, ID + "," + bean.get職種());

		}

		if(!StringUtils.equals(bean.getOld_工程(), bean.get工程())) {
			String path = file_db.getSPath() +  "工程.txt";
			file_db.文件書込(path, ID + "," + bean.get工程());

		}
		if(!StringUtils.equals(bean.getOld_予定終了日(), bean.get予定終了日())) {
			String path = file_db.getSPath() +  "予定終了日.txt";
			file_db.文件書込(path, ID + "," + bean.get予定終了日());
		}
		if(!StringUtils.equals(bean.getOld_実際終了日(), bean.get実際終了日())) {
			String path = file_db.getSPath() +  "実際終了日.txt";
			file_db.文件書込(path, ID + "," + bean.get実際終了日());
		}
	}

	public void 削除案件_by案件Bean(案件Bean bean) {
		String path = file_db.getSPath() +  "削除年月日.txt";
		//String ID = bean.getOld_名称();

		file_db.情報読み込み(fileName);
		String ID = bean.getS_ID();

		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");//设置日期格式
		String sDate = df.format(new Date()).toString();// new Date()为获取当前系统时间
		file_db.文件書込(path, ID + "," + sDate);

		// 1、削除时（采取逻辑删除。就是并不是真的删除了。而是做上删除的标记）
		//    追加一个叫削除时间的文件
		//    -----------内容如下-------------
		//    【采番】，【削除时间】
		//    【采番】，【削除时间】
		//    --------------------------------

		// 2、検索時 ，自动附加【无删除记录】的条件）
		//    凡是上面有记录的。
		//    都不会取得信息
	}

	public static boolean allfieldIsNUll(Object o){
	    try{
	        for(Field field:o.getClass().getDeclaredFields()){
	            field.setAccessible(true);//把私有属性公有化
	            Object object = field.get(o);
	            if(object == null){
	            	continue;
	            }
	            if(object instanceof CharSequence){
	                if(!StringUtils.isEmpty((String)object)){
	                    return false;
	                }
	            }else{
	                if(!ObjectUtils.isEmpty((Object[]) object)){
	                    return false;
	                }
	            }

	        }
	    }catch (Exception e){
	        e.printStackTrace();
	    }
	    return true;
	}
}

