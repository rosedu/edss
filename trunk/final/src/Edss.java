/*import javax.swing.*;
import org.jvnet.substance.skin.*;


public class Edss {
	
	public static void main(String args[])
	{
				edss.gui.Configuration cf = new edss.gui.Configuration("config.xml");
		int skin = cf.skinId;
		// ParseDatabase dataBase = new ParseDatabase("database.xml");
		try {
			switch(skin)
			{
			case 0 : UIManager.setLookAndFeel(new SubstanceAutumnLookAndFeel()); break;
			case 1 : UIManager.setLookAndFeel(new SubstanceBusinessLookAndFeel()); break;
			case 2 : UIManager.setLookAndFeel(new SubstanceBusinessBlueSteelLookAndFeel()); break;
			case 3 : UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel()); break;
			case 4 : UIManager.setLookAndFeel(new SubstanceChallengerDeepLookAndFeel()); break;
			case 5 : UIManager.setLookAndFeel(new SubstanceCremeLookAndFeel()); break;
			case 6 : UIManager.setLookAndFeel(new SubstanceCremeCoffeeLookAndFeel()); break;
			case 7 : UIManager.setLookAndFeel(new SubstanceDustLookAndFeel()); break;
			case 8 : UIManager.setLookAndFeel(new SubstanceDustCoffeeLookAndFeel()); break;
			case 9 : UIManager.setLookAndFeel(new SubstanceEmeraldDuskLookAndFeel()); break;
			case 10 : UIManager.setLookAndFeel(new SubstanceMagmaLookAndFeel()); break;
			case 11 : UIManager.setLookAndFeel(new SubstanceMistAquaLookAndFeel()); break;
			case 12 : UIManager.setLookAndFeel(new SubstanceMistSilverLookAndFeel()); break;
			case 13 : UIManager.setLookAndFeel(new SubstanceModerateLookAndFeel()); break;
			case 14 : UIManager.setLookAndFeel(new SubstanceNebulaLookAndFeel()); break;
			case 15 : UIManager.setLookAndFeel(new SubstanceNebulaBrickWallLookAndFeel()); break;
			case 16 : UIManager.setLookAndFeel(new SubstanceOfficeBlue2007LookAndFeel()); break;
			case 17 : UIManager.setLookAndFeel(new SubstanceOfficeSilver2007LookAndFeel()); break;
			case 18 : UIManager.setLookAndFeel(new SubstanceRavenLookAndFeel()); break;
			case 19 : UIManager.setLookAndFeel(new SubstanceRavenGraphiteLookAndFeel()); break;
			case 20 : UIManager.setLookAndFeel(new SubstanceRavenGraphiteGlassLookAndFeel()); break;
			case 21 : UIManager.setLookAndFeel(new SubstanceSaharaLookAndFeel()); break;
			case 22 : UIManager.setLookAndFeel(new SubstanceTwilightLookAndFeel()); break;
			default : UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());
			}
			
			//UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JFrame.setDefaultLookAndFeelDecorated(true);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				Gui window = new GuiImpl();
		
			}
		});
	}
}
*/