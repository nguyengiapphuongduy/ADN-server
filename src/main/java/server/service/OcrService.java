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
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        PersonResponse person = new PersonResponse();
        person.setCardType(cardType);
        InputStreamResource frontImage = new InputStreamResource(front);
        AnnotateImageResponse frontAnnotateImageResponse
                = cloudVisionTemplate.analyzeImage(frontImage, Feature.Type.TEXT_DETECTION);
        TextAnnotation frontTextAnnotation = frontAnnotateImageResponse.getFullTextAnnotation();
        Optional<String> idOptional = extractIdCardNumber(frontTextAnnotation);
        if (idOptional.isPresent()) {
            Optional<User> userOptional = userRepository.findByIdCardNumber(idOptional.get());
            if (userOptional.isPresent()) {
                NullableFieldBeanUtilsBean.getInstance().copyProperties(person, userOptional.get());
                person.setIdCardNumber(idOptional.get());
                return person;
            } else {
                person.setIdCardNumber(idOptional.get());
            }
        }
        extractName(frontTextAnnotation).ifPresent(person::setName);
        extractDate(frontTextAnnotation).ifPresent(person::setBirthDay);
        extractAddress(frontTextAnnotation).ifPresent(person::setPermanentAddress);
        InputStreamResource backImage = new InputStreamResource(back);
        AnnotateImageResponse backAnnotateImageResponse
                = cloudVisionTemplate.analyzeImage(backImage, Feature.Type.TEXT_DETECTION);
        TextAnnotation backTextAnnotation = backAnnotateImageResponse.getFullTextAnnotation();
        extractCardDate(backTextAnnotation).ifPresent(person::setCardDate);
        return person;
    }

    private Optional<String> extractIdCardNumber(TextAnnotation textAnnotation) {
        return Arrays.stream(textAnnotation.getText().split("[ \n]"))
                .filter(s -> s.matches("[0-9]{9,12}"))
                .findAny();
    }

    private Optional<String> extractAddress(TextAnnotation textAnnotation) {
        List<String> lines = Arrays.asList(textAnnotation.getText().split("\n"));
        Optional<Integer> indexOptional = findLineIndex(lines, "khk");
        return indexOptional.map(integer -> IntStream.range(integer + 1, lines.size())
                .mapToObj(lines::get)
                .collect(Collectors.joining()));
    }

    private Optional<String> extractName(TextAnnotation textAnnotation) {
        List<String> lines = Arrays.asList(textAnnotation.getText().split("\n"));
        Optional<Integer> startOptional = findLineIndex(lines, "hoten");
        Optional<Integer> endOptional = findLineIndex(lines, "sinh");
        if (startOptional.isPresent() && endOptional.isPresent()) {
            return Optional.of(IntStream.range(startOptional.get() + 1, endOptional.get())
                    .mapToObj(lines::get)
                    .collect(Collectors.joining()));
        }
        return Optional.empty();
    }

    private Optional<Integer> findLineIndex(List<String> lines, String keyword) {
        int i;
        for (i = 0; i < lines.size(); i++) {
            String normalized = Normalizer.normalize(lines.get(i), Normalizer.Form.NFKD)
                    .replaceAll("[ \\P{ASCII}]", "")
                    .toLowerCase();
            if (normalized.contains(keyword)) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    private Optional<Date> extractDate(TextAnnotation textAnnotation) {
        Date date;
        String text = textAnnotation.getText();
        Matcher matcher = Pattern.compile("[0-9]{2}.*[01][0-9].*[12][0-9]{3}").matcher(text);
        if (matcher.find()) {
            String dateString = matcher.group(0).replaceAll("[^0-9]", "");
            LocalDate localDate = LocalDate.of(
                    Integer.parseInt(dateString.substring(4)),
                    Integer.parseInt(dateString.substring(2, 4)),
                    Integer.parseInt(dateString.substring(0, 2)));
            date = Date.from(localDate
                    .atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
        } else {
            date = null;
        }
        return Optional.ofNullable(date);
    }

    private Optional<Date> extractCardDate(TextAnnotation textAnnotation) {
        Date date;
        String normalized = Normalizer.normalize(textAnnotation.getText(), Normalizer.Form.NFKD)
                .replaceAll("[\n. \\P{ASCII}]", "")
                .toLowerCase();
        Matcher dayMatcher = Pattern.compile("ngay[0-9]{2}").matcher(normalized);
        Matcher monthMatcher = Pattern.compile("thang[01][0-9]").matcher(normalized);
        Matcher yearMatcher = Pattern.compile("[12][0-9]{3}").matcher(normalized);
        if (dayMatcher.find() && monthMatcher.find() && yearMatcher.find()) {
            LocalDate localDate = LocalDate.of(Integer.parseInt(yearMatcher.group(0)),
                    Integer.parseInt(monthMatcher.group(0).substring(5)),
                    Integer.parseInt(dayMatcher.group(0).substring(4)));
            date = Date.from(localDate
                    .atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
        } else {
            date = null;
        }
        return Optional.ofNullable(date);
    }
}
