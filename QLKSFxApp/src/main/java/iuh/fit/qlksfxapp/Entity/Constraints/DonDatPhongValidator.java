package iuh.fit.qlksfxapp.Entity.Constraints;

import iuh.fit.qlksfxapp.Entity.DonDatPhong;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DonDatPhongValidator implements ConstraintValidator<DonDatPhongConstraints, DonDatPhong> {
    private final String ID_PATTERN = "^\\d{9}$"; // DDMMYYAAA
    @Override
    public boolean isValid(DonDatPhong donDatPhong, ConstraintValidatorContext constraintValidatorContext) {
        if (donDatPhong == null || !donDatPhong.getMaDonDatPhong().matches(ID_PATTERN)) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("maDonDatPhong không hợp lệ (DDMMYYAAA)")
                    .addPropertyNode("maDonDatPhong")
                    .addConstraintViolation();
            return false;
        }
        if(donDatPhong.getNgayNhan().isAfter(donDatPhong.getNgayTra())||donDatPhong.getNgayNhan().isEqual(donDatPhong.getNgayTra())){
            constraintValidatorContext.buildConstraintViolationWithTemplate("Ngày nhận < ngày trả không hợp lệ")
                    .addPropertyNode("ngayNhan")
                    .addPropertyNode("ngayTra")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
