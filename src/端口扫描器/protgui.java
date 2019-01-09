/**
*@author created by 穆洪桥
*@date 2017年7月5日---上午9:40:01
*@problem
*@answer
*@action
*/

package 端口扫描器;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class protgui{
	public static void main(String[] args){
		new GUI();
	}
}
class GUI extends JFrame implements  ActionListener {	
	private JLabel jstartIp,jendIp,jstartPort,jendPort,jportOfThread ,showResult ,empty,type ,status;
	private JTextField fstartIp,fendIp,fstartPort,fendPort,fportOfThread ;
	private JScrollPane result ;
	private JComboBox comboBox ;
	private JButton startScanner,exitScanner ,clear,reset,write;
	private JPanel top,bottom ;
	private JTextArea message ;
	private String startIpStr ,endIpStr;
	private int startPort,endPort,portOfThread ,threadNum ;
	static ArrayList al=new ArrayList();
	public GUI(){
		this.setTitle("端口扫描器") ;
		jstartIp = new JLabel("扫描的Ip") ;
		jstartPort = new JLabel("起始端口") ;
		jendPort = new JLabel("结束端口") ;
		jportOfThread = new JLabel("每个线程扫描端口数") ;
		status=new JLabel("未开始扫描") ;
		showResult = new JLabel("扫描结果") ;
		jendIp = new JLabel("结束Ip"); 
		type = new JLabel("选择扫描的类型") ;
		startScanner = new JButton("扫描");
		exitScanner = new  JButton("退出");
		clear = new JButton("清空") ;
		reset = new JButton("重置") ;
		write = new JButton("写入文件") ;
		fstartIp = new JTextField(20) ;
		fendIp = new JTextField(20) ;
		fstartPort = new JTextField(5) ;
		fendPort = new JTextField(5) ;
		fportOfThread = new JTextField(5) ;
		message = new JTextArea(20,20) ;
		result = new JScrollPane(message) ;
		result.setColumnHeaderView(showResult) ;
		comboBox = new JComboBox() ;
		comboBox.addItem("地址");
		comboBox.addItem("地址段");
		jendIp.setVisible(false) ;
		fendIp.setVisible(false) ;
		top = new JPanel() ;
		top.add(type);
		top.add(comboBox) ;
		top.add(jstartIp) ;
		top.add(fstartIp) ;
		top.add(jendIp) ;
		top.add(fendIp) ;
		top.add(jstartPort) ;
		top.add(fstartPort) ;
		top.add(jendPort) ;	
		top.add(fendPort) ;
		top.add(jportOfThread) ;
		top.add(fportOfThread) ; 		
		bottom = new JPanel() ;
		bottom.add(status) ;
		bottom.add(startScanner) ;	
		bottom.add(clear);
		bottom.add(reset);
		bottom.add(exitScanner) ;
		bottom.add(write) ;
		this.add(top,BorderLayout.NORTH);
		this.add(result,BorderLayout.CENTER) ;
		this.add(bottom,BorderLayout.SOUTH) ;
		comboBox.addActionListener(this) ;
		startScanner.addActionListener(this) ;
		exitScanner.addActionListener(this) ;
		clear.addActionListener(this) ;
		reset.addActionListener(this) ;
		write.addActionListener(this) ;
		setSize(1200, 500);
		Toolkit kit = Toolkit.getDefaultToolkit();    // 定义工具包
	    Dimension screenSize = kit.getScreenSize();   // 获取屏幕的尺寸
	    int screenWidth = screenSize.width/2;         // 获取屏幕的宽
	    int screenHeight = screenSize.height/2;       // 获取屏幕的高
	    int height = this.getHeight();
	    int width = this.getWidth();
	    setLocation(screenWidth-width/2, screenHeight-height/2);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		File f = new File("E:/port.txt");
		if(!f.exists()) {
    		try {
				f.createNewFile();
			} catch (IOException ee) {
				
				ee.printStackTrace();
			}
               
        }  
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==startScanner){ //点击扫描按钮
			//点击时刻
			startIpStr = fstartIp.getText().trim() ;   //得到输入的Ip
			if(checkIP(startIpStr)){
				//判断是否为数字
				try{
					startPort = Integer.parseInt(fstartPort.getText().trim()) ;//130
					endPort =  Integer.parseInt(fendPort.getText().trim()) ;//140
					portOfThread  =Integer.parseInt(fportOfThread.getText().trim())  ;
					threadNum = (endPort-startPort)/portOfThread+1 ;
					//段端口号的范围
					if(startPort<0||endPort>65535||startPort>endPort){
						JOptionPane.showMessageDialog(this, "端口号范围：0~65535,并且最大端口号应大于最小端口号！") ;
						}
					else{
						if(portOfThread>endPort-startPort||portOfThread<1){
							JOptionPane.showMessageDialog(this, "每个线程扫描的端口数不能大于所有的端口数且不能小于1") ;
						}else{
							if(((String) comboBox.getSelectedItem()).equals("地址")){
								
								message.append("--------------------------------------------------------------------------------"+"\n") ;
								SimpleDateFormat da = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
						        Date date = new Date();  
						        String strdate = da.format(date);
								message.append(strdate+"开始端口扫描\n");
								//message.append("正在扫描  "+startIpStr+"          每个线程扫描端口个数"+portOfThread+"\n"+"开启的线程数"+threadNum+"\n") ;
								message.append("正在扫描  "+startIpStr+"\n") ;
								message.append("开始端口  "+startPort+"         结束端口" +endPort+"\n") ;	
								message.append("主机名:"+getHostname(startIpStr)+"\n");
								message.append("开放的端口如下："+"\n") ;
								for(int i = startPort;i <= endPort;) {
									if((i + portOfThread) <= endPort) {
										new Scan(i, i + portOfThread,startIpStr).start();
										i += portOfThread;
									}
									else {
										new Scan(i, endPort,startIpStr).start();
										i += portOfThread;
									}			
								}
								
							}else{
								endIpStr = fendIp.getText() ;
								if(checkIP(endIpStr)){
									//扫描Ip地址段	
									Set ipSet = new HashSet<Object>() ;
									int start = Integer.valueOf(startIpStr.split("\\.")[3]);
									int end = Integer.valueOf(endIpStr.split("\\.")[3]);
									String starts = startIpStr.split("\\.")[0]+"."+startIpStr.split("\\.")[1]+"."+startIpStr.split("\\.")[2];
							    	//生成IP地址
							    	for(int i = start;i<=end;i++){
							    		ipSet.add(starts+"."+i) ;    // 每个地址存入集合					
							    	}
							    	for (Object str : ipSet) {
							    		new ScanIp(str.toString()).start() ;
							    	}
								}else{		
							    	JOptionPane.showMessageDialog(this, "请输入正确的Ip地址") ;
								}
								
							}										
						}				
					}
				}
				catch(NumberFormatException e1){
					JOptionPane.showMessageDialog(this, "错误的端口号或端口号和线程数必须为整数") ;				
				}
			}	
			else{
				JOptionPane.showMessageDialog(this, "请输入正确的Ip地址") ;
			   }
			}			
		else if(e.getSource()==reset){
			 fstartIp.setText("") ;
			 fstartPort.setText("") ;
			 fendPort.setText("") ;
			 fportOfThread.setText("") ; 
		}
		else if(e.getSource()==clear){
			message.setText("") ;
			System.out.println((String) comboBox.getSelectedItem());
		}
		else if(e.getSource()==exitScanner){
			System.exit(1);
		}else if(e.getSource()==comboBox){
			String type=(String) comboBox.getSelectedItem();
			if(type.equals("地址")){
				jendIp.setVisible(false) ;
				fendIp.setVisible(false) ;
				jstartIp.setText("扫描的Ip") ;
			}else{
				jendIp.setVisible(true) ;
				fendIp.setVisible(true) ;
				jstartIp.setText("开始Ip") ;
			}
		}else if(e.getSource()==write){
			File fw = new File("E:/port.txt");
			FileWriter wf;
			try {
				wf = new FileWriter(fw,true);
				String re=message.getText();
				wf.write(re+"\r\n");
				wf.close();
				message.append("写入文件成功"+fw.toString());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	//扫描端口地址的线程
	class Scan extends Thread{
		int maxPort, minPort;
		String Ip;
		Scan(int minPort, int maxPort,String Ip){
			this.minPort=minPort ;
			this.maxPort=maxPort ;
			this.Ip=Ip;
		}
		@SuppressWarnings("unchecked")
		public void run() {
				 Socket socket = null ;				
				 for(int i = minPort;i<maxPort;i++){
					try {						
						socket=new Socket(Ip, i);					
						//通过端口号调用数据库信息	
						finddb(Ip,i);
						//message.append("ip:"+Ip+"    prot:"+i+"\n");
		                socket.close();
					} catch (Exception e) {
						message.append("");
					}  
	    			 status.setText("正在扫描"+i) ;
				 }		
				 status.setText("扫描结束") ;
		   }
	} 
	//扫描Ip地址段查看合法Ip的线程
	class ScanIp extends Thread{
    	String  Ip ;
    	ScanIp(String  Ip ){
    		this.Ip = Ip ;
    	}  	   	
    	 public synchronized void run(){
    	 try {		
    		 for(int i = startPort;i <= endPort; i++) {
	   			//扫描开放的Ip
    			InetAddress.getByName(Ip);
				if((i + portOfThread) <= endPort) {
					new Scan(i, i + portOfThread,Ip).start();
					i += portOfThread;
				}
				else {
					new Scan(i, endPort,Ip).start();
					i += portOfThread;
				}				
			}   		
		 } catch (Exception e) {		
			System.out.println(Ip+"\n");
		 }
    	 
    	}
    }
	//查询数据库中端口号的信息
	synchronized void finddb(String ip,int port){
		try {
			Class.forName("com.hxtt.sql.access.AccessDriver");
			Connection con = DriverManager.getConnection("jdbc:Access:///d:/Database1.accdb");
			Statement stat =con.createStatement();
			String sql = "select * from porttest where id='"+port+ "'";
			ResultSet rs = stat.executeQuery(sql);
			while(rs.next()) {
	               message.append("ip地址："+ip+"  端口名："+rs.getString(1)+"  端口类型："+rs.getString(2)+"  端口描述："+rs.getString(3)+"\n");
	           }
			rs.close();
			stat.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 判断输入的IP是否合法
    private boolean checkIP(String str) {
        Pattern pattern = Pattern
                .compile("^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]"
                        + "|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$");
        Matcher mat=pattern.matcher(str);
		return mat.matches();
    }
   //根据Ip获得主机名
    public static  synchronized String getHostname(String host){
		InetAddress addr ;
		try {
			addr = InetAddress.getByName(host);		
			return addr.getHostName();
		} catch (UnknownHostException e) {
			return "网络不通或您输入的信息无法构造InetAddress对象！";
		}		
	}
}
