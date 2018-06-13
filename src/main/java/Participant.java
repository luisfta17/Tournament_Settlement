import java.math.BigDecimal;

public interface Participant {
    String getName();
    int getScore();
    BigDecimal getPrize();
    void setPrize( BigDecimal prize );
}
