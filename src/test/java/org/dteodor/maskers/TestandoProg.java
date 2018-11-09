package org.dteodor.maskers;


import org.junit.Test;
import static org.junit.Assert.*;

public class TestandoProg {
    @Test
    public void testName() {

    }

    @Test
    public void testVars(){
        String user = System.getenv("B_USER");
        assertNotNull(user);
        String key = System.getenv("B_KEY");
        assertNotNull(key);

    }
}
