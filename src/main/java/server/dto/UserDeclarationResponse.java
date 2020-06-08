package server.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDeclarationResponse {
    // Declaration
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
    // User
    private String id;
    private String cmnd;
    private String cccd;
    private String name;
    private Date birthDay;
    private String permanentAddress;
    private Date cardDate;
    private String cardPlace;
}
