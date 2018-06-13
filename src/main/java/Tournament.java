import java.math.BigDecimal;
import java.util.*;


public class Tournament implements PrizeAllocator{
    private ArrayList<Participant> participants;
    private HashMap<Integer, BigDecimal> prizes;

    public Tournament(){
        this.participants = new ArrayList<Participant>();
        this.prizes = new HashMap<Integer, BigDecimal>();
    }

    public ArrayList<Participant> getParticipants() {
        return new ArrayList<Participant>(this.participants);
    }

    public void addParticipants(Participant participant){
        this.participants.add(participant);
    }

    public void removeParticipant(Participant participant){
        this.participants.remove(participant);
    }

    public void removeAllParticipants(){
        this.participants.clear();
    }

    public HashMap<Integer, BigDecimal> getPrizes() {
        return new HashMap<Integer, BigDecimal>(this.prizes);
    }

    public void addPrize(Integer position, BigDecimal amount){
        this.prizes.put(position, amount);
    }

    public BigDecimal getPrizeForPosition(Integer position){
       return this.prizes.get(position);
    }

    public void removePrizeInPosition(Integer position){
       this.prizes.remove(position);
    }

    public void removeAllPrizes(){
       this.prizes.clear();
    }

    public void sortParticipantsByScore(){
        Collections.sort(this.participants, (playerA, playerB) -> playerA.getScore() < playerB.getScore() ? 1 : playerA.getScore() == playerB.getScore() ? 0 : -1);
    }

    public HashMap<Integer, ArrayList<Participant>> populateHashWithArraysForPositions(){
        HashMap<Integer, ArrayList<Participant>> positions = new HashMap<>();
        for(int i = 1; i < getPrizes().size() + 2; i++) {
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

    public HashMap<Integer, ArrayList<Participant>> createHashMapOfPositions(){
        sortParticipantsByScore();
        HashMap<Integer, ArrayList<Participant>> positions = populateHashWithArraysForPositions();
//        Integer initialPlace = 1;
//        Integer lastPlace = positions.size();
        Integer position = 1;
        Integer lastScore = 0;
        Integer lastPosition = 0;
        for(Participant p : getParticipants()) {
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
//        for(int i = getParticipants().size() -1; i > 0; i--) {
//            if (getParticipants().get(i).getScore() > getParticipants().get(i-1).getScore()) {
//                if(!checkIfHashContainsPlayer(positions, getParticipants().get(i))){
//                    positions.get(initialPlace).add(getParticipants().get(i));
//                    initialPlace++;
//                }
//            }
//            if (getParticipants().get(i).getScore() == getParticipants().get(i-1).getScore()) {
//                if(!checkIfHashContainsPlayer(positions, getParticipants().get(i))){
//                    positions.get(initialPlace).add(getParticipants().get(i));
//                }
//                if(!checkIfHashContainsPlayer(positions, getParticipants().get(i-1))){
//                    positions.get(initialPlace).add(getParticipants().get(i-1));
//                }
//                initialPlace++;
//            }
//        }
//        if (getParticipants().get(0).getScore() < getParticipants().get(1).getScore()) {
//            positions.get(lastPlace).add(getParticipants().get(0));
//            initialPlace++;
//        }
        return positions;
    }

    public void awardPrizes(List<Participant> participants, Map<Integer, BigDecimal> prizes) {

    }
}
