import java.math.BigDecimal;

public class Player implements Participant {
    private String name;
    private int score;
    private BigDecimal prize;

    public Player(String name, int score){
        this.name = name;
        this.score = score;
        this.prize = new BigDecimal("0.00").setScale(2);
    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }

    public BigDecimal getPrize() {
        return this.prize;
    }

    public void setPrize(BigDecimal prize) {
        this.prize = prize;
    }
}
