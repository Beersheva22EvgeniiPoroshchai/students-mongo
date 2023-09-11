package telran.spring.students.dto;

import jakarta.validation.constraints.NotNull;

public record Student (@NotNull Long id, @NotNull String name, @NotNull String phone){
	
	
}
