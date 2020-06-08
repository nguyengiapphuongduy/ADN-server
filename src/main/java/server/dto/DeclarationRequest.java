package server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class DeclarationRequest {
    String countriesVisited;

    Boolean isDomesticTravel;
    String fromProvince;
    String toProvince;
    Date departureDate;
    Date arrivalDate;
    String travelBy;

    // question 1
    @Schema(example = "false")
    Boolean sot;
    @Schema(example = "false")
    Boolean ho;
    @Schema(example = "false")
    Boolean khoTho;
    @Schema(example = "false")
    Boolean viemPhoi;
    @Schema(example = "false")
    Boolean dauHong;
    @Schema(example = "false")
    Boolean metMoi;

    // question 2
    @Schema(example = "false")
    Boolean nguoiBenh;
    @Schema(example = "false")
    Boolean nguoiTuNuocCoBenh;
    @Schema(example = "false")
    Boolean nguoiCoBieuHien;

    // question 3
    @Schema(example = "false")
    Boolean benhGanManTinh;
    @Schema(example = "false")
    Boolean benhMauManTinh;
    @Schema(example = "false")
    Boolean benhPhoiManTinh;
    @Schema(example = "false")
    Boolean benhThanManTinh;
    @Schema(example = "false")
    Boolean benhTimMach;
    @Schema(example = "false")
    Boolean huyetApCao;
    @Schema(example = "false")
    Boolean suyGiamMienDich;
    @Schema(example = "false")
    Boolean ghepTangHoacXuong;
    @Schema(example = "false")
    Boolean tieuDuong;
    @Schema(example = "false")
    Boolean ungThu;
    @Schema(example = "false")
    Boolean mangThai;
}
