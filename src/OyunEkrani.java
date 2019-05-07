import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ConcurrentModificationException;

public class OyunEkrani extends JFrame
{
	public OyunEkrani( String title ) throws HeadlessException
	{
		super(title);
	}
	public static void main( String[] args )
	{
		OyunEkrani ekran = new OyunEkrani("Uzay Oyunu");
		ekran.setResizable(false);
		
		ekran.setFocusable(false); //odaklanmanın JFrame e değil Jpanel e olması için
		ekran.setSize(800,600);
		ekran.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Oyun oyun = new Oyun();
		oyun.requestFocus();	//klavyeden yapılan işlemleri anlaması için
		oyun.addKeyListener(oyun);
		oyun.setFocusable(true);
		oyun.setFocusTraversalKeysEnabled(false);
		
		ekran.add(oyun);
		ekran.setVisible(true);
	}
}
