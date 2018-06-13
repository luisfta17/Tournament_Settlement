import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class TournamentTest {
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    Player player5;
    BigDecimal firstPrize;
    BigDecimal secondPrize;
    BigDecimal thirdPrize;
    BigDecimal fourthPrize;
    Tournament tournament1;

    @Before
    public void before(){
        player1 = new Player("Luis", 22);
        player2 = new Player("Ana", 30);
        player3 = new Player("Andy", 22);
        player4 = new Player("Jessica", 30);
        player5 = new Player("Daniel", 10);
        firstPrize = new BigDecimal("50.00").setScale(2);
        secondPrize = new BigDecimal("20.00").setScale(2);
        thirdPrize = new BigDecimal("10.00").setScale(2);
        fourthPrize = new BigDecimal("5.00").setScale(2);
        tournament1 = new Tournament();
        tournament1.addParticipants(player1);
        tournament1.addParticipants(player2);
        tournament1.addParticipants(player3);
        tournament1.addParticipants(player4);
        tournament1.addPrize(1, firstPrize);
        tournament1.addPrize(2, secondPrize);
        tournament1.addPrize(3, thirdPrize);
    }

    @Test
    public void hasParticipants(){
        assertEquals(4, tournament1.getParticipants().size());
    }

    @Test
    public void canAddParticipants(){
        tournament1.addParticipants(player5);
        assertEquals(5, tournament1.getParticipants().size());
    }

    @Test
    public void canRemoveParticipants(){
        tournament1.removeParticipant(player3);
        assertEquals(3, tournament1.getParticipants().size());
    }

    @Test
    public void canRemoveAllParticipants(){
        tournament1.removeAllParticipants();
        assertEquals(0, tournament1.getParticipants().size());
    }

    @Test
    public void hasPrizes(){
        assertEquals(3, tournament1.getPrizes().size());
    }

    @Test
    public void canAddPrizes(){
        tournament1.addPrize(4, fourthPrize);
        assertEquals(4, tournament1.getPrizes().size());
    }

    @Test
    public void canGetPrizeForPosition(){
        assertEquals("50.00", tournament1.getPrizeForPosition(1).toString());
    }

    @Test
    public void canRemovePrizeInPosition(){
        tournament1.removePrizeInPosition(3);
        assertEquals(2, tournament1.getPrizes().size());
    }

    @Test
    public void canRemoveAllPrizes(){
        tournament1.removeAllPrizes();
        assertEquals(0, tournament1.getPrizes().size());
    }

    @Test
    public void canSortParticipantsByScore(){
        assertEquals("Luis", tournament1.getParticipants().get(0).getName());
        tournament1.addParticipants(player5);
        assertEquals("Luis", tournament1.getParticipants().get(0).getName());
        tournament1.sortParticipantsByScore();
        assertEquals("Daniel", tournament1.getParticipants().get(0).getName());
        assertEquals("Andy", tournament1.getParticipants().get(1).getName());
    }

    @Test
    public void canGenerateHashOfWinners(){
        assertEquals("{1=[], 2=[], 3=[], 4=[]}", tournament1.populateHashWithArraysForPositions().toString());
        tournament1.addPrize(4, fourthPrize);
        assertEquals("{1=[], 2=[], 3=[], 4=[], 5=[]}", tournament1.populateHashWithArraysForPositions().toString());
    }


    @Test
    public void canCheckIfPlayerIsInHash(){
        HashMap<Integer, ArrayList<Participant>> positions = tournament1.populateHashWithArraysForPositions();
        assertEquals(false, tournament1.checkIfHashContainsPlayer(positions, player1));
        positions.get(2).add(player1);
        assertEquals(true, tournament1.checkIfHashContainsPlayer(positions, player1));

    }

    @Test
    public void canSetWinnersInHash() {
        HashMap<Integer, ArrayList<Participant>> hash = tournament1.createHashMapOfPositions();
        assertEquals("Jessica", hash.get(1).get(1).getName());
        assertEquals("Ana", hash.get(1).get(0).getName());
        assertEquals(2, hash.get(1).size());
        assertEquals("Luis", hash.get(2).get(0).getName());
        assertEquals("Andy", hash.get(2).get(1).getName());
        assertEquals(2, hash.get(2).size());
        assertEquals(0, hash.get(3).size());
        assertEquals(0, hash.get(4).size());
        tournament1.addParticipants(player5);
        HashMap<Integer, ArrayList<Participant>> hash2 = tournament1.createHashMapOfPositions();
        assertEquals("Daniel", hash2.get(3).get(0).getName());
    }

}
