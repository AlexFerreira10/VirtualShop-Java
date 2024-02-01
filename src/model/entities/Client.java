package model.entities;

import java.time.LocalDate;

public class Client {
	
	private String name;
	private Integer cpf;
	private LocalDate birthDate;
	
	public Client(String name, Integer cpf, LocalDate birthDate) {
		super();
		this.name = name;
		this.cpf = cpf;
		this.birthDate = birthDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCpf() {
		return cpf;
	}

	public void setCpf(Integer cpf) {
		this.cpf = cpf;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

}
