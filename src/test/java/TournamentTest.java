import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TournamentTest {
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    Player player5;
    List<Participant> participantsList1;
    BigDecimal firstPrize;
    BigDecimal secondPrize;
    BigDecimal thirdPrize;
    BigDecimal fourthPrize;
    Map<Integer, BigDecimal> prizes;
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
        participantsList1 = new ArrayList<>();
        participantsList1.add(player1);
        participantsList1.add(player2);
        participantsList1.add(player3);
        participantsList1.add(player4);
        prizes = new HashMap<>();
        prizes.put(1, firstPrize);
        prizes.put(2, secondPrize);
        prizes.put(3, thirdPrize);
    }

    @Test
    public void canSortParticipantsByScore(){
        assertEquals("Luis", participantsList1.get(0).getName());
        participantsList1.add(player5);
        assertEquals("Luis", participantsList1.get(0).getName());
        List<Participant> list = tournament1.sortParticipantsByScore(participantsList1);
        assertEquals("Ana", list.get(0).getName());
        assertEquals("Jessica", list.get(1).getName());
    }

    @Test
    public void canGenerateHashOfWinners(){
        assertEquals("{1=[], 2=[], 3=[], 4=[]}", tournament1.populateHashWithArraysForPositions(prizes).toString());
        prizes.put(4, fourthPrize);
        assertEquals("{1=[], 2=[], 3=[], 4=[], 5=[]}", tournament1.populateHashWithArraysForPositions(prizes).toString());
    }


    @Test
    public void canCheckIfPlayerIsInHash(){
        HashMap<Integer, ArrayList<Participant>> positions = tournament1.populateHashWithArraysForPositions(prizes);
        assertEquals(false, tournament1.checkIfHashContainsPlayer(positions, player1));
        positions.get(2).add(player1);
        assertEquals(true, tournament1.checkIfHashContainsPlayer(positions, player1));

    }

    @Test
    public void canSetWinnersInHash() {
        HashMap<Integer, ArrayList<Participant>> hash = tournament1.createHashMapOfPositions(participantsList1, prizes);
        assertEquals("Jessica", hash.get(1).get(1).getName());
        assertEquals("Ana", hash.get(1).get(0).getName());
        assertEquals(2, hash.get(1).size());
        assertEquals("Luis", hash.get(2).get(0).getName());
        assertEquals("Andy", hash.get(2).get(1).getName());
        assertEquals(2, hash.get(2).size());
        assertEquals(0, hash.get(3).size());
        assertEquals(0, hash.get(4).size());
        participantsList1.add(player5);
        HashMap<Integer, ArrayList<Participant>> hash2 = tournament1.createHashMapOfPositions(participantsList1, prizes);
        assertEquals("Daniel", hash2.get(3).get(0).getName());
    }

    @Test
    public void canGetJackpot(){
        assertEquals("50.00", tournament1.getJackpot(prizes, 1, 1).toString());
        assertEquals("35.00", tournament1.getJackpot(prizes, 1, 2).toString());
        assertEquals("20.00", tournament1.getJackpot(prizes, 1, 4).toString());
        assertEquals("16.00", tournament1.getJackpot(prizes, 1, 5).toString());
        assertEquals("8.88", tournament1.getJackpot(prizes, 1, 9).toString());
    }
}
