package hust.soict.globalict.fileReader;

import java.io.Serializable;
import java.util.ArrayList;

import hust.soict.globalict.graph.MyLine;
import hust.soict.globalict.graph.MyPoint;



	public class fileReader implements Serializable {

		private static final long serialVersionUID = 1L;
		private ArrayList<MyPoint> arrMyPoint;
		private ArrayList<MyLine> arrMyLine;

		final int r = 15, r2 = 2 * r;

		public ArrayList<MyPoint> getArrMyPoint() {
			return arrMyPoint;
		}

		public void setArrMyPoint(ArrayList<MyPoint> arrMyPoint) {
			this.arrMyPoint = arrMyPoint;
		}

		public ArrayList<MyLine> getArrMyLine() {
			return arrMyLine;
		}

		public void setArrMyLine(ArrayList<MyLine> arrMyLine) {
			this.arrMyLine = arrMyLine;
		}

		public fileReader() {
			arrMyPoint = new ArrayList<MyPoint>();
			arrMyLine = new ArrayList<MyLine>();
		}
	}


