package com.mycompany.myapp.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import lombok.Data;

@Data
public class 文件db {

	String sPath = "C:\\tmp\\";

	String[] fileName = { "姓名", "入社年月日", "生年月日", "性別", "番号", "契約種類", "削除年月日" };

	Map<String, Map> map_data = new HashMap();

	public void 社員情報読み込み() {

		for (int i = 0; i < fileName.length; i++) {
			String sf = sPath + fileName[i] + ".txt";
			try {
				File files = new File(sf);
				Map map = new HashMap();

				if (checkBeforeReadfile(files)) {
					BufferedReader br = new BufferedReader(new FileReader(sf));

					String str;
					while ((str = br.readLine()) != null) {
						/*
						 * 21.尽量避免使用split
						 * 除非是必须的，否则应该避免使用split，split由于支持正则表达式，所以效率比较低，如果是频繁的几十，
						 * 几百万的调用将会耗费大量资源，如果确实需 要频繁的调用split，
						 * 可以考虑使用apache的StringUtils.split(string,char)，频繁split的可以缓存结果。
						 *     2019-01-11
						*/
//						String skey = str.split(",")[0];
//						String svalue = str.split(",")[1];
						String[] reslut = StringUtils.split(str, ",");
						String skey = reslut[0];
						String svalue = reslut[1];
						map.put(skey, svalue);
					}
					System.out.println(map.toString());
					br.close();
					map_data.put(fileName[i], map);

				} else {
					System.out.println("ファイルに書き込めません");
				}
			} catch (FileNotFoundException e) {
				System.out.println(e);
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}

	private static boolean checkBeforeReadfile(File file) {
		if (file.exists()) {
			if (file.isFile() && file.canRead()) {
				return true;
			}
		}

		return false;
	}

	public void 文件書込(String path,String 書込内容 ){
		PrintWriter pw;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(path,true)));
			pw.println(書込内容);
			pw.close();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

	public int 採番(String sPath) throws IOException {

		FileReader fr = new FileReader(sPath); 	//这里定义一个字符流的输入流的节点流，用于读取文件（一个字符一个字符的读取）
		BufferedReader br = new BufferedReader(fr);// 在定义好的流基础上套接一个处理流，用于更加效率的读取文件（一行一行的读取）
		int x = 0; 								// 用于统计行数，从0开始
		while (br.readLine() != null) { 		// readLine()方法是按行读的，返回值是这行的内容
			x++; 								// 每读一行，则变量x累加1
		}
		return x; 								//返回总的行数
	}

	public List<String> 取得全員番号ID(){
		List<String> IDList = new ArrayList();
		Map<String, String> 小map = map_data.get("番号");
		for (Map.Entry<String, String> entry : 小map.entrySet()) {
			String strKey= entry.getKey().replace("\\", "");
			IDList.add(strKey);
		}
		return IDList;
	}

	public String 取得社員ID_by社員番号(String s番号){
		List<String> IDList = new ArrayList();
		Map<String, String> 小map = map_data.get("番号");
		for (Map.Entry<String, String> entry : 小map.entrySet()) {
			if(StringUtils.equals(s番号, entry.getValue())) {
				return entry.getKey().replace("\\", "");
			}
		}
		return null;
	}


}
