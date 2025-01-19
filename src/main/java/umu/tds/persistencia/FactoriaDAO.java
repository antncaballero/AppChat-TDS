package umu.tds.persistencia;
/**
 *  Factoría abstracta para la creación de las distintas factorías DAO concretas
 */
public abstract class FactoriaDAO {

	private static FactoriaDAO unicaInstancia;
	public static final String DAO_TDS = "umu.tds.persistencia.TDSFactoriaDAO";

	/**
	 * Obtiene la instancia única de la factoría
	 * @param tipo
	 * @return la instancia única de la factoría
	 * @throws DAOException
	 */
	public static FactoriaDAO getInstancia(String tipo) throws DAOException {
		if (unicaInstancia == null)
			try {
				unicaInstancia = (FactoriaDAO) Class.forName(tipo).getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				throw new DAOException(e.getMessage());
			}
		return unicaInstancia;
	}
	/**
	 * Obtiene una instancia de la Factoría por defecto
	 * @return
	 * @throws DAOException
	 */
	public static FactoriaDAO getInstancia() throws DAOException {
			return getInstancia(FactoriaDAO.DAO_TDS);	
	}

	/**
	 * Constructor por defecto protegido para que sólo las factorías concretas puedan usarlo
     */
	protected FactoriaDAO() {
	}


	public abstract ContactoIndividualDAO getContactoIndividualDAO();
	public abstract GrupoDAO getGrupoDAO();
	public abstract UsuarioDAO getUsuarioDAO();
	public abstract MensajeDAO getMensajeDAO();

}
