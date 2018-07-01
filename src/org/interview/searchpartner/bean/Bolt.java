package org.interview.searchpartner.bean;

public class Bolt implements MachineParts{ 
	
	@Override
	public boolean equals(Object o) {
 
        if (o == this) {
            return true;
        }

        if ((o instanceof Bolt)) {
            return true;
        }
        
        return false;


 }
}
