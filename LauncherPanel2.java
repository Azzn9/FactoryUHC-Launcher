package me.Azn9;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.animation.Animator;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

@SuppressWarnings("serial")
public class LauncherPanel2 extends JPanel implements SwingerEventListener {

	private Image bglogin = Swinger.getResource("background.png");
	private Image logo = Swinger.getResource("Factory-UHC.png");
	private STexturedButton close = new STexturedButton(Swinger.getResource("close.png")); // 940
	private STexturedButton reduce = new STexturedButton(Swinger.getResource("reduce.png")); // 905
	private Image longline = Swinger.getResource("longline.png");
	public static SColoredBar bar = new SColoredBar(Swinger.getTransparentWhite(100), Swinger.getTransparentWhite(175));
	public static JLabel label = new JLabel("Bienvenue sur FactoryUHC!", SwingConstants.CENTER);
	public static JLabel bvn = new JLabel("Bienvenue " + Launcher.getAuthInfos().getUsername() + "!");
	public static JLabel nom = new JLabel("Pseudo: " + Launcher.getAuthInfos().getUsername());
	public static JLabel uuid = new JLabel("UUID: " + Launcher.getAuthInfos().getUuid());
	public static JLabel grade = new JLabel("Grade: " + /* TODO */ "COMING SOON!");
	public static LauncherPanel2 instance;

	String skin_url;
	Image skin = null;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bglogin, 0, 0, this.getWidth(), this.getHeight(), this);
		g.drawImage(logo, 58, 58, 859, 96, this);
		g.drawImage(longline, 64, 160, 848, 43, this);
		g.drawImage(longline, 64, 480, 848, 43, this);
		g.drawImage(skin, 100, 210, 120, 270, this); // 120 270
	}

	public LauncherPanel2() {
		skin_url = new String("https://crafatar.com/renders/body/" + Launcher.getAuthInfos().getUuid());
		try {
			URL url = new URL(skin_url);
			skin = ImageIO.read(url);
		} catch (IOException e) {
		}
		instance = this;
		this.setLayout(null);
		close.setBounds(940, 4);
		reduce.setBounds(905, 4);
		this.add(close);
		this.add(reduce);
		close.addEventListener(this);
		reduce.addEventListener(this);
		bar.setBounds(0, 547, 975, 18);
		label.setBounds(0, 525, 975, 20);
		label.setForeground(Color.WHITE);
		bvn.setBounds(400, 100, 975, 260);
		bvn.setForeground(Color.WHITE);
		bvn.setFont(bvn.getFont().deriveFont(50f));

		nom.setBounds(400, 275, 975, 25);
		uuid.setBounds(400, 300, 975, 25);
		grade.setBounds(400, 325, 975, 25);

		nom.setForeground(Color.WHITE);
		uuid.setForeground(Color.WHITE);
		grade.setForeground(Color.WHITE);

		nom.setFont(bvn.getFont().deriveFont(20f));
		uuid.setFont(bvn.getFont().deriveFont(20f));
		grade.setFont(bvn.getFont().deriveFont(20f));

		this.add(nom);
		this.add(uuid);
		this.add(grade);
		this.add(bvn);
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

	public static SColoredBar getBar() {
		return bar;
	}

	public static void setLabelText(String t) {
		label.setText(t);
	}

	public static LauncherPanel2 getinstance() {
		return instance;
	}

	public static JLabel getLabel() {
		return label;
	}

}
