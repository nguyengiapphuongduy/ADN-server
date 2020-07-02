package server.service;

import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.TextAnnotation;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import server.constant.CardType;
import server.document.User;
import server.dto.PersonResponse;
import server.repository.UserRepository;
import server.util.NullableFieldBeanUtilsBean;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class OcrService {
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Resource
    private Tesseract tesseract;

    @Resource
    private CloudVisionTemplate cloudVisionTemplate;

    @Resource
    private UserRepository userRepository;

    public PersonResponse extractCardInfoTesseract(CardType cardType, InputStream front, InputStream back)
            throws IOException, TesseractException {
        PersonResponse person = new PersonResponse();
        BufferedImage frontImage = ImageIO.read(front);
        BufferedImage backImage = ImageIO.read(back);
        if (cardType == CardType.CMND) {
            if (frontImage != null) {
                String s1 = tesseract.doOCR(frontImage);
                person.setPermanentAddress(s1);
                logger.info(s1);
            }
            if (backImage != null) {
                String s2 = tesseract.doOCR(backImage);
                person.setCardPlace(s2);
                logger.info(s2);
            }
        }
        return person;
    }

    public PersonResponse extractCardInfoGoogle(CardType cardType, InputStream front, InputStream back) {
        InputStreamResource frontImage = new InputStreamResource(front);
        PersonResponse person = new PersonResponse();
        AnnotateImageResponse annotateImageResponse
                = cloudVisionTemplate.analyzeImage(frontImage, Feature.Type.TEXT_DETECTION);
        TextAnnotation textAnnotation = annotateImageResponse.getFullTextAnnotation();
        if (cardType == CardType.CMND) {
            Optional<String> idOptional = extractIdCardNumber(textAnnotation);
            if (idOptional.isPresent()) {
                Optional<User> userOptional = userRepository.findByIdCardNumber(idOptional.get());
                if (userOptional.isPresent()) {
                    NullableFieldBeanUtilsBean.getInstance().copyProperties(person, userOptional.get());
                    person.setCardType(CardType.CMND);
                    person.setCmnd(idOptional.get());
                    return person;
                }
            }
        }
        return person;
    }

    private Optional<String> extractIdCardNumber(TextAnnotation textAnnotation) {
        return Arrays.stream(textAnnotation.getText().split("[ \n]"))
                .filter(s -> s.matches("[0-9]{9}"))
                .findAny();
    }
}
