package fr.bediss.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.bediss.entities.Produit;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
}
