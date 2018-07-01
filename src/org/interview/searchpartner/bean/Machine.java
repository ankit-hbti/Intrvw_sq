package org.interview.searchpartner.bean;

public class Machine implements MachineParts{
	
	@Override
	public boolean equals(Object o) {
 
        if (o == this) {
            return true;
        }

        if ((o instanceof Machine)) {
            return true;
        }
        
        return false;


 }

}
