package br.com.grupo9.sistemadereservas.model.BO;

import br.com.grupo9.sistemadereservas.interfaces.DAO;
import br.com.grupo9.sistemadereservas.model.DAO.UsuarioDAO;
import br.com.grupo9.sistemadereservas.model.PO.UsuarioPO;

/**
 * Classe que conterá as regras de negócio e os metodos relacionados ao usuario
 * @author Marcos
 */

public class UsuarioBO {
	private UsuarioPO usuarioPO;
	private DAO<UsuarioPO> usuarioDAO;
	
	
	public UsuarioPO capturarUsuarioValido(){
		UsuarioPO usuarioCapturado = null;
		if(getUsuarioDAO().capturarPorId(getUsuarioPO()) != null){
			usuarioCapturado = getUsuarioDAO().capturarPorId(getUsuarioPO());
			if(!getUsuarioPO().getLogin().equals(usuarioCapturado.getLogin())){
				usuarioCapturado = null;
			}
		}
		return usuarioCapturado;
	}
	
	public boolean isUsuarioJaExiste(){
		return ((UsuarioDAO) getUsuarioDAO()).isUsuarioJaExiste(getUsuarioPO());
	}
	
	public UsuarioPO getUsuarioPO(){
		if(this.usuarioPO == null){
			this.usuarioPO = new UsuarioPO();
		}
		return this.usuarioPO;
	}
	
	public void setUsuarioPO(UsuarioPO usuarioPO) {
		this.usuarioPO = usuarioPO;
	}

	public DAO<UsuarioPO> getUsuarioDAO(){
		if(this.usuarioDAO == null){
			this.usuarioDAO = new UsuarioDAO();
		}
		return this.usuarioDAO;
	}
}
