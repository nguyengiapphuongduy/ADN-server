package server.document;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class Declaration {
    @Id
    String id;
    Date createdAt;

    String countriesVisited;

    Boolean isDomesticTravel;
    String fromProvince;
    String toProvince;
    Date departureDate;
    Date arrivalDate;
    String travelBy;

    // question 1
    Boolean sot;
    Boolean ho;
    Boolean khoTho;
    Boolean viemPhoi;
    Boolean dauHong;
    Boolean metMoi;

    // question 2
    Boolean nguoiBenh;
    Boolean nguoiTuNuocCoBenh;
    Boolean nguoiCoBieuHien;

    // question 3
    Boolean benhGanManTinh;
    Boolean benhMauManTinh;
    Boolean benhPhoiManTinh;
    Boolean benhThanManTinh;
    Boolean benhTimMach;
    Boolean huyetApCao;
    Boolean suyGiamMienDich;
    Boolean ghepTangHoacXuong;
    Boolean tieuDuong;
    Boolean ungThu;
    Boolean mangThai;
}
