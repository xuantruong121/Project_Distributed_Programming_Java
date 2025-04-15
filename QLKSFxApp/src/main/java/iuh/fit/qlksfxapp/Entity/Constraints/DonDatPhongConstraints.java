package iuh.fit.qlksfxapp.Entity.Constraints;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Constraint(validatedBy = DonDatPhongValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DonDatPhongConstraints {
        String message() default "Invalid DonDatPhong";
        Class<?>[] groups() default {};
        Class<?>[] payload() default {};
}
