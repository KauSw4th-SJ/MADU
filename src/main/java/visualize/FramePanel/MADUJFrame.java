package visualize.FramePanel;

import javax.swing.JFrame;


@SuppressWarnings("serial")
public class MADUJFrame extends JFrame{
	private MADUJPanel MADUPanel;

	public MADUJFrame(String title) {
        super(title);                
    }
	
	public MADUJPanel getMyPanel() {
		return MADUPanel;
	}

	public void setMyPanel(MADUJPanel myPanel) {
		this.MADUPanel = myPanel;
	}
	
	public void createNewFrame(){		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(MADUPanel);
		this.pack();
		this.setLocationByPlatform(true);
		this.setVisible(true);     
        
	}
	
}
