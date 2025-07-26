package model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Vendedor implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String email;
	private Date datanascimento;
	private Double salarioBase;

	private Departamento departamento;

	public Vendedor(Integer id, String name, String email, Date datanascimento, Double salarioBase,
			Departamento departamento) {

		this.id = id;
		this.name = name;
		this.email = email;
		this.datanascimento = datanascimento;
		this.salarioBase = salarioBase;
		this.departamento = departamento;
	}

	public Vendedor() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDatanascimento() {
		return datanascimento;
	}

	public void setDatanascimento(Date datanascimento) {
		this.datanascimento = datanascimento;
	}

	public Double getSalarioBase() {
		return salarioBase;
	}

	public void setSalarioBase(Double salarioBase) {
		this.salarioBase = salarioBase;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vendedor other = (Vendedor) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return String.format(
				"Vendedor {\n" + "  id = %d,\n" + "  nome = '%s',\n" + "  email = '%s',\n" + "  dataNascimento = %s,\n"
						+ "  salarioBase = %.2f,\n" + "  departamento = %s\n" + "}",
				id, name, email, datanascimento, salarioBase, departamento);
	}

}
