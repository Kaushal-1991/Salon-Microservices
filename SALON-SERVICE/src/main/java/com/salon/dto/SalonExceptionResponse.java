package com.salon.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class SalonExceptionResponse {
	private String message;
	private LocalDateTime dateTime;
	private String path;
}
