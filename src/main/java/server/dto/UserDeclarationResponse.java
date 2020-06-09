package server.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDeclarationResponse {
    // User
    private String idCardNumber;
    private String name;
    private Date birthDay;
    private String permanentAddress;
    private Date cardDate;
    private String cardPlace;
    private String email;
    private String phoneNumber;

    // Declaration
    private String countriesVisited;
    private Boolean isDomesticTravel;
    private String fromProvince;
    private String toProvince;
    private Date departureDate;
    private Date arrivalDate;
    private String travelBy;

    // question 1
    private Boolean sot;
    private Boolean ho;
    private Boolean khoTho;
    private Boolean viemPhoi;
    private Boolean dauHong;
    private Boolean metMoi;

    // question 2
    private Boolean nguoiBenh;
    private Boolean nguoiTuNuocCoBenh;
    private Boolean nguoiCoBieuHien;

    // question 3
    private Boolean benhGanManTinh;
    private Boolean benhMauManTinh;
    private Boolean benhPhoiManTinh;
    private Boolean benhThanManTinh;
    private Boolean benhTimMach;
    private Boolean huyetApCao;
    private Boolean suyGiamMienDich;
    private Boolean ghepTangHoacXuong;
    private Boolean tieuDuong;
    private Boolean ungThu;
    private Boolean mangThai;
}
