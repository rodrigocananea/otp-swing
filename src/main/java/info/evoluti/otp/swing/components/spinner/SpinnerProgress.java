package info.evoluti.otp.swing.components.spinner;

import javax.swing.JProgressBar;

/**
 *
 * @author Raven
 */
public class SpinnerProgress extends JProgressBar {

    public SpinnerProgress() {
        init();
    }

    @Override
    public void updateUI() {
        setUI(new SpinnerProgressUI());
    }

    private void init() {
        setUI(new SpinnerProgressUI());
    }
}
