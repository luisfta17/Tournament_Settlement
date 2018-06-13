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
        Collections.sort(this.participants, (playerA, playerB) -> playerA.getScore() < playerB.getScore() ? -1 : playerA.getScore() == playerB.getScore() ? 0 : 1);
    }

    public void awardPrizes(List<Participant> participants, Map<Integer, BigDecimal> prizes) {

    }
}
