import java.math.BigInteger;

public class Key {
    private final BigInteger n;
    private final BigInteger e_d;

    public Key(BigInteger n, BigInteger e_d) {
        this.n = n;
        this.e_d = e_d;
    }

    public BigInteger getN() {
        return n;
    }

    public BigInteger getE() {
        return e_d;
    }
}