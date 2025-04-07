//package Constraints;
//
//import Entity.Phong;
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//
//public class PhongValidator implements ConstraintValidator<PhongConstraints,Phong> {
//    private static final String ID_PATTERN = "^[A-Z]{3}-\\d{1}\\d{3}$"; // PPP-YXXX
//
//    @Override
//    public boolean isValid(Phong phong, ConstraintValidatorContext constraintValidatorContext) {
//        if(phong == null || !phong.getMaPhong().matches(ID_PATTERN)) {
//            constraintValidatorContext.buildConstraintViolationWithTemplate("maPhong không hợp lệ (PPP-YXXX)")
//                    .addPropertyNode("maPhong")
//                    .addConstraintViolation();
//            return false;
//        }
//        return true;
//    }
//}
