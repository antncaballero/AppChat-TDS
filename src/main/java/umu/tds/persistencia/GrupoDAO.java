package umu.tds.persistencia;

import java.util.List;

import umu.tds.dominio.Grupo;
/**
 * Interfaz que define las operaciones de persistencia relacionadas con los grupos
 */
public interface GrupoDAO {
	public void registrarGrupo(Grupo grupo);
	public void borrarGrupo(Grupo grupo);
	public void modificarGrupo(Grupo grupo);
	public Grupo recuperarGrupo(int codigo);
	public List<Grupo> recuperarTodosLosGrupos();
}
