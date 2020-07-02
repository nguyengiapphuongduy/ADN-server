package server.controller;

import net.sourceforge.tess4j.TesseractException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.constant.CardType;
import server.constant.OcrServiceType;
import server.dto.CardRequest;
import server.dto.PersonResponse;
import server.service.OcrService;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@RestController
@RequestMapping("ocr")
public class OcrController {

    @Resource
    private OcrService ocrService;

    @PostMapping
    public ResponseEntity<PersonResponse> ocr(
            @RequestParam(defaultValue = "TESSERACT", required = false) OcrServiceType service,
            @RequestBody CardRequest cardRequest) {
        try {
            CardType cardType = cardRequest.getCardType();
            if (cardType != CardType.CMND && cardType != CardType.CCCD) {
                return ResponseEntity.badRequest().build();
            }
            byte[] frontDecoded = Base64.getDecoder().decode(cardRequest.getFrontImageBase64());
            InputStream frontImageInputStream = new ByteArrayInputStream(frontDecoded);
            byte[] backDecoded = Base64.getDecoder().decode(cardRequest.getBackImageBase64());
            InputStream backImageInputStream = new ByteArrayInputStream(backDecoded);
            PersonResponse body;
            switch (service) {
                case TESSERACT:
                    body = ocrService.extractCardInfoTesseract(cardType, frontImageInputStream, backImageInputStream);
                    return ResponseEntity.ok(body);
                case GOOGLE:
                    body = ocrService.extractCardInfoGoogle(cardType, frontImageInputStream, backImageInputStream);
                    return ResponseEntity.ok(body);
                default:
                    return ResponseEntity.badRequest().build();
            }
        } catch (IOException | TesseractException e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }
}
