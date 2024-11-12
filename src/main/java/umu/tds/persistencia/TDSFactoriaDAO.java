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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MensajeDAO getCancionDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
