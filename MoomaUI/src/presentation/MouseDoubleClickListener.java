package presentation;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class MouseDoubleClickListener extends MouseAdapter {
	private Status status = Status.NULL;
	private int delay;
	private Timer resetTimer;
	private TimerTask tTask;
	
	public MouseDoubleClickListener() {
		super();
		this.resetTimer = new Timer();
		this.delay = (int) Toolkit.getDefaultToolkit().getDesktopProperty("awt.multiClickInterval");
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		switch (status) {
			case NULL:
			case SINGLE_CLICK:
				status = Status.DOUBLE_CLICK;
				tTask = new TimerTask() {
					
					@Override
					public void run() {
						status = Status.SINGLE_CLICK;
					}
				};
				resetTimer.schedule(tTask, this.delay);
			mouseClickedNormal(e);
			break;
			
			case DOUBLE_CLICK:
				status = Status.SINGLE_CLICK;
				this.tTask.cancel();
				mouseDoubleClicked(e);
				break;
		}
	}
	
	public void mouseClickedNormal(MouseEvent e) {
		
	}
	
	public void mouseDoubleClicked(MouseEvent e) {
		
	}
}

enum Status {
	NULL, SINGLE_CLICK, DOUBLE_CLICK
}