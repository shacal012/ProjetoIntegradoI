package br.com.grupo9.sistemadereservas.model.DAO;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.grupo9.sistemadereservas.interfaces.DAO;
import br.com.grupo9.sistemadereservas.model.PO.CargoPO;
import br.com.grupo9.sistemadereservas.model.Util.PersistenceUtil;

public class CargoDAO implements DAO<CargoPO> {
	private EntityManager manager;

	@Override
	public boolean cadastrar(CargoPO entidade) {
		getManager().getTransaction().begin();
		try {
			getManager().persist(entidade);
			getManager().getTransaction().commit();
			return true;
		} catch (Exception e) {
			getManager().getTransaction().rollback();
			 e.printStackTrace();
			return false;
		}finally {
			fecharManager();
		}
	}

	@Override
	public CargoPO capturarPorId(CargoPO entidade) {
		try{
			StringBuilder query = new StringBuilder();
			query.append("SELECT u ")
				 .append("FROM cargo u ")
				 .append("WHERE u.chave = :chave");
			TypedQuery<CargoPO> typedQuery = getManager().createQuery(query.toString(),CargoPO.class);
				typedQuery.setParameter("chave", entidade.getChave());
				return (CargoPO) typedQuery.getSingleResult();
		}catch (Exception e) {
			System.out.println("\nOcorreu um erro ao capturar o cargo pela chave. Causa:\n");
			e.printStackTrace();
			return null;
		}finally {
			fecharManager();
		}
	}

	@Override
	public boolean atualizar(CargoPO entidade) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT u ")
			 .append("FROM cargo u")
			 .append("WHERE u.chave = :chave");
		TypedQuery<CargoPO> typedQuery = getManager().createQuery(query.toString(),CargoPO.class);
			typedQuery.setParameter("chave", entidade.getChave().intValue());
			CargoPO cargo = (CargoPO)typedQuery.getSingleResult();
			getManager().getTransaction().begin();
		try{
			if(cargo != null && cargo.getNome().equals(entidade.getNome())){
				getManager().merge(entidade);
			}
			getManager().getTransaction().commit();
			return true;
		}catch (Exception e) {
			getManager().getTransaction().rollback();
			System.out.println("\nOcorreu um erro ao tentar alterar o cargo. Causa:\n");
			e.printStackTrace();
			return false;
		}finally {
			fecharManager();
		}
	}

	@Override
	public List<CargoPO> listar(Integer pagina, Integer qtdRegistros) {
		try{
			StringBuilder query = new StringBuilder();
			query.append("SELECT u ")
				 .append("FROM cargo u ");
			TypedQuery<CargoPO> typedQuery = getManager().createQuery(query.toString(),CargoPO.class);
				return (List<CargoPO>) typedQuery.setFirstResult(pagina).setMaxResults(qtdRegistros).getResultList();
		}catch (Exception e) {
			System.out.println("\nOcorreu um erro ao tentar capturar todos os cargos. Causa:\n");
			e.printStackTrace();
			return null;
		}finally {
			fecharManager();
		}
	}

	@Override
	public boolean excluir(CargoPO entidade) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT u ")
			 .append("FROM cargo u ")
			 .append("WHERE u.chave = :chave");
		TypedQuery<CargoPO> typedQuery = getManager().createQuery(query.toString(),CargoPO.class);
			typedQuery.setParameter("chave", entidade.getChave().intValue());
			CargoPO cargo = (CargoPO)typedQuery.getSingleResult();
			getManager().getTransaction().begin();
		try{
			if(cargo != null && cargo.getChave().equals(entidade.getChave())){
				cargo.setDataExclusao(Calendar.getInstance());
				getManager().merge(cargo);
			}
			getManager().getTransaction().commit();
			return true;
		}catch (Exception e) {
			getManager().getTransaction().rollback();
			System.out.println("\nOcorreu um erro ao tentar excluir o cargo. Causa:\n");
			e.printStackTrace();
			return false;
		}		finally {
			fecharManager();
		}
	}

	@Override
	public void fecharManager() {
		if(this.manager.isOpen()){
			this.manager.close();
		}				
	}
	
	private EntityManager getManager(){
		if(this.manager == null){
			this.manager = PersistenceUtil.getEntityManager();
		}else if(!this.manager.isOpen()){
			this.manager = PersistenceUtil.getEntityManager();
		}
		return this.manager;
	}
}
