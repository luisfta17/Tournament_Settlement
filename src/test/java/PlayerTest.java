import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class PlayerTest {
    Player player1;
    BigDecimal superPrize;

    @Before
    public void before(){
        player1 = new Player("Luis", 22);
        superPrize = new BigDecimal("50.00").setScale(2);
    }

    @Test
    public void hasName(){
        assertEquals("Luis", player1.getName());
    }

    @Test
    public void hasScore(){
        assertEquals(22, player1.getScore());
    }

    @Test
    public void hasPrize(){
        assertEquals("0.00", player1.getPrize().toString());
    }

    @Test
    public void canSetPrize(){
        player1.setPrize(superPrize);
        assertEquals("50.00", player1.getPrize().toString());
    }

}
