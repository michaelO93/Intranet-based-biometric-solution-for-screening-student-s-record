package unnbiometrics;

import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.processing.*;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import java.awt.*;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import unnbiometrics.database.Database;
import unnbiometrics.model.Identity;

public class EnrollmentForm extends CaptureForm {
    public com.digitalpersona.uareu.Fmd enrollmentFMD;
    private final DPFPEnrollment enroller = DPFPGlobal.getEnrollmentFactory().createEnrollment();

    String reg3;

    EnrollmentForm(Frame owner) {
        super(owner);
    }

    @Override
    protected void init() {
        super.init();
        this.setTitle("FINGERPRINT ENROLLMENT");
        updateStatus();

    }

    @Override
    protected void process(DPFPSample sample) {
        super.process(sample);

        try {
            for (Identity identity : Identity.loadAll()) {
                DPFPVerificationResult result = identity.verify(sample);

                if (result.isVerified()) {
                    makeReport("THe fingerprint is already registered");
                    throw new IllegalArgumentException("FINGERPRINT ALREADY REGISTERED!");
                }
            }
            makeReport("The fingerprint feature set was created.");
            DPFPFeatureSet features 
                    = extractFeatures(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);
            enroller.addFeatures(features);		// Add feature set to template.
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (DPFPImageQualityException ex) {
        } catch (IOException ex) {
            Logger.getLogger(EnrollmentForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            updateStatus();

            // Check if template has been created.
            switch (enroller.getTemplateStatus()) {
                case TEMPLATE_STATUS_READY:	// report success and stop capturing
                    stop();
                    ((addStudentForm) getOwner()).setTemplate(enroller.getTemplate());
                    setPrompt("Click Close, and then click Fingerprint Verification.");
                    break;

                case TEMPLATE_STATUS_FAILED:	// report failure and restart capturing
                    enroller.clear();
                    stop();
                    updateStatus();
                    ((addStudentForm) getOwner()).setTemplate(null);
                    JOptionPane.showMessageDialog(EnrollmentForm.this,
                            "The fingerprint template is not valid. Repeat fingerprint enrollment.",
                            "Fingerprint Enrollment", JOptionPane.ERROR_MESSAGE);
                    start();
                    break;
            }
        }
    }

    private void updateStatus() {
        // Show number of samples needed.
        setStatus(String.format("Fingerprint samples needed: %1$s", enroller.getFeaturesNeeded()));
    }

    public boolean NrolSuccess(String regNumber) throws Exception {
        DPFPTemplate template = this.enroller.getTemplate();
        if (template != null) {
            Identity newId = new Identity(regNumber, template, null, new Date());
            Database.helper(Identity.class).insert(newId);
            return true;
        }

        return false;
    }


}
