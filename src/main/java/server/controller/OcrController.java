package server.controller;

import net.sourceforge.tess4j.TesseractException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.constant.CardType;
import server.dto.CardRequest;
import server.dto.PersonResponse;
import server.service.OcrService;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("ocr")
public class OcrController {

    @Resource
    private OcrService ocrService;

    @PostMapping
    public ResponseEntity<PersonResponse> ocr(@RequestBody CardRequest cardRequest) {
        try {
            CardType cardType = cardRequest.getCardType();
            if (cardType != CardType.CMND && cardType != CardType.CCCD) {
                return ResponseEntity.badRequest().build();
            }
            byte[] frontDecoded = Base64.getDecoder().decode(cardRequest.getFrontImageBase64());
            BufferedImage frontImage = ImageIO.read(new ByteArrayInputStream(frontDecoded));
            byte[] backDecoded = Base64.getDecoder().decode(cardRequest.getBackImageBase64());
            BufferedImage backImage = ImageIO.read(new ByteArrayInputStream(backDecoded));
            PersonResponse body = ocrService.extractCardInfo(cardType, frontImage, backImage);
            return ResponseEntity.ok(body);
        } catch (IOException | TesseractException e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }
}
