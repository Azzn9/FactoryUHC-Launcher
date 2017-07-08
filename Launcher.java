package me.Azn9;

import java.io.File;
import java.io.IOException;

import fr.theshark34.openauth.AuthPoints;
import fr.theshark34.openauth.AuthenticationException;
import fr.theshark34.openauth.Authenticator;
import fr.theshark34.openauth.model.AuthAgent;
import fr.theshark34.openauth.model.response.AuthResponse;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.internal.InternalLaunchProfile;
import fr.theshark34.openlauncherlib.internal.InternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.minecraft.GameFolder;
import fr.theshark34.openlauncherlib.minecraft.GameInfos;
import fr.theshark34.openlauncherlib.minecraft.GameTweak;
import fr.theshark34.openlauncherlib.minecraft.GameType;
import fr.theshark34.openlauncherlib.minecraft.GameVersion;
import fr.theshark34.openlauncherlib.minecraft.MinecraftLauncher;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;
import fr.theshark34.supdate.application.integrated.FileDeleter;
import fr.theshark34.supdate.exception.BadServerResponseException;
import fr.theshark34.supdate.exception.BadServerVersionException;
import fr.theshark34.supdate.exception.ServerDisabledException;
import fr.theshark34.supdate.exception.ServerMissingSomethingException;
import fr.theshark34.swinger.Swinger;

public class Launcher {

	public static final GameVersion MG_VERSION = new GameVersion("1.8.0", GameType.V1_8_HIGHER);
	public static final GameInfos MG_INFOS = new GameInfos("FactoryUHC", MG_VERSION, 
			new GameTweak[] { GameTweak.FORGE });
	public static final File MG_DIR = MG_INFOS.getGameDir();

	private static AuthInfos authInfos;

	static Thread t;

	public static void auth(String username, String password) throws AuthenticationException {
		Authenticator auth = new Authenticator(Authenticator.MOJANG_AUTH_URL, AuthPoints.NORMAL_AUTH_POINTS);
		AuthResponse res = auth.authenticate(AuthAgent.MINECRAFT, username, password, "");
		setAuthInfos(new AuthInfos(res.getSelectedProfile().getName(), res.getAccessToken(),
				res.getSelectedProfile().getId()));
	}

	public static void update() {
		System.out.println("1");
		LauncherFrame.getInstance().setVisible(false);
		System.out.println("2");
		LauncherFrame.getInstance().setContentPane(new LauncherPanel2());
		System.out.println("3");
		LauncherFrame.getInstance().setVisible(true);
		System.out.println("4");
		SUpdate su = new SUpdate("URL_DOWNLOAD_FILES", MG_DIR);
		System.out.println("5");
		su.addApplication(new FileDeleter());
		System.out.println("6");
		LauncherPanel2.getinstance().add(LauncherPanel2.getBar());
		System.out.println("7");
		LauncherPanel2.getinstance().add(LauncherPanel2.getLabel());
		System.out.println("8");
		LauncherPanel2.setLabelText("Mise à jour des fichiers du jeu et lancement...");
		System.out.println("9");
		t = new Thread() {
			int val;
			int max;

			@Override
			public void run() {
				while (!t.isInterrupted()) {
					val = (int) (BarAPI.getNumberOfTotalDownloadedBytes());
					max = (int) (BarAPI.getNumberOfTotalBytesToDownload());
					LauncherPanel2.getBar().setMaximum(max);
					LauncherPanel2.getBar().setValue(val);
					if (BarAPI.getNumberOfFileToDownload() == 0) {
						LauncherPanel2.setLabelText("Mise à jour des fichiers du jeu et lancement...");
					} else {
						LauncherPanel2.setLabelText("Téléchargement des fichiers " + BarAPI.getNumberOfDownloadedFiles()
								+ " / " + BarAPI.getNumberOfFileToDownload() + " - " + Swinger.percentage(val, max)
								+ "%");
					}
				}
			}
		};
		System.out.println("10");
		t.start();
		System.out.println("11");
		try {
			su.start();
		} catch (BadServerResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServerDisabledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadServerVersionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServerMissingSomethingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t.interrupt();
		LauncherFrame.getPanel().remove(LauncherPanel2.getBar());
		LauncherPanel2.setLabelText("Téléchargement des fichiers du jeu terminé ! - Lancement du jeu en cours...");
	}

	public static void interruptThread() {
		t.interrupt();
	}

	public static void launch() throws IOException, InterruptedException, LaunchException {
		InternalLaunchProfile lp = MinecraftLauncher.createInternalProfile(MG_INFOS, GameFolder.BASIC, authInfos);
		InternalLauncher launcher = new InternalLauncher(lp);
		launcher.launch();
		LauncherFrame.getInstance().setVisible(false);
		System.exit(0);
	}

	/**
	 * @return the authInfos
	 */
	public static AuthInfos getAuthInfos() {
		return authInfos;
	}

	/**
	 * @param authInfos
	 *            the authInfos to set
	 */
	public static void setAuthInfos(AuthInfos authInfos) {
		Launcher.authInfos = authInfos;
	}

}
