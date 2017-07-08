package me.Azn9;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.theshark34.openauth.AuthenticationException;

@SuppressWarnings("serial")
public class LauncherKeyListener extends JPanel implements KeyListener {

	public void setFieldsEnabled(boolean enabled) {
		LauncherPanel.getMail().setEnabled(enabled);
		LauncherPanel.getPass().setEnabled(enabled);
		LauncherPanel.getPlay().setEnabled(enabled);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource().equals(LauncherPanel.getMail()) || e.getSource().equals(LauncherPanel.getPass())) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
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
							JOptionPane.showMessageDialog(get(), e.getErrorModel().getErrorMessage(), "Erreur",
									JOptionPane.ERROR_MESSAGE);
							setFieldsEnabled(true);
							return;
						}
						LauncherPanel.getSaver().set("username",LauncherPanel.getMail().getText());
						try {
							Launcher.update();
						} catch (Exception e) {
							setFieldsEnabled(true);
							Launcher.interruptThread();
							JOptionPane.showMessageDialog(get(), "Erreur lors de la mise à jour du jeu!", "Erreur",
									JOptionPane.ERROR_MESSAGE);
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
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public LauncherKeyListener get() {
		return this;
	}

}
