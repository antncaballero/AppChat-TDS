package umu.tds.persistencia;



public abstract class FactoriaDAO {

	private static FactoriaDAO unicaInstancia;

	public static final String DAO_TDS = "umu.tds.persistencia.TDSFactoriaDAO";

	
	public static FactoriaDAO getInstancia(String tipo) throws DAOException {
		if (unicaInstancia == null)
			try {
				unicaInstancia = (FactoriaDAO) Class.forName(tipo).getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				throw new DAOException(e.getMessage());
			}
		return unicaInstancia;
	}

	public static FactoriaDAO getInstancia() throws DAOException {
			return getInstancia(FactoriaDAO.DAO_TDS);	
	}

	/* Constructor */
	protected FactoriaDAO() {
	}


	public abstract ContactoIndividualDAO getContactoIndividualDAO();
	public abstract GrupoDAO getGrupoDAO();
	public abstract UsuarioDAO getUsuarioDAO();
	public abstract MensajeDAO getMensajeDAO();

}
