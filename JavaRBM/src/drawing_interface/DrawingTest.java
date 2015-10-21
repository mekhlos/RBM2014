package drawing_interface;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

public class DrawingTest implements Runnable {

	private JFrame frame;

	private MyDrawPanel drawPanel;

	private List<Point> points;

	public DrawingTest() {
		points = new ArrayList<Point>();
	}

	@Override
	public void run() {
		frame = new JFrame("Drawtable");

		drawPanel = new MyDrawPanel(this);
		MyListener alpha = new MyListener(this);
		drawPanel.addMouseMotionListener(alpha);
		drawPanel.addMouseListener(alpha);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(drawPanel);
		frame.setSize(800, 600);
		frame.setVisible(true);
	}

	public JPanel getDrawingPanel() {
		return drawPanel;
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoint(int x, int y) {
		points.add(new Point(x, y));
	}

	public void resetPoints() {
		points.clear();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new DrawingTest());
	}

	private class MyListener extends MouseInputAdapter {

		private DrawingTest drawingTest;

		public MyListener(DrawingTest drawingTest) {
			this.drawingTest = drawingTest;
		}

		@Override
		public void mouseDragged(MouseEvent event) {
			drawingTest.setPoint(event.getX(), event.getY());
			drawingTest.getDrawingPanel().repaint();
		}

		@Override
		public void mouseReleased(MouseEvent event) {
			drawingTest.resetPoints();
		}

	}

	private class MyDrawPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		private DrawingTest drawingTest;

		public MyDrawPanel(DrawingTest drawingTest) {
			this.drawingTest = drawingTest;
		}

		@Override
		public void paintComponent(Graphics g) {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.setColor(Color.BLACK);
			for (int i = 1; i < drawingTest.getPoints().size(); i++) {
				Point p1 = drawingTest.getPoints().get(i - 1);
				Point p2 = drawingTest.getPoints().get(i);
				g.drawLine(p1.x, p1.y, p2.x, p2.y);
			}
		}
	}

}