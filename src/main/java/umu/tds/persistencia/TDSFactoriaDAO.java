package umu.tds.persistencia;

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
		return (UsuarioDAO) AdaptadorUsuarioTDS.getUnicaInstancia();
	}

	@Override
	public MensajeDAO getMensajeDAO() {
		return (MensajeDAO) AdaptadorMensajeTDS.getUnicaInstancia();
	}

	
}
