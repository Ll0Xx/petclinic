package com.antont.petclinic.pet.validation;

import com.antont.petclinic.pet.PetTypeRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TypeValidator implements ConstraintValidator<TypeConstraint, Integer> {

    final private PetTypeRepository typeRepository;

    public TypeValidator(PetTypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public void initialize(TypeConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return typeRepository.existsById(value);
    }
}
