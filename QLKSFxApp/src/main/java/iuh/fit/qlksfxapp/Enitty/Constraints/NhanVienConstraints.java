package iuh.fit.qlksfxapp.Enitty.Constraints;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NhanVienValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NhanVienConstraints {
    String message() default "Invalid NhanVien";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
