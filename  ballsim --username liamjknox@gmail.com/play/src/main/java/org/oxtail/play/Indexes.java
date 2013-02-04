package org.oxtail.play;

import java.util.ArrayList;
import java.util.List;

public class Indexes {

	private List<Index> indexes = new ArrayList<>();

	public Indexes() {
	}

	public void add(Index index) {
		indexes.add(index);
	}

	public List<Index> getIndexes() {
		return indexes;
	}

	@Override
	public String toString() {
		return "Indexes [indexes=" + indexes + "]";
	}

	public final static class Index {
		private int x;
		private int y;

		public Index(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		@Override
		public String toString() {
			return x + ":" + y;
		}

	}

}
