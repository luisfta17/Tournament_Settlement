import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TournamentTest {
    Player player1, player2, player3, player4, player5, player6, player7, player8, player9, player10, player11, player12;
    List<Participant> participantsList1, participantsList2, participantsList3, participantsList4, participantsList5;
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
        player6 = new Player("Jamie", 40);
        player7 = new Player("Hannah", 60);
        player8 = new Player("Han", 80);
        player9 = new Player("Colin", 85);
        player10 = new Player("Carla", 10);
        player11 = new Player("Maria", 10);
        player12 = new Player("Mariano", 9);
        firstPrize = new BigDecimal("50.00").setScale(2);
        secondPrize = new BigDecimal("20.00").setScale(2);
        thirdPrize = new BigDecimal("10.00").setScale(2);
        fourthPrize = new BigDecimal("5.00").setScale(2);
        tournament1 = new Tournament();
        participantsList1 = new ArrayList<>();
        participantsList2 = new ArrayList<>();
        participantsList3 = new ArrayList<>();
        participantsList4 = new ArrayList<>();
        participantsList5 = new ArrayList<>();
        participantsList1.add(player1);
        participantsList1.add(player2);
        participantsList1.add(player3);
        participantsList1.add(player4);
        participantsList2.add(player1);
        participantsList2.add(player5);
        participantsList2.add(player6);
        participantsList2.add(player7);
        participantsList2.add(player8);
        participantsList2.add(player9);
        participantsList3.add(player10);
        participantsList3.add(player5);
        participantsList3.add(player4);
        participantsList3.add(player7);
        participantsList4.add(player11);
        participantsList4.add(player10);
        participantsList4.add(player5);
        participantsList4.add(player1);
        participantsList4.add(player12);
        participantsList5.add(player10);
        participantsList5.add(player11);
        participantsList5.add(player8);
        participantsList5.add(player12);
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
        assertEquals("50.00", tournament1.getJackpot(prizes, 1, 1, 1).toString());
        assertEquals("35.00", tournament1.getJackpot(prizes, 1, 2, 2).toString());
        assertEquals("20.00", tournament1.getJackpot(prizes, 1, 4, 4).toString());
        assertEquals("16.00", tournament1.getJackpot(prizes, 1, 5, 5).toString());
        assertEquals("8.88", tournament1.getJackpot(prizes, 1, 9, 9).toString());
        assertEquals("20.00", tournament1.getJackpot(prizes, 2, 1, 1).toString());
        assertEquals("15.00", tournament1.getJackpot(prizes, 2, 2, 2).toString());
        assertEquals("5.00", tournament1.getJackpot(prizes, 3, 1, 2).toString());
        assertEquals("3.33", tournament1.getJackpot(prizes, 3, 1, 3).toString());
    }

    @Test
    public void canAwardPrizes(){
        tournament1.awardPrizes(participantsList1, prizes);
        assertEquals("35.00", player4.getPrize().toString());
        assertEquals("35.00", player2.getPrize().toString());
        assertEquals("5.00", player1.getPrize().toString());
        assertEquals("5.00", player3.getPrize().toString());
        tournament1.awardPrizes(participantsList2, prizes);
        assertEquals("50.00", player9.getPrize().toString());
        assertEquals("20.00", player8.getPrize().toString());
        assertEquals("10.00", player7.getPrize().toString());
        assertEquals("0.00", player5.getPrize().toString());
        tournament1.awardPrizes(participantsList3, prizes);
        assertEquals("50.00", player7.getPrize().toString());
        assertEquals("20.00", player4.getPrize().toString());
        assertEquals("5.00", player5.getPrize().toString());
        assertEquals("5.00", player10.getPrize().toString());
        tournament1.awardPrizes(participantsList4, prizes);
        assertEquals("50.00", player1.getPrize().toString());
        assertEquals("10.00", player5.getPrize().toString());
        assertEquals("10.00", player10.getPrize().toString());
        assertEquals("10.00", player11.getPrize().toString());
        tournament1.awardPrizes(participantsList5, prizes);
        assertEquals("50.00", player8.getPrize().toString());
        assertEquals("15.00", player10.getPrize().toString());
        assertEquals("15.00", player11.getPrize().toString());
        assertEquals("0.00", player12.getPrize().toString());

    }
}
