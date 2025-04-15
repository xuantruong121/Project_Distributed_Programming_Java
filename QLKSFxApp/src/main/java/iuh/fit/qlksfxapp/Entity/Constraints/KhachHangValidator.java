package iuh.fit.qlksfxapp.Entity.Constraints;

import iuh.fit.qlksfxapp.Entity.KhachHang;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class KhachHangValidator implements ConstraintValidator<KhachHangConstraints, KhachHang> {
    private final String ID_PATTERN = "^\\d{12}$"; //DDMMYYXXXXXX
    private static final String PHONE_PATTERN = "^0[35789]\\d{8}$";     // Phone numbers starting with 03, 05, 07, 08, 09
    private static final String CANCUOC_PATTERN = "^\\d{12}$";           // 12-digit ID number
    @Override
    public boolean isValid(KhachHang khachHang, ConstraintValidatorContext constraintValidatorContext) {
       if (khachHang.getMaKhachHang() == null || !khachHang.getMaKhachHang().matches(ID_PATTERN)) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Mã khách hàng không hợp lệ (12 chữ số)")
                    .addPropertyNode("maKhachHang")
                    .addConstraintViolation();
            return false;
        }
        if (khachHang.getSoDienThoai() != null && !khachHang.getSoDienThoai().matches(PHONE_PATTERN)) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Số điện thoại không hợp lệ (0[35789]xxxxxxxx)")
                    .addPropertyNode("soDienThoai")
                    .addConstraintViolation();
            return false;
        }
        if (khachHang.getCanCuocCongDan() != null && !khachHang.getCanCuocCongDan().matches(CANCUOC_PATTERN)) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Căn cước công dân không hợp lệ (12 chữ số)")
                    .addPropertyNode("canCuocCongDan")
                    .addConstraintViolation();
            return false;
        }
        if(khachHang.getNgaySinh()!=null && Period.between(khachHang.getNgaySinh(), LocalDate.now()).getYears()<15){
            constraintValidatorContext.buildConstraintViolationWithTemplate("Ngày sinh không hợp lệ (Phải từ 15 tuổi trở lên)")
                    .addPropertyNode("ngaySinh")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
