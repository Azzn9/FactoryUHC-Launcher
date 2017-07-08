package me.Azn9;

import javax.swing.JFrame;

import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.animation.Animator;
import fr.theshark34.swinger.util.WindowMover;

@SuppressWarnings("serial")
public class LauncherFrame extends JFrame {
	
	private static LauncherFrame instance;
	private static LauncherPanel launcherPanel;
	private static LauncherPanel2 launcherPanel2;
	
	public LauncherFrame() {
		this.setTitle("FactoryUHC launcher");
		this.setSize(975, 565);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);

		WindowMover mover = new WindowMover(this);
		this.addMouseListener(mover);
		this.addMouseMotionListener(mover);
		
		launcherPanel = new LauncherPanel();
		this.setContentPane(launcherPanel);
		this.setIconImage(Swinger.getResource("bootsrap.jpg"
				+ ""));
		this.setOpacity(0.0f);
		this.setVisible(true);
		LauncherPanel.requestFo();
		
		Animator.fadeInFrame(this, Animator.FAST);
	}

	public static void main(String[] args) {
		Swinger.setSystemLookNFeel();
		Swinger.setResourcePath("/me/Azn9/rescources/");
		instance = new LauncherFrame();
	}

	
	public static LauncherFrame getInstance() {
		return instance;
	}
	
	public static LauncherPanel getPanel() {
		return launcherPanel;
	}

	public static LauncherPanel2 getPanel2() {
		return launcherPanel2;
	}
	public static void setLauncherPanel2(LauncherPanel2 launcherPanel2) {
		LauncherFrame.launcherPanel2 = launcherPanel2;
	}
}