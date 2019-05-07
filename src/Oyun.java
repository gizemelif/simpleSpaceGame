import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ConcurrentModificationException;

class Ates
{
	private int x;
	private int y;
	public Ates( int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	public int getX()
	{
		return x;
	}
	public void setX( int x )
	{
		this.x = x;
	}
	public int getY()
	{
		return y;
	}
	public void setY( int y )
	{
		this.y = y;
	}

}
public class Oyun extends JPanel implements KeyListener,ActionListener
{
	Timer timer = new Timer( 5,this );	//5 milisaniyede bir actionPerformed çalışacak
	private int gecen_sure = 0;
	private int  harcanan_ates = 0;
	private BufferedImage image;
	private ArrayList<Ates> atesler = new ArrayList<Ates>();
	private int atesdirY = 1; //ateşler oluşacak daha sonra her actionPerformed olduğunda ateşleri y koordinatında toplayacağız
	private int topX = 0; //sağa-sola gitmeyi ayarlayacak
	private int topdirX = 2;
	private int uzayGemisiX = 0;
	private int dirUzayX = 20; //20br sağa veya sola kaymayı sağlayacak
	
	public boolean kontrolEt()
	{
		for( Ates ates : atesler )
		{
			if( new Rectangle(ates.getX(),ates.getY(),10,20).intersects(new Rectangle(topX,0,20,20)))
			{
				return true;
			}
		}
		return false;
	}
	public Oyun()
	{
		try
		{
			image = ImageIO.read(new FileImageInputStream(new File("ship.png")));
		} catch (IOException e)
		{
			Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, e);
		}
		setBackground(Color.orange);
		timer.start();
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		
		gecen_sure += 5;
		g.setColor(Color.black);
		g.fillOval(topX, 0, 20, 20);
		
		g.drawImage(image, uzayGemisiX, 490, image.getWidth(), image.getHeight(), this);
		
		for( Ates ates: atesler )
		{
			if( ates.getY() < 0 )
			{
				atesler.remove(ates);
			}
		}
		g.setColor(Color.blue);
		
		for( Ates ates : atesler )
		{
			g.fillRect(ates.getX(), ates.getY(), 10, 20);
		}
		if( kontrolEt())
		{
			timer.stop();
			String message = "Kazandınız !\n"+
							"Harcanan Ateş : "+ harcanan_ates +
							"\nGeçen Süre : "+gecen_sure/1000.0+" sn";
			JOptionPane.showMessageDialog(this, message);
			System.exit(0);
		}
	}

	//actionPerformed da repaint() metodunu en son çağıracağız ki her action da tekrar paint() metodunu çağırsın
	@Override
	public void repaint()
	{
		
		super.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		
		for(Ates ates : atesler )
		{
			ates.setY(ates.getY() - atesdirY );
		}
		
		topX += topdirX;
		
		if( topX >= 750 )
			topdirX = -topdirX;
		if( topX <= 0 )
			topdirX = -topdirX;
		
		repaint();
		
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int c = e.getKeyCode();  //klavyeden sola veya sağa bastığımızda o değeri alacak
		
		if( c == KeyEvent.VK_LEFT )
		{
			if( uzayGemisiX <= 0 )
			{
				uzayGemisiX = 0;
			}
			else
				uzayGemisiX -= dirUzayX;
		}
		else if( c == KeyEvent.VK_RIGHT )
		{
			if( uzayGemisiX >= 750 )
				uzayGemisiX = 750;
			else
				uzayGemisiX += dirUzayX;
		}
		else if( c == KeyEvent.VK_CONTROL )
		{
			atesler.add(new Ates( uzayGemisiX+18,470));	//uzay gemisi görsel olduğu içi tam uç kısmından ateş çıkması için +15 yaptık
			harcanan_ates++;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}
	
}
