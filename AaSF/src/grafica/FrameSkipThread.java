package grafica;

import grafica.board.Board;
import util.ObjectUtil;

public class FrameSkipThread extends ActionThread {
	
	private Board board;
	
	public FrameSkipThread(Board board) {
		ObjectUtil.checkNull(board);
		this.board = board;
	}

	@Override
	public void run() {
		try {
			while(true) {
				board.repaint();
					Thread.sleep(1000/50);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

}
