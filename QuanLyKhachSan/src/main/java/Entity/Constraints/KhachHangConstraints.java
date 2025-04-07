package Entity.Constraints;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = KhachHangValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface KhachHangConstraints {
    String message() default "Invalid KhachHang";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
