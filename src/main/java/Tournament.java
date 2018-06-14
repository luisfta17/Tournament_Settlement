import java.math.BigDecimal;
import java.util.*;


public class Tournament implements PrizeAllocator{
    public Tournament(){
    }


    public List<Participant> sortParticipantsByScore(List<Participant> participants){
        Collections.sort(participants, (playerA, playerB) -> playerA.getScore() < playerB.getScore() ? 1 : playerA.getScore() == playerB.getScore() ? 0 : -1);
        return participants;
    }

    public HashMap<Integer, ArrayList<Participant>> populateHashWithArraysForPositions(Map<Integer, BigDecimal> prizes){
        HashMap<Integer, ArrayList<Participant>> positions = new HashMap<>();
        for(int i = 1; i < prizes.size() + 2; i++) {
            positions.put(i, new ArrayList<Participant>());
        }
        return  positions;
    }

    public boolean checkIfHashContainsPlayer(HashMap<Integer, ArrayList<Participant>> positions, Participant player){
        Integer key = 1;
        for (int i = 0; i < positions.size(); i++ ){
            if (positions.get(key).contains(player)){
                return true;
            }
            key++;
        }
        return false;
    }

    public HashMap<Integer, ArrayList<Participant>> createHashMapOfPositions(List<Participant> participants, Map<Integer, BigDecimal> prizes){
        List<Participant> list = sortParticipantsByScore(participants);
        HashMap<Integer, ArrayList<Participant>> positions = populateHashWithArraysForPositions(prizes);
        Integer position = 1;
        Integer lastScore = 0;
        Integer lastPosition = 0;
        for(Participant p : list) {
            if (p.getScore() != lastScore) {
                lastScore = p.getScore();
                positions.get(position).add(p);
                lastPosition = position;
                position++;
            }
            else if (p.getScore() == lastScore){
                lastScore = p.getScore();
                positions.get(lastPosition).add(p);
            }
        }
        return positions;
    }

    public BigDecimal getJackpot(Map<Integer, BigDecimal> prizes, Integer initialPosition, Integer finalPosition){
        BigDecimal jackPot = new BigDecimal("0.00").setScale(2, BigDecimal.ROUND_FLOOR);
        for (int i = 0; i < Math.min(prizes.size(), finalPosition); i++){
            BigDecimal prize = prizes.get(initialPosition);
            jackPot = jackPot.add(prize);
            initialPosition++;
        }

        return jackPot.divide(new BigDecimal(finalPosition), BigDecimal.ROUND_FLOOR);
    }

    public void awardPrizes(List<Participant> participants, Map<Integer, BigDecimal> prizes) {
        // sort participants
        // create hash of arrays in function of size of prizes
        // group participants in positions in hash

    }
}
