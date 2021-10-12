package fr.bediss.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.bediss.entities.Categorie;
import fr.bediss.entities.Produit;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
	
	List<Produit> findByNomProduit(String nom);
	List<Produit> findByNomProduitContains(String nom); 
	
	@Query("select p from Produit p where p.nomProduit like %?1 and p.prixProduit > ?2")
	List<Produit> findByNomPrix (String nom, Double prix);
	
//	ou Bien
//	@Query("select p from Produit p where p.nomProduit like %:nom and p.prixProduit > :prix")
//	List<Produit> findByNomPrix (@Param("nom") String nom,@Param("prix") Double prix);
	
	//tourne les produits ayant une catégorie donnée 
	@Query("select p from Produit p where p.categorie = ?1")
	List<Produit> findByCategorie (Categorie categorie);

	//retourne les produits ayant une catégorie donnée, en fournissant l’id de la catégorie
	List<Produit> findByCategorieIdCat(Long id);
	
	// trier
	List<Produit> findByOrderByNomProduitAsc();
	
	//Trier les produits selon leurs noms et leurs prix
	@Query("select p from Produit p order by p.nomProduit ASC, p.prixProduit DESC")
	List<Produit> trierProduitsNomsPrix ();

}
