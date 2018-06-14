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
        for(int i = 1; i <= prizes.size() + 1; i++) {
            positions.put(i, new ArrayList<>());
        }
        return  positions;
    }

    public HashMap<Integer, ArrayList<Participant>> createHashMapOfPositions(List<Participant> participants, Map<Integer, BigDecimal> prizes){
        List<Participant> list = sortParticipantsByScore(participants);
        HashMap<Integer, ArrayList<Participant>> positions = populateHashWithArraysForPositions(prizes);
        Integer position = 1;
        Integer lastScore = 0;
        Integer lastPosition = 0;
        for(Participant p : list) {
            if (p.getScore() != lastScore) {
                if(position > positions.size()){
                    position = positions.size();
                }
                lastScore = p.getScore();
                positions.get(position).add(p);
                lastPosition = position;
                position++;
            }
            else if (p.getScore() == lastScore){
                positions.get(lastPosition).add(p);
            }
        }
        return positions;
    }

    public BigDecimal collectJackpot(Map<Integer, BigDecimal> prizes, int prizeKey, int numWinners){
        BigDecimal prizeValue = new BigDecimal("0.00").setScale(2, BigDecimal.ROUND_FLOOR);
        int j = prizeKey;
        int limit = Math.min(prizeKey + numWinners, prizes.size() + 1);
        do{
            if(j > prizes.size()) break;
            prizeValue = prizeValue.add(prizes.get(j));
            j++;
        } while(j < limit);

        return prizeValue;
    }

    public void awardPrizes(List<Participant> participants, Map<Integer, BigDecimal> prizes) {
        HashMap<Integer, ArrayList<Participant>> hashResults = this.createHashMapOfPositions(participants, prizes);
        int prizeKey = 1;
        for(int i = 1; i <= hashResults.size(); i ++){
            if(hashResults.get(i).size() == 0) break;
            int numWinners = hashResults.get(i).size();
            BigDecimal prizeValue = collectJackpot(prizes, prizeKey, numWinners);
            BigDecimal individualPrize = prizeValue.divide(new BigDecimal(numWinners), BigDecimal.ROUND_FLOOR);
            for(Participant participant : hashResults.get(i) ){
                participant.setPrize(individualPrize);
            }
            prizeKey += numWinners;
        }
    }
}
