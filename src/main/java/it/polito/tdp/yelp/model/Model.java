package it.polito.tdp.yelp.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	
	private Graph<User, DefaultWeightedEdge> grafo;
	private List<User> utenti;
	private int grado;
	
	//risultati del simulatore
	private int numeroGiorni;
	private List<Giornalista> giornalisti;
	
	public String creaGrafo(int minRevisioni, int anno) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		YelpDao dao = new YelpDao();
		this.utenti = dao.getUsersWithReviews(minRevisioni);
		Graphs.addAllVertices(this.grafo, this.utenti);
		
		for(User u1 : this.utenti) {
			for(User u2 : this.utenti) {
				if(!u1.equals(u2) && u1.getUserId().compareTo(u2.getUserId())<0) {
					int sim = dao.calcolaSimilarita(u1, u2, anno);
					if(sim>0) {
						Graphs.addEdge(this.grafo, u1, u2, sim);
					}
				}
			}
		}
		
		return "Grafo creato con "+this.grafo.vertexSet().size()+" vertici e "
				+this.grafo.edgeSet().size()+" archi\n";
	}
	
	public List<User> utentiPiuSimili(User u) {
		this.grado = 0;
		for(DefaultWeightedEdge e : this.grafo.edgesOf(u)) {
			if(this.grafo.getEdgeWeight(e)>grado) {
				grado = (int)this.grafo.getEdgeWeight(e);
			}
		}
		
		List<User> result = new ArrayList<>();
		for(DefaultWeightedEdge e : this.grafo.edgesOf(u)) {
			if((int)this.grafo.getEdgeWeight(e)==grado) {
				User u2 = Graphs.getOppositeVertex(this.grafo, e, u);
				result.add(u2);
			}
		}
		return result;
	}
	
//	public int getGrado() {
//		return this.grado;
//	}
	
	public List<User> getUsers() {
		return this.utenti;
	}
	
	public void simula(int intervistatori, int utenti) {
		Simulatore sim = new Simulatore(grafo);
		sim.init(intervistatori, utenti);
		sim.run();
		this.numeroGiorni = sim.getNumeroGiorni();
		this.giornalisti = sim.getGiornalisti();
	}
	
	public int getNumeroGiorni() {
		return this.numeroGiorni;
	}
	
	public List<Giornalista> getGiornalisti() {
		return this.giornalisti;
	}
	
}
