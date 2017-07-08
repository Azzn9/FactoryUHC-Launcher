package me.Azn9;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.animation.Animator;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

@SuppressWarnings({ "serial", "unused" })
public class LauncherPanel extends JPanel implements SwingerEventListener {

	private Image bglogin = Swinger.getResource("background.png");
	private Image bgupdate = Swinger.getResource("launcher update.png");
	private Image logo = Swinger.getResource("Factory-UHC.png");
	private Image tf = Swinger.getResource("textfield.png");

	private static JPasswordField pass = new JPasswordField();
	private STexturedButton close = new STexturedButton(Swinger.getResource("close.png")); // 940
	private Image line = Swinger.getResource("line.png");
	static Image im = Swinger.getResource("login.png");
	// private UsernameSaver saver = new UsernameSaver(Launcher.MG_INFOS);
	private static Saver saver = new Saver(new File(Launcher.MG_DIR, "launcher.properties"));
	private static JTextField mail;
	static Image imh = Swinger.getResource("login_hover.png");
	private static JButton play = new JButton(new ImageIcon(im));
	private STexturedButton reduce = new STexturedButton(Swinger.getResource("reduce.png")); // 905
	private Image lock = Swinger.getResource("lock.png");
	private Image user = Swinger.getResource("user.png");
	private STexturedButton bu = new STexturedButton(Swinger.getResource("login.png"));

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bglogin, 0, 0, this.getWidth(), this.getHeight(), this);
		g.drawImage(tf, 360, 260, 250, 35, this);
		g.drawImage(tf, 360, 308, 250, 35, this);
		g.drawImage(logo, 58, 58, 859, 96, this);
		g.drawImage(line, (int) 275.5, 200, 424, 43, this);
		g.drawImage(line, (int) 275.5, 372, 424, 43, this);
		g.drawImage(user, 330, 267, 27, 28, this);
		g.drawImage(lock, 330, 315, 27, 28, this);
	}

	public LauncherPanel() {
		mail = new JTextField(LauncherPanel.saver.get("username"));
		this.setLayout(null);
		mail.setOpaque(false);
		mail.setBorder(null);
		mail.setFont(getMail().getFont().deriveFont(25f));
		// mail.setForeground(Color.WHITE);
		// mail.setCaretColor(Color.WHITE);
		pass.setOpaque(false);
		pass.setBorder(null);
		pass.setFont(getPass().getFont().deriveFont(25f));
		// pass.setForeground(Color.WHITE);
		// pass.setCaretColor(Color.WHITE);
		mail.setBounds(362, 260, 246, 35);
		getPass().setBounds(362, 308, 246, 35);
		// play.setBounds(501, 422);
		close.setBounds(940, 4);
		reduce.setBounds(905, 4);
		this.add(close);
		this.add(reduce);
		close.addEventListener(this);
		reduce.addEventListener(this);
		play.setBounds(501, 350, 110, 20);
		play.setBorder(BorderFactory.createEmptyBorder());
		play.setContentAreaFilled(false);

		this.add(mail);
		this.add(pass);
		this.add(play);

		mail.addKeyListener(new LauncherKeyListener());
		pass.addKeyListener(new LauncherKeyListener());
		play.addMouseListener(new LauncherMouseEvent());
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public static void requestFo() {
		if (getSaver().get("username") != null && getSaver().get("username").toString() != null) {
			getPass().requestFocus();
		}
	}

	@Override
	public void onEvent(SwingerEvent e) {
		if (e.getSource() == close) {
			Animator.fadeOutFrame(LauncherFrame.getInstance(), Animator.FAST, new Runnable() {

				@Override
				public void run() {
					System.exit(0);
				}

			});
		} else if (e.getSource() == reduce) {
			LauncherFrame.getInstance().setState(JFrame.ICONIFIED);
		}
	}

	public static JButton getPlay() {
		return play;
	}

	public static LauncherPanel getHim() {
		return new LauncherPanel();
	}

	public static JPasswordField getPass() {
		return pass;
	}

	public static void setPass(JPasswordField pass) {
		LauncherPanel.pass = pass;
	}

	public static JTextField getMail() {
		return mail;
	}

	public static Saver getSaver() {
		return saver;
	}
}
