package hust.soict.globalict.graph;

import java.awt.Component;
import java.awt.Point;

import javax.swing.JPopupMenu;

public class RightClick extends JPopupMenu {
		private static final long serialVersionUID = 1L;

		private Point point;

		public Point getPoint() {
			return point;
		}

		public void setPoint(Point point) {
			this.point = point;
		}

		public RightClick() {
			super();
		}

	public void show(Component invoker, int x, int y) {
		point = new Point(x, y);
		super.show(invoker, x, y);
	}
	}


