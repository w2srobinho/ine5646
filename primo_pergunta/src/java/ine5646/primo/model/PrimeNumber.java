package ine5646.primo.model;

/**
 *
 * @author robinho
 */
public class PrimeNumber
{
    // retorna true se num for primo ou false caso contrário.
    public boolean ehPrimo(long num)
    {
        if (num > 3) {
            for (int i = 2; i < num; ++i) {
                if (num % i == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
