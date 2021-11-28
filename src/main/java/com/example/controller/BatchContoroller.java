package com.example.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Batch;
import com.example.domain.Sample;
import com.example.service.BatchService;

@Controller
@RequestMapping("/batchController")
public class BatchContoroller {
	

	@Autowired
	private BatchService batchService;

	@RequestMapping("")
	public String index() {
		return "test";
	}

	@RequestMapping("/export")
	public String export(Model model) {
		int[] idList = { 1, 2, 3 };
		String[] nameList = { "佐藤", "鈴木", "高橋" };

		try {
			FileWriter f = new FileWriter("C:\\env\\app\\sts-4.11.0.RELEASE\\csv用ファイル.csv", false);
			PrintWriter p = new PrintWriter(new BufferedWriter(f));

			p.print("社員番号");
			p.print(" ");
			p.print("名前");
			p.println();

			for (int i = 0; i < idList.length; i++) {
				p.print(idList[i]);
				p.print(",");
				p.print(nameList[i]);
				p.println();
			}

			String message = "成功";
			model.addAttribute("message", message);

			p.close();
			return index();

		} catch (IOException ex) {
			ex.printStackTrace();

			String message = "失敗";
			model.addAttribute("message", message);

			return index();

		}
	}

	// こっちを作成
	@RequestMapping("/download")
	public String download(Model model) {

		List<Sample> sampleList = batchService.findAll();
		
		Path path = Paths.get("C:\\env\\app\\sts-4.11.0.RELEASE\\csv用TestFile.csv");

		try{
		  Files.createFile(path);
		}catch(IOException e){
		  System.out.println(e);
		}

		try {
			FileWriter f = new FileWriter("C:\\env\\app\\sts-4.11.0.RELEASE\\csv用TestFile.csv", false);
			PrintWriter p = new PrintWriter(new BufferedWriter(f));

			p.print("role_id");
			p.print(",");
			p.print("role_name");
			p.print(",");
			p.print("role_description");
			p.println();

			for (Sample sample : sampleList) {
				p.print(sample.getRoleId());
				p.print(",");
				p.print(sample.getRoleName());
				p.print(",");
				p.print(sample.getRoleDescription());
				p.println();
			}

			p.close();
			return index();

		} catch (IOException ex) {
			ex.printStackTrace();

			return index();

		}
	}

	//コンソール出力
	@RequestMapping("/upload")
	public String upload() {

		try {

			FileInputStream fi = new FileInputStream("C:\\env\\app\\sts-4.11.0.RELEASE\\csv用ファイル.csv");
			InputStreamReader is = new InputStreamReader(fi);
			BufferedReader br = new BufferedReader(is);

			String line;

			int i = 0;

			String[] arr = null;
			while ((line = br.readLine()) != null) {
				if (i == 0) {
					arr = new String[] { "role_id", "role_name", "role_description" };
					arr = line.split(",");
					
					String[]test=line.split(",");
						//System.out.println("test0:"+test[0]);
						//System.out.println("test1:"+test[1]);
						//System.out.println("test2:"+test[2]);
				}else {
					System.out.println("------------------");
					
					System.out.println("データ"+i+"件目");
					
					String[]data=line.split(",");
					
					int colno=0;
					for(String column:arr) {
						System.out.println(data[colno]);
						colno++;
					}
				}
				
				i++;

			}
			br.close();

			return "test";
		} catch (Exception e) {
			e.printStackTrace();
			return "test";
		}
	}
	
	
	//DBにアップロード
	@RequestMapping("/uploadForDb")
	public String uploadForDb() {
		
		
		
		try {
			
			FileInputStream fi = new FileInputStream("C:\\env\\app\\sts-4.11.0.RELEASE\\csv用ファイル.csv");
			InputStreamReader is = new InputStreamReader(fi);
			BufferedReader br = new BufferedReader(is);
			
			

			String line;

			int i = 0;
			
			Batch batch=new Batch();
			String[] arr = null;
			
			

			while((line=br.readLine())!=null) {
				
				

				
				if(i==0) {
					
					
					
					arr=line.split(",");
					arr=new String[] {"role_id","role_name","role_description"};
					
					

				}else {
				
					

					
				String[]dataList= line.split(",");

				batch.setRoleId(dataList[0]);
				batch.setRoleName(dataList[1]);
				batch.setRoleDescription(dataList[2]);
				
				System.out.println("batch:"+batch);
				
				batchService.insertCsv(batch);
				
				}
				i++;
			}
			
	
			
			/**while ((line = br.readLine()) != null) {
				if (i == 0) {
					arr = new String[] { "role_id", "role_name", "role_description" };
					arr = line.split(",");

				}else {
					System.out.println("------------------");
					
					System.out.println("データ"+i+"件目");
					
					String[]data=line.split(",");
					
					int colno=0;
					for(String column:arr) {
						System.out.println(column+":"+data[colno]);
						colno++;
					}
				}
				
				i++;

			}*/
			br.close();

			return "test";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("失敗⇒catch");
			return "test";
		}
	}
}
