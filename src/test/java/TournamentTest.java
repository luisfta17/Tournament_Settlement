import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TournamentTest {
    Player player1, player2, player3, player4, player5, player6, player7, player8, player9, player10, player11, player12, player13;
    List<Participant> participantsList1, participantsList2, participantsList3,
            participantsList4, participantsList5, participantsList6, participantsList7, participantsList8;
    BigDecimal firstPrize;
    BigDecimal secondPrize;
    BigDecimal thirdPrize;
    BigDecimal fourthPrize;
    BigDecimal fifthPrize;
    Map<Integer, BigDecimal> prizes, prizes1, prizes2, prizes3;
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
        player13 = new Player("Mariano", 2);
        firstPrize = new BigDecimal("50.00").setScale(2);
        secondPrize = new BigDecimal("20.00").setScale(2);
        thirdPrize = new BigDecimal("10.00").setScale(2);
        fourthPrize = new BigDecimal("5.00").setScale(2);
        fifthPrize = new BigDecimal("2.00").setScale(2);
        tournament1 = new Tournament();
        participantsList1 = new ArrayList<>();
        participantsList2 = new ArrayList<>();
        participantsList3 = new ArrayList<>();
        participantsList4 = new ArrayList<>();
        participantsList5 = new ArrayList<>();
        participantsList6 = new ArrayList<>();
        participantsList7 = new ArrayList<>();
        participantsList8 = new ArrayList<>();
        participantsList1.add(player1);
        participantsList1.add(player2);
        participantsList1.add(player3);
        participantsList1.add(player4);
        prizes = new HashMap<>();
        prizes1 = new HashMap<>();
        prizes2 = new HashMap<>();
        prizes3 = new HashMap<>();
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
    public void canGenerateHashOfArrays(){
        assertEquals("{1=[], 2=[], 3=[], 4=[]}", tournament1.createHashWithEmptyArraysForPositions(prizes).toString());
        prizes.put(4, fourthPrize);
        assertEquals("{1=[], 2=[], 3=[], 4=[], 5=[]}", tournament1.createHashWithEmptyArraysForPositions(prizes).toString());
    }

    @Test
    public void canSetWinnersInHash() {
        HashMap<Integer, ArrayList<Participant>> hash = tournament1.populateHashMapOfPositions(participantsList1, prizes);
        assertEquals("Jessica", hash.get(1).get(1).getName());
        assertEquals("Ana", hash.get(1).get(0).getName());
        assertEquals(2, hash.get(1).size());
        assertEquals("Luis", hash.get(2).get(0).getName());
        assertEquals("Andy", hash.get(2).get(1).getName());
        assertEquals(2, hash.get(2).size());
        assertEquals(0, hash.get(3).size());
        assertEquals(0, hash.get(4).size());
        participantsList1.add(player5);
        HashMap<Integer, ArrayList<Participant>> hash2 = tournament1.populateHashMapOfPositions(participantsList1, prizes);
        assertEquals("Daniel", hash2.get(3).get(0).getName());
    }

    @Test
    public void canAwardPrizes4Players3PrizesCase1(){
        tournament1.awardPrizes(participantsList1, prizes);
        assertEquals("35.00", player4.getPrize().toString());
        assertEquals("35.00", player2.getPrize().toString());
        assertEquals("5.00", player1.getPrize().toString());
        assertEquals("5.00", player3.getPrize().toString());
    }

    @Test
    public void canAwardPrizes6Players3PrizesCase2(){
        participantsList2.add(player1);
        participantsList2.add(player5);
        participantsList2.add(player6);
        participantsList2.add(player7);
        participantsList2.add(player8);
        participantsList2.add(player9);
        tournament1.awardPrizes(participantsList2, prizes);
        assertEquals("50.00", player9.getPrize().toString());
        assertEquals("20.00", player8.getPrize().toString());
        assertEquals("10.00", player7.getPrize().toString());
        assertEquals("0.00", player5.getPrize().toString());
        assertEquals("0.00", player1.getPrize().toString());
        assertEquals("0.00", player6.getPrize().toString());
    }

    @Test
    public void canAwardPrizes4Players3PrizesCase3(){
        participantsList3.add(player10);
        participantsList3.add(player5);
        participantsList3.add(player4);
        participantsList3.add(player7);
        tournament1.awardPrizes(participantsList3, prizes);
        assertEquals("50.00", player7.getPrize().toString());
        assertEquals("20.00", player4.getPrize().toString());
        assertEquals("5.00", player5.getPrize().toString());
        assertEquals("5.00", player10.getPrize().toString());
    }

    @Test
    public void canAwardPrizes5Players3PrizesCase4(){
        participantsList4.add(player11);
        participantsList4.add(player10);
        participantsList4.add(player5);
        participantsList4.add(player1);
        participantsList4.add(player12);
        tournament1.awardPrizes(participantsList4, prizes);
        assertEquals("50.00", player1.getPrize().toString());
        assertEquals("10.00", player5.getPrize().toString());
        assertEquals("10.00", player10.getPrize().toString());
        assertEquals("10.00", player11.getPrize().toString());
        assertEquals("0.00", player12.getPrize().toString());
    }

    @Test
    public void canAwardPrizes5Players3and4Prizes(){
        participantsList5.add(player10);
        participantsList5.add(player11);
        participantsList5.add(player8);
        participantsList5.add(player12);
        participantsList5.add(player13);
        prizes1.put(1, firstPrize);
        prizes1.put(2, secondPrize);
        prizes1.put(3, thirdPrize);
        prizes1.put(4, fourthPrize);
        tournament1.awardPrizes(participantsList5, prizes);
        assertEquals("50.00", player8.getPrize().toString());
        assertEquals("15.00", player10.getPrize().toString());
        assertEquals("15.00", player11.getPrize().toString());
        assertEquals("0.00",  player12.getPrize().toString());
        assertEquals("0.00",  player13.getPrize().toString());
        tournament1.awardPrizes(participantsList5, prizes1);
        assertEquals("50.00", player8.getPrize().toString());
        assertEquals("15.00", player10.getPrize().toString());
        assertEquals("15.00", player11.getPrize().toString());
        assertEquals("5.00",  player12.getPrize().toString());
        assertEquals("0.00",  player13.getPrize().toString());
    }

    @Test
    public void canAwardPrizes10Players5Prizes(){
        participantsList6.add(player1);
        participantsList6.add(player2);
        participantsList6.add(player3);
        participantsList6.add(player4);
        participantsList6.add(player5);
        participantsList6.add(player6);
        participantsList6.add(player7);
        participantsList6.add(player8);
        participantsList6.add(player9);
        participantsList6.add(player10);
        prizes2.put(1, firstPrize);
        prizes2.put(2, secondPrize);
        prizes2.put(3, thirdPrize);
        prizes2.put(4, fourthPrize);
        prizes2.put(5, fifthPrize);
        tournament1.awardPrizes(participantsList6, prizes2);
        assertEquals("50.00", player9.getPrize().toString());
        assertEquals("20.00", player8.getPrize().toString());
        assertEquals("10.00", player7.getPrize().toString());
        assertEquals("5.00", player6.getPrize().toString());
        assertEquals("1.00", player4.getPrize().toString());
        assertEquals("1.00", player2.getPrize().toString());
        assertEquals("0.00", player1.getPrize().toString());
        assertEquals("0.00", player10.getPrize().toString());
        assertEquals("0.00", player3.getPrize().toString());
        assertEquals("0.00", player5.getPrize().toString());
    }


    @Test
    public void canAwardPrizes5Players3PrizesWithTies(){
        participantsList7.add(player1);
        participantsList7.add(player2);
        participantsList7.add(player5);
        participantsList7.add(player10);
        participantsList7.add(player11);
        participantsList7.add(player12);
        tournament1.awardPrizes(participantsList7, prizes);
        assertEquals("50.00", player2.getPrize().toString());
        assertEquals("20.00", player1.getPrize().toString());
        assertEquals("3.33", player5.getPrize().toString());
        assertEquals("3.33", player10.getPrize().toString());
        assertEquals("3.33", player11.getPrize().toString());
        assertEquals("0.00", player12.getPrize().toString());
    }

    @Test
    public void canAwardPrizes13Players7Prizes(){
        participantsList8.add(player1);
        participantsList8.add(player2);
        participantsList8.add(player3);
        participantsList8.add(player4);
        participantsList8.add(player5);
        participantsList8.add(player6);
        participantsList8.add(player7);
        participantsList8.add(player8);
        participantsList8.add(player9);
        participantsList8.add(player10);
        participantsList8.add(player11);
        participantsList8.add(player12);
        participantsList8.add(player13);
        prizes3.put(1, firstPrize);
        prizes3.put(2, firstPrize);
        prizes3.put(3, secondPrize);
        prizes3.put(4, secondPrize);
        prizes3.put(5, thirdPrize);
        prizes3.put(6, thirdPrize);
        prizes3.put(7, fourthPrize);
        tournament1.awardPrizes(participantsList8, prizes3);
        assertEquals("50.00", player9.getPrize().toString());
        assertEquals("50.00", player8.getPrize().toString());
        assertEquals("20.00", player7.getPrize().toString());
        assertEquals("20.00", player6.getPrize().toString());
        assertEquals("10.00", player2.getPrize().toString());
        assertEquals("10.00", player4.getPrize().toString());
        assertEquals("2.50", player3.getPrize().toString());
        assertEquals("2.50", player1.getPrize().toString());
        assertEquals("0.00", player5.getPrize().toString());
        assertEquals("0.00", player10.getPrize().toString());
        assertEquals("0.00", player11.getPrize().toString());
        assertEquals("0.00", player12.getPrize().toString());
        assertEquals("0.00", player13.getPrize().toString());
    }

}
