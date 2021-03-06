package br.com.grupo9.sistemadereservas.controle.WebServices.Services;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.grupo9.sistemadereservas.controle.Util.SecurityUtil;
import br.com.grupo9.sistemadereservas.model.BO.ClienteBO;
import br.com.grupo9.sistemadereservas.model.PO.ClientePO;
import br.com.grupo9.sistemadereservas.model.PO.UsuarioPO;

@RequestScoped
@Path("/clientews")
@Produces("application/json")
@Consumes("application/json")
public class ClienteWS {
	
	ClienteBO clienteBO;
	
	@POST
	@Path("/cadastrar/")
	public List<String> create(final UsuarioPO usuario) {
		List<String> retorno = new ArrayList<>();
		getClienteBO().setUsuarioPO(usuario);
		getClienteBO().getUsuarioPO().setSenha(SecurityUtil.getHash(getClienteBO().getUsuarioPO().getSenha()));		
		if(getClienteBO().cadastrar()){
			retorno.add("sucess");
		}else{
			retorno = getClienteBO().getMensagemErro();
		}
		return retorno;
	}

	@GET
	@Path("/capturar/{login}")
	public UsuarioPO findById(@PathParam("login") final String login) {
		getClienteBO().getUsuarioPO().setLogin(login);
		UsuarioPO usuario = getClienteBO().capturarUsuarioValido();
		if(usuario != null){
			return usuario;
		}else{
			return null;
		}
	}

	@GET
	public List<ClientePO> listAll(@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {
		//TODO: retrieve the clientepoes 
		final List<ClientePO> clientepoes = null;
		return clientepoes;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	public Response update(@PathParam("id") Long id, final ClientePO clientepo) {
		//TODO: process the given clientepo 
		return Response.noContent().build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") final Long id) {
		//TODO: process the clientepo matching by the given id 
		return Response.noContent().build();
	}
	
	@GET
	@Path("/verificaSeExiste/{login}")
	public List<String> isExisteLoginCadastrado(@PathParam("login") final String login){
		List<String> retorno = new ArrayList<>();
		getClienteBO().getUsuarioPO().setLogin("%"+login+"%");
		if(getClienteBO().isUsuarioJaExiste()){
			retorno.add("true");
		}else{
			retorno.add("false");
		}
		return retorno;
	}
	
	private ClienteBO getClienteBO(){
		if(this.clienteBO == null){
			this.clienteBO = new ClienteBO();
		}
		return this.clienteBO;
	}

}
