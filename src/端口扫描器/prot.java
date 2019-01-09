/**
*@author created by 穆洪桥
*@date 2017年7月6日---上午8:29:42
*@problem
*@answer
*@action
*/

package 端口扫描器;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
//1-110000
public class prot extends Thread{
	static ArrayList arr = new ArrayList();
	static String ccc="";
	public void in(){
		File f2 = new File("E:/port111.txt");
		SimpleDateFormat da = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date date = new Date();  
        String strdate = da.format(date);
		try {
			FileWriter wfs = new FileWriter(f2,true);
			wfs.write("###扫描日志\r\n");
			wfs.write(strdate+"开始端口扫描\r\n");
			System.out.println(strdate+"开始端口扫描\r\n");
			
			wfs.close();
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
	}

	
	String ip;
	String s;
	String e;
	public prot(String ips,String ss,String es){
		this.ip=ips;
		this.s=ss;
		this.e=es;
	}
	public void run(){
		//Scanner sc = new Scanner(System.in);
		File f = new File("E:/port111.txt");
		ArrayList l=new ArrayList();
		if(!f.exists()) {
    		try {
				f.createNewFile();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
               
        }  
		int starport,endport;
		starport = Integer.parseInt(s);
		endport = Integer.parseInt(e);
		if(starport<1||starport>65535||endport<1||endport>65535){
			
			System.out.printf("端口在1~65535之间!");
			return;
		}else if(starport>endport){ 
			System.out.println("起始端口大于结束端口");
			return;
		}
		
		InetAddress address = null;
		try {
			address = InetAddress.getByName(ip);
		} catch (UnknownHostException e1) {
				
			e1.printStackTrace();
		}
			
			
		for(int nport=starport;nport<=endport;nport++){        
			
			try{
				System.out.print("Scanning "+nport);        
				Socket s1=new Socket(address,nport);
				s1.close();       
				l.add(""+nport);
				
				System.out.println(":open");
				//System.out.println(s1.toString());
			}catch(IOException e1){
				System.out.println(":close");
			}
		}

		for(int i=0;i<l.size();i++){
			String re = "port: "+l.get(i)+" is Open";
			try {
    			FileWriter wf = new FileWriter(f,true);
    			wf.write(re+"\r\n");
    			
    			wf.close();
    		} catch (IOException e1) {
    			
    			e1.printStackTrace();
    		}
			
		}
	}

	public static void main(String[] args) {
		prot p1=new prot("127.0.0.1","135","150");

		p1.in();
		p1.start();
		try {
			p1.join();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
		File r = new File("E:/port111.txt");
		try {
			FileReader in = new FileReader(r);
			BufferedReader br = new BufferedReader(in);
			String s="";
			
			while((s=br.readLine())!=null){
				if(s.equals("###扫描日志")){
					//System.out.println(s);
					arr.clear();
					//ccc="";
				}
				//System.out.println(s);
				arr.add(s);
				//ccc=ccc+s+"\n";
			}
			br.close();
			for(int i=0;i<arr.size();i++){
				System.out.println(arr.get(i));
			}
			
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		
	}
}