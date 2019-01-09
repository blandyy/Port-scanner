/**
*@author created by 穆洪桥
*@date 2017年7月6日---上午6:25:21
*@problem
*@answer
*@action
*/

package 端口扫描器;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ui extends JFrame implements  ActionListener{

	private JLabel scaIp,startport,endport,scanum,showResult;
	private JTextField fscaIp,fstartport,fendport,fscanum;
	private JButton startScanner,exitScanner ,clear,reset;
	private JScrollPane result ;
	private JPanel top,bottom ;
	private JTextArea message ;
	public ui(){
		this.setTitle("端口扫描器");
		scaIp = new JLabel("扫描的Ip");
		startport = new JLabel("起始端口");
		endport = new JLabel("结束端口");
		scanum = new JLabel("每个线程扫描端口数");
		startScanner = new JButton("扫描");
		exitScanner = new  JButton("退出");
		clear = new JButton("清空") ;
		reset = new JButton("重置") ;
		fscaIp = new JTextField(20) ;
		fstartport = new JTextField(5) ;
		fendport = new JTextField(5) ;
		fscanum = new JTextField(5) ;
		message = new JTextArea(20,20) ;
		result = new JScrollPane(message) ;
		showResult = new JLabel("扫描结果") ;
		result.setColumnHeaderView(showResult) ;
		top = new JPanel() ;
		bottom = new JPanel() ;
		top.add(scaIp);
		top.add(fscaIp);
		top.add(startport);
		top.add(fstartport);
		top.add(endport);
		top.add(fendport);
		top.add(scanum);
		top.add(fscanum);
		bottom.add(startScanner) ;	
		bottom.add(clear);
		bottom.add(reset);
		bottom.add(exitScanner) ;
		this.add(top,BorderLayout.NORTH);
		this.add(result,BorderLayout.CENTER) ;
		this.add(bottom,BorderLayout.SOUTH) ;
		startScanner.addActionListener(this) ;
		exitScanner.addActionListener(this) ;
		clear.addActionListener(this) ;
		reset.addActionListener(this) ;
		setSize(1000, 500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private boolean checkIP(String str) {
        Pattern pattern = Pattern
                .compile("^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]"
                        + "|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$");
        return pattern.matcher(str).matches();
    }
	public static void main(String[] args){
		new ui();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}

