package com.bilous.instagram;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.bilous.instagram");

        noClasses()
            .that()
                .resideInAnyPackage("com.bilous.instagram.service..")
            .or()
                .resideInAnyPackage("com.bilous.instagram.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.bilous.instagram.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
