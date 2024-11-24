package umu.tds.persistencia;

import java.util.List;

import umu.tds.dominio.ContactoIndividual;

public interface ContactoIndividualDAO {

	public void registrarContactoIndividual(ContactoIndividual contactoIndividual);

	public void borrarContactoIndividual(ContactoIndividual contactoIndividual);

	public void modificarContactoIndividual(ContactoIndividual contactoIndividual);

	public ContactoIndividual recuperarContactoIndividual(int codigo);

	public List<ContactoIndividual> recuperarTodosLosContactosIndividuales();
	
}
