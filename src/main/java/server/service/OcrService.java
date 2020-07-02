package server.service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import server.constant.CardType;
import server.dto.PersonResponse;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;

@Service
public class OcrService {

    @Resource
    private Tesseract tesseract;

    public PersonResponse extractCardInfo(CardType cardType, BufferedImage front, BufferedImage back)
            throws TesseractException {
        PersonResponse person = new PersonResponse();
        if (cardType == CardType.CMND) {
            String result = tesseract.doOCR(front) + tesseract.doOCR(back);
            person.setName(result);
        }
        return person;
    }
}
