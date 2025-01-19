package umu.tds.persistencia;
/**
 * Clase que implementa una factoria de DAOs concreta para el sistema de persistencia TDS
 */
public class TDSFactoriaDAO extends FactoriaDAO{
	
	public TDSFactoriaDAO () {
	}

	@Override
	public ContactoIndividualDAO getContactoIndividualDAO() {
		return (ContactoIndividualDAO) AdaptadorContactoIndividualTDS.getInstancia();
	}

	@Override
	public GrupoDAO getGrupoDAO() {
		return (GrupoDAO) AdaptadorGrupoTDS.getInstancia();
	}

	@Override
	public UsuarioDAO getUsuarioDAO() {
		return (UsuarioDAO) AdaptadorUsuarioTDS.getInstancia();
	}

	@Override
	public MensajeDAO getMensajeDAO() {
		return (MensajeDAO) AdaptadorMensajeTDS.getInstancia();
	}

	
}
