/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unnbiometrics.model;

import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import unnbiometrics.database.Database;
import unnbiometrics.database.Map;

/**
 *
 * @author michael-prime
 */
public class Identity {

    private final DPFPVerification verificator
            = DPFPGlobal.getVerificationFactory().createVerification();

    protected DPFPFeatureSet extractFeatures(DPFPSample sample, DPFPDataPurpose purpose) {
        DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory()
                .createFeatureExtraction();
        try {
            return extractor.createFeatureSet(sample, purpose);
        } catch (DPFPImageQualityException e) {
            return null;
        }
    }

    public final String regNumber;
    public final DPFPTemplate fingerprint;
    public final BufferedImage picture;
    public final Date dateRegistered;

    public Identity(String regNumber, DPFPTemplate fingerprint,
            BufferedImage picture, Date dateRegistered) {
        this.regNumber = regNumber;
        this.fingerprint = fingerprint;
        this.picture = picture;
        this.dateRegistered = dateRegistered;
    }

    public DPFPVerificationResult verify(DPFPSample sample) {
        DPFPFeatureSet features = extractFeatures(sample,
                DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);
        return verificator.verify(features, fingerprint);
    }

    public static Identity load(String regNumber) throws IOException {
        return Database.helper(Identity.class)
                .get(regNumber);
    }

    public static List<Identity> loadAll() throws IOException {
        return Database.helper(Identity.class).getAll();
    }

    public static class Factory extends unnbiometrics.database.Factory<Identity> {

        public Factory() {
            super(Identity.class, "regNumber", "fingerprint",
                    "picture", "dateRegistered");
        }

        @Override
        public Identity from(Map map) {
            DPFPTemplate template = DPFPGlobal.getTemplateFactory().createTemplate();
            template.deserialize(map.getAny("fingerprint"));
            
            byte[] bytes = map.getAny("picture");
            BufferedImage image = null;
            if (bytes != null) {
                try {
                    image = ImageIO.read(new ByteArrayInputStream(bytes));
                } catch (IOException ex) {
                    Logger.getLogger(Identity.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            return new Identity(map.getStr("regNumber"),
                    template, image,
                    map.getAny("dateRegistered"));
        }

        @Override
        public Object get(Identity i, String field) {
            switch (field.toLowerCase()) {
                case "regnumber":
                    return i.regNumber;
                case "dateregistered":
                    return i.dateRegistered;
                case "fingerprint":
                    return i.fingerprint.serialize();
                case "picture":
                    if (i.picture == null) return null;
                    try {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(i.picture, "jpg", baos);
                        return baos.toByteArray();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                default:
                    throw new IllegalArgumentException(field);
            }
        }
    }
}
