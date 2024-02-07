package model.entities;

import java.time.LocalDate;

public class Client {
	
	private String name;
	private String cpf;
	private LocalDate birthDate;
	
	public Client(String name, String cpf, LocalDate birthDate) {
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "Client{" +
				"name='" + name + '\'' +
				", cpf=" + cpf +
				", birthDate=" + birthDate +
				'}';
	}
}