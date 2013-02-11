package org.oxtail.play.inarow;

import java.util.ArrayList;
import java.util.List;

import org.oxtail.play.Indexes;
import org.oxtail.play.Indexes.Index;

//@formatter:off
/**
0:0 1:0 2:0 3:0 4:0 5:0 6:0
0:1 1:1 2:1 3:1 4:1 5:1 6:1
0:2 1:2 2:2 3:2 4:2 5:2 6:2
0:3 1:3 2:3 3:3 4:3 5:3 6:3
0:4 1:4 2:4 3:4 4:4 5:4 6:4
0:5 1:5 2:5 3:5 4:5 5:5 6:5
*/
public class IndexBuilder {

	private String[] xIndex = {"0123","1234","2345","3456"};
	private String[] yIndex = {"0123","1234","2345"};
	
	private String[] dIndex = { "0:0,1:1,2:2,3:3", "1:1,2:2,3:3,4:4", "2:2,3:3,4:4,5:5" , 
			                    "0:1,1:2,2:3,3:4", "1:2,2:3,3:4,4:5",
			                    "0:2,1:3,2:4,3:5",
			                    "1:0,2:1,3:2,4:3", "2:1,3:2,4:3,5:4", "3:2,4:3,5:4,6:5",
							    "2:0,3:1,4:2,5:3", "3:1,4:2,5:3,6:4",
							    "3:0,4:1,5:2,6:3",
							    
								"6:0,5:1,4:2,3:3", "5:1,4:2,3:3,2:4", "4:2,3:3,2:4,1:5", 
								"6:1,5:2,4:3,3:4", "5:2,4:3,3:4,2:5",
								"6:2,5:3,4:4,3:5",
								"5:0,4:1,3:2,2:3", "4:1,3:2,2:3,1:4", "3:2,2:3,1:4,0:5",
								"4:0,3:1,2:2,1:3", "3:1,2:2,1:3,0:4",
								"3:0,2:1,1:2,0:3"
			};

	
	
	
	
	public List<Indexes> buildIndexs() {
		
		List<Indexes> all = new ArrayList<>();
		for (int y = 0;y<6;y++) {
		  for (String s : xIndex) {
			  Indexes indexes = new Indexes();
			  all.add(indexes);
			  for (char c : s.toCharArray()) {	
				  int x = Character.getNumericValue(c);
				  indexes.add(new Index(x,y));
			 }
			  
		  }
		}
	
		for (int x = 0;x<7;x++) {
			 for (String s : yIndex) {
				 Indexes indexes = new Indexes();
				 all.add(indexes);
				  for (char c : s.toCharArray()) {	
					  int y = Character.getNumericValue(c);
					  indexes.add(new Index(x,y));
				}
			  }
		}
		
		for(String s : dIndex) {
			 Indexes indexes = new Indexes();
			 all.add(indexes);
			  for (String t : s.split(",")) {	
				  String[] xy = t.split(":");
				  int x = Integer.parseInt(xy[0]);
				  int y = Integer.parseInt(xy[1]);
				  indexes.add(new Index(x,y));
			  }
		}
		for (Indexes i : all)
			System.out.println(i);
		return all;
	}
	
	public static void main(String[] a) {
		new IndexBuilder().buildIndexs();
	}
	
}
