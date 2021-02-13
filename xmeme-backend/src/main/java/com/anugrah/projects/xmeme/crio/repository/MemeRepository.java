package com.anugrah.projects.xmeme.crio.repository;

import com.anugrah.projects.xmeme.crio.entity.Meme;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemeRepository extends JpaRepository<Meme, Long> {

	/**
	 * @return list of meme - latest 100 memes from the DB
	 * @implSpec - find the latest 100 memes from the database
	 * this method uses JpaRepository to generate query for the needed operation
	 */
	List<Meme> findTop100ByOrderByIdDesc();

}
