package me.Azn9;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.theshark34.openauth.AuthenticationException;

@SuppressWarnings("serial")
public class LauncherMouseEvent extends JPanel implements MouseListener {
	
	public void setFieldsEnabled(boolean enabled) {
		LauncherPanel.getMail().setEnabled(enabled);
		LauncherPanel.getPass().setEnabled(enabled);
		LauncherPanel.getPlay().setEnabled(enabled);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(LauncherPanel.getPlay())) {
			System.out.println("play");
			setFieldsEnabled(false);
			if (LauncherPanel.getMail().getText().replaceAll(" ", "").length() == 0
					|| !LauncherPanel.getMail().getText().contains("@")
					|| LauncherPanel.getPass().getText().replaceAll(" ", "").length() == 0) {
				JOptionPane.showMessageDialog(get(), "Veuillez entrer une adresse mail ou un mot de passe valide!",
						"Erreur", JOptionPane.ERROR_MESSAGE);
				setFieldsEnabled(true);
				return;
			}

			Thread t = new Thread() {
				@Override
				public void run() {
					try {
						Launcher.auth(LauncherPanel.getMail().getText(), LauncherPanel.getPass().getText());
					} catch (AuthenticationException e) {
						JOptionPane.showMessageDialog(get(), e.getErrorModel().getErrorMessage(),
								"Erreur", JOptionPane.ERROR_MESSAGE);
						setFieldsEnabled(true);
						return;
					}
					LauncherPanel.getSaver().set("usernam",LauncherPanel.getMail().getText());
					try {
						System.out.println("aaaaaa");
						Launcher.update();
						System.out.println("bbbbbbbbb");
					} catch (Exception e) {
						System.out.println("cccccc");
						setFieldsEnabled(true);
						Launcher.interruptThread();
						JOptionPane.showMessageDialog(get(), "Erreur lors de la mise à jour du jeu!",
								"Erreur", JOptionPane.ERROR_MESSAGE);
						setFieldsEnabled(true);
						return;
					}
					try {
						Launcher.launch();
					} catch (Exception e) {
						setFieldsEnabled(true);
						Launcher.interruptThread();
						JOptionPane.showMessageDialog(get(), "Erreur lors du lancement du jeu!", "Erreur",
								JOptionPane.ERROR_MESSAGE);
						setFieldsEnabled(true);
						return;
					}
				}
			};
			t.start();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		LauncherPanel.getPlay().setIcon(new ImageIcon(LauncherPanel.imh));
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		LauncherPanel.getPlay().setIcon(new ImageIcon(LauncherPanel.im));
	}

	public LauncherMouseEvent get() {
		return this;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
