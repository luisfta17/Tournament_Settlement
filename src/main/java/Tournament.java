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
                if(position > positions.size()){
                    position = positions.size();
                }
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

    public BigDecimal getJackpot(Map<Integer, BigDecimal> prizes, Integer initialPosition, Integer finalPosition, Integer numberOfPlayers){
        BigDecimal jackPot = new BigDecimal("0.00").setScale(2, BigDecimal.ROUND_FLOOR);
        for (int i = 0; i < Math.min(prizes.size(), finalPosition); i++){
            BigDecimal prize = prizes.get(initialPosition);
            jackPot = jackPot.add(prize);
            initialPosition++;
        }
        return jackPot.divide(new BigDecimal(numberOfPlayers), BigDecimal.ROUND_FLOOR);

    }


    public void awardPrizes(List<Participant> participants, Map<Integer, BigDecimal> prizes) {
        //Build hash of participants placing in each position
        HashMap<Integer, ArrayList<Participant>> hashResults = this.createHashMapOfPositions(participants, prizes);

          //Initialise prize to start at
          int prizeKey = 1;

          //iterate through each participant result
          for(int i = 1; i <= hashResults.size(); i ++){

              //If noone came in a position then exit
              if(hashResults.get(i).size() == 0) break;

              // Get how many people placed in position i
              int numWinners = hashResults.get(i).size();

              // Initialise prize pool
              BigDecimal prizeValue = new BigDecimal("0.00").setScale(2, BigDecimal.ROUND_FLOOR);

              // start j at next prize
              int j = prizeKey;

              //get limit for j in the loop
              int limit = Math.min(prizeKey + numWinners, prizes.size() + 1);
              do{
                  // if we exceed bounds break
                  if(j > prizes.size()) break;

                  //get current prize and add it to the total prize pool
                  prizeValue = prizeValue.add(prizes.get(j));
                  j++;
              } while(j < limit);


              BigDecimal individualPrize = prizeValue.divide(new BigDecimal(numWinners), BigDecimal.ROUND_FLOOR);
              //assign individual prizes
              for(Participant participant : hashResults.get(i) ){
                  participant.setPrize(individualPrize);
              }
              //Update starting prize for next position
              prizeKey += numWinners;
          }
//        Integer prizePosition = 1;
//        Integer currentPosition = 1;
//        for (int i = 0; i < prizes.size(); i++){
//            Integer numberOfPlayersInPosition = hashResults.get(currentPosition).size();
//            if (numberOfPlayersInPosition >= prizes.size()){
//                BigDecimal jackPot = this.getJackpot(prizes, prizePosition, numberOfPlayersInPosition, numberOfPlayersInPosition);
//                for (Participant player: hashResults.get(prizePosition)){
//                    player.setPrize(jackPot);
//                }
//                break;
////            } else if(numberOfPlayersInPosition == prizes.size()){
////                BigDecimal jackPot = this.getJackpot(prizes, prizePosition, 1, numberOfPlayersInPosition);
////                for (Participant player: hashResults.get(prizePosition)){
////                    player.setPrize(jackPot);
////                }
////                break;
//
//            }else {
//                BigDecimal jackPot = this.getJackpot(prizes, prizePosition, numberOfPlayersInPosition, numberOfPlayersInPosition);
//                for (Participant player: hashResults.get(currentPosition)){
//                    player.setPrize(jackPot);
//                }
//                prizePosition += numberOfPlayersInPosition;
//                currentPosition++;
//            }
//            if (prizePosition > prizes.size()){
//                break;
//            }
//        }
//
    }
}
