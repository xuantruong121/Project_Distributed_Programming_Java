package Constraints;

import Entity.NhanVien;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class NhanVienValidator implements ConstraintValidator<NhanVienConstraints, NhanVien> {

    private static final String ID_PATTERN = "^[A-Z]{3}-\\d{2}\\d{4}$"; // LLL-YYXXXX
    private static final String PHONE_PATTERN = "^0[35789]\\d{8}$";     // Phone numbers starting with 03, 05, 07, 08, 09
    private static final String CANCUOC_PATTERN = "^\\d{12}$";           // 12-digit ID number
    @Override
    public boolean isValid(NhanVien nhanVien, ConstraintValidatorContext constraintValidatorContext) {
      
        // not blank
        if(nhanVien.getMaNhanVien() == null || !nhanVien.getMaNhanVien().matches(ID_PATTERN)) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("maNhanVien không hợp lệ (LLL-YYXXXX)")
                    .addPropertyNode("maNhanVien")
                    .addConstraintViolation();
            return false;
        }
        if(nhanVien.getSoDienThoai() != null && !nhanVien.getSoDienThoai().matches(PHONE_PATTERN)) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Số điện thoại không hợp lệ (0[35789]xxxxxxxx)")
                    .addPropertyNode("soDienThoai")
                    .addConstraintViolation();
            return false;
        }
        if(nhanVien.getCanCuocCongDan() != null && !nhanVien.getCanCuocCongDan().matches(CANCUOC_PATTERN)) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Căn cước công dân không hợp lệ (12 chữ số)")
                    .addPropertyNode("canCuocCongDan")
                    .addConstraintViolation();
            return false;
        }
        if (nhanVien.getNgaySinh() != null  && Period.between(nhanVien.getNgaySinh(), LocalDate.now()).getYears()<18) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Ngày sinh không hợp lệ (Phải từ 18 tuổi trở lên)")
                    .addPropertyNode("ngaySinh")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
