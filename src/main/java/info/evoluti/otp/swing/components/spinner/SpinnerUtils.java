package info.evoluti.otp.swing.components.spinner;

/**
 *
 * @author Raven
 */
public class SpinnerUtils {

    public static float easeInOutQuad(float x) {
        double v = x < 0.5 ? 2 * x * x : 1 - Math.pow(-2 * x + 2, 2) / 2;
        return (float) v;
    }
}
