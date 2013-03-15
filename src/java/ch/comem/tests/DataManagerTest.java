/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.comem.tests;

import ch.comem.services.MembersManager;
import javax.ejb.Stateless;
import javax.jws.WebService;

/**
 *
 * @author Pierre-Alexandre
 */
@Stateless
@WebService
public class DataManagerTest implements DataManagerTestLocal {
    
    private String[] firstNames = {"Paul", "Arthur", "Danielle", "Georges", 
                                   "Isabelle", "Chuck", "Mario", "Joëlle",
                                   "Léa", "Walter"};
    
    private String[] lastNames = {"Hogan", "Lion", "Müller", "Pasche", 
                                  "Adjani", "Norris", "Kart", "Wyss", 
                                  "Teoni", "Baertchi"};
    
    private int[] ages = {55, 432, 28, 33, 24, 94, 230, 33, 23, 18};

    private MembersManager mm;
    
    @Override
    public void testMethods() {
        for (int index = 0; index < 10; index++) {
            long x = mm.createMember(firstNames[index], lastNames[index], ages[index]);
            System.out.println(x);
        }
    }

    

}
